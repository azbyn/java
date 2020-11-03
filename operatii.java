//DRusu 2005

import java.util.regex.*;

public class operatii {
    static OpInfo[] operations = {
        new OpInfo("Cel mai mare num&#259;r &icirc;ntreg pozitiv de tip:",
                   "Cel mai mare num&#259;r &icirc;ntreg pozitiv de tip ${TYPE}",
                   "Cel mai mare &icirc;ntreg de tip ${TYPE} pozitiv: 2<SUP>${POW}</SUP> - 1",
                   "", 0, (i, j, sizeof) -> {
                       int bitCount = sizeof << 3;
                       return (1L<< (bitCount-1))-1;
                   }),

        new OpInfo("Cel mai mic num&#259;r &icirc;ntreg negativ de tip:",
                   "Cel mai mic num&#259;r &icirc;ntreg negativ de tip ${TYPE}",
                   "Cel mai mic &icirc;ntreg de tip ${TYPE} negativ: -2<SUP>${POW}</SUP>",
                   "", 0, (i, j, sizeof) -> {
                       int bitCount = sizeof << 3;
                       return (1L<< (bitCount-1));
                   }),

        new OpInfo("Num&#259;rul <FONT COLOR=\"red\">1</FONT>, num&#259;r de tip:",
                   "Num&#259;rul de tip ${TYPE} 1",
                   "Num&#259;rul &icirc;ntreg de tip ${TYPE} +1",
                   "", 0, (i,j,s)-> 1),

        new OpInfo("Num&#259;rul <FONT COLOR=\"red\">-1</FONT>, num&#259;r de tip:",
                   "Num&#259;rul de tip ${TYPE} -1",
                   "Num&#259;rul &icirc;ntreg de tip ${TYPE} -1",
                   "", 0, (i,j,s)-> -1),

        new OpInfo("Num&#259;rul <FONT COLOR=\"red\">i</FONT>, num&#259;r de tip:",
                   "Num&#259;rul de tip ${TYPE} i",
                   "Num&#259;rul &icirc;ntreg de tip ${TYPE} i",
                   "", 0, (i,j,s)-> i),

        new OpInfo("Num&#259;rul <FONT COLOR=\"red\">j</FONT>, num&#259;r de tip:",
                   "Num&#259;rul de tip ${TYPE} j",
                   "Num&#259;rul &icirc;ntreg de tip ${TYPE} j",
                   "", 0, (i,j,s)-> j),

        new OpInfo("Schimbare de semn. <FONT COLOR=\"red\">-i</FONT>, num&#259;r de tip:",
                   "Opera&#355;ia de schimbare de semn a unui num&#259;r &icirc;ntreg de tip ${TYPE}",
                   "-i", 1, (i,j,s)-> -i),

        new OpInfo("Complementariere. <FONT COLOR=\"red\">~i</FONT>, num&#259;r de tip:",
                   "Opera&#355;ia de complementariere a unui num&#259;r &icirc;ntreg de tip ${TYPE}",
                   "~i", 1, (i,j,s)-> ~i),

        new OpInfo("Incrementare cu 1. <FONT COLOR=\"red\">++i</FONT>, num&#259;r de &#355;ip:",
                   "Opera&#355;ia de incrementare cu 1 a unui num&#259;r &icirc;ntreg de tip ${TYPE}",
                   "++i", 1, (i,j,s)-> ++i),

        new OpInfo("Decrementare cu 1. <FONT COLOR=\"red\">- -i</FONT>, num&#259;r de tip:",
                   "Opera&#355;ia de decrementare cu 1 a unui num&#259;r &icirc;ntreg de tip ${TYPE}",
                   "--i", 1, (i,j,s)-> --i),

        new OpInfo("Adunare. <FONT COLOR=\"red\">i + j</FONT>, num&#259;r de tip:",
                   "Opera&#355;ia de adunare a dou&#259; numere &icirc;ntregi de tip ${TYPE}",
                   "i + j", 2, (i,j,s)-> i+j),

        new OpInfo("Sc&#259;dere. <FONT COLOR=\"red\">i - j</FONT>, num&#259;r de tip:",
                   "Opera&#355;ia de Sc&#259;dere a dou&#259; numere &icirc;ntregi de tip ${TYPE}",
                   "i - j", 2, (i,j,s)-> i-j),

        new OpInfo("&Icirc;nmul&#355;ire. <FONT COLOR=\"red\">i * j</FONT>, num&#259;r de tip:",
                   "Opera&#355;ia de &Icirc;nmul&#355;ire a dou&#259; numere &icirc;ntregi de tip ${TYPE}",
                   "i * j", 2, (i,j,s)-> i*j),

        new OpInfo("&Icirc;mp&#259;r&#355;ire. <FONT COLOR=\"red\">i / j</FONT>, num&#259;r de tip:",
                   "Opera&#355;ia de &Icirc;mp&#259;r&#355;ire a dou&#259; numere &icirc;ntregi de tip ${TYPE}",
                   "i / j", 2, (i,j,s)-> i/j),

        new OpInfo("&#350;I logic. <FONT COLOR=\"red\">i &amp; j</FONT>, num&#259;r de tip:",
                   "Opera&#355;ia &#350;I logic &icirc;ntre dou&#259; numere &icirc;ntregi de tip ${TYPE}",
                   "i & j", 2, (i,j,s)-> i & j),

        new OpInfo("SAU logic. <FONT COLOR=\"red\">i | j</FONT>, num&#259;r de tip:",
                   "Opera&#355;ia SAU logic &icirc;ntre dou&#259; numere &icirc;ntregi de tip ${TYPE}",
                   "i | j", 2, (i,j,s)-> i | j),

        new OpInfo("SAU EXCLUSIV. <FONT COLOR=\"red\">i ^ j</FONT>, num&#259;r de tip:",
                   "Opera&#355;ia SAU EXCLUSIV &icirc;ntre dou&#259; numere &icirc;ntregi de tip ${TYPE}",
                   "i ^ j", 2, (i,j,s)-> i ^ j),

        new OpInfo("Deplasare la st&acirc;nga. <FONT COLOR=\"red\">i &lt;&lt; j</FONT>, num&#259;r de tip:",
                   "Opera&#355;ia de deplasare la st&acirc;nga a unui num&#259;r &icirc;ntreg de tip ${TYPE}",
                   "i &lt;&lt; j", 2, (i,j,s)-> i << j),

        new OpInfo("Deplasare la dreapta. <FONT COLOR=\"red\">i &gt;&gt; j</FONT>, num&#259;r de tip:",
                   "Opera&#355;ia de deplasare la dreapta a unui num&#259;r &icirc;ntreg de tip ${TYPE}",
                   "i &gt;&gt; j", 2, (i,j,s)-> i >> j),

        new OpInfo("Deplasare la dreapta a complementarului. <FONT COLOR=\"red\">(~i) &gt;&gt; j</FONT>, num&#259;r de tip:",
                   "Opera&#355;ia de deplasare la dreapta a complementarului unui num&#259;r &icirc;ntreg de tip ${TYPE}",
                   "(~i) &gt;&gt; j", 2, (i,j,s)-> ~i >> j),

        new OpInfo("Deplasare la dreapta prin complementare la st&acirc;nga cu zerouri. <FONT COLOR=\"red\">i &gt;&gt;&gt; j</FONT>, num&#259;r de tip:",
                   "Opera&#355;ia de deplasare la dreapta prin complementare la st&acirc;nga cu zerouri a unui num&#259;r &icirc;ntreg de tip ${TYPE}",
                   "i &gt;&gt;&gt; j", 2, (i,j,s)-> i >>> j),

        new OpInfo("Deplasare la dreapta a complementarului prin complementare la st&acirc;nga cu zerouri. <FONT COLOR=\"red\">(~i) &gt;&gt;&gt; j</FONT>, num&#259;r de tip:",
                   "Opera&#355;ia de deplasare la dreapta a complementarului unui num&#259;r &icirc;ntreg de tip ${TYPE} prin complementare la st&acirc;nga cu zerouri",
                   "(~i) &gt;&gt;&gt; j", 2, (i,j,s)-> ~i >>> j),

        // new operations:
        new OpInfo("Setare bit de pe poziția j. <FONT COLOR=\"red\">i | (1 &lt;&lt; j)</FONT>, num&#259;r de tip:",
                   "Opera&#355;ia de setare a bitului de pe poziția j, numere de tip ${TYPE}",
                   "i | (1 &lt;&lt; j)", 2, (i,j,s) -> i | (1 << j)),

        new OpInfo("Distanța dintre i și j, d(i, j). <FONT COLOR=\"red\">i < j ? j-i : i-j</FONT>, num&#259;r de tip:",
                   "Distanța dintre i, numere de tip ${TYPE}",
                   "i < j ? j-i : i-j", 2, (i,j,s) -> i < j ? (j-i) : (i-j)),
    };
    public operatii() {}

