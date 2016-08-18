import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.sound.sampled.Clip;


public class MenuButton extends MenuElement{

	

	

	private BufferedImage texture;
	private BufferedImage texture_pressed;
	private BufferedImage texture_hover;
	private String name = "";
	private Clip sound;
	
	
	
	
	
	
	public MenuButton(int x, int y, int width, int height,String name, int id, BufferedImage texture, BufferedImage texture_pressed, BufferedImage texture_hover, Clip sound)
	{
		super(x,y,width,height);
		this.name = name;
		this.id = id;
		if (sound != null)
		{
			this.sound = sound;
		}
		
		if (texture != null && texture_pressed != null)
		{
			this.texture = texture;
			this.texture_pressed = texture_pressed;
			this.texture_active = texture;
			this.texture_hover = texture_hover;
		}
		
		trigerWidth = (int) ( size.width/2 -  size.width*0.13 ) ;
		trigerHeight =  size.height/4;
	
	}
	
	public String getName()
	{
		return name;
	}
	
	public BufferedImage getTexture()
	{
		return texture_active;
	}
	
	
	
	@Override
	public void released()
	{
		texture_active = texture;
	}
	
	@Override
	public void unHover()
	{
		texture_active = texture;
	}
	
	@Override
	public void hover()
	{
		 texture_active = texture_hover;
	}
	
		
	@Override
	public void pressed()
	{
		texture_active = texture_pressed;
		playSound();
	}
	
	private void playSound()
	{
		if (sound != null)
		{
			sound.setFramePosition(0);
			sound.start();
		}
	}

	
	@Override
	public void draw(Graphics gBuffer)
	{
		gBuffer.setFont(new java.awt.Font("2 Prong Tree", 0, 30) );
		gBuffer.setColor(new Color(151, 127, 67));
		
		
		
			gBuffer.drawImage(this.getTexture(), this.getPosition().x - this.getSize().width/2,
					this.getPosition().y - this.getSize().height/2,
					this.getSize().width, this.getSize().height, null);
			
			
			gBuffer.setFont(new java.awt.Font("2 Prong Tree", 0, 30) );
			gBuffer.setColor(new Color(151, 127, 67));
			
		
			gBuffer.drawString(this.getName(), this.getPosition().x  - gBuffer.getFontMetrics().stringWidth(this.getName())/2, this.getPosition().y  + 13);
			
			if (Settings.Collisers)
			{
				gBuffer.setColor(Color.RED);
				gBuffer.drawRect(this.getPosition().x - this.getTrigerWidth(), 
					this.getPosition().y - this.getTrigerHeight(),
					this.getTrigerWidth()*2, this.getTrigerHeight()*2);
			}
	}

}
