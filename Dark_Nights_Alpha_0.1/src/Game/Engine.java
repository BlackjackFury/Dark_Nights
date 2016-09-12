package Game;

import java.lang.instrument.Instrumentation;

public class Engine implements Runnable
{
	
	
	public static Map map = new Map();
	private boolean isRun;
	private boolean isReady;

	
	
	private int vector_x, vector_y;
	private double cos, sin;
	
	private int height, width;
	
	private int distance ;
	private double real_x, real_y;
	private Tile[][] buff;
	private int shiftX, shiftY, shift;
	private Player player;
	
	public Engine(int width, int height)
	{
		this.width = width;
		this.height = height;
		player = new Player(0,0);
		real_x = player.getX();
		real_y = player.getY();
		start();
	}
	
	public int getMoveX()
	{
		return shiftX;
	}
	
	public int getMoveY()
	{
		return shiftY;
	}
	
	
	
	public void start()

	{
		
		isRun = true;	
		new Thread(this).start();
		
	}
	
	public void formTiles()
	{
		
	    Tile[][]  buff = new Tile[width/64 + 8][height/16 + 8];
		int iter1 = 0, iter2 = 0;
	//	System.out.println("i "+(player.getX()/64 - width/128) + " " + (player.getX()/64 + width/128));
	//	System.out.println( "j "+(player.getY()/16 - height/32) + " " + (player.getY()/16 + height/32));
		
		for (int i = player.getX()/64 - width/128 ; i < player.getX()/64 + width/128 + 8; i++)
		{
			
			for (int j = player.getY()/16 - height/32; j < player.getY()/16 + height/32 + 5; j++)
			{
				if ( i >= 0 && j >= 0
						&& i < map.getTiles().length && j <  map.getTiles()[0].length)
				{
					buff[iter1][iter2] = map.getTiles()[i][j];
				}
				
				iter2 ++;
			}
			iter2 = 0;
			iter1 ++;
		}
		this.buff = buff;
		shiftX = player.getX() % 64;
		shiftY = player.getY() % 16;
		shift = (player.getY()/16 + height/32);
	}
	
	public int getShift()
	{
	//	System.out.println(player.getY()/16 + height/32);
		return shift;
	}
	
	
	public Tile[][] getDrawTiles()
	{
		return buff;
	}
	
	public boolean isReady()
	{
		return isReady;
	}
	
	
	public void mousePressed(int move_to_x, int move_to_y)
	{
	
		vector_x = move_to_x  - width/2;
		vector_y = move_to_y  - height/2;
		
		distance = (int) Math.sqrt(Math.pow(vector_x, 2) + Math.pow(vector_y, 2));
		
	}
	
	public void move()
	{
		
		
		
		int speed = 3;
		
		//System.out.println(distance);
		if ((distance > speed || distance < -1*speed))
		{
			cos = vector_x/Math.sqrt(Math.pow(vector_x,2) + Math.pow(vector_y,2));
			sin = Math.sqrt(1 - Math.pow(cos, 2));
		
			if (vector_y < 0)
			{
				sin = -1*sin;
			}
			
		//	System.out.println(Math.pow(cos*speed,2) + Math.pow(sin*speed,2));
			real_x = real_x + cos*speed;
			real_y = real_y + sin*speed;
			
			player.setX((real_x + cos*speed));
			player.setY((real_y + sin*speed));
			distance = distance - speed;
		}
		else
		{
			
			real_x = real_x + cos*distance;
			real_y = real_y + sin*distance;
			
			player.setX((real_x + cos*distance));
			player.setY((real_y + sin*distance));
			distance =  0;
		}
		
	//	GamePanel.console.addField("Distance " + String.valueOf(distance),1);
	}

	@Override
	public void run()
	{
	//	real_x = map.getPlayer().getX();
	//	real_y = map.getPlayer().getY();
		
		while(isRun)
		{
			isReady = false;
			long tick_start = System.currentTimeMillis();
				
				
			    formTiles();
			    move();
			    
			isReady = true;
				
				 long tick_finish = System.currentTimeMillis();
				 
					if (tick_finish - tick_start < 25)
					{
						
						try {
							Thread.sleep(25 - (tick_finish - tick_start));
						} catch (InterruptedException e) {
							
							e.printStackTrace();
						}
						
					}
					tick_finish = System.currentTimeMillis();
					
					
					int yShift = (player.getY()) % 16;
					int xShift = player.getX()  % 32; 
					
					GamePanel.console.addField("xshift " + String.valueOf(xShift), 9);
					GamePanel.console.addField("yshift " + String.valueOf(yShift), 10);
					
					
					
					if(Math.abs(xShift) + 2*Math.abs(yShift) < 64)	
					{	
						GamePanel.console.addField("X: " + ((player.getX() )/64 ), 2);				
						GamePanel.console.addField("Y: " + ((player.getY() )/16), 3);
					}
					else 
					{
						GamePanel.console.addField("X: " + ((player.getX() )/64 ), 2);				
						GamePanel.console.addField("Y: " + ((player.getY() )/16  ), 3);
					}
					
					
					
					GamePanel.console.addField("X: " + player.getX(), 4);
					GamePanel.console.addField("Y: " + player.getY(), 5);
						
			}
		
			
		}
	
}