    static void usage() {
        System.out.println("<HR>");
        System.out.println("<BR><BR>");
        System.out.println("Sintaxa liniei de comand&#259; este urm&#259;toarea: <FONT COLOR=\"red\">java  operatii  i  j  k  >  info.htm</FONT><BR>");
        System.out.println("unde i &#351;i j trebuie s&#259; fie dou&#259; numere &icirc;ntregi cuprinse &icirc;ntre -9223372036854775808 &#351;i 9223372036854775807 iar num&#259;rul k trebuie s&#259; fie 0 sau 1. Valoarea k = 0 va determina afi&#351;area opera&#355;iilor f&#259;r&#259; dep&#259;&#351;iri de domeniu iar k = 1 va determina afi&#351;area opera&#355;iilor cu dep&#259;&#351;iri de domeniu.");
        System.out.println("<BR><BR>");
        System.out.println("<HR>");
        System.out.println("</BODY>");
        System.out.println("</HTML>");
    }
    static long getLongOrComplain(String args[], int i, String paramNotGivenMsg, String notNumericMsg) {
        try {
            return Long.parseLong(args[i]);
        }
        catch(ArrayIndexOutOfBoundsException arrayindexoutofboundsexception)
        {
            antet1();
            System.out.println(paramNotGivenMsg);
            usage();
        }
        catch(NumberFormatException numberformatexception)
        {
            antet1();
            System.out.println(notNumericMsg);
            usage();
        }
        System.exit(-1);
        return 42;
    }
    public static void main(String args[])
    {
        OpInfo.castToSizeSanityCheck();

        long l = getLongOrComplain(args, 0,
                                   /*param not given*/ "<B>Nu a&#355;i introdus nici-un parametru!",
                                   /*not numeric*/ "<B>A&#355;i introdus gre&#351;it primul parametru ! Acesta trebuie s&#259; fie un num&#259;r &icirc;ntreg.");

        long l1 = getLongOrComplain(args, 1,
                                    /*param not given*/"<B>Nu a&#355;i introdus ultimii doi parametri !",
                                    /*not numeric*/"<B>A&#355;i introdus gre&#351;it al doilea parametru ! Acesta trebuie s&#259; fie un num&#259;r &icirc;ntreg.");
        long l2 = getLongOrComplain(args, 2,
                                    /*param not given*/"<B>Nu a&#355;i introdus ultimul parametru !",
                                    /*not numeric*/"<B>A&#355;i introdus gre&#351;it al treilea parametru ! Acesta trebuie s&#259; fie num&#259;rul 0 sau num&#259;rul 1.");
        if((l2 != 0L) & (l2 != 1L))
        {
            antet1();
            System.out.println("<B>A&#355;i introdus gre&#351;it al treilea parametru ! Acesta trebuie s&#259; fie num&#259;rul 0 sau num&#259;rul 1.");
            usage();
            return;
        }
        else
        {
            antet();
            System.out.println("<B><SPAN STYLE=\"Font-Size : 14pt\">A&#355;i introdus urm&#259;toarele numere &icirc;ntregi:  i = " + l + ",  j = " + l1 + ",  k = " + l2 + "</SPAN></B><SPAN STYLE=" + '"' + "Font-Size : 14pt" + '"' + "> </SPAN><BR>");
            System.out.println("<HR>");
            System.out.println("<BR><BR>");
            System.out.println("<B>Sintaxa liniei de comand&#259; este urm&#259;toarea: <FONT COLOR=\"red\">java  operatii  i  j  k  >  info.htm</FONT><BR>");
            System.out.println("unde i &#351;i j trebuie s&#259; fie dou&#259; numere &icirc;ntregi cuprinse &icirc;ntre -9223372036854775808 &#351;i 9223372036854775807 iar num&#259;rul k trebuie s&#259; fie 0 sau 1. Valoarea k = 0 va determina afi&#351;area opera&#355;iilor f&#259;r&#259; dep&#259;&#351;iri de domeniu iar k = 1 va determina afi&#351;area opera&#355;iilor cu dep&#259;&#351;iri de domeniu.");
            System.out.println("<BR><BR>");
            System.out.println("Testa&#355;i &#351;i alte valori.</B>");
            System.out.println("<BR><BR>");
            System.out.println("<HR>");
            System.out.println("<BR>");
            System.out.println("<A NAME=\"index\"></A>");
            tabel_index(domeniu(l, l1, l2));
            tabele(domeniu(l, l1, l2), l, l1);

            System.out.println("</BODY>");
            System.out.println("</HTML>");
            return;
        }
    }

