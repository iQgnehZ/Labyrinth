package exercise1; 

import java.lang.Math.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.InputMismatchException;
import java.lang.*;
import java.util.Scanner;

public class Laby {
	public static final char START = 'X';
	public static final char END = 'O';
	public static final char SPACE = ' ';
	public static final char MUR = 'M';
	private int size;
	private char[][] laby;
	
	public Laby()
	{
		this(5,5,5);
	}
	public Laby(int size, int in, int out)
	{
		this.size = size;
		laby = new char[this.size][this.size];
		for(int i = 0; i < this.size; i++)
		{
				laby[0][i] = MUR;
		}
		for(int i = 1; i < this.size-1; i++)
		{
			for(int j = 0; j < this.size; j++)
			{
					if(j == 0 || j == this.size-1)
					{
						laby[i][j] = MUR;
					}
					else
					{
						laby[i][j] = SPACE;
					}
			}
		}
		for(int i = 0; i < this.size; i++)
		{
				laby[this.size-1][i] = MUR;
		}
		laby[in][0] = SPACE;
		laby[out][this.size-1] = SPACE;
	}

	/*
	 * 作用：检测上下界之间能不能建墙
	 * 输入：上下界及出入口的位置
	 * 返回：能建墙->可以建墙的位置
	 * 		 不能建墙->空表
	 */
	@SuppressWarnings("unused")
	public ArrayList<Integer> createMurable(int lowerBound, int upperBound, int...door)
	{
		int numDoor = door.length;	
		int high = upperBound - lowerBound + 1;   //[下界，上界]之间包含的元素个数
		int removal = 2;

		Integer[] doorInt = new Integer[door.length];
		for(int i = 0; i < door.length; i++)
		{
			doorInt[i] = door[i];
		}
		ArrayList<Integer> usedLoc = new ArrayList<Integer>(Arrays.asList(doorInt));//出/入口所在位置  
        ArrayList<Integer> dangerousLoc = new ArrayList<Integer>();                 //不可以建墙的位置
        //包括：出入口
        for(Integer i:usedLoc)
        {
        	dangerousLoc.add(i);
        }
        //也包括：上界/下界/上界减一/下界加一
        dangerousLoc.add(upperBound);
        dangerousLoc.add(upperBound - 1);
        dangerousLoc.add(lowerBound);
        dangerousLoc.add(lowerBound + 1);
        //
        
		//排序
		Collections.sort(dangerousLoc, new Comparator<Integer>() {
		        public int compare(Integer int1, Integer int2)
		        {

		            return  int1.compareTo(int2);
		        }
		    });
		int numDangerous = dangerousLoc.size();
		//去重
		for(int i = 0,j=0; i < numDangerous - 2; i++)
		{
			if(dangerousLoc.get(i) == dangerousLoc.get(i+1))
			{
				dangerousLoc.set(i,null);
			}				
		}
		for(;dangerousLoc.contains(null);)
		{
			dangerousLoc.remove(null);
		}

		//可以建墙的位置
		ArrayList<Integer> useableLoc = new ArrayList<Integer>();
		for(int i = 0; i < high; i++)
		{   

			if(!dangerousLoc.contains(lowerBound + i))
			{
				useableLoc.add(lowerBound+i);
			}
		}
		
		return useableLoc;		
	}
	
	
	
	/*
	 * 作用：在随机选定的一行生成墙
	 * 输入：可用的行，要生成的墙的左右范围
	 * 输出：选定的行
	 */
	public int createMurRow(ArrayList<Integer> mur, int colLeft, int colRight)
	{
		Random select = new Random();
		int index = select.nextInt(mur.size()); 
		int s = mur.get(index);
		for(int i = colLeft; i < colRight; i++)
		{
			laby[s][i] = MUR;
		}
		return s;
	}
	/*
	 * 作用：在随机选定的一列生成墙
	 * 输入：可用的列，要生成的墙的上下范围
	 * 输出：选定的列
	 */
	public int createMurCol(ArrayList<Integer> mur, int rowLow, int rowHigh)
	{
		Random select = new Random();
		int index = select.nextInt(mur.size()); 
		int s = mur.get(index);
		for(int i = rowLow; i < rowHigh; i++)
		{
			laby[i][s] = MUR;
		}
		return s;
	}
	
