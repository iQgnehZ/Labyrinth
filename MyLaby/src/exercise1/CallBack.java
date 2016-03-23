package exercise1;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.lang.reflect.InvocationTargetException;

public class CallBack
{

	public static void main(String[] args) throws InvocationTargetException, InterruptedException 
	{
		EventQueue.invokeAndWait(new Runnable()
				{
					public void run()
					{
						SimpleFrame frame = new SimpleFrame();
						frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
						frame.setVisible(true);
						JPanel panel = new DirectionPanel();        
				        frame.add(panel);
					}
			
				});
	}
	
}

class SimpleFrame extends JFrame
{
	private int width = 0;
	private int height = 0;
	private int locationX = 0;
	private int locationY = 0;
	private Image icon = null;
	public SimpleFrame()
	{	
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		this.width = screenSize.width/2;
		this.height = screenSize.height/2;
		this.locationX = screenSize.width/4;
		this.locationY = screenSize.height/4;
		this.icon = kit.getImage("src/Image/icon.jpg");			
		
		setSize(width,height);
		setLocation(locationX,locationY);
		
		setTitle("Labyrinthe");
		setIconImage(icon);
		setBackground(Color.gray);
		}
	
		
}

class DirectionPanel extends JPanel
{
	 private final int WIDTH=300,HEIGHT=200;  
	 private final int JUMP=10; 
	 private ImageIcon image;
	 private int x,y;
	
	 public DirectionPanel()
	{
	     addKeyListener (new DirectionListener());  
	
	     x=WIDTH/2;
	     y=HEIGHT/2;
	
	     image =new ImageIcon("src/Image/icon.jpg");  
	
	     setPreferredSize (new Dimension(WIDTH,HEIGHT)); 
	     setFocusable(true);   
	}
	
	public void paintComponent(Graphics g)
	{
		super.paintComponent(g); 
		image.paintIcon(this,g,x,y);  
	}
	
	private class DirectionListener implements KeyListener
	{
		public void keyPressed(KeyEvent event)
	   {
	       switch(event.getKeyCode())
	       {
		       case  KeyEvent.VK_UP: 
		                     y-=JUMP;
		                     break;
		       case  KeyEvent.VK_DOWN: 
		                     y+=JUMP;
		                     break;
		       case  KeyEvent.VK_LEFT: 
		                     x-=JUMP;
		                     break;
		       case  KeyEvent.VK_RIGHT:
		                     x+=JUMP;
		                     break;
	       }
	       repaint();
	   }
	
	   public void keyTyped(KeyEvent event) {  }
	   public void keyReleased(KeyEvent event) {  }
	}
}







