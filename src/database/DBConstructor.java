package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

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
	
	
}
