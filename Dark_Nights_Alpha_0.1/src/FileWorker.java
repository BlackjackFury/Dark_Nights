import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

public class FileWorker {
	
	
	private String path;
	private BufferedReader in;
	
	
	public FileWorker()
	{
			path = "config.conf";
	}
	
	public void parseFile()
	{
		try {
			in = new BufferedReader(new FileReader(path));
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			System.out.println("Cant load the conf file");
		}
		String lineText;
		int i = 0;
		try {
			while ((lineText = in.readLine()) != null) 
			{
				Settings.settings[i] = Float.valueOf(lineText.split("=")[1]);
				i++;
			}
		} catch (IOException e) {
			
			e.printStackTrace();
		}
	}

}
