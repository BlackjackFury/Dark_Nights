import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;


public class MenuTextField extends MenuElement{

	
	private String string = "";


	private BufferedImage texture;
	private BufferedImage texture_pointer;
	private int shift;
	private int textShift;
	private int font_size = 25;
	private int actionTime;
	private boolean isFull = false;
	
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
	 
	 public MenuTextField(int x, int y, int width, int height, int font_size, BufferedImage texture, BufferedImage texture_pointer)
	 {
		 
		 	super(x,y,width,height);
		 	
		 	this.font_size = font_size;

			if (texture != null && texture_pointer != null)
			{
				this.texture = texture;
				this.texture_pointer = texture_pointer;
			}
			trigerWidth = (int) ( size.width/2 -  size.width*0.13 ) ;
			trigerHeight =  size.height/4;
	 }
	 
	 public void addChar(char new_char)
	 {
		 actionTime = (int) (System.currentTimeMillis() % 1000);
		 if (	(new_char  >= 'A' && new_char <= 'Z')  || (new_char  >= 'a' && new_char <= 'z') || new_char == ' ')
		 {
			 string = string + new_char;
			 if (isFull)
			 {
				 	//textShift ++;
				 	System.out.println(textShift);
			 }
		 }
		 
	 }
	 
	 public void removeChar()
	 {
		 actionTime = (int) (System.currentTimeMillis() % 1000);
		 if (string.length() != 0)
		 {
			 string = string.substring(0, string.length() - 1);
			 if (isFull)
			 {
				 if (textShift != 0)
				 {
					// textShift --;
					 System.out.println(textShift);
				 }
			 }
		 }
		 
	 }
	 
	
	
	public BufferedImage getTexture()
	{
		return texture;
	}
	
	public String getText()
	{
		return string;
	}
	
	
	
	public void setShift(int shift)
	{
		
		if (shift > this.trigerWidth*2 - this.trigerWidth*2/20)
		{
			
			this.shift = this.trigerWidth*2 - this.trigerWidth*2/10;
			//System.out.println("=====" + this.shift);
			isFull = true;
		//	textShift++;
		}
		else 
		{
			this.shift = shift;
			isFull = false;
		}
		
	
	}
	
	public int getShift()
	{
		return shift;
	}
	
	public int getFontSize()
	{
		return font_size;
	}
	
	public void setFontSize(int fontSize)
	{
		this.font_size = fontSize;
	}
	
	public void shiftText()
	{
		textShift++;
	}
	
	@Override
	public void setActive()
	{
		 
	}
	
	@Override
	public void setInActive()
	{
		isActive = false;
	}
	
	@Override
	public void released()
	{
	//	isActive = false;
	}
		
	public void unHover()
	{
		
	}
	@Override
	public void pressed()
	{
		actionTime = (int) (System.currentTimeMillis() % 1000);
		isActive = true;
	}
	
	public void draw(Graphics gBuffer)
	{
		gBuffer.setFont(new java.awt.Font("2 Prong Tree", 0, 30) );
		gBuffer.setColor(new Color(151, 127, 67));
		
		
			gBuffer.drawImage(this.getTexture(), this.getPosition().x - this.getSize().width/2,
					this.getPosition().y - this.getSize().height/2,
					this.getSize().width, this.getSize().height, null);
		
			
		//	gBuffer.drawString( this.getText(), this.getPosition().x, this.getPosition().y + 25);
			
			this.setFontSize(30);
			
			this.setShift(gBuffer.getFontMetrics().stringWidth(this.getText().substring(textShift)));
			
		//	System.out.println(textShift);
		//	System.out.println(this.getText().substring(textShift));
		//	System.out.println(gBuffer.getFontMetrics().stringWidth(this.getText().substring(textShift)));
			
			textShift = 0;
			while (gBuffer.getFontMetrics().stringWidth(this.getText().substring(textShift)) >  (this.trigerWidth*2 - this.trigerWidth*2/20))
					{
						textShift ++;
					}
				
				gBuffer.drawString( this.getText().substring(textShift),
						this.getPosition().x - this.trigerWidth, 
						this.getPosition().y + this.trigerHeight - 6);
			
				
			
		
			
			if(this.isActive() && ((((System.currentTimeMillis()%1000) > actionTime) && (System.currentTimeMillis()%1000) < (actionTime + 500 ) && actionTime < 500) 
						|| ((((System.currentTimeMillis()%1000) > actionTime) || (System.currentTimeMillis()%1000) < (actionTime + 500 )%1000) && actionTime > 500)) )
			{
				
				gBuffer.drawImage(texture_pointer, this.getPosition().x - this.trigerWidth + this.getShift() - 12,
						this.getPosition().y - this.trigerHeight,
							33, 33, null);	
				
				
			}
			
			if (Settings.Collisers)
			{
			gBuffer.setColor(Color.red);
			
			gBuffer.drawRect( this.getPosition().x - this.getTrigerWidth(),
					this.getPosition().y - this.getTrigerHeight(),
					this.getTrigerWidth()*2, this.getTrigerHeight()*2);
			
			gBuffer.drawRect(this.getPosition().x - this.trigerWidth + this.getShift(),
					this.getPosition().y - this.trigerHeight, 3,40);
			}
	}
	
	
}