    static int domeniu(long l, long l1, long l2)
    {
        byte byte0;
        if((Math.abs(l) < 127L) | (l == -128L))
        {
            if((Math.abs(l1) < 127L) | (l1 == -128L))
                byte0 = 1;
            else
            if((Math.abs(l1) < 32767L) | (l1 == -32768L))
                byte0 = 2;
            else
            if((Math.abs(l1) < 0x7fffffffL) | (l1 == 0xffffffff80000000L))
                byte0 = 3;
            else
                byte0 = 4;
        }
        else
        if((Math.abs(l) < 32767L) | (l == -32768L))
        {
            if((Math.abs(l1) < 32767L) | (l1 == -32768L))
                byte0 = 2;
            else
            if((Math.abs(l1) < 0x7fffffffL) | (l1 == 0xffffffff80000000L))
                byte0 = 3;
            else
                byte0 = 4;
        }
        else
        if((Math.abs(l) < 0x7fffffffL) | (l == 0xffffffff80000000L))
        {
            if((Math.abs(l1) < 0x7fffffffL) | (l1 == 0xffffffff80000000L))
                byte0 = 3;
            else
                byte0 = 4;
        }
        else
        {
            byte0 = 4;
        }
        if(l2 == 1L)
            byte0 = 1;

        return byte0;
    }

