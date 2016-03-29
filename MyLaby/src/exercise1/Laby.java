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
	 * ���ã�������½�֮���ܲ��ܽ�ǽ
	 * ���룺���½缰����ڵ�λ��
	 * ���أ��ܽ�ǽ->���Խ�ǽ��λ��
	 * 		 ���ܽ�ǽ->�ձ�
	 */
	@SuppressWarnings("unused")
	public ArrayList<Integer> createMurable(int lowerBound, int upperBound, int...door)
	{
		int numDoor = door.length;	
		int high = upperBound - lowerBound + 1;   //[�½磬�Ͻ�]֮�������Ԫ�ظ���
		int removal = 2;

		Integer[] doorInt = new Integer[door.length];
		for(int i = 0; i < door.length; i++)
		{
			doorInt[i] = door[i];
		}
		ArrayList<Integer> usedLoc = new ArrayList<Integer>(Arrays.asList(doorInt));//��/�������λ��  
        ArrayList<Integer> dangerousLoc = new ArrayList<Integer>();                 //�����Խ�ǽ��λ��
        //�����������
        for(Integer i:usedLoc)
        {
        	dangerousLoc.add(i);
        }
        //Ҳ�������Ͻ�/�½�/�Ͻ��һ/�½��һ
        dangerousLoc.add(upperBound);
        dangerousLoc.add(upperBound - 1);
        dangerousLoc.add(lowerBound);
        dangerousLoc.add(lowerBound + 1);
        //
        
		//����
		Collections.sort(dangerousLoc, new Comparator<Integer>() {
		        public int compare(Integer int1, Integer int2)
		        {

		            return  int1.compareTo(int2);
		        }
		    });
		int numDangerous = dangerousLoc.size();
		//ȥ��
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

		//���Խ�ǽ��λ��
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
	 * ���ã������ѡ����һ������ǽ
	 * ���룺���õ��У�Ҫ���ɵ�ǽ�����ҷ�Χ
	 * �����ѡ������
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
	 * ���ã������ѡ����һ������ǽ
	 * ���룺���õ��У�Ҫ���ɵ�ǽ�����·�Χ
	 * �����ѡ������
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
		//ȡ������ڵ�����
		int[] doorRow = new int[door.length];
		for(int i = 0; i < door.length; i++)
		{
			doorRow[i] = door[i].getX();
		}
		//������ǰ״̬���Ƿ���Խ���ǽ
		ArrayList<Integer> rowUseable = new ArrayList<Integer>();
		rowUseable = createMurable(rowLow,rowHigh,doorRow);
	
		//ȡ������ڵ�����
		int[] doorCol = new int[door.length];
		for(int i = 0; i < door.length; i++)
		{
			doorCol[i] = door[i].getY();
		}
		//������ǰ������Ƿ���Խ���ǽ
		ArrayList<Integer> colUseable = new ArrayList<Integer>();
		colUseable = createMurable(colLeft,colRight,doorCol);
	
		//�������������ܽ�ǽ����ݹ����
		if(rowUseable.isEmpty() || colUseable.isEmpty())
					return;
		//����ǽ
		int rowMur = createMurRow(rowUseable, colLeft, colRight);
		//����ǽ		
	    int colMur = createMurCol(colUseable, rowLow, rowHigh);
		
		//���彨ǽ�������ɵ��ĸ�С����
		ArrayList<Point> zone1 = new ArrayList<Point>(5);   //���Ͻ�
		ArrayList<Point> zone2 = new ArrayList<Point>(5);   //���Ͻ�
		ArrayList<Point> zone3 = new ArrayList<Point>(5);   //���½�
		ArrayList<Point> zone4 = new ArrayList<Point>(5);   //���½�
		
		//��ԭʼ��������ӵ��ĸ����γɵ�������
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
		
		//�򶴲����������·ֳɵ��ĸ�����
		Random chose = new Random();
		boolean moreHole = chose.nextBoolean();
		if(moreHole)	//��ǽ���������������ŵ�ǽ����һ��
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
				int colHole = RandomRange.getRand(colMur, colRight);;     //����ǽ�����һ��֮��ѡһ�д�
				laby[rowMur][colHole] = SPACE;
				zone2.add(new Point(rowMur,colHole));
				zone4.add(new Point(rowMur,colHole));
			}
		}
		else           //���ŵ�ǽ���������������ŵ���һ��
		{
			int colHole = RandomRange.getRand(colLeft,colMur);   
			laby[rowMur][colHole] = SPACE;
			zone1.add(new Point(rowMur,colHole));
			zone3.add(new Point(rowMur,colHole));
			
			colHole = RandomRange.getRand(colMur, colRight);;     //����ǽ�����һ��֮��ѡһ�д�
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
	
		//�����ݹ���ã��ֱ����ĸ�С�������ظ���������
		divAndCon(rowLow,rowMur,colLeft,colMur,zone1.toArray(new Point[zone1.size()]));      //����1
		divAndCon(rowLow,rowMur,colMur,colRight,zone2.toArray(new Point[zone2.size()]));	 //����2    
		divAndCon(rowMur,rowHigh,colLeft,colMur,zone3.toArray(new Point[zone3.size()]));	 //����3
		divAndCon(rowMur,rowHigh,colMur,colRight,zone4.toArray(new Point[zone4.size()]));	 //����4
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
		System.out.println("�װ���˧������Ҫ�����Թ�ѽ");  
        
        int size = 0;
        for(;;)
        {
	        try{
	        	Scanner sc = new Scanner(System.in);
	        	size = sc.nextInt();
	        }
	        catch(InputMismatchException e)
	        {
	        	System.out.println("Ϲ��ɶ���װ���->_->");
	        	System.out.println("����һ�Σ���~");
	        	continue;
	        }
	        if(size <= 2)
	        {
	        	System.out.println("��Ҳ̫С�˵��->_->");
	        	System.out.println("�����������~~");		
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
	 * ���ã���(min , max)֮�����������
	 * ���룺�������ɵ�����������½�
	 * ������涨��Χ�ڵ�һ������
	 */
	public static int getRand(int min, int max)
	{
		Random select = new Random();
		return select.nextInt(max-min-1)+min+1;
	}
}