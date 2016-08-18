import java.awt.Dimension;
import java.awt.Toolkit;

import javax.swing.JFrame;

public class Window extends JFrame {

	private int windowWidth;
	private int windowHeight;
	private Menu menu = new Menu();
	private Logo logo = new Logo();
	private double screenWidth;
	private double screenHeight;
	private long startTime;
	private long spendTime;
	
	
	public Window(int width, int height)
	{
		windowWidth = width;
		windowHeight = height;
		init();
	}
	
	public void init()
	{
		
		this.setSize(windowWidth, windowHeight);
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		screenWidth = screenSize.getWidth();
		screenHeight =  screenSize.getHeight();
		
		
	
		//this.setUndecorated(true);
		//this.setResizable(false);
		
		

		this.setLocation((int)screenWidth/2 - windowWidth/2,(int)screenHeight/2 - windowHeight/2);
		
		this.setVisible(true);
		this.setDefaultCloseOperation(EXIT_ON_CLOSE);
		
		this.add(logo);
		logo.start();
		//this.setResizable(false);
		this.pack();
	
		startTime = System.currentTimeMillis();
		
		menu.init();
	
		spendTime = System.currentTimeMillis() - startTime;
		
		if (spendTime < 4000)
		{
			try {
				Thread.sleep(4000 - spendTime);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		logo.stop();
		
		this.add(menu);
		menu.start();
		
		this.pack();
		
		
		while (menu.isRunning())
		{
			try {
				Thread.sleep(40);
			} catch (InterruptedException e) {
				
				e.printStackTrace();
			}
		}
		
		this.add(logo);
		logo.start();
		
	}
	
	public void startSingleGame()
	{
		
	}
}
