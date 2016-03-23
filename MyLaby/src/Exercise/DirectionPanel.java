package Exercise;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DirectionPanel extends JPanel
{
 private final int WIDTH=300,HEIGHT=200;  //����С
 private final int JUMP=10;  //ͼƬ�ƶ��ߴ�

 private ImageIcon up,down,left,right,currentImage;
 private int x,y;

 public DirectionPanel()
{
     addKeyListener (new DirectionListener());  //ע�������

     x=WIDTH/2;
     y=HEIGHT/2;

     up=new ImageIcon("src/Image/icon.jpg");  //����ָ���ļ�����ͼƬ����     
     down=new ImageIcon("src/Image/icon.jpg");
     left=new ImageIcon("src/Image/icon.jpg");
     right=new ImageIcon("src/Image/icon.jpg");



    setBackground(Color.yellow);  // ��屳��ɫ
    setPreferredSize (new Dimension(WIDTH,HEIGHT)); 
          // ��������С
    setFocusable(true);   // ���ɻ�ý���
}

public void paintComponent(Graphics g)
{
  super.paintComponent(g);  // ���ø��๹�췽�������Ʊ���ɫ
  currentImage.paintIcon(this,g,x,y);  
        //��(x,y)����ͼ��(���Ͻ�)��this��ʾ�����۲��ߵ����
}

private class DirectionListener implements KeyListener
{
   public void keyPressed(KeyEvent event)
   {
       switch(event.getKeyCode())    // ��ȡ�밴����������������
       {
           case  KeyEvent.VK_UP:        // �����ּ������Ϸ����
                         currentImage=up;
                         y-=JUMP;
                         break;
           case  KeyEvent.VK_DOWN:   //   �����ּ������·����
                         currentImage=down;
                         y+=JUMP;
                         break;
           case  KeyEvent.VK_LEFT:     //  �����ּ����������
                         currentImage=left;
                         x-=JUMP;
                         break;
           case  KeyEvent.VK_RIGHT:  // �����ּ������ҷ���
                         currentImage=right;
                         x+=JUMP;
                         break;
       }

       repaint();  
           //ˢ����塣����ػ����򣬵���paintComponent����
  }

   public void keyTyped(KeyEvent event) {  }
   public void keyReleased(KeyEvent event) {  }
}
}
