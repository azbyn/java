package personal_projects.lambda;


import java.util.ArrayList;
import java.util.Arrays;

public class PosException extends Exception {
    public int pos;
    public PosException(int pos, String msg) {
        super(msg);
        this.pos = pos;
    }

    public String toNiceString(ArrayList<Token> tokens) {
        String res = "";
        int spaceCount = 0;
        int i = 0;
        for (Token t : tokens) {
            String s = t.val;
            if (t.type == Token.Type.Var) s += ' ';
            res += s;
            if (i++ < pos)
                spaceCount += s.length();
        }

        return getMessage() + "\n"+ res + "\n"+ nspaces(spaceCount) +"^";
    }
    public static String nspaces(int n) {
        char[] repeat = new char[n];
        Arrays.fill(repeat, ' ');
        return new String(repeat);
    }
}