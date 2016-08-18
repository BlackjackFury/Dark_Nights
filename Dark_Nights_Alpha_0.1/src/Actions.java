import java.io.IOException;
import java.net.Socket;

public class Actions implements Runnable{

	private String out = "";
	private MenuLabel MultiOutLabel;
	private MenuTextField nameField ;
	private MenuTextField passField;
	private Socket fromserver = null;
	private MenuCheckBox box ;
	private Scene scene ;
	private boolean isActive = true;
	
	public void action(int arg)
	{
		switch (arg) {
		case 0 : System.exit(-1);
			break;
		case 3 : connectToServer();
			break;
		case -10 : isActive = false;
			break;
		}
		//update();
	}
	
	
	public void setUpSliders(MenuSlider slider)
	{
		
		//	vc.setValue((vc.getMaximum() - vc.getMinimum())*(Settings.settings[slider.getID() - 100]/100) + vc.getMinimum());	
			slider.setShift(Settings.settings[slider.getID() - 100]);
	}
	
	private void connectToServer()
	{
		
		if (nameField.getText().length() > 3 && passField.getText().length() > 3)
		{
		out = "";
		this.MultiOutLabel.setLabel(out);
		
		int tries = 0;
		boolean success = false;
		 	
		new Thread(this).start();
		}
		else 
		{
			out = "Enter nickname and password";
			this.MultiOutLabel.setLabel(out);
		}
		
	}
	
	public void setScene(Scene scene )
	{
		this.scene = scene;
	}
	
	public boolean isActive()
	{
		return isActive;
	}
	
	public void setMultiOut(MenuLabel label)
	{
		this.MultiOutLabel = label;
	}
	
	public void setNameField(MenuTextField field)
	{
		this.nameField = field;
	}
	public void setPassField(MenuTextField field)
	{
		this.passField = field;
	}
	
	public String getOut()
	{
		return out;
	}
	
	public void setCollidersSwitch(MenuCheckBox box)
	{
		this.box = box;
		if (Settings.Collisers)
		{
			box.check();
		}
	}
	public void update()
	{
		if (box != null)
		{
			Settings.Collisers = box.isChecked();
		}
	}


	@Override
	public void run() {
		
		  int tries = 0;
		  boolean success = false;
		 	
		  while (success == false && tries < 5)
		  {
		   // System.out.println("Connecting to... "+ "127.0.0.1");
		    try {
				fromserver = new Socket("127.0.0.1",4444);
				success = true;
			} catch (IOException e) {
				
				tries++;
			}
		  }
		  if (success == false)
		  {
			  out = "Connection refused : connect";
		  }
		  	 System.out.println(nameField.getText());
			 System.out.println(passField.getText());
		
			 this.MultiOutLabel.setLabel(out);
			 
			 scene.buttonPressed(2);
	}
	
}
