package comp.graphics; import java.awt.*; public class Ticker extends Panels implements Runnable {     public Thread th;     public int nrLines = 6;     public String line[] = new String[nrLines];     public Font f = new Font("Helvetica", 1, 12);     public int i=0, j=0, jj=1;         public Ticker() { super();           setFont(f);          line[0] = "Primul text  ";         line[1] = "Al doilea text  ";         line[2] = "Al treilea text  ";         line[3] = "Al patrulea text  ";         line[4] = "Al cincilea text  ";         line[5] = "Al saselea text  ";     }     public void start() {if(th == null) {i=-2; repaint(); th = new Thread(this); th.start();}}     public void stop() {if(th != null) {th.stop(); th = null;}}     public void run(){               try{Thread.sleep(1000);}              catch(InterruptedException e){}                     catch(Exception              e){}              while(th!=null){ for(i=0; i<line.length; i++){ jj=1; if(i==5) jj=60;                                           for(j=jj;              j<line[i].length();              j++){                                                                                                                              repaint();                                                                                                                              try{Thread.sleep(100);}              catch(InterruptedException e){}  }  if(i==1 || i==3 || i==5){ 
                                                                                                                try{Thread.sleep(3000);}              catch(InterruptedException e){}       }   } }     }     public void paint(Graphics g){super.paint(g); update(g);}     public void update(Graphics g){ g.setColor(Color.white); if((i & 1) == 0){ if(j==1)super.paint(g);  if(0<j && j<line[i].length()) g.drawString(line[i].substring(0, j), 7, 16); }else  if(0<j && j<line[i].length()) g.drawString(line[i].substring(0, j), 7, 30);     }     }
