package Exercise;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class DirectionPanel extends JPanel
{
 private final int WIDTH=300,HEIGHT=200;  //面板大小
 private final int JUMP=10;  //图片移动尺寸

 private ImageIcon up,down,left,right,currentImage;
 private int x,y;

 public DirectionPanel()
{
     addKeyListener (new DirectionListener());  //注册监听器

     x=WIDTH/2;
     y=HEIGHT/2;

     up=new ImageIcon("src/Image/icon.jpg");  //根据指定文件创建图片对象     
     down=new ImageIcon("src/Image/icon.jpg");
     left=new ImageIcon("src/Image/icon.jpg");
     right=new ImageIcon("src/Image/icon.jpg");



    setBackground(Color.yellow);  // 面板背景色
    setPreferredSize (new Dimension(WIDTH,HEIGHT)); 
          // 设置面板大小
    setFocusable(true);   // 面板可获得焦点
}

public void paintComponent(Graphics g)
{
  super.paintComponent(g);  // 调用父类构造方法，绘制背景色
  currentImage.paintIcon(this,g,x,y);  
        //在(x,y)绘制图标(左上角)，this表示用作观察者的组件
}

private class DirectionListener implements KeyListener
{
   public void keyPressed(KeyEvent event)
   {
       switch(event.getKeyCode())    // 获取与按键相关联的虚拟键码
       {
           case  KeyEvent.VK_UP:        // 非数字键盘向上方向键
                         currentImage=up;
                         y-=JUMP;
                         break;
           case  KeyEvent.VK_DOWN:   //   非数字键盘向下方向键
                         currentImage=down;
                         y+=JUMP;
                         break;
           case  KeyEvent.VK_LEFT:     //  非数字键盘向左方向键
                         currentImage=left;
                         x-=JUMP;
                         break;
           case  KeyEvent.VK_RIGHT:  // 非数字键盘向右方向
                         currentImage=right;
                         x+=JUMP;
                         break;
       }

       repaint();  
           //刷新面板。清空重绘区域，调用paintComponent方法
  }

   public void keyTyped(KeyEvent event) {  }
   public void keyReleased(KeyEvent event) {  }
}
}
