package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
//import java.util.ArrayList;

public class DBConstructor {
	
	/*Initialize database*/
	public void init() throws ClassNotFoundException{
		Class.forName("org.sqlite.JDBC");
	}
	
	/*Connect to database project4*/
	public Connection connectDB() throws SQLException {
		Connection con = DriverManager.getConnection("jdbc:sqlite:project4.db");
		return con;
	}
	
	/*Create a sql Statement*/
	public Statement editDB(Connection con) throws SQLException {
		Statement stat = con.createStatement();
		return stat;
	}
	
	/*Close connection*/
	public void closeDB(Connection con) throws SQLException {
		con.close();
	}
	
	/*Create a Table*/
	public void createTable(Statement stat, String tablename, String[] fields, String[] types) throws SQLException {
		stat.execute("drop table if exists"
				+ " "
				+ tablename);
		stat.execute("create "
				+ "table "
				+ tablename
				+ " "
				+ "("
				+ buildFields(fields, types)
				+ ")");
	}
	
	/*Generate fields*/
	public String buildFields(String[] fields, String[] types) {
		String text = new String();
		
		for (int i = 0; i < fields.length; i++) {
			if (i == fields.length - 1) {
				text = text + fields[i] + " " + types[i];
			} else {
				text = text + fields[i] + " " + types[i] + ", ";
			}
		}
		
		return text;
	}
	
	/*Insert data to the Table*/
	public void insertData(Statement stat, String tablename, List<?> data) throws SQLException { 
		stat.execute("insert into"
				+ " "
				+ tablename
				+ " "
				+ "values("
				+ buildData(data)
				+ ")");
	}
	
	/*Generate data*/
	public String buildData(List<?> data) {
		String text = new String();
		
		for (int i = 0; i < data.size(); i++) {
			if (i == data.size() - 1) {
				text = text + data.get(i);
			} else {
				text = text + data.get(i) + ", ";
			}
		}
		
		return text;
	}
	
	/*Create table - one time function*/
	public void createUserTable() throws SQLException {
		Connection con = connectDB();
		Statement stat = editDB(con);
		String[] fields = {"Username", 
				"Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
				"8", "9", "10", "11", "12", 
				"13", "14", "15", "16", "17", 
				"18", "19", "20", "21", "22", 
				"Busy"};
		String[] types = {"string", 
				"string", "string", "string", "string", "string",
				"integer", "integer", "integer", "integer", "integer", 
				"integer", "integer", "integer", "integer", "integer", 
				"integer", "integer", "integer", "integer", "integer", 
				"boolean"};
		createTable(stat, "user", fields, types);
		closeDB(con);
	}
	
	/*Insert data into a col*/
//	public void updateCol(Statement stat, String colname, String username, String coltext) throws SQLException {
//		stat.execute("update " + username + "Schedule (" + colname + ") values (" + coltext + ")");
//	}
	
	/*Insert data into user - one time function?*/
	
}
