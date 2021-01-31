package comp.windows; import java.awt.*; import exemple.Exemple; public class Window1 extends Frame{               Exemple              ex;              public Window1(Exemple ex){        this.ex = ex;        setTitle("Window1");        setBackground(new Color(0,0,0));        setFont(new Font("TimesRoman", Font.BOLD, 12));        setForeground(new Color(255,255,255));        setResizable(false);        setCursor(12);        setIconImage(ex.ico); 
       resize(400,400);        Toolkit tool=getToolkit();        Dimension res=tool.getScreenSize();        move((int)((res.width-400)/2+100),(int)((res.height-400)/2+100)); } public boolean handleEvent(Event e){        if(e.id==Event.WINDOW_DESTROY){  dispose();  return true;        }else return false; }     public void paint(Graphics g){         int ww = size().width, hh = size().height; for(int i = 0; i <= (int)(ww/176); i++) for(int j = 0; j <= (int)(hh/136); j++)  g.drawImage(ex.backg, i*176, j*136, this);         g.drawImage(ex.sunset,  (ww-ex.sunset.getWidth(this))/2, (hh-ex.sunset.getHeight(this))/2, this);     } }
