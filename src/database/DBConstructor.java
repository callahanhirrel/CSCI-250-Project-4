package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
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
	public void insertData(Statement stat, String tablename, String[] data) throws SQLException { 
		stat.execute("insert into"
				+ " "
				+ tablename
				+ " "
				+ "values("
				+ buildData(data)
				+ ")");
	}
	
	/*Generate data*/
	public String buildData(String[] data) {
		String text = new String();
		
		for (int i = 0; i < data.length; i++) {
			if (i == data.length - 1) {
				text = text + data[i];
			} else {
				text = text + data[i] + ", ";
			}
		}
		
		return text;
	}
	
}
