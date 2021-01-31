package personal_projects.lambda;

import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Collections;

public class BigIntHelper {
//    BigInteger val;
//    public BigInt(String s) { val = new BigInteger(s); }
//    public BigInt(int i) { val = new BigInteger(String.valueOf(i)); }
//
//    public int getInt() { return val.intValue(); }
//    public long getLong() { return val.longValue(); }

    public static boolean isZero(BigInteger a) { return equals(a, BigInteger.ZERO); }
    public static boolean equals(BigInteger a, BigInteger b) { return a.compareTo(b) == 0; }
    public static BigInteger fromBool(boolean b) { return b? BigInteger.ONE : BigInteger.ZERO; }

}
