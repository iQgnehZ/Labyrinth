package exercise1;

public class Point
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
	
	public void setX(int x) {
		this.x = x;
	}

	public void setY(int y) {
		this.y = y;
	}

	public boolean equals(Point p)
	{
		if(this.x == p.getX() && this.y == p.getY())
			return true;
		return false;
	}
	
	public int hashCode() {
	    int hash = 7;
	    hash = 71 * hash + this.x;
	    hash = 71 * hash + this.y;
	    return hash;
	}
	
	
}
