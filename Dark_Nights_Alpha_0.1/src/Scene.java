import java.awt.Graphics;

public class Scene {
	
	protected MenuElement[] menuElements = new MenuElement[0];
	protected String Name;


	

	
	
	
	public void add(MenuElement elem)
	{
		MenuElement[] buff = new MenuElement[menuElements.length];
		
		for (int i = 0; i < menuElements.length; i ++)
		{
			buff[i] = menuElements[i];
		}
		
		menuElements = new MenuElement[buff.length + 1];
		
		for (int i = 0; i < buff.length; i ++)
		{
			menuElements[i] = buff[i];
		}
		menuElements[menuElements.length - 1] = elem;
	}
	
	public MenuElement[] getElememts()
	{
		return menuElements;
	}
	
	
	public void buttonPressed(int arg)
	{
		if (arg > 0)
		{
			Menu.scenes[0] = Menu.scenes[arg];
		}
	}
	
	public void drawScene(Graphics gBuffer)
	{
		for (int i = 0; i < this.getElememts().length; i++)
		{
			this.getElememts()[i].draw(gBuffer);
		}
	}
	
	public void updateColliders(float width, float height)
	{
		for (int i = 0; i < this.getElememts().length; i++)
		{
			//this.getElememts()[i].setTrigerHeight((int)(this.getElememts()[i].getTrigerHeight()*height));
		//	this.getElememts()[i].setTrigerWidth((int)(this.getElememts()[i].getTrigerWidth()*width));
			this.getElememts()[i].updatePosition(width, height);
		}
	}
	
}
