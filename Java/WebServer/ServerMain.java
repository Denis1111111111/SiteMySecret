package Update;
import java.io.IOException;




public class ServerMain 
{
	private static int port = 9000;
	
	public static void main(String[] args) throws IOException
	{
		SimpleHttpServer httpServ = new SimpleHttpServer(port);	
	}
}
