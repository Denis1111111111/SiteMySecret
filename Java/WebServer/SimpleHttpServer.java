package Update;
import com.sun.net.httpserver.*;


import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.util.List;

public class SimpleHttpServer
{	
	private  Controller controller = new Controller();
	private String query = null;
	private byte[] bytes = null;
	private OutputStream os = null;
	private List<String> cookies = null;
	private Cookie cookie = new Cookie();
	private String [] arr = null;

	public SimpleHttpServer(int port) throws IOException
	{
		HttpServer server = HttpServer.create();
		server.bind( new InetSocketAddress(port), 0);
		HttpContext context = server.createContext("/", new Handler());
		context.setAuthenticator(new Auth());
		server.setExecutor(null);
		server.start();
	}



	private  class Handler implements HttpHandler 
	{
		@Override
		public void handle(HttpExchange exchange) throws IOException 
		{
			
			System.out.println(exchange.getRequestMethod());
			StringBuilder builder = new StringBuilder();  
			query = exchange.getRequestURI().toString();

			query = query.substring(1, query.length());

			System.out.println(query + "       zapros");

			cookies = cookie.getCookie(exchange);

			arr = controller.identificationRequest(query, cookies);

			cookie.putCookie(exchange, arr[1]);
			builder.append(arr[0]);
			bytes = builder.toString().getBytes();
			exchange.sendResponseHeaders(200, bytes.length);

			os = exchange.getResponseBody();
			os.write(bytes);
			os.close(); 

		}
	}

	private class Auth extends Authenticator
	{
		@Override
		public Result authenticate(HttpExchange httpExchange)
		{
			if ("/forbidden".equals(httpExchange.getRequestURI().toString()))
				return new Failure(403);
			else
				return new Success(new HttpPrincipal("c0nst", "realm"));
		}
	}
}