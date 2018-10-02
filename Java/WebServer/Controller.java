package Update;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.sql.SQLException;
import java.util.List;


public class Controller 
{
	private DAOSQL dao = new DAOSQL();
	private ReadFiles readFiles = new ReadFiles();
	private Authentificator auth = new Authentificator();
	private ConverterJson converter = new ConverterJson();
	private RequestContext text = null;
	private String token = null;
	private String result = null;




	public  String[] identificationRequest(String query, List<String> cookies) 
	{
		token = null;
		
		if(query.equals(""))
		{
			result = readFiles.readFile("Index.html");
			return new String[] {result, token};
		}

		if(isApi(query) != null)
		{
			try 
			{
				query = isApi(URLDecoder.decode( query, "UTF-8" ));
			} 

			catch (UnsupportedEncodingException e) 
			{
				System.out.println("Controller   35");
				e.printStackTrace();
			}

			if (auth.isValidData(query).equals( "incorrect data")) 
			{
				return new String[] {"incorrect data", token};
			}

			result = execute(query, cookies );
			System.out.println(result + "   " + token);
			return new String[] {result, token};
		}

		else
		{
			result = readFiles.readFile(query);
			return new String[] {result, token};
		}
	}

	private  String isApi(String query)
	{
		if(query.substring(0, 3).equals("api") || query.substring(0, 3).equals("API"))
		{
			return query.substring(4, query.length());
		}

		return null;
	}

	private boolean isAdmin(String reqStr)
	{
		RequestContext text = converter.fromJson(reqStr);

		if (text.isAdmin.equals("true")  || text.name.equals("Admin")) 
		{
			return true;
		}

		else 
		{
			return false;
		}
	}

	private  boolean isFirstRequest(String query)
	{
		text = converter.fromJson(query);

		if(text.command.equals("readIndex") || text.command.equals("createIndex"))
		{
			return true;
		}

		return false;
	}




	public String execute(String reqStr, List<String> cookies) 
	{
		if(isFirstRequest(reqStr))
		{
			if(text.command.equals("createIndex"))
			{

				try
				{
					return dao.getUserData(reqStr, "-1");
				} 

				catch (SQLException e)
				{
					e.printStackTrace();
				}
			}

			if(text.command.equals("readIndex"))
			{
				String result = null;

				try 
				{
					result = dao.getUserData(reqStr, "-1");
				}

				catch (SQLException e) 
				{
					e.printStackTrace();
				}

				if (result.equals("wrong name or password"))
				{
					return "wrong name or password";
				}

				token = auth.putToken(result);

				if(isAdmin(reqStr))
				{
					return "Admin page open"; 
				}

				else
				{
					return "User page open";
				}	
			} 
		}

		String resultValidate = auth.validate(reqStr, cookies);
		if (resultValidate.equals("wrong name or password"))
		{
			return "wrong name or password";
		}
		token = auth.exchangeCookie(reqStr, cookies); 

		try 
		{
			return dao.getUserData(reqStr, resultValidate);
		}

		catch (SQLException e) 
		{
			e.printStackTrace();
		}
		return resultValidate;	
	}	
}
