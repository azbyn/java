package personal_projects.lambda;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.stream.*;

class EvalException extends PosException {
    public EvalException(int pos, String msg) { super(pos, msg); }
}

interface CharPrinter {
    void print(char c);
}

public class Evaluator {
    Environment env = new Environment();

    static Evaluator instance = new Evaluator();

    public CharPrinter getPrinter() { return env.printer; }

    public void setPrinter(CharPrinter printer) { env.printer = printer; }

    static class ReplResult {
        boolean success;
        String niceInput;
        String resStr;
        public ReplResult(boolean success, String niceInput, String resStr) {
            this.success = success;
            this.resStr = resStr;
            this.niceInput = niceInput;
        }
    }
    //also catches stuff like DivideByZeroExceptions
    public ReplResult replEval(String str) {

        var tr = Tokenizer.instance.tryTokenize(str);
        if (!tr.success()) {
            return new ReplResult(false, str, "Lexer error: "+ tr.error().getMessage());
        }

        ArrayList<Token> toks = tr.result();


        if (toks.isEmpty()) {
            return new ReplResult(true, "", "");
        }

        var pr = Parser.instance.tryParse(toks);
        if (!pr.success()) {
            var niceInput = toks.stream().filter(x -> !x.val.isEmpty()).map(x-> x.val)
                    .collect(Collectors.joining(" "));
            return new ReplResult(false, niceInput, "Parsing error: "+ pr.error().toNiceString(toks));
        }
        var ast = pr.result();

        var niceInput = ast == null ? "" : ast.toString();
        String res;
        try {
            AstNode.EvalObject obj = eval(ast);
//            System.out.println("Input: "+niceInput+"\nout:"+obj);
            env.assign("ans", obj);
            res = obj.toString();
            return new ReplResult(true, niceInput, res);
        } catch (EvalException e) {
            // Console.WriteLine($"err: {e}");
            res = "Error: " + e.toNiceString(toks);
        }
        catch (Exception e) {
            System.err.println("err: "+ e);
//            e.printStackTrace();
            res = "Error: " + e.getMessage();
        }
        return new ReplResult(false, niceInput, res);
    }

    // returns true on success
    public Expected<AstNode.EvalObject, EvalException> tryEval(AstNode val) {
        try {
            return Expected.success(eval(val));
        } catch (EvalException e) {
            return Expected.error(e);
        }
    }

    // throws EvalException on failure
    public AstNode.EvalObject eval(AstNode n) throws EvalException {
//        System.out.println("eval:" + (n.getClass()));

//        System.out.println("eval:" + n);

        //the fancy new switch doesn't want to work here for some reason
        if (n instanceof AstNode.EvalObject) {
            return (AstNode.EvalObject) n;
        } else if (n instanceof AstNode.Binary) {
            var v = (AstNode.Binary) n;
            var a = enforceEvalInt(v.a);
            var b = enforceEvalInt(v.b);
            return new AstNode.Int(v.pos, v.fun.apply(a, b));
        } else if (n instanceof AstNode.Unary) {
            var v = (AstNode.Unary) n;
            var a = enforceEvalInt(v.a);
            return new AstNode.Int(v.pos, v.fun.apply(a));
        } else if (n instanceof AstNode.Variable) {
            var v = (AstNode.Variable) n;
            var val = env.tryFind(v.name);
            if (val == null)
                throw new EvalException(v.pos, "Variable '"+v.name+"' undefined");
            return val;
        } else if (n instanceof AstNode.FunCall) {
            var v = (AstNode.FunCall) n;
            var fun = eval(v.fun);

            if (fun instanceof AstNode.Int) {
                throw new EvalException(v.pos, "Int is not callable");
            } else if (fun instanceof AstNode.Builtin) {
                return ((AstNode.Builtin) fun).call(eval(v.arg));
            } else if (fun instanceof AstNode.Lambda) {
                return ((AstNode.Lambda) fun).call(eval(v.arg), this);
            }

            throw new IllegalStateException();
        } else if (n instanceof AstNode.Assign) {
            var v = (AstNode.Assign) n;
            var value = eval(v.val);
            return env.assign(v.name, value);
        } else if (n instanceof AstNode.If) {
            var v = (AstNode.If) n;
            var cond = eval(v.cond);
            return cond.getBoolValue() ? eval(v.t) : eval(v.f);
        } else if (n instanceof AstNode.Or) {
            var v = (AstNode.Or) n;
            var a = eval(v.a);
            return a.getBoolValue() ? a : eval(v.b);
        } else if (n instanceof AstNode.And) {
            var v = (AstNode.And) n;
            var a = eval(v.a);
            return a.getBoolValue() ? eval(v.b) : a;
        }
        throw new IllegalStateException();
    }

    BigInteger enforceEvalInt(AstNode n) throws EvalException { return enforceInt(n.pos, eval(n)); }

    public static BigInteger enforceInt(int pos, AstNode.EvalObject obj) throws EvalException {
        if (obj instanceof AstNode.EvalObject.Int)
            return ((AstNode.Int) obj).val;
        String name = obj.getClass().getName();
        if (name.startsWith("Eval"))
            name = name.substring(4);
        throw new EvalException(pos, "Expected Int, got "+name+":\n"+obj);
    }
}