package Update;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;


public class ConverterJson 
{
	public RequestContext fromJson(String json)
	{
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		RequestContext regStr = gson.fromJson(json, RequestContext.class);
		return regStr;
	}

	public String toGson(RequestContext reqTxt)
	{
		GsonBuilder builder = new GsonBuilder();
		Gson gson = builder.create();
		return  gson.toJson(reqTxt);
	}
}
