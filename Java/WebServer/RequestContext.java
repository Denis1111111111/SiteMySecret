package Update;
public class RequestContext // RequestContext
{
	public	String  id;
	public	String  name;
	public	String  password;
	public	String  secret;
	public	String  isAdmin;
	public	String  command;
	
	public RequestContext(String id, String name, String password, String secret)
	{
		this.id = id;
		this.name = name;
		this.password = password;
		this.secret = secret;
	}
}
