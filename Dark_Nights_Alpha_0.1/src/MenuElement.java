import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;



public class MenuElement {

	protected Position position;
	protected Size size;
	protected BufferedImage texture_active;	
	
	protected boolean isActive = false;

	
	protected int trigerWidth;
	protected int trigerHeight;
	
	protected int id;
	
	class Position
	{
		int x, y;	
		
	public Position(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	}
	
	
	 class Size
	 {
		
		int width, height;
		
		public Size(int width, int height )
		{
			this.width = width;
			this.height = height;
		}
	 }
	 
	 public MenuElement(int x, int y, int width, int height)
		{
			
			
			position = new Position(x,y);
			size = new Size(width, height);
			
			
		}
	 
	 
	 
	 public BufferedImage getTexture()
	 {
			return texture_active;
	 }
	 
	 
	 public void setActive()
	 {
		isActive = true;
	 }

	public void setInActive()
	{
		isActive = false;
	}
		
	public boolean isActive()
	{
		return isActive;
	}
	
	 
	public Position getPosition()
	{
		return position;
	}
			
	public Size getSize()
	{
		return size;
	}
	
	public int getTrigerHeight()
	{
		return trigerHeight;
	}
	
	public int getID()
	{
		return id;
	}
	
	public int getTrigerWidth()
	{
		return trigerWidth;
	}
	
	public void setTrigerHeight(int trigerHeight)
	{
		this.trigerHeight = trigerHeight;
	}
	
	public void setTrigerWidth(int trigerWidth)
	{
		this.trigerWidth = trigerWidth;
	}
	
	public void draw(Graphics gBuffer)
	{
		gBuffer.setColor(Color.RED);
		gBuffer.drawRect(position.x - trigerWidth, 
				position.y - trigerHeight,
				trigerWidth*2, trigerHeight*2);
	}
	
	public void pressed(){
	
	}
	
	public void hover()
	{
		
	}
	public void unHover()
	{
		
	}
	
	public boolean isCollide(int x, int y)
	{
		boolean isCollide = false;
		
		if ((x > this.getPosition().x - this.getTrigerWidth()) && 
				(x < this.getPosition().x +  this.getTrigerWidth()))
		{
			if ((y > this.getPosition().y - this.getTrigerHeight()) && 
					(y < this.getPosition().y +  this.getTrigerHeight()))
			{
				isCollide = true;
			}
		}
		return isCollide;
		
	}
	
	public void released()
	{
		
	}
	
	public void updatePosition(float x, float y)
	{
		position.x = (int) (position.x* x);
		position.y = (int) (position.y* y);
	}
}
