import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.FontFormatException;
import java.awt.Graphics;
import java.awt.GraphicsEnvironment;
import java.awt.Image;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.FloatControl;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JPanel;

public  class Menu extends JPanel implements Runnable, MouseListener, KeyListener, MouseMotionListener
{
	
	
	public static Scene[] scenes = new Scene[1];	
	private Scene activeScene;
	private boolean isRunning;
	
	private int width = 1200;
	private int height = 720;
	
	private Actions actions = new Actions();
	
	
	private BufferedImage mainMenuBack;
	private BufferedImage button;
	private BufferedImage button_hover;	
	private BufferedImage button_pressed;
	private BufferedImage textField;
	private BufferedImage palochka;
	private BufferedImage slider_back;
	private BufferedImage slider_button;
	private BufferedImage slider_fill;
	private BufferedImage check_box;
	private BufferedImage check_box_hovered;
	private BufferedImage check_mark;
	private BufferedImage label;
	private BufferedImage label_2;
	private BufferedImage button_arrow_right;
	private Image cursor;
	private Image cursor_lite;
	private Cursor c_lite;
	private Cursor c;
	private Toolkit toolkit = Toolkit.getDefaultToolkit();
	
	private Clip buttonSound;
	private Clip gameSound;
	
	private FloatControl vc;
	private FloatControl buttonVc;
	
	private long startTime;
	private long spendTime;
	
	private int tick;
	private int FPS;
	private int FPS_Array[] = new int[30];
	
	private FileWorker file = new FileWorker();
	
