package personal_projects.lambda;

import java.io.Console;
import java.math.BigInteger;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;
import java.util.function.*;

public class Environment {
    interface Function_<R, A> {
        R apply(A a) throws EvalException;
    }

    static BigInteger Int(AstNode.EvalObject o) throws EvalException { return Evaluator.enforceInt(0, o); }
    static AstNode.EvalObject I(BigInteger v) { return new AstNode.Int(0, v); }

    static AstNode.EvalObject BOO(AstNode.Builtin.Func f, String name) {
        return new AstNode.Builtin(f, name);
    }
    static AstNode.EvalObject BII(Function_<BigInteger, BigInteger> f, String name) {
        return BOO(x -> I(f.apply(Int(x))), name);
    }
    static AstNode.EvalObject BIO(Function_<AstNode.EvalObject, BigInteger> f, String name) {
        return BOO((AstNode.EvalObject x) -> f.apply(Int(x)), name);
    }

    interface BiFunction_<R, A, B> {
        R apply(A a, B b) throws EvalException;
    }


    static AstNode.EvalObject BB(BiFunction_<BigInteger, BigInteger, BigInteger> f, String name) {
        return BOO((AstNode.EvalObject a) ->
                BOO((AstNode.EvalObject b) ->
                        I(f.apply(Int(a), Int(b))), name+ " $"+a),name);
    }

    public CharPrinter printer = System.out::print;

//    public CharPrinter Printer { get; set; } = c => Console.Write(c);
    /*static AstNode.EvalObject fromString(String s) {
        try {
            return Evaluator.instance.eval(Parser.instance.parse(Tokenizer.instance.tokenize(s)));
        } catch (TokenizerException | ParserException e) {
            System.err.println(e);
        }
    }*/

    HashMap<String, AstNode.EvalObject> values = new HashMap<String, AstNode.EvalObject>() {{
        put("sgn", BII(x -> BigInteger.valueOf(x.signum()), "sgn"));
        put("pow", BB((a, b) -> a.pow(b.intValue()), "pow"));
        put("ans", I(BigInteger.ZERO));
        put("debug", BOO((AstNode.EvalObject x) -> {
            for (var v : values.entrySet())
                System.out.println(v.getKey() +": "+v.getValue());
            return x;
        }, "debug"));
//        { "getc", B((EvalObject _) =>
//            I((int)Console.ReadKey().KeyChar), "getc") },
    }};

    public Environment() {
        values.put("putc", BIO((BigInteger i) -> {
            printer.print((char)i.intValue());
            return BOO((AstNode.EvalObject x) ->x, "id");
        }, "putc"));
    }

    //returns null if it doesn't find the variable
    public AstNode.EvalObject tryFind(String name) {
        return values.getOrDefault(name, null);
    }
    public AstNode.EvalObject assign(String name, AstNode.EvalObject val) {
        values.put(name, val);
        return val;
    }
}
