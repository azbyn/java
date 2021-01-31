package comp.graphics;

import exemple.Exemple;
import java.awt.*;

public class ToolBar extends Panels {
    public Buttons Newf;
    public Buttons Open;
    public Buttons Save;
    public Buttons Help;
    public Exemple ex;

    public ToolBar(Exemple ex, int w) {
        super();
        this.ex = ex;
        load(w);
    }

    private void load(int w) {
        setLayout(new BorderLayout(10, 10));
        Panels pan = new Panels();
        Newf = new Buttons("", ex.newIm);
        add(Newf);
        Newf.setBounds(5, 5, 25, 25);
        Open = new Buttons("", ex.openIm);
        add(Open);
        Open.setBounds(30, 5, 25, 25);
        Save = new Buttons("", ex.saveIm);
        add(Save);
        Save.setBounds(55, 5, 25, 25);
        Help = new Buttons("", ex.helpIm);
        add(Help);
        Help.setBounds(w - 40, 5, 25, 25);
        add("Center", pan);
    }

    public void resize(int w) {
        load(w);
    }

    public boolean handleEvent(Event e) {
        if (e.id == 504)
            setCursor(new Cursor(0));
        return super.handleEvent(e);
    }
}
