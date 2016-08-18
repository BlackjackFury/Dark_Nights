import java.awt.Image;
import java.awt.image.BufferedImage;

public class Texture {

	
	private BufferedImage texture;
	private int textureID;
	private int width;
	private int height;
	
	public Texture(Image texture, int id, int height, int width)
	{
		this.texture = (BufferedImage)texture.getScaledInstance(width, height, Image.SCALE_SMOOTH );
	}
	
	public BufferedImage getTexture()
	{
		return texture;
	}

	public int getID()
	{
		return textureID;
	}
	
	public int getWidth()
	{
		return width;
	}
	
	public int getHeight()
	{
		return height;
	}
}
