package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
//import java.util.ArrayList;

public class DBConstructor {
	
	String[] daysOfTheWeek = {"Monday", "Tuesday", "Wednesday", "Thursday", "Friday"};
	
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
	
//	public void createTable(Statement stat, String tablename, String[] fields, String[] types) throws SQLException {
//		stat.execute("create "
//				+ "table "
//				+ tablename
//				+ " "
//				+ "("
//				+ buildFields(fields, types)
//				+ ")");
//	}
	
//	public String buildFields(String[] fields, String[] types) {
//		String text = new String();
//		
//		for (int i = 0; i < fields.size(); i++) {
//			if (i == fields.size() - 1) {
//				text = text + fields.get(i) + " " + types.get(i);
//			} else {
//				text = text + fields.get(i) + " " + types.get(i) + ", ";
//			}
//		}
//		
//		return text;
//	}
	

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
	
	public void createScheduleTables() throws Exception {
		Connection con = connectDB();
		for (String day : daysOfTheWeek) {
			Statement stat = editDB(con);
			try {
				stat.execute("CREATE TABLE " + day + " ( `username` TEXT, `hour8` INTEGER, `hour9` INTEGER, `hour10` INTEGER, `hour11` INTEGER, `hour12` INTEGER, `hour13` INTEGER, `hour14` INTEGER, `hour15` INTEGER, `hour16` INTEGER, `hour17` INTEGER, `hour18` INTEGER, `hour19` INTEGER, `hour20` INTEGER, `hour21` INTEGER, `hour22` INTEGER )");
			} catch (SQLException e) {
				e.printStackTrace();
				throw new Exception("Failed to create schedule tables.");
			}
		}
		con.close();
	}
	
	public void addNameToDatabase(String name) throws Exception {
		Connection con = connectDB();
		for (String day : daysOfTheWeek) {
			Statement stat = editDB(con);
			try {
				stat.execute("INSERT INTO " + day + "(name,hour8,hour9,hour10,hour11,hour12,hour13,hour14,hour15,hour16,hour17,hour18,hour19,hour20,hour21,hour22) VALUES (" + name + ",0,0,0,0,0,0,0,0,0,0,0,0,0,0,0");
			}
			catch (SQLException e) {
				e.printStackTrace();
				throw new Exception("Failed to add name to the tables.");
			}
		}
	}
	
	/*Create table - one time function*/
//	public void createUserTable() throws SQLException {
//		Connection con = connectDB();
//		Statement stat = editDB(con);
//		String[] fields = {"Username", 
//				"Monday", "Tuesday", "Wednesday", "Thursday", "Friday",
//				"8", "9", "10", "11", "12", 
//				"13", "14", "15", "16", "17", 
//				"18", "19", "20", "21", "22", 
//				"Busy"};
//		String[] types = {"string", 
//				"string", "string", "string", "string", "string",
//				"integer", "integer", "integer", "integer", "integer", 
//				"integer", "integer", "integer", "integer", "integer", 
//				"integer", "integer", "integer", "integer", "integer", 
//				"boolean"};
//		createTable(stat, "user", fields, types);
//		closeDB(con);
//	}
	
	/*Insert data into a col*/
//	public void updateCol(Statement stat, String colname, String username, String coltext) throws SQLException {
//		stat.execute("update " + username + "Schedule (" + colname + ") values (" + coltext + ")");
//	}
	
	/*Insert data into user - one time function?*/
	
}
