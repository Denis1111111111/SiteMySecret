package Update;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;

public class ReadFiles    //constructor!!!
{
	private String PathToTheFolder = "C:\\xampp\\htdocs\\SiteMySecretJava\\";
	
	
	public String readFile(String filename) 
	{
//		FileSystems.getDefault().getSeparator();
		File f = new File(PathToTheFolder + filename);
		
		try
		{
			byte[] bytes = Files.readAllBytes(f.toPath());
			return new String(bytes, "UTF-8");
		} 
		
		catch (FileNotFoundException e) 
		{
			e.printStackTrace();
		} 
		
		catch (IOException e) 
		{
			e.printStackTrace();
		}
		
		return "";
	}
}
