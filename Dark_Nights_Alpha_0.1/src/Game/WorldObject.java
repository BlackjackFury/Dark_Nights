package Game;
public class WorldObject implements java.io.Serializable{
	
	private double x, y;
	private int id;
	private static final long serialVersionUID = -2204622381145640429L;
	
	public WorldObject(int id ,int x, int y)
	{
		this.x = x;
		this.y = y;
		
		this.id = id;
	}

	public WorldObject()
	{
		
	}
	
	public int getX()
	{
		return (int) x;
	}
	
	public int getY()
	{
		return (int) y;
	}
	
	public double getDx()
	{
		return x;
	}
	
	public double getDy()
	{
		return y;
	}
	
	public int getId()
	{
		return id;	
	}
}