    static void antet()
    {
        System.out.println("<HTML>");
        System.out.println();
        System.out.println("<HEAD>");
        System.out.println();
        System.out.println("<SCRIPT language=\"javascript\">");
        System.out.println("var timerONE");
        System.out.println("var timerTWO");
        System.out.println("var defStatus = ' '");
        System.out.println("function ShowStatus(newstat){");
        System.out.println("clearTimeout(timerONE)");
        System.out.println("clearTimeout(timerTWO)");
        System.out.println("if (newstat != defStatus){");
        System.out.println("var cmd1 = 'ShowStatus(\"' + defStatus + '\")'");
        System.out.println("timerONE = window.setTimeout(cmd1,6000)}");
        System.out.println("scrllStatus(newstat,newstat.length)}");
        System.out.println("function scrllStatus(scrllStat,i){");
        System.out.println("if (i >= 0){");
        System.out.println("if (i >= 2) i = i - 2");
        System.out.println("else  --i");
        System.out.println("window.status = scrllStat.substring(i,scrllStat.length)");
        System.out.println("cmd2='scrllStatus(\"'+ scrllStat +'\",'+ i +')'");
        System.out.println("timerTWO = window.setTimeout(cmd2,10)}}");
        System.out.println("</SCRIPT>");
        System.out.println();
        System.out.println("<TITLE>Opera&#355;ii cu numere &icirc;ntregi.</TITLE>");
        System.out.println();
        System.out.println("</HEAD>");
        System.out.println();
        System.out.println("<BODY BACKGROUND=\"background.jpg\" TEXT=\"black\" LINK=\"#0033CC\" ALINK=\"red\" VLINK=\"#0033CC\" BGPROPERTIES=\"fixed\">");
        System.out.println();
        System.out.println("<HR>");
    }

