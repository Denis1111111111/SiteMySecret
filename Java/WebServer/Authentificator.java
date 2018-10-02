package Update;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.List;




public class Authentificator 
{

	private HashMap<String, String> map = new HashMap<String, String>();
	private ConverterJson converter = new ConverterJson();



	public String validate(String reqStr,  List<String> cookies ) 
	{
		if(cookies == null)
		{
			return "wrong name or password";
		}

		for(String cookie : cookies)
		{	
			if(cookie.substring(0, 8).equals("MyCookie"))
			{

				String key = cookie.substring(9, cookie.length());

				if(	map.containsKey(key))
				{
					return map.get(key);
				}
			}
		}
		return "wrong name or password";
	}

	public String exchangeCookie(String reqStr,  List<String> cookies)
	{
		for(String cookie : cookies)
		{		
			if(cookie.substring(0, 8).equals("MyCookie"))
			{

				String key = cookie.substring(9, cookie.length());	

				if(	map.containsKey(key))
				{

					String id = map.get(key);
					removeToken(key);
					return putToken(id);
				}
			}
		}
		return null;
	}


	public String  isValidData(String reqStr)
	{

		RequestContext text = converter.fromJson(reqStr);

		if (text.secret.equals("")  && text.command.equals("createIndex")  || text.secret.equals("")  && text.command.equals("createAdmin")  || text.secret.equals("")  && text.command.equals("updateAdmin") || text.secret.equals("") && text.command.equals("deleteAdmin") || text.command.equals("readIndex") && text.password.equals("")    || text.name.equals("")  && text.command.equals("readIndex"))
		{
			return "incorrect data";
		}
		return "Valid";
	}


	public String putToken(String id)  //create
	{
		String time = LocalDateTime.now().toString().replaceAll(":|-|\\.", "");

		map.put(time, id);

		return time;
	}

	private void removeToken(String token)
	{
		map.remove(token);
	}	
}
