package Game;

import java.util.ArrayList;

public class Console {
	
	private int x;
	private int y;
	
	public Console(int x, int y)
	{
		this.x = x;
		this.y = y;
	}
	
	
	private String[] fields = new String[0];
	
	
	public String[] getFields()
	{
		
		return fields;
	}
	
	public  void addField(String newField, int index)
	{
		
		if (fields.length - 1 < index  )
		{
			String[] buff = new String[fields.length];
			for (int i = 0; i < fields.length; i ++)
			{
				buff[i] = fields[i];
			}
			
			fields = new String[index + 1];
			
			for (int i = 0; i < buff.length; i ++)
			{
				fields[i] = buff[i];
			}
			
			fields[index] = newField;
			
		}
		
		fields[index] = newField;

	}
	

	public int getX()
	{
		return x;
	}
	
	public int getY()
	{
		return y;
	}

}
