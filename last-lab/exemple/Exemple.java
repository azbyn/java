package exemple;

import comp.graphics.*;
import comp.windows.*;
import java.awt.*;
import java.net.URL;
import buffon.*;
import personal_projects.lambda.*;

// todo add stuff from the lab

public class Exemple extends Frame {
    public String text = "";
    public Image backg, sunset, ico, newIm, openIm, saveIm, helpIm;
    Toolkit tool;
    int ww, hh, prevw;
    ToolBar tb;
    StatusBar sb;
    Ticker tk;
    boolean tk1;
    Font f = new Font("Helvetica", Font.BOLD + Font.ITALIC, 20);
    FontMetrics fm = getFontMetrics(f);
    public static void main(String args[]) { new Exemple("Exemple"); }
    public Exemple() {
        tool = getToolkit();
        Dimension res = tool.getScreenSize();
        ww = res.width;
        hh = res.height;
        prevw = ww;
        setBackground(new Color(38, 104, 165));
        setFont(f);
        setForeground(new Color(255, 255, 0));
        setResizable(false);
        loadImages();
        setIconImage(ico);
        adaugaMenuBar();
        setLayout(null);
        tb = new ToolBar(this, 400);
        add(tb);
        tb.setBounds(5, 52, 390, 33);
        tb.Save.disable();
        sb = new StatusBar(396, 33);
        add(sb);
        sb.setBounds(5, 361, 396, 33);
        tk = new Ticker();
        add(tk);
        tk.setBounds(50, 220, 300, 50);
        tk.setVisible(false);
        reshape((int)((res.width - 400) / 2), (int)((res.height - 400) / 2), 400,
                400);
        show();
    }
    public Exemple(String title) {
        this();
        setTitle(title);
    }
    public boolean handleEvent(Event e) {
        if (e.id == Event.WINDOW_DESTROY)
            System.exit(0);
        else if (e.id == Event.ACTION_EVENT && e.target instanceof MenuItem) {
            if ("Exit".equals(e.arg))
                System.exit(0);
            else if ("Open".equals(e.arg)) {
                FileDialog fd = new FileDialog(this, "Open");
                fd.show();
                if (fd.getFile() != null) {
                    text = "Ati selectat fisierul " + fd.getFile() + ".";
                    repaint();
                }
                return true;
            } else if ("Save".equals(e.arg)) {
                FileDialog fd = new FileDialog(this, "Save");
                fd.setMode(1);
                fd.show();
                if (fd.getFile() != null) {
                    text = "Ati salvat cu numele " + fd.getFile() + ".";
                    repaint();
                }
                return true;
            } else if ("Ticker".equals(e.arg)) {
                text = "";
                repaint();
                if (!tk1) {
                    tk.setVisible(true);
                    tk.start();
                } else {
                    tk.setVisible(false);
                    tk.stop();
                }
                tk1 = !tk1;
                return true;
            } else if ("New Window".equals(e.arg)) {
                Window1 w = new Window1(this);
                w.show();
                return true;
            } else if ("Edit".equals(e.arg)) {
                Edit edit = new Edit(this);
                edit.show();
                return true;
            } else if ("Calculator".equals(e.arg)) {
                new calc(/*this*/).show();
            } else if ("Lambda".equals(e.arg)) {
                new Lambda(/*this*/).show();
            } else if ("Buffon".equals(e.arg)) {
                new Buffon().show();
            } else {
                text = "Ati selectat meniul " + e.arg.toString() + ".";
                repaint();
                return true;
            }
        } else if (e.id == 1001) {
            if (e.target == tb.Newf) {
                sb.setText("New File", "", "");
                return true;
            }
            if (e.target == tb.Open) {
                sb.setText("", "Open File", "");
                return true;
            }
            if (e.target == tb.Help) {
                sb.setText("", "", "");
                return true;
            }
        } else
            return false;
        return false;
    }
    public void paint(Graphics g) {
        g.drawString(text, (size().width - fm.stringWidth(text)) / 2, 180);
    }
    public void adaugaMenuBar() {
        MenuBar men = new MenuBar();
        Menu file = new Menu("File");

        Menu exemple = new Menu("Exemple");
        Menu about = new Menu("About");
        file.add("New");
        file.add("Open");
        file.add("Save");
        file.add("Save              As..");
        file.add("-");
        file.add("Calculator");
        file.add("Buffon");
        file.add("-");

        Menu personalProjects = new Menu("Personal Projects");
        personalProjects.add("Lambda");
        file.add(personalProjects);


        file.add("-");
        file.add("Exit");
        exemple.add("Ticker");
        exemple.add("Edit");
        exemple.add("Label");
        exemple.add("New              Window");
        men.add(file);
        men.add(exemple);
        men.add(about);
        setMenuBar(men);
    }
    public URL GetResources(String s) { return this.getClass().getResource(s); }

    public void loadImages() {
        try {
            MediaTracker mt = new MediaTracker(this);
            backg = tool.getImage(GetResources("../comp/images/backg.jpg"));
            sunset = tool.getImage(GetResources("../comp/images/sunset.jpg"));
            ico = tool.getImage(GetResources("../comp/images/ico.gif"));
            newIm = tool.getImage(GetResources("../comp/images/newIm.gif"));
            openIm = tool.getImage(GetResources("../comp/images/openIm.gif"));
            saveIm = tool.getImage(GetResources("../comp/images/saveIm.gif"));
            helpIm = tool.getImage(GetResources("../comp/images/helpIm.gif"));
            mt.addImage(backg, 0);
            mt.addImage(sunset, 1);
            mt.addImage(ico, 2);
            mt.addImage(newIm, 3);
            mt.addImage(newIm, 4);
            mt.addImage(newIm, 5);
            mt.addImage(newIm, 6);
            mt.waitForAll();
        } catch (Throwable e) {
            System.out.println("Eroare la incarcarea imaginilor!");
        }
    }
}
