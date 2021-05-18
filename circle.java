import java.awt.*;
import java.awt.Color;
import java.awt.event.*;
import javax.swing.*;

public class Main extends JPanel {
   public void paintComponent(Graphics g) {
      System.out.println("start going loop");
      super.paintComponent(g);
      Polygon p = new Polygon();
      p.addPoint((int) (200), (int) (200));
      p.addPoint((int) (200), (int) (350));
      p.addPoint((int) (400), (int) (350));
      p.addPoint((int) (400), (int) (250));
      p.addPoint((int) (350), (int) (250));
      p.addPoint((int) (350), (int) (200));
      g.setColor(Color.green);
      g.fillPolygon(p);
      //p.setColor(Color.GRAY);
      //p.addPoint((int) (200 + 100 * Math.cos(Math.PI * 0)), (int) (200 + 100 * Math.sin(Math.PI * 0)));
      //p.addPoint((int) (200 + 100 * Math.cos(Math.PI * 1)), (int) (200 + 100 * Math.sin(Math.PI * 1)));
      //p.addPoint((int) (200 + 300 * Math.cos(Math.PI * 0)), (int) (250 + 300 * Math.sin(Math.PI * 0)));
      g.drawPolygon(p);
   }
   public static void main(String[] args) {
      JFrame frame = new JFrame();
      frame.setTitle("Polygon");
      frame.setSize(500, 500);
      frame.addWindowListener(new WindowAdapter() {
         public void windowClosing(WindowEvent e) {
            System.exit(0);
         }
      });
      Container contentPane = frame.getContentPane();
      contentPane.add(new Main());
      frame.setVisible(true);
   }
}