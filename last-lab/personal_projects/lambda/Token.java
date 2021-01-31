package personal_projects.lambda;


public class Token {
    public enum Type {
        Eof,//only to be used inside the parser
        Int,        // duh
        Var,        // identifier like sqrt...
        LeftParen,  // (
        RightParen, // (
        Lambda,     // λ or \
        Dot,        // . (for λ a. a)
        Plus,       // +
        Minus,      // -
        Div,        // /
        Mul,        // *
        Mod,        // %
        Pow,        // ^
        Eq,         // ==
        Ne,         // !=
        Lt,         // <=
        Gt,         // >=
        Le,         // <
        Ge,         // >
        And,        // &&
        Or,         // ||
        Not,        // !
        Xor,        // ^^
        BAnd,       // &
        BOr,        // |
        Lsh,        // <<
        Rsh,        // >>
        Assign,     // =
        Question,   // ?
        Colon,      // :
    }
    public Type type;
    public String val;
    public Token() {this(Type.Eof, ""); }
    public Token(Type type, String val)
    {
        this.type = type;
        this.val = val;
    }

    @Override
    public String toString() {
        return "{type=" + type + "'" + val + "'}";
    }
}
