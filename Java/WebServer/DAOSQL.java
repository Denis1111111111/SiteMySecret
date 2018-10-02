package Update;

import java.util.ArrayList;
import com.mysql.jdbc.PreparedStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


public class DAOSQL implements DAO
{
	private ConverterJson converter = new ConverterJson();
	private ArrayList	<RequestContext> answer = new ArrayList <RequestContext>();

	public String getUserData(String reqStr, String id) throws SQLException 
	{
		RequestContext text = converter.fromJson(reqStr);

		switch (text.command)
		{

		case "readAdmin":
		{
			String query = "SELECT count(*) From table1";
			String count = read(query).get(0).name;     
			query = "SELECT * FROM table1";
			ArrayList <RequestContext> answer = read(query);
			String str = count + ", ";

			for (int i = 0; i < Integer.parseInt(count); i++) 
			{
				str = str + answer.get(i).id + " " + answer.get(i).name  + " " + answer.get(i).password  + " " + answer.get(i).secret  + ", ";
			}

			return str;
		}

		case "updateAdmin":
		{
			String []ar = text.secret.split(",");   
			update(ar[0],ar[1]);
			return "field secret updated";
		}

		case "deleteAdmin":
		{
			delete(text.secret);
			return "account deleted";
		}

		case "createAdmin":
		{
			String []ar = text.secret.split(",");

			String query = "SELECT * FROM table1 WHERE name=" + "'" + ar[0] + "'";
			if (read(query).toString() != "[]" )
			{
				return "use a different name";
			}

			create(ar[0],ar[1],ar[2]);
			return "account created";
		}

		case "createIndex":
		{
			String query = "SELECT * FROM table1 WHERE name='" + text.name + "'";
			if (read(query).toString() != "[]" )
			{
				return "use a different name";
			}

			create(text.name, text.password, text.secret);
			return "account created";
		}

		case "readIndex":
		{
			String query = "SELECT * FROM table1 WHERE name='" + text.name + "'AND password='" + text.password + "'";
			ArrayList <RequestContext> answer = read(query); 

			if (read(query).toString() == "[]" )
			{
				return	"wrong name or password";
			}

			if(answer.get(0).name.equals(text.name))
			{
				return answer.get(0).id;
			}

			return	"wrong name or password";
		}

		case "readUser":
		{
			String query = "SELECT * FROM table1 WHERE id=" + "'" + id + "'";
			ArrayList <RequestContext> answer = read(query); 

			return answer.get(0).secret;
		}

		case "updateUser":
		{
			update(id,text.secret);
			return "field secret updated";
		}

		case "deleteUser":
		{
			delete(id);
			return "account deleted";
		}
		}
		return "Error";
	}



	@Override
	public ArrayList<RequestContext> create(String name, String password,String secret) throws SQLException 
	{
		answer.clear();

		String createQuery = "INSERT INTO table1 (name,password,secret) VALUE (?,?,?)";
		Connection	con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
		PreparedStatement preparedStatement =  (PreparedStatement) con.prepareStatement(createQuery);
		preparedStatement.setString(1, name);
		preparedStatement.setString(2, password);
		preparedStatement.setString(3, secret);
		preparedStatement.executeUpdate();
		con.close();
		return answer;
	}

	@Override
	public ArrayList<RequestContext> read(String query) throws SQLException 
	{
		answer.clear();
		if(query.equals("SELECT count(*) From table1"))
		{
			Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
			Statement stmn = (Statement) con.createStatement();
			ResultSet rs = ((java.sql.Statement) stmn).executeQuery(query);

			while(rs.next())
			{
				answer.add(new RequestContext (rs.getString(1),rs.getString(1),rs.getString(1),rs.getString(1)));
			}
			rs.close();
			con.close();

			return answer;	
		}

		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
		Statement stmn = (Statement) con.createStatement();
		ResultSet rs = ((java.sql.Statement) stmn).executeQuery(query);
		while(rs.next())
		{
			answer.add(new RequestContext (rs.getString(1),rs.getString(2),rs.getString(3),rs.getString(4)));
		}
		rs.close();
		con.close();
		return answer;
	}

	@Override
	public ArrayList<RequestContext> update(String id, String secret) throws SQLException 
	{
		answer.clear();

		String updateQuery = "UPDATE table1 SET  secret=? WHERE id=?";
		Connection	con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
		PreparedStatement preparedStatement =  (PreparedStatement) con.prepareStatement(updateQuery);
		preparedStatement.setString(1, secret);
		preparedStatement.setString(2, id);
		preparedStatement.executeUpdate();
		con.close();
		return answer;
	}

	@Override
	public ArrayList<RequestContext> delete(String id) throws SQLException
	{
		answer.clear();

		String deleteQuery = "DELETE FROM table1 WHERE id=?";
		Connection	con = DriverManager.getConnection("jdbc:mysql://localhost:3306/test", "root", "");
		PreparedStatement preparedStatement =  (PreparedStatement) con.prepareStatement(deleteQuery);
		preparedStatement.setString(1, id);
		preparedStatement.executeUpdate();
		con.close();
		return answer;
	}	
}
