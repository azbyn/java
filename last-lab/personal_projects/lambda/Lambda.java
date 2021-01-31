package personal_projects.lambda;


import javax.swing.*;
import javax.swing.text.*;
import java.awt.*;
import java.awt.event.*;
import java.io.*;

public class Lambda extends Frame {
    public static void main(String args[]) { new Lambda(); }

    TextField input;
    JTextPane output;
    Label label;

    JScrollPane scroll;


    void redoLayout() {

        int padding = 10;
        int h = getHeight()-padding;
        int w = getWidth()-padding;

        int bottomHeight = 40;
        int hh = h-bottomHeight;
        int labelWidth = 60;

        scroll.setBounds(padding, padding, w-padding, hh-padding);
//        hh+= padding;

        label.setBounds(padding, hh, labelWidth, bottomHeight);

        input.setBounds(labelWidth, hh, w- labelWidth, bottomHeight);
    }

    public Lambda() {
        Font f = new Font("Consolas", Font.PLAIN, 20);

        setTitle("Lambda");
        setVisible(true);

        setMinimumSize(new Dimension(600, 600));


        setLayout(null);
        output = new JTextPane();//, TextArea.SCROLLBARS_VERTICAL_ONLY);

        output.setText("");
        output.setFont(f);
        output.setEditable(false);

        scroll = new JScrollPane(output);
        add(scroll);

        input = new TextField("");
        input.setFont(f);

        add(input);

        label = new Label(">>>");
        label.setFont(f);
        add(label);

        addComponentListener(new ComponentAdapter() {
            public void componentResized(ComponentEvent e) {
                redoLayout();
            }
        });
        redoLayout();
        input.requestFocus();
//        input.addActionListener(e -> {
//            eval(input.getText());
//            input.setText("");
//        });
        input.addKeyListener(new KeyListener() {
            @Override
            public void keyTyped(KeyEvent e) { }

            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case KeyEvent.VK_ENTER:
                        eval(input.getText());
                        input.setText("");
                        break;
                    case KeyEvent.VK_UP:
                        if (input.getText().isEmpty())
                            input.setText(last);
                        break;
                    case KeyEvent.VK_DOWN:
                        if (!input.getText().isEmpty() ) {
                            last = input.getText();
                            input.setText("");
                        }
                        break;
                }
            }

            @Override
            public void keyReleased(KeyEvent e) { }
        });


        Evaluator.instance.setPrinter(c -> {
            System.out.write(c);
            append(c);
        });
        doAutoRun("autorun.txt");

        output.setCaretPosition(output.getDocument().getLength());
    }


    @Override
    protected void processWindowEvent(WindowEvent e) {
        switch (e.getID()) {
            case WindowEvent.WINDOW_CLOSING:
            case WindowEvent.WINDOW_CLOSED:
                dispose();
                break;
        }
        super.processWindowEvent(e);
    }

    private void appendToPane(JTextPane tp, String msg, Color c) {
        StyleContext sc = StyleContext.getDefaultStyleContext();
        AttributeSet aset = null;
        if (c != null)
            aset = sc.addAttribute(SimpleAttributeSet.EMPTY, StyleConstants.Foreground, c);

        StyledDocument document = (StyledDocument) tp.getDocument();
        try {
            document.insertString(document.getLength(), msg, aset);
            tp.setCaretPosition(document.getLength());
        } catch (BadLocationException e) {
            e.printStackTrace();
        }
//        var s = scroll.getHorizontalScrollBar();
//        s.setValue(s.getMaximum());
    }


    void appendError(String msg) {
        appendToPane(output, msg+"\n", Color.red);
    }
    void appendText(String msg) {
        appendToPane(output, msg+"\n", null);
    }
    void append(char msg) {
        appendToPane(output, String.valueOf(msg), null);
    }

    String last = "";
    void eval(String str) {
        last = str;
//        String res;
        appendText(">>> "+str);
        var res = Evaluator.instance.replEval(str);
        if (!res.success)
            appendError(res.resStr);
        else
            appendText(res.resStr);
    }

    void doAutoRun(String path) {
        try {
            String content = "";
            BufferedReader reader = new BufferedReader(new FileReader(path));
            String ln;
            while ((ln = reader.readLine()) != null) {
                ln = ln.trim();
                if (ln.isEmpty()) continue;
                if (ln.charAt(0) == '#') continue;
                var last = ln.charAt(ln.length()-1);
                if (last != '.' && last != '(') {
                    eval(content+ln);
                    content = "";
                }
                else
                    content += ln;
            }
            eval(content);
        } catch (Exception e) {
            appendError("Error "+e.getMessage());
        }

    }

}
