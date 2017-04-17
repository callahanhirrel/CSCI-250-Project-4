package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DBConstructor {
	
	public void init() throws ClassNotFoundException{
		Class.forName("org.sqlite.JDBC");
	}
	
	public Connection connectDB() throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:sqlite:project4.db");
		return con;
	}
	
	public Statement editDB(Connection con) throws SQLException {
		Statement stat = con.createStatement();
		return stat;
	}
	
	public void closeDB(Connection con) throws SQLException {
		con.close();
	}
	
	public void createTable(Statement stat, String tablename, ArrayList<String> fields, ArrayList<String> types) throws SQLException {
		stat.execute("create "
				+ "table "
				+ tablename
				+ " "
				+ "("
				+ buildFields(fields, types)
				+ ")");
	}
	
	public String buildFields(ArrayList<String> fields, ArrayList<String> types) {
		String text = new String();
		
		for (int i = 0; i < fields.size(); i++) {
			if (i == fields.size() - 1) {
				text = text + fields.get(i) + " " + types.get(i);
			} else {
				text = text + fields.get(i) + " " + types.get(i) + ", ";
			}
		}
		
		return text;
	}
	
	
}
