package Game;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Polygon;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;


import javax.imageio.ImageIO;
import javax.swing.JPanel;

public class GamePanel extends JPanel implements Runnable, MouseListener, KeyListener, MouseMotionListener{
	
	
	private boolean isRunning;
	private int rWidth;
	private int rHeight;

	private Engine engine ;
	private BufferedImage grass;
	private BufferedImage water;
	static Console console;
	
	private long startTime;
	private long spendTime;
	private int tick;
	private int FPS;
	private int FPS_Array[] = new int[30];
	private Tile[][] tile;
	private int shift;
	private int x0 = 0;
	
	public  GamePanel(int width, int height)
	{
		System.setProperty("sun.java2d.opengl", "true");
		rWidth = width;
		rHeight = height;
		console = new Console(20,0);
		engine = new Engine(rWidth,rHeight);	
		shift = engine.getShift();
		this.addMouseListener(this);
		this.addKeyListener(this);
		this.addMouseMotionListener(this);
		this.setFocusable(true);
		this.setPreferredSize(new Dimension(width, height));
		this.setBackground(Color.black);
		this.grabFocus();
	}
	
	public void initTextures()
	{
		
		try {
			grass = ImageIO.read(new File("./textures/grass.png"));
			water = ImageIO.read(new File("./textures/water.png"));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
		
		console.addField("FPS " + String.valueOf(FPS),1);
	}
	
	private void render()
	{
		this.setBackground(Color.WHITE);
		BufferedImage gBufferImage  = new BufferedImage(rWidth,rHeight, BufferedImage.TYPE_INT_ARGB);
		Graphics gBuffer = gBufferImage.createGraphics();
		gBuffer.fillRect(0, 0, this.getWidth(), this.getHeight());
		drawGround(gBuffer);
	//	drawGrid(gBuffer);
	//	drawFPS(gBuffer);
		drawPlayer(gBuffer);
	//	drawCenters(gBuffer);
		drawConsole(gBuffer);
		
		this.getGraphics().drawImage(gBufferImage, 0, 0,this.getWidth(), this.getHeight(), null );
	}
	
	public void drawConsole(Graphics gBuffer)
	{
		gBuffer.setColor(Color.red);
		int shift = 0;
		for (String field : console.getFields())
		{		
			if (field != null)
				gBuffer.drawString(field, console.getX(),  console.getY() + shift);
			shift = shift + 20;
		}
		
	}
	
	public void start()
	{
		initFPS();
		initTextures();
		isRunning = true;
		new Thread(this).start();
	}
	
	private void drawPlayer(Graphics gBuffer)
	{
		gBuffer.setColor(Color.red);
		gBuffer.drawOval(rWidth/2 , rHeight/2, 4, 4);
	}
	
	
	public void drawGrid(Graphics gBuffer)
	{
		
		gBuffer.setColor(Color.black);
		
		for (int i = -2 ; i < rWidth/64 + 2; i++)
		{
			for (int j = -2 ; j < rHeight/16 + 2; j++)
			{
				
				Polygon polygon = new Polygon();
				
				
				polygon.addPoint(64*i + 32, 32*j );
				polygon.addPoint(64*i + 64, 32*j + 16 );
				polygon.addPoint(64*i + 32, 32*j + 32 );
				polygon.addPoint(64*i,      32*j + 16 );
				
			
				gBuffer.drawPolygon(polygon);
			}
		}
	}
	
	public void drawCenters(Graphics gBuffer)
	{
		int moveX = engine.getMoveX();
		int moveY = engine.getMoveY();
	
		gBuffer.setColor(Color.red);
		
		if (engine.isReady())
		{
			
			
			 tile = engine.getDrawTiles();
			 
			 
			 if (shift != engine.getShift())
			 {	 
				 shift = engine.getShift();
				 if (x0 ==   32)
				 {					
					 x0 = 0;
					 
				 }
				 else 
				 {			   
					 x0=  32;
				 }				 
			 }
			
		}
		else
		{
			System.err.println();
		}
	
		for (int i = 0; i < tile.length; i++)
		{
			for (int j = 0; j <  tile[0].length; j++)
			{
				if (tile[i][j] != null)
				{
				
				
				if (j % 2 == 0)
				{
					
					//thread = new RenderThread(gBuffer,  rWidth, rHeight,  x0,  moveX,  moveY, tile, grass,  water);
				//	new Thread(thread).start();
					gBuffer.drawRect(i*64 +  x0 - moveX + 26 - 32, j*16 - moveY + 8 - 8,  64,16);
				//	gBuffer.setColor(Color.red);
					
				//	gBuffer.drawString(String.valueOf(i+2) + "," + String.valueOf(j+2), i*64  - x0 - moveX -
				//			gBuffer.getFontMetrics().stringWidth(String.valueOf(i+2) + "," + String.valueOf(j+2))/2, j*16 + 4 - moveY);
					
				}
				else 
				{
					gBuffer.drawRect(i*64 + 32 - x0 - moveX + 26 - 32, j*16 - moveY + 8 - 8, 64,16);
				
				//	gBuffer.setColor(Color.red);
					
					
				//	gBuffer.drawString(String.valueOf(i) + "," + String.valueOf(j),i*64 - 32 - x0 - moveX -
				//			gBuffer.getFontMetrics().stringWidth(String.valueOf(i) + "," + String.valueOf(j))/2, j*16 + 4 - moveY);
				}
				
				
		
				}
			}
		}
	}
	
	
	public void drawGround(Graphics gBuffer)
	{
	
		int moveX = engine.getMoveX();
		int moveY = engine.getMoveY();
	
		
		
		BufferedImage imageDraw = null;
		
		if (engine.isReady())
		{
			
			
			 tile = engine.getDrawTiles();
			 
			 
			 if (shift != engine.getShift())
			 {	 
				 shift = engine.getShift();
				 if (x0 ==   32)
				 {					
					 x0 = 0;
					 
				 }
				 else 
				 {			   
					 x0=  32;
				 }
						 
			 }
			
		}
		else
		{
			System.err.println();
		}
	
		for (int i = 0; i < tile.length; i++)
		{
			for (int j = 0; j <  tile[0].length; j++)
			{
				if (tile[i][j] != null)
				{
					
				switch (tile[i][j].get_id())
				{
					case 0 :  imageDraw = grass; break;
					
					case 1 : imageDraw = water; break;
					
					case 2 : imageDraw = water; break;
				
				}
				
				}
				else 
				{
					imageDraw = null;
				}
				
				console.addField("Move X " + moveX, 6);
				console.addField("Move Y " + moveY, 7);
				console.addField("X0 " + x0, 8);
			
				
				if (j % 2 == 0)
				{
					
					//thread = new RenderThread(gBuffer,  rWidth, rHeight,  x0,  moveX,  moveY, tile, grass,  water);
				//	new Thread(thread).start();
					gBuffer.drawImage(imageDraw, i*64  + x0 - moveX + 26 - 32, j*16 - moveY + 8 - 16, null);
				//	gBuffer.setColor(Color.red);
					
				//	gBuffer.drawString(String.valueOf(i+2) + "," + String.valueOf(j+2), i*64  - x0 - moveX -
				//			gBuffer.getFontMetrics().stringWidth(String.valueOf(i+2) + "," + String.valueOf(j+2))/2, j*16 + 4 - moveY);
					
				}
				else 
				{
					gBuffer.drawImage(imageDraw, i*64 + 32 - x0 - moveX + 26 -32 , j*16 - moveY  + 8 - 16, null);
				
				//	gBuffer.setColor(Color.red);
					
					
				//	gBuffer.drawString(String.valueOf(i) + "," + String.valueOf(j),i*64 - 32 - x0 - moveX -
				//			gBuffer.getFontMetrics().stringWidth(String.valueOf(i) + "," + String.valueOf(j))/2, j*16 + 4 - moveY);
				}
				
				
		
				}
		}
		/*
			for (int i = -2; i < rWidth/64 + 3; i++)
			{
				for (int j = -2; j <  rHeight/16 + 2; j++)
				{
				gBuffer.setColor(Color.black);
				Polygon polygon = new Polygon();
			
				
				polygon.addPoint(64*i + 32 + x0 - moveX, 32*j - moveY);
				polygon.addPoint(64*i + 64 + x0 - moveX, 32*j + 16 - moveY);
				polygon.addPoint(64*i + 32 + x0 - moveX, 32*j + 32 - moveY);
				polygon.addPoint(64*i + x0 - moveX,      32*j + 16 - moveY);
			
		
				gBuffer.drawPolygon(polygon);
				}
			}
			*/
		
		//	javax.media.j3d.ImageComponent2D canvas;
			
				//	gBuffer.drawImage(ground, (-1)*engine.getMoveX() - x0, (-1)*engine.getMoveY(), null);
	}
	
	
	@Override
	public void mouseDragged(MouseEvent arg0) {
	
	
	}

	@Override
	public void mouseMoved(MouseEvent arg0) {
	
	
	}

	@Override
	public void keyPressed(KeyEvent arg0) {
		
		
		
	}

	@Override
	public void keyReleased(KeyEvent arg0) {
		
		
	}

	@Override
	public void keyTyped(KeyEvent arg0) {
		
		
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

	@Override
	public void mousePressed(MouseEvent arg0) {
		
		if (arg0.getButton() == arg0.BUTTON1)
		{
			engine.mousePressed(arg0.getX(), arg0.getY());
		}
	}

	@Override
	public void mouseReleased(MouseEvent arg0) {
	
		
	}

	@Override
	public void run() {
		shift = engine.getShift();
		while(isRunning)
		{
			startTime = System.currentTimeMillis();
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
				
				tick = 0;		
			}
		}
		
		
	}

}