    static void antet1()
    {
        System.out.println("<HTML>");
        System.out.println();
        System.out.println("<HEAD>");
        System.out.println();
        System.out.println("<TITLE>Eroare la introducerea parametrilor !</TITLE>");
        System.out.println("<BGSOUND SRC=\"error.wav\" LOOP=-1>");
        System.out.println();
        System.out.println("</HEAD>");
        System.out.println();
        System.out.println("<BODY BACKGROUND=\"background.jpg\" TEXT=\"black\" BGPROPERTIES=\"fixed\">");
        System.out.println();
    }

    static void tableFirstRow(int i) {
        final int firstWidth = 100 - (4-i+1)*12;
        System.out.println("<TR>");
        System.out.println("<TD WIDTH=\""+firstWidth+"%\">");
        System.out.println("<P ALIGN=\"CENTER\"><B>Tipul opera&#355;iei</B>");
        System.out.println("</TD>");
        if (i <= 1) {
            System.out.println("<TD WIDTH=\"12%\">");
            System.out.println("<P ALIGN=\"CENTER\"><B><A HREF=\"#8\" onMouseOver=\"ShowStatus('Opera&#355;ii cu numere &icirc;ntregi de tip byte.')\">(8 bi&#355;i)</A></B>");
            System.out.println("</TD>");
        }
        if (i <= 2) {
            System.out.println("<TD WIDTH=\"12%\">");
            System.out.println("<P ALIGN=\"CENTER\"><B><A HREF=\"#16\" onMouseOver=\"ShowStatus('Opera&#355;ii cu numere &icirc;ntregi de tip short.')\">(16 bi&#355;i)</A></B>");
            System.out.println("</TD>");
        }
        if (i <= 3) {
            System.out.println("<TD WIDTH=\"12%\">");
            System.out.println("<P ALIGN=\"CENTER\"><B><A HREF=\"#32\" onMouseOver=\"ShowStatus('Opera&#355;ii cu numere &icirc;ntregi de tip int.')\">(32 bi&#355;i)</A></B>");
            System.out.println("</TD>");
        }
        if (i <= 4) {
            System.out.println("<TD WIDTH=\"12%\">");
            System.out.println("<P ALIGN=\"CENTER\"><B><A HREF=\"#64\" onMouseOver=\"ShowStatus('Opera&#355;ii cu numere &icirc;ntregi de tip long.')\">(64 bi&#355;i)</A></B>");
            System.out.println("</TD>");
        }
        System.out.println("</TR>");
    }


    static void tabel_index(int i)
    {
        System.out.println();
        System.out.println();
        System.out.println("<TABLE BORDER=\"1\" WIDTH=\"100%\">");
        tableFirstRow(i);

        int index = 1;
        for (OpInfo oi : operations) {
            oi.printTableRow(i, index++);
        }

        System.out.println("</TABLE>");
        System.out.println("</P>");
        System.out.println("<P><BR>");

        System.out.println();
        System.out.println();
    }

