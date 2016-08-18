import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.sound.sampled.FloatControl;


public class MenuSlider extends MenuElement{

	
	private BufferedImage texture_back;
	private BufferedImage texture_button;
	private BufferedImage texture_fill;
	
	private int shift = 30;

	
	private int fill_x;
	private int fill_y;
	
	private int button_x;
	private int button_y;
	private float percent;
	
	private FloatControl vc;
	
	 public MenuSlider(int x, int y, int width, int height, int id, FloatControl vc ,BufferedImage texture_back, BufferedImage texture_button, BufferedImage texture_fill)
		{
			
			super(x,y,width,height);
			this.id = id;
			if (vc != null)
			{
				this.vc = vc;
			//	vc.setValue((vc.getMaximum() - vc.getMinimum())*(Settings.MUSIC_VOLUME/100) + vc.getMinimum());	
			//	this.setShift(Settings.MUSIC_VOLUME);
			//	this.vc.setValue((vc.getMaximum() - vc.getMinimum())*( this.getPercent()/100) + vc.getMinimum());
			}
				
			if (texture_back != null && texture_button != null && texture_fill != null)
			{
				this.texture_back = texture_back;
				this.texture_button = texture_button;
				this.texture_fill = texture_fill;
				
			}
			
			trigerWidth = (int) ( size.width/2 -  size.width*0.13 ) ;
			trigerHeight =  size.height/4;
			
			fill_x = -6;
			fill_y = trigerWidth/2;
			button_y = -4;
			button_x = -25;
			
		}
	 	
	 	
	 
		
		public BufferedImage getBackTexture()
		{
			return texture_back;
		}
		
		public BufferedImage getButtonTexture()
		{
			return texture_button;
		}
		
		public BufferedImage getFillTexture()
		{
			return texture_fill;
		}
			
		public void setShift(float percent)
		{
		
			shift = (int) ((trigerWidth * 2 - size.height/2)*(percent/100));
			
		}
		
		public void setShift(int shift)
		{
			if (shift < 1)
			{
				this.shift = 1;
				
			}
			else
			
			if (shift >   trigerWidth * 2 - size.height/2)
			{
				this.shift =   trigerWidth * 2 - size.height/2;
				
			}
			else
			{
				this.shift = shift;
				
			}
			
			percent = ((float)this.shift/(trigerWidth*2 - size.height/2))*100;
			if (vc != null)
			{
				this.vc.setValue((vc.getMaximum() - vc.getMinimum())*( this.getPercent()/100) + vc.getMinimum());
			}
			
		}
		
		public int getShift()
		{
			return shift;
		}
		
		public float getPercent()
		{
			return percent;
		}
		
		
		public int getFill_x()
		{
			return fill_x;
		}
		
		public int getFill_y()
		{
			return fill_y;
		}
		
		public int getButton_x()
		{
			return button_x;
		}
		
		public int getButton_y()
		{
			return button_y;
		}
		@Override
		public void pressed()
		{
			isActive = true;
		}
		
		@Override
		public void released()
		{
			isActive = false;
		}
		
			
		
		public void draw(Graphics gBuffer)
		{
			gBuffer.drawImage(this.getBackTexture(), this.getPosition().x - this.getSize().width/2, 
					this.getPosition().y - this.getSize().height/2,
					this.getSize().width, this.getSize().height, null);
			
	
			
			BufferedImage buff = this.getFillTexture();
			
			
			
			if (buff != null)
			{
				buff = buff.getSubimage(buff.getWidth() - (buff.getWidth()/(this.getTrigerWidth()*2))*this.getShift(), 
				   	0,
					(buff.getWidth()/(this.getTrigerWidth()*2))*this.getShift(),
					 buff.getHeight());
			
			}
			
			gBuffer.drawImage(buff, 
					this.getPosition().x - this.getTrigerWidth() + this.getFill_x(), 
					this.getPosition().y - this.getSize().height/2,
					this.getShift() - this.getFill_x()*3, this.getSize().height, null);
			
			gBuffer.drawImage(this.getButtonTexture(), 
					this.getPosition().x - this.getTrigerWidth() + this.getShift() , 
					this.getPosition().y - this.getSize().height / 4 + this.getButton_y(),
					this.getSize().height  / 2  , this.getSize().height / 2 , null);
			
			gBuffer.setColor(Color.RED);
			
			if (Settings.Collisers)
			{
				gBuffer.drawRect(this.getPosition().x - this.getTrigerWidth() + this.getShift() , 
					this.getPosition().y - this.getSize().height / 4 + this.getButton_y(),
					this.getSize().height  / 2  , this.getSize().height / 2);
			
				gBuffer.drawRect(this.getPosition().x - this.getTrigerWidth(), 
					this.getPosition().y - this.getTrigerHeight(),
					this.getTrigerWidth()*2, this.getTrigerHeight()*2);
			
				gBuffer.drawRect( 
					this.getPosition().x - this.getTrigerWidth() + this.getFill_x(), 
					this.getPosition().y - this.getSize().height/2,
					this.getShift() - this.getFill_x()*3, this.getSize().height);
			}
		}
		
}
