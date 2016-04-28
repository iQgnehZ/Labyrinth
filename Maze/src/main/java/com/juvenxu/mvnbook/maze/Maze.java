/**
  @version 1.30 2000-06-02
   @author Cay Horstmann
*/
package com.juvenxu.mvnbook.maze;
import java.awt.*;

import java.awt.event.*;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.InputMismatchException;
import java.util.Scanner;

import javax.imageio.ImageIO;
import javax.swing.*;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Maze
{
	private JFrame frame = new ImageFrame();
	private ImagePanel imagePanel = new ImagePanel(10);
	private JMenuBar menubar = new JMenuBar();
	private JMenu m = new JMenu("我想……");
	private JMenuItem mit1 = new MItem1("o(*￣▽￣*)ブ玩新的");
	private JMenuItem mit2 = new MItem2("(●'◡'●)从头玩");
	private JMenuItem mit3 = new MItem3("(；′⌒`)我要认输啦");
	private JMenuItem mit4 = new MItem4("(╯▔皿▔)╯不玩啦");
	private  int size = 10;
	public void init()
	{
		m.add(mit1);
		m.add(mit2);
		m.add(mit3);
		m.add(mit4);
		menubar.add(m);
		frame.setJMenuBar(menubar);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.add(imagePanel);
		frame.setSize(imagePanel.getSize().width+20,imagePanel.getSize().height+60);
		frame.setVisible(true); 
	}
	public void changePanel(int size){
		frame.remove(imagePanel);
		imagePanel = new ImagePanel(size);
		init();
	}
	public void rePlayPanel(){
		imagePanel.initPanel();
		imagePanel.repaint();
	}
	public void helpPanel(){
		imagePanel.pathImagePanel();
		imagePanel.repaint();
	}
	public static void main(String[] args){
       Maze maze = new Maze();
       maze.init();
    }
	class MItem1 extends JMenuItem{

		public MItem1(String s){
			super(s);
			addActionListener(new Mit1Handler());
			setAccelerator(KeyStroke.getKeyStroke("ctrl N"));
		}
		class Mit1Handler implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				String regEx = "^[1-9][0-9]*$";
				Pattern pat = Pattern.compile(regEx);
				for(;;){
					String result = (String)JOptionPane.showInputDialog(null,"你想要多大的迷宫？");
					Matcher mat = pat.matcher(result);
					 if (mat.find()) {
						 	size = Integer.parseInt(result);
				            break;
				        }
				        else {
				            continue;
				        }
				}
				changePanel(size);
			}
		}
	}
	class MItem2 extends JMenuItem{

		public MItem2(String s){
			super(s);
			addActionListener(new Mit1Handler());
			setAccelerator(KeyStroke.getKeyStroke("ctrl R"));
		}
		class Mit1Handler implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				rePlayPanel();
			}
		}
	}
	class MItem3 extends JMenuItem{

		public MItem3(String s){
			super(s);
			addActionListener(new Mit3Handler());
			setAccelerator(KeyStroke.getKeyStroke("ctrl H"));
		}
		class Mit3Handler implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				helpPanel();
			}
		}
	}
	class MItem4 extends JMenuItem{

		public MItem4(String s){
			super(s);
			addActionListener(new Mit4Handler());
			setAccelerator(KeyStroke.getKeyStroke("ctrl Q"));
		}
		class Mit4Handler implements ActionListener {
			public void actionPerformed(ActionEvent e) {
				System.exit(0);
			
		}
	}
}
}
/** 
    A frame with an image panel
*/
class ImageFrame extends JFrame
{

   private Image icon = null;
   
   public ImageFrame()
   {
	   try {
		this.icon=ImageIO.read(this.getClass().getResourceAsStream("/icon.jpg"));
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
	 //  Toolkit kit = Toolkit.getDefaultToolkit();
	//	this.icon = kit.getImage("src/main/res/icon.jpg");	
		setTitle("Labyrinth");
		setIconImage(icon);
		setBackground(Color.gray);
   }   
}
 
/**
   A panel that displays a tiled image
*/
class ImagePanel extends JPanel
{
	private Laby labyFrame;
	private char murs[][];
	private Image murImaHori;
	private Image murImaVer;
	private Image footprint;
	private PanleHandler panleHandler = new PanleHandler();
	private int x,y;
	private int in;
	private boolean flag=false;
 
	public char[][] newLaby(int size)
	{
		
		labyFrame = new Laby(size);
        labyFrame.createLaby(labyFrame);
        return labyFrame.getLaby();
	}
  
	public char[][] showPath()
	{
        labyFrame.ShortestPath(labyFrame.getEnter(), labyFrame.getExit());
        labyFrame.showPath();
        return labyFrame.getLaby();
	}
	
	public int getIn(){
		return in;
	}

	public void setFootX(int x) {
		this.x = x;
	}

