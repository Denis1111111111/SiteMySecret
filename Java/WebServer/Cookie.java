package Update;

import java.util.ArrayList;
import java.util.List;
import com.sun.net.httpserver.Headers;
import com.sun.net.httpserver.HttpExchange;



public class Cookie
{

	private Headers respHeaders = null;
	List<String> cookies = null;
	List<String> values = null;

	public  void putCookie(HttpExchange exchange, String cookie )
	{
		if(cookie == null)
		{
			return;
		}

		values = new ArrayList<String>();    
		values.add("MyCookie=" + cookie + "; version=1; Path=/; Max-Age =" +  (10800 + 1800)  + "; HttpOnly"); 


		respHeaders = exchange.getResponseHeaders();
		respHeaders.put("Set-Cookie", values);		
	}

	public  List<String>  getCookie(HttpExchange exchange)
	{
		cookies = exchange.getRequestHeaders().get("Cookie");
		return cookies;
	}
}