    static void tabele(int i, long l, long l1)
    {
        System.out.println();
        System.out.println();
        if (i <= 1)
            tabel_all((byte)(int)l, (byte)(int)l1, 1);
        if (i <= 2)
            tabel_all((short)(int)l, (short)(int)l1, 2);
        if (i <= 3)
            tabel_all((int)l, (int)l1, 4);
        if (i <= 4)
            tabel_all(l, l1, 8);
        
        System.out.println();
        System.out.println();
    }

    static void tabel_all(long v0, long v1, int sizeof) {
        String type = OpInfo.sizeofToName(sizeof);

        long pow = (sizeof << 3)-1;
        long min = 1 << pow;
        long max = ~min;
        
        System.out.println();
        System.out.println();
        System.out.println("<TABLE BORDER=\"1\" WIDTH=\"100%\">");
        System.out.println("<TR>");
        System.out.println("<TD WIDTH=\"100%\">");
        System.out.println("<P ALIGN=\"CENTER\"><A NAME=\""+(sizeof<<3)+"\"></A><B><SPAN STYLE=\"Font-Size : 14pt\">Opera&#355;ii cu numere &icirc;ntregi de tip byte (reprezentare pe 8 de bi&#355;i):</SPAN></B>");
        System.out.println("</TD>");
        System.out.println("</TR>");

        int index = 1;

        for (OpInfo oi : operations) {
            oi.print(index++, v0, v1, sizeof);
        }
        
        System.out.println("</TABLE>");
        System.out.println("</P>");
        System.out.println("<P><BR>");
        System.out.println();
        System.out.println();
    }
}
interface Op {
    long call(long i, long j, int sizeof);
}
class OpInfo {
    static Pattern typePattern = Pattern.compile("${TYPE}", Pattern.LITERAL);
    static Pattern powPattern = Pattern.compile("${POW}", Pattern.LITERAL);
    
    String title;
    String indexDescriptionTemplate;
    String descriptionTemplate;
    String code;
    int arity; // as in unary, binary, ...
    Op op;
    
    public OpInfo(String title, String description, String code, int arity, Op op) {
        this.title = title;
        this.indexDescriptionTemplate = description +".";
        this.descriptionTemplate = description+": "+code;
        this.code = code;
        this.arity = arity;
        this.op = op;
    }
    public OpInfo(String title, String indexDescription, String description, String code, int arity, Op op) {
        this.title = title;
        this.indexDescriptionTemplate = indexDescription + ".";
        this.descriptionTemplate = description;
        this.code = code;
        this.arity = arity;
        this.op = op;
    }

    void printTableRow(int i, int idx) {
        final int firstWidth = 100 - (4-i+1)*12;
        System.out.println("<TR>");
        System.out.println("<TD WIDTH=\""+firstWidth+"%\"><B>"+title + "</B></TD>");

        Matcher m = typePattern.matcher(indexDescriptionTemplate);
        if (i <= 1) {
            System.out.println("<TD WIDTH=\"12%\">");
            System.out.println("<P ALIGN=\"CENTER\"><B><A HREF=\"#b"+idx+"\" onMouseOver=\"ShowStatus('"+m.replaceAll("byte")+"')\">byte</A></B>");
            System.out.println("</TD>");
        }
        if (i <= 2) {
            System.out.println("<TD WIDTH=\"12%\">");
            System.out.println("<P ALIGN=\"CENTER\"><B><A HREF=\"#s"+idx+"\" onMouseOver=\"ShowStatus('"+m.replaceAll("short")+"')\">short</A></B>");
            System.out.println("</TD>");
        }
        if (i <= 3) {
            System.out.println("<TD WIDTH=\"12%\">");
            System.out.println("<P ALIGN=\"CENTER\"><B><A HREF=\"#i"+idx+"\" onMouseOver=\"ShowStatus('"+m.replaceAll("int")+"')\">int</A></B>");
            System.out.println("</TD>");
        }
        if (i <= 4) {
            System.out.println("<TD WIDTH=\"12%\">");
            System.out.println("<P ALIGN=\"CENTER\"><B><A HREF=\"#l"+idx+"\" onMouseOver=\"ShowStatus('"+m.replaceAll("long")+"')\">long</A></B>");
            System.out.println("</TD>");
        }
        System.out.println("</TR>");
    }