	public void setFootY(){
		this.y = 0;
	}	
	public ImagePanel(int size)
   {
		
		addKeyListener(panleHandler);
		flag=true;
		setFocusable(true);
  
	   try
		{  
		   murImaVer=ImageIO.read(this.getClass().getResourceAsStream("/mur_vertical.png"));
		   murImaHori=ImageIO.read(this.getClass().getResourceAsStream("/mur_horizontal.png"));
		   footprint=ImageIO.read(this.getClass().getResourceAsStream("/footprint.jpg"));
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	    murs = newLaby(size);
	    setSize(20*murs.length,20*murs.length);
	    for(int i = 0; i<murs.length-1; i++){
	    	  if(murs[i][0] == Laby.SPACE){
	    		  x=i; in=i;break;
	    	  }
	    }    	
	    y=0;	     
	}
	public void pathImagePanel()
	 {
		    murs = showPath();
		    y=murs.length-1;
		    if(flag){
				  removeKeyListener(panleHandler);
				  flag = !flag;
			  }
	}
	public void initPanel(){
		x=in;
		y=0;
		for (int i = 0; i < murs.length; i++)
	    	  for (int j = 0; j < murs.length; j++)
	    	  {
	    		  if(murs[i][j] == Laby.PATH)
	    			  murs[i][j] = Laby.SPACE;
	    	  }
		if(!flag){
			addKeyListener(panleHandler);
			flag = !flag;
		}
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
    			  if (murs[i][j] == Laby.MUR &&(murs[i-1][j] == Laby.MUR||murs[i+1][j] == Laby.MUR)) 
        			  g.copyArea(0, 0, imageWidth, imageHeight,
        					  i * imageWidth, j * imageHeight);
        		  else if(murs[i][j] == Laby.MUR)
        			  g.copyArea((murs.length-1)*imageWidth, (murs.length-1)*imageHeight, imageWidth, imageHeight,
        					  i * imageWidth - (murs.length-1)*imageWidth , j * imageHeight - (murs.length-1)*imageHeight);
    		  }
    		  else if(i == 0)
    		  {
    			  if (murs[i][j] == Laby.MUR&& murs[i+1][j] == Laby.MUR) 
        			  g.copyArea(0, 0, imageWidth, imageHeight,
        					  i * imageWidth, j * imageHeight);
        		  else if(murs[i][j] == Laby.MUR)
        			  g.copyArea((murs.length-1)*imageWidth, (murs.length-1)*imageHeight, imageWidth, imageHeight,
        					  i * imageWidth - (murs.length-1)*imageWidth , j * imageHeight - (murs.length-1)*imageHeight);
    		  }
    		  else
    		  {
    			  if (murs[i][j] == Laby.MUR&& murs[i-1][j] == Laby.MUR) 
        			  g.copyArea(0, 0, imageWidth, imageHeight,
        					  i * imageWidth, j * imageHeight);
        		  else if(murs[i][j] == Laby.MUR)
        			  g.copyArea((murs.length-1)*imageWidth, (murs.length-1)*imageHeight, imageWidth, imageHeight,
        					  i * imageWidth - (murs.length-1)*imageWidth , j * imageHeight - (murs.length-1)*imageHeight);
    		  }
    			  
    	  }
	      for (int i = 0; i < murs.length; i++){
	    	  for (int j = 0; j < murs.length; j++){
	    		  if(murs[i][j] == Laby.PATH){
	    			  g.drawImage(footprint, i*20, j*20, null);
	    			  x=in;
	    	    	  y=0;
	    		  }	  
	    	  }
	      }
	      g.drawImage(footprint, x*20, y*20, null);
  } 
   
   class PanleHandler implements KeyListener  
   {  
	   public void keyPressed(KeyEvent event){
		   
		   switch(event.getKeyCode())    // 获取与按键相关联的虚拟键码
	       {
	           case  KeyEvent.VK_UP:        // 非数字键盘向上方向键
	        	   if(y-1>=0&&murs[x][y-1]==Laby.SPACE){
	        		   y-=1;
		        	   break;
	        	   }
	        	   else{
	        		   break;
	        	   }
	           case  KeyEvent.VK_DOWN:   //   非数字键盘向下方向键
	        	   if(y+1<=murs.length&&(murs[x][y+1]==Laby.SPACE||murs[x][y+1]==Laby.END)){
	        		   y+=1;
		        	   break;
	        	   }
	        	   else{
	        		   break;
	        	   }                
	           case  KeyEvent.VK_LEFT:     //  非数字键盘向左方向键
	        	   if(x-1>=0&&murs[x-1][y]==Laby.SPACE){
	        		   x-=1;
		        	   break;
	        	   }
	        	   else{
	        		   break;
	        	   }         
	           case  KeyEvent.VK_RIGHT:  // 非数字键盘向右方向
	        	   if(x+1<=murs.length&&murs[x+1][y]==Laby.SPACE){
	        		   x+=1;
		        	   break;
	        	   }
	        	   else{
	        		   break;
	        	   }
	       }
	       repaint();  
	       if(murs[x][y]==Laby.END){
			   JOptionPane JWin = new JOptionPane();
			   JOptionPane.showMessageDialog(null, "给亲一口~傻蛋儿~");
			   if(flag){
		    		  removeKeyListener(panleHandler);
		    		  flag = !flag;
		    	}	
		   }
	   }

	   public void keyTyped(KeyEvent event) {  }
	   public void keyReleased(KeyEvent event) {  }
   }
}





  
	