	public void divAndCon(int rowLow, int rowHigh, int colLeft, int colRight, Point...door)
	{
		//取出出入口的行数
		int[] doorRow = new int[door.length];
		for(int i = 0; i < door.length; i++)
		{
			doorRow[i] = door[i].getX();
		}
		//分析当前状态下是否可以建横墙
		ArrayList<Integer> rowUseable = new ArrayList<Integer>();
		rowUseable = createMurable(rowLow,rowHigh,doorRow);
	
		//取出出入口的列数
		int[] doorCol = new int[door.length];
		for(int i = 0; i < door.length; i++)
		{
			doorCol[i] = door[i].getY();
		}
		//分析当前情况下是否可以建竖墙
		ArrayList<Integer> colUseable = new ArrayList<Integer>();
		colUseable = createMurable(colLeft,colRight,doorCol);
	
		//如果横或竖都不能建墙，则递归结束
		if(rowUseable.isEmpty() || colUseable.isEmpty())
					return;
		//建横墙
		int rowMur = createMurRow(rowUseable, colLeft, colRight);
		//建竖墙		
	    int colMur = createMurCol(colUseable, rowLow, rowHigh);
		
		//定义建墙后新生成的四个小区域
		ArrayList<Point> zone1 = new ArrayList<Point>(5);   //左上角
		ArrayList<Point> zone2 = new ArrayList<Point>(5);   //右上角
		ArrayList<Point> zone3 = new ArrayList<Point>(5);   //左下角
		ArrayList<Point> zone4 = new ArrayList<Point>(5);   //右下角
		
		//将原始出入口添加到四个新形成的区域中
		for(int i = 0; i < door.length; i++)
		{
			if(doorRow[i] < rowMur && doorCol[i] < colMur)
			{
				zone1.add(door[i]);
			}
			else if(doorRow[i] < rowMur && doorCol[i] > colMur)
			{
				zone2.add(door[i]);
			}
			else if(doorRow[i] > rowMur && doorCol[i] < colMur)
			{
				zone3.add(door[i]);
			}
			else if(doorRow[i] > rowMur && doorCol[i] > colMur)
			{
				zone4.add(door[i]);
			}
		}
		
		//打洞并将洞加入新分成的四个区域
		Random chose = new Random();
		boolean moreHole = chose.nextBoolean();
		if(moreHole)	//竖墙上有两个洞，横着的墙上有一个
		{
			int rowHole = RandomRange.getRand(rowLow,rowMur);         
			laby[rowHole][colMur] = SPACE;
			zone1.add(new Point(rowHole,colMur));
			zone2.add(new Point(rowHole,colMur));
			
			rowHole = RandomRange.getRand(rowMur, rowHigh);         
			laby[rowHole][colMur] = SPACE;
			zone3.add(new Point(rowHole,colMur));
			zone4.add(new Point(rowHole,colMur));
			
			if(chose.nextBoolean())
			{
				int colHole = RandomRange.getRand(colLeft,colMur);   
				laby[rowMur][colHole] = SPACE;
				zone1.add(new Point(rowMur,colHole));
				zone3.add(new Point(rowMur,colHole));
			}
			else
			{
				int colHole = RandomRange.getRand(colMur, colRight);;     //在竖墙到最后一列之间选一列打洞
				laby[rowMur][colHole] = SPACE;
				zone2.add(new Point(rowMur,colHole));
				zone4.add(new Point(rowMur,colHole));
			}
		}
		else           //横着的墙上有两个洞，竖着的有一个
		{
			int colHole = RandomRange.getRand(colLeft,colMur);   
			laby[rowMur][colHole] = SPACE;
			zone1.add(new Point(rowMur,colHole));
			zone3.add(new Point(rowMur,colHole));
			
			colHole = RandomRange.getRand(colMur, colRight);;     //在竖墙到最后一列之间选一列打洞
			laby[rowMur][colHole] = SPACE;
			zone2.add(new Point(rowMur,colHole));
			zone4.add(new Point(rowMur,colHole));
			
			if(chose.nextBoolean())
			{
				int rowHole = RandomRange.getRand(rowLow,rowMur);         
				laby[rowHole][colMur] = SPACE;
				zone1.add(new Point(rowHole,colMur));
				zone2.add(new Point(rowHole,colMur));			}
			else
			{
				int rowHole = RandomRange.getRand(rowMur, rowHigh);         
				laby[rowHole][colMur] = SPACE;
				zone3.add(new Point(rowHole,colMur));
				zone4.add(new Point(rowHole,colMur));
			}
			
		}
	
		//自身递归调用，分别在四个小区域内重复上述操作
		divAndCon(rowLow,rowMur,colLeft,colMur,zone1.toArray(new Point[zone1.size()]));      //区域1
		divAndCon(rowLow,rowMur,colMur,colRight,zone2.toArray(new Point[zone2.size()]));	 //区域2    
		divAndCon(rowMur,rowHigh,colLeft,colMur,zone3.toArray(new Point[zone3.size()]));	 //区域3
		divAndCon(rowMur,rowHigh,colMur,colRight,zone4.toArray(new Point[zone4.size()]));	 //区域4
	}
	
	public char[][] getLaby() {
		return laby;
	}

	public String toString()
	{
		String s = "";
		for(char[] row : laby)
		{
			for(char c : row)
			{
				s += c;
			}
			s += "\r";
		}
		return s;
			
	}
	
	public int getSize() {
		return size;
	}

	public static void main(String[] agrs)
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
		Laby laby1 = new Laby(size,in,out);
		Point enter = new Point(in,0);
		Point exit = new Point(out,laby1.getSize());
        laby1.divAndCon(0,laby1.getSize()-1, 0, laby1.getSize()-1, enter, exit);
		System.out.print(laby1);
	}
}

class RandomRange
{	
	/*
	 * 作用：在(min , max)之间生成随机数
	 * 输入：所需生成的随机数的上下界
	 * 输出：规定范围内的一个整数
	 */
	public static int getRand(int min, int max)
	{
		Random select = new Random();
		return select.nextInt(max-min-1)+min+1;
	}
}
