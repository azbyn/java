package personal_projects.lambda;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.function.*;

class ParserException extends PosException {
    public ParserException(int pos, String msg) { super(pos, msg); }
}

public class Parser {
    ArrayList<Token> tokens = null;
    int pos = 0;

    static Parser instance = new Parser();

    private Parser() {}
    ParserException expected(Token.Type tt) { return expected(tt.toString()); }
    ParserException expected(String tt) {
        return new ParserException(pos, "Expected "+tt+", found "+ type());
    }
    boolean isEof() { return pos >= tokens.size(); }
    Token.Type type() {
        if (pos >= tokens.size())
            return Token.Type.Eof;
        return tokens.get(pos).type;
    }

    boolean is(Token.Type tt) { return type() == tt; }
    boolean is(Token.Type... types) {
        Token.Type tt = type();
        for (Token.Type t0: types)
            if (t0 == tt) return true;
        return false;
    }

    Token consume(Token.Type... tt) {
        boolean res = is(tt);
        if (res)
            return tokens.get(pos++);
        return null;
    }
    Token require(Token.Type tt) throws ParserException {
        Token val = consume(tt);
        if (val == null)
            throw expected(tt);
        return val;
    }
    static AstNode makeBinary(int pos, Token t, AstNode x, AstNode y) {
        BiFunction<BigInteger, BigInteger, BigInteger> fun = switch (t.type) {
            case Plus  -> BigInteger::add;
            case Minus -> BigInteger::subtract;
            case Mul   -> BigInteger::multiply;
            case Div   -> BigInteger::divide;
            case Mod   -> BigInteger::mod;
            case BAnd  -> BigInteger::and;
            case Xor   -> BigInteger::xor;
            case BOr   -> BigInteger::or;
            case Lsh   -> (a, b) -> a.shiftLeft(b.intValue());
            case Rsh   -> (a, b) -> a.shiftRight(b.intValue());
            case Pow   -> (a, b) -> a.pow(b.intValue());
            case Eq    -> (a, b) -> BigIntHelper.fromBool(a.compareTo(b) == 0);
            case Ne    -> (a, b) -> BigIntHelper.fromBool(a.compareTo(b) != 0);
            case Le    -> (a, b) -> BigIntHelper.fromBool(a.compareTo(b) <= 0);
            case Ge    -> (a, b) -> BigIntHelper.fromBool(a.compareTo(b) >= 0);
            case Lt    -> (a, b) -> BigIntHelper.fromBool(a.compareTo(b) < 0);
            case Gt    -> (a, b) -> BigIntHelper.fromBool(a.compareTo(b) > 0);

            // we don't throw ParserException since it's a bug
            // in the code if we reach this point, not a problem
            // with our input
            default -> throw new IllegalStateException();
        };
        return new AstNode.Binary(pos, t.val, fun, x, y);
    }
    static AstNode makeUnary(int pos, Token t, AstNode x) {
        Function<BigInteger, BigInteger> fun = switch(t.type) {
            case Plus  -> a -> a;
            case Minus -> BigInteger::negate;
            case Not   -> a -> BigIntHelper.fromBool(BigIntHelper.isZero(a));
            default    -> throw new IllegalStateException();
            };
        return new AstNode.Unary(pos, t.val, fun, x);
    }

    interface NextSupplier {
        AstNode get() throws ParserException;
    }

    AstNode leftRecursiveImpl(NextSupplier next, Token.Type... args) throws ParserException {
        var a = next.get();
        for (;;) {
            int p = pos;
            var t = consume(args);
            if (t != null)
                a = makeBinary(p, t, a, next.get());
            else
                return a;
        }
    }
    //-----The recursive descent parser bit-----

    // returns true on success

    public Expected<AstNode, ParserException> tryParse(ArrayList<Token> tokens) {
        try {
            return Expected.success(parse(tokens));
        } catch (ParserException e) {
            return Expected.error(e);
        }
    }

    // throws ParserException on failure
    // may return null if there are no tokens
    public AstNode parse(ArrayList<Token> tokens) throws ParserException {
        if (tokens.size() == 0) return null;
        this.tokens = tokens;
        pos = 0;
        return main();
    }
    // main: expression
    AstNode main() throws ParserException { return expression(); }

    // expression: assign EOF
    AstNode expression() throws ParserException { return assign(); }