	public  Menu()
	{
		this.addMouseListener(this);
		this.addKeyListener(this);
		this.addMouseMotionListener(this);
		this.setFocusable(true);
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.black);
		this.grabFocus();
	}
	
	public boolean isRunning()
	{
		return isRunning;
	}

	public void addScene(Scene scene)
	{
		Scene[] buff = new Scene[scenes.length];
		
		for (int i = 0; i < scenes.length; i ++)
		{
			buff[i] = scenes[i];
		}
		
		scenes = new Scene[buff.length + 1];
		
		for (int i = 0; i < buff.length; i ++)
		{
			scenes[i] = buff[i];
		}
		scenes[scenes.length - 1] = scene;
	}
	
	public void init()
	{
		file.parseFile();
		createScenes();
		initFPS();
		
	}
	
	public void start()
	{	
		this.setFocusable(true);
		this.grabFocus();
		isRunning = true;
		new Thread(this).start();
		
	}
	
	public void initTextures()
	{
		try {
			mainMenuBack = ImageIO.read(new File("./textures/main_menu_back.png"));
			button = ImageIO.read(new File("./textures/button.png"));
			button_hover = ImageIO.read(new File("./textures/button_hover.png"));
			palochka = ImageIO.read(new File("./textures/palochka.png"));
			button_pressed = ImageIO.read(new File("./textures/button_pressed.png"));
			cursor = toolkit.getImage("textures/cursor.png");
			cursor_lite = toolkit.getImage("textures/cursor_lite.png");
			textField = ImageIO.read(new File("./textures/textfield.png"));
			check_box = ImageIO.read(new File("./textures/checkbox.png"));
			check_box_hovered = ImageIO.read(new File("./textures/checkbox_hover.png"));
			check_mark = ImageIO.read(new File("./textures/check_mark.png"));
			slider_back = ImageIO.read(new File("./textures/slider_bar_back1.png"));
			slider_button = ImageIO.read(new File("./textures/slider_button.png"));
			slider_fill = ImageIO.read(new File("./textures/slider_fill.png"));
			label = ImageIO.read(new File("./textures/label.png"));
			label_2 = ImageIO.read(new File("./textures/label_2.png"));
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}
	
	public void initFont()
	{

		     GraphicsEnvironment ge = 
		         GraphicsEnvironment.getLocalGraphicsEnvironment();
		     try {
		    	 
				ge.registerFont(java.awt.Font.createFont(java.awt.Font.TRUETYPE_FONT, new File("./textures/MenuFont.ttf")));
				
			} catch (FontFormatException | IOException e) {
			
				e.printStackTrace();
			}
   		     
	}
	
	public void initSounds()
	{
		
		try {
			File soundFile = new File("./sounds/snd.wav");
			AudioInputStream ais = AudioSystem.getAudioInputStream(soundFile);
			
			File button_sound_file = new File("./sounds/button_sound.wav");
			AudioInputStream button_sound_stream = AudioSystem.getAudioInputStream(button_sound_file);
			
			buttonSound = AudioSystem.getClip();
			buttonSound.open(button_sound_stream);
			
			gameSound = AudioSystem.getClip();
			gameSound.open(ais);
			gameSound.setFramePosition(0); 

			
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {

			e.printStackTrace();
			
		}	
		vc = (FloatControl) gameSound.getControl(FloatControl.Type.MASTER_GAIN);
		vc.setValue((vc.getMaximum() - vc.getMinimum())*(Settings.settings[0]/100) + vc.getMinimum());	
		buttonVc = (FloatControl) buttonSound.getControl(FloatControl.Type.MASTER_GAIN);
		buttonVc.setValue((buttonVc.getMaximum() - buttonVc.getMinimum())*(Settings.settings[1]/100) + buttonVc.getMinimum());	
		
		gameSound.start(); 

	}
	
	public void setSliders()
	{
		for (int j = 0; j < scenes.length; j++)
		{
			for(int i = 0 ; i < scenes[j].getElememts().length ; i ++)
			{
				if (scenes[j].getElememts()[i] instanceof MenuSlider)
				{
					actions.setUpSliders((MenuSlider) scenes[j].getElememts()[i]);
				}
			}
		}
	}
	
	public void createScenes()
	{	
		initTextures();
		initFont();
		initSounds();
		
		Scene mainScene = new Scene();	
		this.addScene(mainScene);
		setActiveScene(mainScene);
		
		
		
		mainScene.add(new MenuBackground(0,0, width, height , mainMenuBack));
		mainScene.add(new MenuButton(width/2, 200, 300, 75,"Singleplayer", -10 , button, button_pressed, button_hover, buttonSound));
		mainScene.add(new MenuButton(width/2, 300, 300, 75,"Multiplayer", 2, button, button_pressed, button_hover, buttonSound));
		mainScene.add(new MenuButton(width/2, 400, 300, 75,"Settings", 4, button, button_pressed, button_hover, buttonSound));
		mainScene.add(new MenuButton(width/2, 500, 300, 75,"Exit", 0, button, button_pressed, button_hover, buttonSound));
		
		Scene multiScene = new Scene();
		this.addScene(multiScene);
		
		MenuLabel multiOut = new MenuLabel(width/2, 300, 350, 75, "", null );
		multiOut.setColor(Color.RED);
		multiOut.setFont(new java.awt.Font("Arial", 0, 20));
		actions.setMultiOut(multiOut);
		
		MenuTextField nameField = new MenuTextField(width/2,200,350,75, 15, textField,palochka);
		MenuTextField passField = new MenuTextField(width/2,250,350,75, 15, textField, palochka);
		actions.setNameField(nameField);
		actions.setPassField(passField);
		
		multiScene.add(new MenuBackground(0,0,width, height, mainMenuBack));
		
		multiScene.add(new MenuButton(width/2, 400, 300, 75, "log in", 3, button,button_pressed, button_hover,buttonSound));
		multiScene.add(new MenuButton(width/2, 500, 300, 75 ,"return", 1, button, button_pressed, button_hover,buttonSound));
		
		multiScene.add(nameField);
		multiScene.add(passField);
		multiScene.add(multiOut);
		
		Scene connectScene = new Scene();
		this.addScene(connectScene);
		connectScene.add(new MenuBackground(0,0,width, height, mainMenuBack));
		MenuLabel connectLabel = new MenuLabel(width/2, height/2, 300, 75, "Connecting ...", null );
		connectLabel.setColor(Color.BLACK);
		connectLabel.setFont(new java.awt.Font("2 Prong Tree", 0, 50));
		connectScene.add(connectLabel);
		
		Scene settingsScene = new Scene();
		this.addScene(settingsScene);
		
		MenuCheckBox collidersBox = new MenuCheckBox(300,400, 60, 60, check_box, check_mark , check_box_hovered );
		actions.setCollidersSwitch(collidersBox);
		settingsScene.add(new MenuBackground(0,0,width, height, mainMenuBack));
		settingsScene.add(new MenuButton(width/2, 500, 300, 75,"return",1, button, button_pressed, button_hover,buttonSound));
		
		settingsScene.add(new MenuLabel(160, 155, 300, 75, "Music Sound", label_2 ));
		settingsScene.add(new MenuSlider(160, 200, 300, 75, 100,vc, slider_back, slider_button, slider_fill));
		
		settingsScene.add(new MenuLabel(160, 265, 300, 75, "Button Sound", label_2 ));
		settingsScene.add(new MenuSlider(160, 310, 300, 75, 101, buttonVc, slider_back, slider_button, slider_fill));
		settingsScene.add(new MenuLabel(130, 400, 250, 75, "Colliders", label_2 ));
		settingsScene.add(collidersBox);
		
		setSliders();
	}
	
	public void setActiveScene(Scene scene)
	{
		actions.setScene(scene);
		activeScene = scene;
		scenes[0] = scene;
	}
	
	private void render()
	{
		BufferedImage gBufferImage  = new BufferedImage(width,height, BufferedImage.TYPE_INT_ARGB);
		Graphics gBuffer = gBufferImage.createGraphics();
		//gBuffer.fillRect(0, 0, 1200, 800);
		activeScene.drawScene(gBuffer);
		
		drawFPS(gBuffer);
		
		this.getGraphics().drawImage(gBufferImage, 0, 0,this.getWidth(), this.getHeight(), null );
		
	}

	private void drawFPS(Graphics gBuffer)
	{
		gBuffer.setColor(Color.RED);
		gBuffer.setFont(new java.awt.Font("Arial", 0, 25));
		gBuffer.drawString(Integer.toString(FPS), 0, 25);
	}
	
	public void initFPS()
	{
		for (int i = 0; i < FPS_Array.length; i ++)
		{
			FPS_Array[i] = 0;
		}
	}
	
	private void FPSCounter()
	{
		
		int bFPS = (int) (1000/(spendTime+0.001));
		
		FPS_Array[tick] = bFPS;
		int buffFPS = 0;
		for (int i = 0; i < FPS_Array.length; i++)
		{
			
			buffFPS = FPS_Array[i] + buffFPS;
		}
		if (tick == 25)
			FPS = buffFPS/FPS_Array.length;
	}
	
	private void sizeCheck()
	{
		if (this.getWidth() != width || this.getHeight() != height)
		{
			//System.out.println(this.getHeight()/(float)height);
			for (int i = 1; i < scenes.length; i ++)
			{	System.out.println(this.getWidth()/(float)width);
				scenes[i].updateColliders(this.getWidth()/(float)width, this.getHeight()/(float)height);
			}
			width = this.getWidth();
			height = this.getHeight();
		}
			
	}
	
	@Override
	public void run() 
	{
		
		
		while(isRunning)
		{
			if (!gameSound.isRunning())
			{
				
				gameSound.setFramePosition(0); 
				gameSound.start(); 
			}
		
			startTime = System.currentTimeMillis();
			activeScene = scenes[0];
			render();
			spendTime = System.currentTimeMillis() - startTime;
			
			if (spendTime < 16.7)
			{
				try {
					Thread.sleep(16 - spendTime);
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}
			}
			spendTime = System.currentTimeMillis() - startTime;
			
			FPSCounter();
			
			tick ++;
			
			if (tick == 30)
			{
				sizeCheck();
				tick = 0;		
			}
			
			
			if (!actions.isActive())
			{
				isRunning = false;
			}
		}
	}
	
	@Override
	public void mouseClicked(MouseEvent arg0) {
		
	}
	
	@Override
	public void mouseEntered(MouseEvent arg0) {
		
		
	}
	@Override
	public void mouseExited(MouseEvent arg0) {
		
		
	}
	@SuppressWarnings("static-access")
	@Override
	public void mousePressed(MouseEvent arg0) {
	
		
		
		
		if (arg0.getButton() == arg0.BUTTON1)
		{
			
			for (int i = 0; i < activeScene.getElememts().length; i ++)
			{
				if (activeScene.getElememts()[i].isCollide(arg0.getX(), arg0.getY()))
				{		
						 activeScene.getElememts()[i].pressed();
						 actions.update();
				}
					else 
					{
						if (activeScene.getElememts()[i] instanceof MenuTextField )
						{
							
							activeScene.getElememts()[i].setInActive();
						}
					}
			}		
		}
	}
	
	
	@SuppressWarnings("static-access")
	@Override
	public void mouseReleased(MouseEvent arg0) {
		
		if (arg0.getButton() == arg0.BUTTON1)
		{
			
		
		for (int i = 0; i < activeScene.getElememts().length; i ++)
		{
			
			activeScene.getElememts()[i].released();
			
			if (activeScene.getElememts()[i].isCollide(arg0.getX(), arg0.getY()))
			{
					if (activeScene.getElememts()[i] instanceof MenuButton)
					{
						this.activeScene.buttonPressed(activeScene.getElememts()[i].getID());
						actions.action(activeScene.getElememts()[i].getID());
					}		
			}	
		}
		}
		
		
		
		
		
	}
	@Override
	public void keyPressed(KeyEvent arg0) {
		
		for (int i = 0; i < activeScene.getElememts().length; i ++)
		{
			if (activeScene.getElememts()[i] instanceof MenuTextField && activeScene.getElememts()[i].isActive())
			{
				if (arg0.getKeyCode() == 8)
				{
					((MenuTextField) activeScene.getElememts()[i]).removeChar();
				}
				else
				{
					((MenuTextField) activeScene.getElememts()[i]).addChar(arg0.getKeyChar());
				}
				
			}
		}
		
		
	}
	@Override
	public void keyReleased(KeyEvent arg0) {
		
		
	}
	@Override
	public void keyTyped(KeyEvent arg0) {

		
		
	}
	@Override
	public void mouseDragged(MouseEvent arg0) {
		
		for (int i = 0; i < activeScene.getElememts().length; i ++)
		{
			if (activeScene.getElememts()[i] instanceof MenuSlider && activeScene.getElememts()[i].isActive())
			{
				
				((MenuSlider) activeScene.getElememts()[i]).setShift(arg0.getX() - activeScene.getElememts()[i].getPosition().x + 
						activeScene.getElememts()[i].getTrigerWidth() - activeScene.getElememts()[i].getSize().height/4);
			
			}
		}
		
		
	}
	@Override
	public void mouseMoved(MouseEvent arg0) 
	{
	
		for (int i = 0; i < activeScene.getElememts().length; i ++)
		{
			if (activeScene.getElememts()[i].isCollide(arg0.getX(), arg0.getY()))
			{
					activeScene.getElememts()[i].hover();
			}
				else 
				{
					
					activeScene.getElememts()[i].unHover();	
				}
		}
		
		
	}
	
	
}
