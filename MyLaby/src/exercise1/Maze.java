/**
   @version 1.30 2000-06-02
   @author Cay Horstmann
*/
package exercise1;
import java.awt.*;
import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;
 
public class Maze
{
   
   public static void main(String[] args)
   {
   
      ImageFrame frame = new ImageFrame();
      frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      frame.setVisible(true);
    
}
 
}
 
/** 
    A frame with an image panel
*/
class ImageFrame extends JFrame
{
 
   public ImageFrame()
   {
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		//this.width = screenSize.width/2;
		//this.height = screenSize.height/2;
		this.locationX = screenSize.width/4;
		this.locationY = screenSize.height/4;
		this.icon = kit.getImage("src/Image/icon.jpg");	
 
      setTitle("Labyrinth");
      setSize(WIDTH, HEIGHT);
      setLocation(locationX,locationY);
      setIconImage(icon);
      setBackground(Color.gray);
      // add panel to frame
 
      ImagePanel panel = new ImagePanel();
      add(panel);
    
}
 
   public static final int WIDTH = 300;
   public static final int HEIGHT = 200;
   private int locationX = 0;
   private int locationY = 0;
   private Image icon = null;
 
}
 
/**
   A panel that displays a tiled image
*/
class ImagePanel extends JPanel
{
	private char murs[][];
	private Image murImaHori;
	private Image murImaVer;
	public char[][] creatLaby()
	{
		System.out.println("亲爱的帅比你想要多大的迷宫呀"); 
        int size = 0;
        for(;;)
        {
	        try{
	        	Scanner sc = new Scanner(System.in);
	        	size = sc.nextInt();
	        }
	        catch(InputMismatchException e)
	        {
	        	System.out.println("瞎输啥呢亲爱的->_->");
	        	System.out.println("再输一次，乖~");
	        	continue;
	        }
	        if(size <= 2)
	        {
	        	System.out.println("这也太小了点吧->_->");
	        	System.out.println("快来输个大点的~~");		
	        }
	        else
	        {
	        	break;
	        }
        }
        int in = RandomRange.getRand(0, size);
        int out = RandomRange.getRand(0, size);
	    Laby laby = new Laby(size,in,out);
		Point enter = new Point(in,0);
		Point exit = new Point(out,laby.getSize());
        laby.divAndCon(0,laby.getSize()-1, 0, laby.getSize()-1, enter, exit);
        
        return laby.getLaby();
	}
    
  
	public ImagePanel()
   {
   
      // acquire the image
	   try
		{
		    murImaVer = ImageIO.read(new File("src/Image/mur_vertical.png"));
			murImaHori = ImageIO.read(new File("src/Image/mur_horizontal.png"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	    murs = creatLaby();
	}
	    
   public void paintComponent(Graphics g)
   {
   
      super.paintComponent(g);
    
      int imageWidth = murImaVer.getWidth(this);
      int imageHeight = murImaVer.getHeight(this);
      
      g.drawImage(murImaHori, 0, 0, null);
   	  g.drawImage(murImaVer, (murs.length -1)*imageWidth, (murs.length -1)*imageHeight, this);
     
      
      for (int i = 0; i < murs.length; i++)
    	  for (int j = 0; j < murs.length; j++)
    	  {
    		  
    		  if(i != 0 && i != murs.length - 1)
    		  {
    			  if (murs[i][j] == 'M'&&(murs[i-1][j] == 'M'||murs[i+1][j] == 'M')) 
        			  g.copyArea(0, 0, imageWidth, imageHeight,
        					  i * imageWidth, j * imageHeight);
        		  else if(murs[i][j] == 'M')
        			  g.copyArea((murs.length-1)*imageWidth, (murs.length-1)*imageHeight, imageWidth, imageHeight,
        					  i * imageWidth - (murs.length-1)*imageWidth , j * imageHeight - (murs.length-1)*imageHeight);
    		  }
    		  else if(i == 0)
    		  {
    			  if (murs[i][j] == 'M'&& murs[i+1][j] == 'M') 
        			  g.copyArea(0, 0, imageWidth, imageHeight,
        					  i * imageWidth, j * imageHeight);
        		  else if(murs[i][j] == 'M')
        			  g.copyArea((murs.length-1)*imageWidth, (murs.length-1)*imageHeight, imageWidth, imageHeight,
        					  i * imageWidth - (murs.length-1)*imageWidth , j * imageHeight - (murs.length-1)*imageHeight);
    		  }
    		  else
    		  {
    			  if (murs[i][j] == 'M'&& murs[i-1][j] == 'M') 
        			  g.copyArea(0, 0, imageWidth, imageHeight,
        					  i * imageWidth, j * imageHeight);
        		  else if(murs[i][j] == 'M')
        			  g.copyArea((murs.length-1)*imageWidth, (murs.length-1)*imageHeight, imageWidth, imageHeight,
        					  i * imageWidth - (murs.length-1)*imageWidth , j * imageHeight - (murs.length-1)*imageHeight);
    		  }
    			  
    	  }
    		  
   } 
}