    void print(int idx, long i, long j, int sizeof) {
        i = castToSize(i, sizeof);
        j = castToSize(j, sizeof);
        long res = this.op.call(i, j, sizeof);

        String s1 = typePattern.matcher(descriptionTemplate).replaceAll(sizeofToName(sizeof));
        String s2 = powPattern.matcher(s1).replaceAll(String.valueOf((sizeof << 3) -1));

        char c = sizeofToChar(sizeof);
        long v0 = castToSize(i, sizeof);
        long v1 = castToSize(j, sizeof);
        long v2 = castToSize(res, sizeof);
        
        System.out.println("<TR>");
        System.out.println("<TD WIDTH=\"100%\"><A NAME=" + c+idx + "></A><B><FONT COLOR=" + '"' + "red" + '"' + ">" + s2 + "</FONT></B><BR>");
        System.out.println("<BR>");
        System.out.println("<TABLE BORDER=\"0\" WIDTH=\"100%\">");
        System.out.println("<TR>");
        System.out.println("<TD WIDTH=\"15%\"><B>zecimal:</B></TD>");
        if (arity > 0) {
            System.out.println("<TD WIDTH=\"85%\">");
            System.out.println("<TABLE BORDER=\"0\" WIDTH=\"100%\">");
            System.out.println("<TR>");
            System.out.println("<TD WIDTH=\"15%\"><B>i:</B></TD>");
            System.out.println("<TD WIDTH=\"85%\">" + v0 + "</TD>");
            System.out.println("</TR>");
            if (arity >= 2) {
                System.out.println("<TR>");
                System.out.println("<TD WIDTH=\"15%\"><B>j:</B></TD>");
                System.out.println("<TD WIDTH=\"85%\">" + v1 + "</TD>");
                System.out.println("</TR>");
            }
            System.out.println("<TR>");
            System.out.println("<TD WIDTH=\"15%\"><B>" + code + ":</B></TD>");
        }
        System.out.println("<TD WIDTH=\"85%\">" + v2 + "</TD>");
        System.out.println("</TR>");
        if (arity > 0) {
            System.out.println("</TABLE>");
            System.out.println("</TD>");
            System.out.println("</TR>");
        }
        System.out.println("<TR>");
        System.out.println("<TD WIDTH=\"15%\"><B>hexazecimal:</B></TD>");
        if (arity > 0) {
            System.out.println("<TD WIDTH=\"85%\">");
            System.out.println("<TABLE BORDER=\"0\" WIDTH=\"100%\">");
            System.out.println("<TR>");
            System.out.println("<TD WIDTH=\"15%\"><B>i:</B></TD>");
            System.out.println("<TD WIDTH=\"85%\">" + toHex(v0, sizeof) + "</TD>");
            System.out.println("</TR>");
            if (arity >= 2) {
                System.out.println("<TR>");
                System.out.println("<TD WIDTH=\"15%\"><B>j:</B></TD>");
                System.out.println("<TD WIDTH=\"85%\">" + toHex(v1, sizeof) + "</TD>");
                System.out.println("</TR>");
            }
            System.out.println("<TR>");
            System.out.println("<TD WIDTH=\"15%\"><B>" + code + ":</B></TD>");
        }
        System.out.println("<TD WIDTH=\"85%\">" + toHex(v2, sizeof) + "</TD>");
        System.out.println("</TR>");
        if (arity > 0) {
            System.out.println("</TABLE>");
            System.out.println("</TD>");
            System.out.println("</TR>");
        }
        System.out.println("<TR>");
        System.out.println("<TD WIDTH=\"15%\"><B>binar:</B></TD>");
        if (arity > 0) {
            System.out.println("<TD WIDTH=\"85%\">");
            System.out.println("<TABLE BORDER=\"0\" WIDTH=\"100%\">");
            System.out.println("<TR>");
            System.out.println("<TD WIDTH=\"15%\"><B>i:</B></TD>");
            System.out.println("<TD WIDTH=\"85%\">" + toBinary(v0, sizeof)+"</TD>");
            System.out.println("</TR>");
            if (arity >= 2) {
                System.out.println("<TR>");
                System.out.println("<TD WIDTH=\"15%\"><B>j:</B></TD>");
                System.out.println("<TD WIDTH=\"85%\">" + toBinary(v1, sizeof)+"</TD>");
                System.out.println("</TR>");
            }
            System.out.println("<TR>");
            System.out.println("<TD WIDTH=\"15%\"><B>" + code + ":</B></TD>");
        }
        System.out.println("<TD WIDTH=\"85%\">" + toBinary(v2, sizeof)+"</TD>");
        if (arity > 0) {
            System.out.println("</TR>");
            System.out.println("</TABLE>");
            System.out.println("</TD>");
        }
        System.out.println("</TR>");
        System.out.println("</TABLE>");
        System.out.println("<BR>");
        System.out.println("<BR>");
        System.out.println("<B><A HREF=\"#index\" onMouseOver=\"ShowStatus('index')\">index</A></B></TD>");
        System.out.println("</TR>");
    }

