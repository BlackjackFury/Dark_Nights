import java.awt.Dimension;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class Logo extends JPanel implements Runnable{
	
	private BufferedImage logo = null;
	private boolean isRunning ;
	public Logo()
	{
		init();
	}
	

	public void init()
	{
		
		this.setPreferredSize(new Dimension(1200, 720));
		
		try {
			logo = ImageIO.read(new File("./textures/logo.jpg"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		//if (logo == null)
		//		System.out.println(1);
		
		
	
	}
	
	public void start()
	{
		isRunning = true;
		new Thread(this).start();
	}
	
	public void stop()
	{
		isRunning = false;
	}
	
	public void render()
	{
		
		this.getGraphics().drawImage(logo, 0, 0, 1200, 720, null);
		
	}

	@Override
	public void run() {
		while(isRunning)
		{
			render();
		}
		
	}
}
