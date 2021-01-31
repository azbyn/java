package comp.graphics;
import java.awt.*;
public class Buttons extends Button {
  Image img;
  public Buttons(String s) { super(s); }
  public Buttons(String s, Image img) {
    this(s);
    this.img = img;
  }
  public void paint(Graphics g) { g.drawImage(img, 5, 5, this); }
}