    static String toHex(long i, int sizeof)
    {
        String s = new String(Long.toHexString(i));
        int maxLen = sizeof * 2;
        if(s.length() > maxLen)
            s = s.substring(s.length() - maxLen, s.length());
        return s;
    }
    static void assertEq(long a, long b) {
        if (a != b)
            System.err.println("Assertion failed: "+a + "!=" +b );
    }
    static void castToSizeSanityCheck() {
        assertEq(castToSize(0xc42, 1), 0x42);
        assertEq(castToSize(-0xc42, 1), -0x42);
        assertEq(castToSize(0xc42, 4), 0xc42);
        
        assertEq(castToSize(0x8000, 2), -0x8000);
    }

    static long castToSize(long i, int sizeof) {
        // doing this is overly complicated and doesn't even work for i=0x8000, sizeof=2
        /*int bitCount = sizeof << 3;
        if (bitCount >= 64) return i;
        
        return i < 0 || ((i & (1 << (bitCount-1))) != 0)
            ? (~((1<< bitCount)-1) | i)
            : (((1<< bitCount)-1) & i);*/
        //this is simpler:
        switch (sizeof) {
        case 1: return (byte) i;
        case 2: return (short) i;
        case 4: return (int) i;
        case 8: return i;
        default: throw new IllegalArgumentException("sizeof expected to be in {1,2,4,8}");
        }
    }

    static String toBinary(long j, int sizeof) {
        int bitCount = sizeof << 3;
        String res = "";
        for (int k = bitCount - 1; k >= 0; k--)
            res += ((1L << k & j) != 0) ? '1' : '0';
        return res;
    }
    static char sizeofToChar(int sizeof) {
        switch (sizeof) {
        case 1: return 'b';
        case 2: return 's';
        case 4: return 'i';
        case 8: return 'l';
        default: throw new IllegalArgumentException("sizeof expected to be in {1,2,4,8}");
        }
    }
    static String sizeofToName(int sizeof) {
        switch (sizeof) {
        case 1: return "byte";
        case 2: return "short";
        case 4: return "int";
        case 8: return "long";
        default: throw new IllegalArgumentException("sizeof expected to be in {1,2,4,8}");
        }
    }
}
