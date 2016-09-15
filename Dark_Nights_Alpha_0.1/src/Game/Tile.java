package Game;
import java.io.Serializable;

public class Tile {

	/**
	 * 
	 */

	private int level;
	private int id;

	
	public Tile(int id)
	{
		this.id = id;
	}
	
	public int get_id()
	{
		return id;
	}
	public int get_level()
	{
		return level;
	}
	public void setId(int id)
	{
		this.id = id;
	}
}
