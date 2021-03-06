package Game;
import java.io.Serializable;

public class Player 
{
	/**
	 * 
	 */
	
	private double x, y;
	private double rot;
	private boolean isMoving;
	public Player(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	public double getDX()
	{
		return x;
	}
	
	public double getDY()
	{
		return y;
	}
	
	public int getX()
	{
		return (int)x;
	}
	
	public int getY()
	{
		return (int)y;
	}
	
	
	public void setX(double x)
	{
		this.x = x;
	}
	
	public void setY(double y)
	{
		this.y = y;
	}
	
	public double getRot()
	{
		return rot;
	}
	
	public boolean isMoving()
	{
		return isMoving;
	}
	
	public void setMoving(boolean isMoving)
	{
		this.isMoving = isMoving;
	}
	
	public void setRot(double rot)
	{
		this.rot = rot;
	}
}
