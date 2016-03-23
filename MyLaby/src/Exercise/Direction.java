package Exercise;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class Direction
{
    public static void main(String[] args)
     {
        JFrame frame=new JFrame("Drection");  // ´´½¨Í¼ÎÄ¿ò
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(600, 600);
        JPanel panel = new DirectionPanel();        
        frame.add(panel);

        frame.setVisible(true);
     }
}