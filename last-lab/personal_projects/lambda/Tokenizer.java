package personal_projects.lambda;

import java.util.regex.*;
import java.util.ArrayList;

class TokenizerException extends Exception {
    public TokenizerException(String line, int pos, String msg) {
        super(msg + ":\n" + line + "\n" + PosException.nspaces(pos) + "^");
    }
}
public class Tokenizer {
    ArrayList<Token> result;
    String str;
    String orgStr;
    
    public static Tokenizer instance = new Tokenizer();

    private Tokenizer() {}

    boolean pat(String s, Token.Type tt) {
        if (str.startsWith(s))
        {
            result.add(new Token(tt, s));
            str = str.substring(s.length());
            return true;
        }
        return false;
    }

    boolean rpat(String reg, Token.Type tt) {
        return rpat(reg, 0, tt);
    }
    boolean rpat(String reg, int i, Token.Type tt) {
        Pattern pattern = Pattern.compile("^"+ reg);
        Matcher m = pattern.matcher(str);
        if (m.find()) {
            var s = m.group(i);//.Value;
            result.add(new Token(tt, s));
            str = str.substring(m.end());
            return true;
        }
        return false;
    }

    boolean rpat(String reg) {
        Pattern pattern = Pattern.compile("^"+ reg);
        Matcher m = pattern.matcher(str);
        if (m.find()) {
            str = str.substring(m.end());
            return true;
        }
        return false;
    }
    public Expected<ArrayList<Token>, TokenizerException> tryTokenize(String s) {
        try {
            return Expected.success(tokenize(s));
        } catch (TokenizerException e) {
            return Expected.error(e);
        }

    }
    public ArrayList<Token> tokenize(String s) throws TokenizerException {

        result = new ArrayList<>();
        str = String.join(" ", s.split("[\\n\\r]"));
        orgStr = str;
        while (str.length() > 0) {
            if (rpat("\\s+")) continue;
            if (rpat("#.*")) continue;
            if (pat(".", Token.Type.Dot)) continue;
            if (rpat("[0-9]+", Token.Type.Int)) continue;
            if (rpat("[_a-zA-Z][_a-zA-Z0-9]*", Token.Type.Var)) continue;
            if (pat("\\", Token.Type.Lambda)) continue;
            if (pat("λ",  Token.Type.Lambda)) continue;
            if (pat("(",  Token.Type.LeftParen)) continue;
            if (pat(")",  Token.Type.RightParen)) continue;
            if (pat("+",  Token.Type.Plus)) continue;
            if (pat("-",  Token.Type.Minus)) continue;
            if (pat("**", Token.Type.Pow)) continue;
            if (pat("^^", Token.Type.Xor)) continue;
            if (pat("^",  Token.Type.Pow)) continue;
            if (pat("*",  Token.Type.Mul)) continue;
            if (pat("/",  Token.Type.Div)) continue;
            if (pat("%",  Token.Type.Mod)) continue;
            if (pat("<<", Token.Type.Lsh)) continue;
            if (pat(">>", Token.Type.Rsh)) continue;
            if (pat("==", Token.Type.Eq)) continue;
            if (pat("!=", Token.Type.Ne)) continue;
            if (pat("<=", Token.Type.Le)) continue;
            if (pat(">=", Token.Type.Ge)) continue;
            if (pat("<" , Token.Type.Lt)) continue;
            if (pat(">" , Token.Type.Gt)) continue;
            if (pat("&&", Token.Type.And)) continue;
            if (pat("||", Token.Type.Or)) continue;
            if (pat("!",  Token.Type.Not)) continue;
            if (pat("&",  Token.Type.BAnd)) continue;
            if (pat("|",  Token.Type.BOr)) continue;
            if (pat("=",  Token.Type.Assign)) continue;
            if (pat("?",  Token.Type.Question)) continue;
            if (pat(":",  Token.Type.Colon)) continue;

            throw new TokenizerException(
                    orgStr, orgStr.length() - str.length(),
                    "Invalid char '"+str.charAt(0)+"'");
        }

        return result;
    }
}
