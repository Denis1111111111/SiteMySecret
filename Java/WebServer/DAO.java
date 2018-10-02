package Update;

import java.sql.SQLException;
import java.util.ArrayList;

public interface DAO 
{
	public ArrayList<RequestContext> create(String name, String password,String secret) throws SQLException;
	public ArrayList<RequestContext> read(String query) throws SQLException;
	public ArrayList<RequestContext> update(String id, String secret) throws SQLException;;
	public ArrayList<RequestContext> delete(String query) throws SQLException;;
}
