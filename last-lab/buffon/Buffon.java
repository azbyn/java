package buffon;

import java.awt.*;
import java.awt.event.WindowEvent;
import java.net.URL;

public class Buffon extends Frame {
    Toolkit tool;
    int ww, hh;
    public Image backg, info;
    public SimPanel simPanel;
    public InfoPanel infoPanel;
    TextField tf;
    Button start;
    Font f = new Font("TimesRoman", 1, 14);

//    public static void main(String args[]) {new Buffon();}

    public Buffon() {
        tool=getToolkit();
        Dimension res=tool.getScreenSize();
        ww=res.width;
        hh=res.height;
        setResizable(false);
        setTitle("estimarea numărului PI folosind problema lui Buffon");
        setIconImage(tool.getImage(GetResources("images/ico.gif")));
        setBackground(new Color(67, 134, 187));
        setLayout(null);
        loadImage();

        tf = new TextField("100");
        tf.setForeground(Color.blue);
        tf.setFont(f);
        tf.requestFocus();
        add(tf);
        tf.setBounds(275, 55, 80, 20);

        start = new Button("START");
        add(start);
        start.setBounds(470, 55, 80, 20);

        simPanel = new SimPanel(this) ;
        add(simPanel);
        simPanel.setBounds(25, 100, 600, 500);

        infoPanel = new InfoPanel(this);
        add(infoPanel);
        infoPanel.setBounds(25, 625, 600, 115);

        resize(ww, hh);
        move(0,0);
        setVisible(true);
    }

    public java.net.URL GetResources(String s) {
        return this.getClass().getResource(s);
    }

    public void loadImage() {
        try{
            MediaTracker mediatracker = new MediaTracker(this);
            backg=tool.getImage(GetResources("images/backg.jpg"));
            mediatracker.addImage(backg, 0);
            info=tool.getImage(GetResources("images/buffon.gif"));
            mediatracker.addImage(info, 0);
            mediatracker.waitForAll();
        }
        catch (Throwable t) {}
    }
    public void paint(Graphics g) {
        for(int i = 0; i <= (int)(ww/200); i++)
            for(int j = 0; j <= (int)(hh/200); j++)
                g.drawImage(backg, i*200, j*200, this);
        g.setColor(Color.white);
        g.setFont(f);
        g.drawString("NUMARUL DE ARUNCARI: ", 75, 70);
        g.drawImage(info, 655, 135, this);
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

    public boolean handleEvent(Event e) {
        if(e.id==Event.WINDOW_DESTROY){
            dispose();
            return false;
        }
        switch(e.id) {
        default: break;
        case 1001:
            if(e.target == start) {
                tf.requestFocus();
                simPanel.simulare(tf.getText());
            }
            break;
        }
        return super.handleEvent(e);
    }
}

class InfoPanel extends Panels {
    public int nraruncari;
    public int nrintersectii;
    public double PI;
    Font f;
    Color col1 = Color.white;
    Color col2 = Color.yellow;
    public InfoPanel(Buffon buffon) {
        super(buffon. backg);
        this.f = buffon.f;
    }
    public void paint(Graphics g) {
        super. paint(g);
        g.setFont(f);
        int w = 50;
        g.setColor(col1);
        g.drawString("NUMARUL DE ARUNCARI :", w, 25);
        g.drawString("" + nraruncari, w + 300, 25);
        g.drawString("NUMARUL DE CAZURI FAVORABILE :", w, 50);
        g.drawString("" + nrintersectii, w + 300, 50);
        g.drawString("ESTIMAREA LUI PI :", w, 75);
        g.drawString("" + PI, w + 300, 75);
        g.drawString("NUMARUL PI :", w, 100);
        g.setColor(col2);
        g.drawString("3.141592653589793238462643383", w + 300, 100);
    }
    public void setInfo(int nraruncari, int nrintersectii, double PI) {
        this.nraruncari = nraruncari;
        this.nrintersectii = nrintersectii;
        this. PI = PI;
        repaint();
    }
}

class SimPanel extends Panels {
    Buffon buffon;
    Color col1= Color.yellow;
    Point starts[];
    Point ends[];
    int nrAruncari;
    int nrCazuriFavorabile;
    double pi;
    public SimPanel(Buffon buffon) {
        super(buffon.backg);
        this. buffon = buffon;
    }
    public void paint(Graphics g) {
        super.paint(g);
        g.setColor(new Color(95,191,255));
        g.drawLine(50, 100, 550, 100);
        g.drawLine(50, 200, 550, 200);
        g.drawLine(50, 300, 550, 300);
        g.drawLine(50, 400, 550, 400);
        g.setColor(new Color(64,128,191));
        for(int i = 0; i<nrAruncari; i++) {
            g.setColor(new Color(127+(int)(Math.random()*127),
                                 127+(int)(Math.random()*127),
                                 127+(int)(Math.random()*127)));
            g.drawLine(starts[i].x, starts[i].y, ends[i].x, ends[i].y);
        }
        buffon.infoPanel.setInfo(nrAruncari, nrCazuriFavorabile, pi);
    }
    
    public void simulare(String nr){
        try {nrAruncari = Integer.parseInt(nr);}
        catch(NumberFormatException e){}
        starts = new Point[nrAruncari];
        ends = new Point[nrAruncari];
        buffon.infoPanel.setInfo(nrAruncari,0,0.0);
        simulare(nrAruncari);
    }
    void simulare (int nr) {
        nrCazuriFavorabile = 0;
        pi = 0.0;
        double x, x1 = 0.0;
        double y, y1 = 0.0;
        double theta = 0.0;
        for(int i = 0; i< nr; i++) {
            x = 100.0 + Math.random()*400D;
            y = 50.0 + Math.random()*400D;
            theta = Math.random() * Math.PI;
            x1 = 50*Math.cos(theta);
            y1 =50*Math.sin(theta);
            starts[i] = new Point((int)(x - x1), (int)(y - y1));
            ends[i] = new Point((int)(x + x1), (int)(y + y1));
            for(int j = 1; j <5; j++) {
                int l = j*100;
                if (l- 50 <=y && y <= l+ 50){
                    if(Math.abs(y - l) <= y1){
                        nrCazuriFavorabile++;
                        break;
                    }
                }
            }
        }
        if(nrCazuriFavorabile!=0)
            pi = (2.0 * (double)nrAruncari) / (double)nrCazuriFavorabile;
        else
            pi = 0.0;
        repaint();
    }
    
}

class Panels extends Panel {
    public Image im, im1;
    public Panels(Image im) {this.im = im;}
    public void update(Graphics g) { paint(g);}
    public void paint(Graphics g) {
        super.paint(g);
        Dimension dimension = size();
        im1 = createImage(dimension. width, dimension. height);
        pan(im1.getGraphics());
        g.drawImage(im1, 0, 0, this);
    }
    public void pan(Graphics g) {
        Dimension dimension = size();
        int w = dimension. width;
        int h = dimension. height;
        Color color = getBackground();
        g.setColor(color);
        g.fillRect(0, 0, w, h);
        for(int k = 0; k < w; k += im.getWidth(this))
            for(int l = 0; l< h; l += im.getHeight(this))
                g.drawImage(im, k, l, this);
        g.setColor(color.brighter());
        g.drawRect(1, 1, w - 2, h - 2);
        g.setColor(color.darker());
        g.drawRect(0, 0, w - 2, h - 2);
    }
}
