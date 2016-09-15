package Game;
import java.util.ArrayList;
import java.util.Random;

public class Map {
	
	private static final int MAP_SIZE = 100;
	private ArrayList<WorldObject> objects = new ArrayList<WorldObject>();
	private Tile[][] tileMap = new Tile[100][400];
		
	public Map()
	{
		Random rand = new Random();
		
		for (int i = 0 ; i < tileMap.length; i ++)
		{
			for (int j = 0; j < tileMap[0].length; j ++)
			{
			

				tileMap[i][j] = new Tile(0);
					
			}
		}
		
		for (int i = 0; i < tileMap.length; i ++)
			
			tileMap[i][0] = new Tile(1);
		
		for (int i = 0; i < tileMap[0].length; i ++)
			
			tileMap[0][i] = new Tile(1);
		
		
		
	}
	
	public Tile[][] getTiles()
	{
		return tileMap;
	}
	
	public ArrayList<WorldObject> getObjects()
	{
		return objects;
	}

}
