import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class MenuLabel extends MenuElement {

	
	
	
	private String label;
	private Color color = new Color(151, 127, 67);
	private java.awt.Font font = new java.awt.Font("2 Prong Tree", 0, 25);
	
	
	
	public MenuLabel(int x, int y, int width, int height, String label, BufferedImage texture ) {
		super(x, y, width, height);
		
		if (texture != null)
		{
			this.texture_active = texture;
		}
		this.label = label;
	}
	
	public void setColor(Color color)
	{
		this.color = color;
	}
	
	public void setFont(java.awt.Font font)
	{
		this.font = font;
	}

	public String getLabel()
	{
		return label;
	}
	
	public void setLabel(String label)
	{
		this.label = label;
	}
	
	public void draw(Graphics gBuffer)
	{
		gBuffer.setFont(font);
		gBuffer.setColor(color);
		
		gBuffer.drawImage(this.getTexture(), this.getPosition().x - this.getSize().width/2, 
				this.getPosition().y - this.getSize().height/2,
				this.getSize().width, this.getSize().height, null);
		
		
		
		gBuffer.drawString(this.getLabel(), this.getPosition().x  - gBuffer.getFontMetrics().stringWidth(this.getLabel())/2, this.getPosition().y );
		
		if (Settings.Collisers)
		{
		gBuffer.setColor(Color.red);
		gBuffer.drawRect( this.getPosition().x - this.getSize().width/2, 
				this.getPosition().y - this.getSize().height/2,
				this.getSize().width, this.getSize().height);
		}
	}
}
