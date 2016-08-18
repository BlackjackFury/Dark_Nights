import java.awt.Graphics;
import java.awt.Image;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class MenuBackground extends MenuElement{

	
	private Clip sound;
	
	public MenuBackground(int x, int y, int width, int height, BufferedImage texture) {
		super(x, y, width, height);
		
		
		if (texture != null)
		{
			this.texture_active = texture;
		}
		position.x = width;
		position.y = height;
	}
	
	public void setSound(File sound)
	{
		try {	 
			
			AudioInputStream ais = AudioSystem.getAudioInputStream(sound);
			this.sound = AudioSystem.getClip();
			this.sound.open(ais);
			
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
			
			e.printStackTrace();
		}
	}
	
	public void playSound()
	{
		if (!this.sound.isRunning())
		{
			this.sound.setFramePosition(0); 
			this.sound.start(); 
		}
	}
	
	public void draw(Graphics gBuffer)
	{
		if (sound != null)
		{
			playSound();
		}
		gBuffer.drawImage(texture_active, 0, 0 ,position.x, position.y,null);
		
	}

}