    // assign: var "=" assign
    //       | funCall
    AstNode assign() throws ParserException {
        var a = funCall();
        int p = pos;
        if (consume(Token.Type.Assign) != null) {
            if (!(a instanceof AstNode.Variable))
                throw new ParserException(pos, "Variable expected");
            var variable = (AstNode.Variable) a;

            return new AstNode.Assign(p, variable.name, assign());
        }
        return a;
    }


    // funCall: [funCall] ternary
    AstNode funCall() throws ParserException {
        var a = ternary();
        for (;;) {
            if (isEof() || is(Token.Type.RightParen) || is(Token.Type.Question) ||
                is(Token.Type.Colon) || is(Token.Type.Assign)) return a;
            int p = pos;
            a = new AstNode.FunCall(p, a, ternary());
        }
    }

    // ternary: prefix [ "?" expression ":" ternary ]
    AstNode ternary() throws ParserException {
        var cond = prefix();
        int p = pos;

        if (consume(Token.Type.Question) == null)
            return cond;
        var a = expression();
        require(Token.Type.Colon);

        return new AstNode.If(p, cond, a, ternary());
    }

    // prefix: ["+" | "-" | "!"] booleanOp
    AstNode prefix() throws ParserException {
        int p = pos;
        Token t = consume(Token.Type.Plus, Token.Type.Minus, Token.Type.Not);
        if (t != null)
            return makeUnary(p, t, boolOp());
        return boolOp();
    }

    // booleanOp: [booleanOp ("&&" | "||")] equals
    AstNode boolOp() throws ParserException {
        var a = equals();
        for (;;) {
            int p = pos;

            if (consume(Token.Type.And) != null)
                a = new AstNode.And(p, a, equals());
            else if (consume(Token.Type.Or) != null)
                a = new AstNode.Or(p, a, equals());
            else
                return a;
        }
    }
    // equals: [equals ("==" | "!=")] compare
    AstNode equals() throws ParserException {
        return leftRecursiveImpl(this::compare, Token.Type.Eq, Token.Type.Ne);
    }

    // compare: bOr [("<"|">"|"<="|">=") bOr]
    AstNode compare() throws ParserException {
        return leftRecursiveImpl(this::bOr, Token.Type.Lt, Token.Type.Gt, Token.Type.Le, Token.Type.Ge);
    }

    // bOr: [bOr ("|" | "^^")] bAnd
    AstNode bOr() throws ParserException {
        return leftRecursiveImpl(this::bAnd, Token.Type.BOr, Token.Type.Xor);
    }

    // bAnd: [bAnd "&"] bShift
    AstNode bAnd() throws ParserException {
        return leftRecursiveImpl(this::bShift, Token.Type.BAnd);
    }

    // bShift: [bShift ("<<" | ">>")] add
    AstNode bShift() throws ParserException {
        return leftRecursiveImpl(this::add, Token.Type.Lsh, Token.Type.Rsh);
    }

    // add: [add ("+" | "-")] multi
    AstNode add() throws ParserException {
        return leftRecursiveImpl(this::multi, Token.Type.Plus, Token.Type.Minus);
    }

    // multi: [multi ("*" | "/" | "%")] power
    AstNode multi() throws ParserException {
        return leftRecursiveImpl(this::power, Token.Type.Mul, Token.Type.Div, Token.Type.Mod);
    }

    // power: lambda ["^" power]
    AstNode power() throws ParserException {
        var a = lambda();
        int p = pos;

        Token t = consume(Token.Type.Pow);
        if (t != null)
            return makeBinary(p, t, a, power());
        return a;
    }

    // lambda: "λ" Var "." expression
    //       | term
    AstNode lambda() throws ParserException {
        int p = pos;
        if (consume(Token.Type.Lambda) == null) {
            return term();
        }
        Token t = require(Token.Type.Var);
        require(Token.Type.Dot);
        return new AstNode.Lambda(p, t.val, expression());
    }
    // term: "(" expression ")"
    //     | Int | Var
    AstNode term() throws ParserException {
        int p = pos;
        if (consume(Token.Type.LeftParen) != null) {
            var e = expression();
            require(Token.Type.RightParen);
            return e;
        }
        Token t = consume(Token.Type.Int);
        if (t != null)
            return new AstNode.Int(p, t.val);

        t = consume(Token.Type.Var);
        if (t != null)
            return new AstNode.Variable(p, t.val);

        throw expected("term");
    }
}

