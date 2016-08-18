import java.awt.Graphics;
import java.awt.image.BufferedImage;



public class MenuCheckBox extends MenuElement{

	

	private BufferedImage texture_active;
	private BufferedImage texture_check;
	private BufferedImage texture_box;
	private BufferedImage texture_hover;
	

	private boolean isChecked = false;
	

	 
	 public MenuCheckBox(int x, int y, int width, int height, BufferedImage texture_box, BufferedImage texture_check, BufferedImage texture_hover)
		{
			
			super(x,y,width,height);
			
			if (texture_box != null && texture_check != null)
			{
				this.texture_box = texture_box;
				this.texture_check = texture_check;
				this.texture_hover = texture_hover;
				this.texture_active = texture_box;
			}
			
			trigerWidth = size.width/2;
			trigerHeight = size.height/2;
			
		}
	 
	 
	 
	 public BufferedImage getBoxTexture()
	 {
			return texture_active;
	 }
	 
	 public BufferedImage getCheckTexture()
	 {
			return texture_check;
	 }
	 
	
	
	public boolean isChecked()
	{
		return isChecked;
	}
	 
	public void check()
	{
		isChecked = true;
	}
	
	public void unCheck()
	{
		isChecked = false;
	}
	
	
	
	@Override
	public void unHover()
	{
		texture_active = texture_box;
	}
	
	@Override
	public void hover()
	{
		 texture_active = texture_hover;
	}
		
	@Override
	public void pressed()
	{
		if (!isChecked())
		{
			check();
		}
		else 
		{
			unCheck();
		}
	}
	 
	public void draw(Graphics gBuffer)
	{
		gBuffer.drawImage(this.getBoxTexture(), this.getPosition().x - this.getSize().width/2, 
				this.getPosition().y - this.getSize().height/2,
				this.getSize().width, this.getSize().height, null);
		
		if (this.isChecked())
		{
			gBuffer.drawImage(this.getCheckTexture(), this.getPosition().x - this.getSize().width/2, 
					this.getPosition().y - this.getSize().height/2,
					this.getSize().width, this.getSize().height, null);
		}	
		
			if (Settings.Collisers)
			{
				gBuffer.drawRect( this.getPosition().x - this.getSize().width/2, 
						this.getPosition().y - this.getSize().height/2,
						this.getSize().width, this.getSize().height);
			}
		
	}
	
}
