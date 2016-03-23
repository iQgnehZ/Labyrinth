package exercise1; 

import java.lang.Math.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.lang.*;

public class LabyMaker {
	public static final char START = 'X';
	public static final char END = 'O';
	public static final char SPACE = ' ';
	public static final char MUR = 'M';
	private int size;
	private char[][] laby;
	
	public LabyMaker(int size)
	{
		this.size = size+2;
		laby = new char[this.size][this.size];
		for(int i = 0; i < this.size; i++)
		{
				laby[0][i] = MUR;
		}
		for(int i = 1; i < this.size; i++)
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
					laby[1][0] = SPACE;
					laby[this.size-2][this.size-1] = SPACE;
			}
		}
		for(int i = 0; i < this.size; i++)
		{
				laby[this.size-1][i] = MUR;
		}
	}
	
	//Divide&Conquer
	/*public void DAC(int rowHaut, int rowLow, int colomnLeft, int colomnRight, Point enter, Point exit)
	{
		
		int high = rowHaut - rowLow + 1;
		int removalRow = 4;
		if(enter.getY() != rowLow+1 && enter.getY() != rowHaut-1)
		{
			removalRow++;
		}
		if(enter.getY()!= exit.getY()&& exit.getY() != rowLow+1 && exit.getY() != rowHaut-1)
		{
			removalRow++;
		}
		boolean addRow;
		if(high - removalRow > 0)
		{
			addRow = true;
		}
		else
		{
			addRow = false;
		}
		
		int removalColomn = 4;
		int wide = colomnRight - colomnLeft + 1;
		if(enter.getX() != colomnLeft+1 && enter.getX() != colomnRight-1)
		{
			removalColomn++;
		}
		if(enter.getX()!= exit.getX()&& exit.getX() != colomnLeft+1 && exit.getX() != colomnRight-1)
		{
			removalColomn++;
		}
		boolean addColomn;
		if(high - removalColomn > 0)
		{
			addColomn = true;
		}
		else
		{
			addColomn = false;
		}
		
		if(addRow == false && addColomn == false)
			return;
		
		
		Random chose = new Random();
		int rowMur = chose.nextInt(high-5);
		if(addRow == true)
		{
			for(; rowMur == enter.getY()|| rowMur == exit.getY();)
			{
				rowMur = chose.nextInt(high-5);
			}
			for(int i = colomnLeft; i < colomnRight ;i++)
			{
				laby[rowLow + 2 + rowMur][i] = MUR;
			}
			
		}
		int colMur = chose.nextInt(wide-5);
		if(addColomn == true)
		{
			for(; colMur == enter.getX()|| colMur == exit.getX();)
			{
				colMur = chose.nextInt(wide-5);
			}
			for(int i = rowLow; i < rowHaut ;i++)
			{
				laby[i][colomnLeft+ 2 + colMur] = MUR;
			}
		}
		
		boolean moreHole = chose.nextBoolean();
		if(moreHole)	//竖着的墙上有两个洞，横着的墙上有一个
		{
			int rowHole = chose.nextInt(rowMur);         //在第0行到横墙之间选一行打洞
			laby[rowLow + 1 + rowHole][colomnLeft+ 2 + colMur] = SPACE;

			rowHole = chose.nextInt(rowHaut-rowMur-4);         //在横墙和最后一行之间选一行打洞
			laby[rowHaut - 1 - rowHole][colomnLeft+ 2 + colMur] = SPACE;
			
			if(chose.nextBoolean())
			{
				int colHole = chose.nextInt(colMur);
				laby[rowLow + 2 + rowMur][colomnLeft+ 1 + colHole] = SPACE;				
			}
			else
			{
				int colHole = chose.nextInt(colomnRight - colMur -4);
				laby[rowLow + 2 + rowMur][colomnRight - colHole - 1] = SPACE;	
			}
		}
		else           //横着的墙上有两个洞，竖着的有一个
		{
			int colHole = chose.nextInt(colMur);
		    laby[rowLow + 2 + rowMur][colomnLeft+ 1 + colHole] = SPACE;
			
		    colHole = chose.nextInt(colomnRight-colMur-4);
			laby[rowLow + 2 + rowMur][colomnRight - colHole - 1] = SPACE;
			
			if(chose.nextBoolean())
			{
				int rowHole = chose.nextInt(rowMur);
				laby[rowLow + 1 + rowHole][colomnLeft+ 2 + colMur] = SPACE;			
			}
			else
			{
				int rowHole = chose.nextInt(rowHaut - rowMur -4);
				laby[rowHaut - 1 - rowHole][colomnLeft+ 2 + colMur] = SPACE;	
			}
			
		}
		return;
	}*/
	
	public void DAC(int rowHaut, int rowLow, int colomnLeft, int colomnRight, Point...door)
	{
		int numDoor = door.length;	
		int high = rowHaut - rowLow + 1;
		int removalRow = 4;
		
		ArrayList<Integer> doorY = new ArrayList<Integer>();
		for(int i = 0; i < numDoor; i++)
		{
			doorY.add(door[i].getY());
		}
		//排序
		Collections.sort(doorY, new Comparator<Integer>() {
		        public int compare(Integer int1, Integer int2)
		        {

		            return  int1.compareTo(int2);
		        }
		    });
		//去重
		for(int i = 0,j=0; i < numDoor-1; i++)
		{
			if(doorY.get(i) == doorY.get(i+1))
			{
				doorY.remove(i-j);
				j++;
			}				
		}
		//找出可去除的行数
		if(doorY.get(0) != rowLow + 1)
		{
			removalRow++;
		}
		if(doorY.get(doorY.size() - 1) != rowHaut - 1)
		{
			removalRow++;
		}
		
		boolean addRow;
		if(high - removalRow > 0)
		{
			addRow = true;
		}
		else
		{
			addRow = false;
		}
		
		int removalColomn = 4;
		int wide = colomnRight - colomnLeft + 1;
		ArrayList<Integer> doorX = new ArrayList<Integer>();
		for(int i = 0; i < numDoor; i++)
		{
			doorX.add(door[i].getX());
		}
		//排序
		Collections.sort(doorX, new Comparator<Integer>() {
		        public int compare(Integer int1, Integer int2)
		        {

		            return  int1.compareTo(int2);
		        }
		    });
		//去重
		for(int i = 0,j=0; i < numDoor-1; i++)
		{
			if(doorX.get(i) == doorX.get(i+1))
			{
				doorX.remove(i-j);
				j++;
			}				
		}
		//找出可去除的列数
		if(doorX.get(0) != colomnLeft + 1)
		{
			removalColomn++;
		}
		if(doorX.get(doorX.size() - 1) != colomnRight-1)
		{
			removalColomn++;
		}
		
		boolean addColomn;
		if(high - removalColomn > 0)
		{
			addColomn = true;
		}
		else
		{
			addColomn = false;
		}
		
		if(addRow == false && addColomn == false)
			return;
		
		
		Random chose = new Random();
		int rowMur = chose.nextInt(high-5);
		if(addRow == true)
		{
			for(; doorY.contains(rowMur);)
			{
				rowMur = chose.nextInt(high-5);
			}
			for(int i = colomnLeft; i < colomnRight ;i++)
			{
				laby[rowLow + 2 + rowMur][i] = MUR;
			}
			
		}
		int colMur = chose.nextInt(wide-5);
		if(addColomn == true)
		{
			for(; doorX.contains(colMur);)
			{
				colMur = chose.nextInt(wide-5);
			}
			for(int i = rowLow; i < rowHaut ;i++)
			{
				laby[i][colomnLeft+ 2 + colMur] = MUR;
			}
		}
		
		boolean moreHole = chose.nextBoolean();
		if(moreHole)	//竖着的墙上有两个洞，横着的墙上有一个
		{
			int rowHole = chose.nextInt(rowMur);         //在第0行到横墙之间选一行打洞
			laby[rowLow + 1 + rowHole][colomnLeft+ 2 + colMur] = SPACE;

			rowHole = chose.nextInt(rowHaut-rowMur-4);         //在横墙和最后一行之间选一行打洞
			laby[rowHaut - 1 - rowHole][colomnLeft+ 2 + colMur] = SPACE;
			
			if(chose.nextBoolean())
			{
				int colHole = chose.nextInt(colMur);
				laby[rowLow + 2 + rowMur][colomnLeft+ 1 + colHole] = SPACE;				
			}
			else
			{
				int colHole = chose.nextInt(colomnRight - colMur -4);
				laby[rowLow + 2 + rowMur][colomnRight - colHole - 1] = SPACE;	
			}
		}
		else           //横着的墙上有两个洞，竖着的有一个
		{
			int colHole = chose.nextInt(colMur);
		    laby[rowLow + 2 + rowMur][colomnLeft+ 1 + colHole] = SPACE;
			
		    colHole = chose.nextInt(colomnRight-colMur-4);
			laby[rowLow + 2 + rowMur][colomnRight - colHole - 1] = SPACE;
			
			if(chose.nextBoolean())
			{
				int rowHole = chose.nextInt(rowMur);
				laby[rowLow + 1 + rowHole][colomnLeft+ 2 + colMur] = SPACE;			
			}
			else
			{
				int rowHole = chose.nextInt(rowHaut - rowMur -4);
				laby[rowHaut - 1 - rowHole][colomnLeft+ 2 + colMur] = SPACE;	
			}
			
		}
		return;
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
		LabyMaker laby1 = new LabyMaker(20);
		Point in = new Point(1,1);
		Point out = new Point(laby1.getSize()-1,laby1.getSize()-2);
		laby1.DAC(laby1.getSize()-1, 0, 0, laby1.getSize()-1, in, out);
		System.out.print(laby1);
	}
}

class Point
{
	private int x;
	private int y;
	
	Point(int x, int y)
	{
		this.x = x;
		this.y = y;
	}

	public int getX() {
		return x;
	}
	
	public int getY() {
		return y;
	}
}