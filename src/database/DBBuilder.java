package database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import gui.ScheduleController;

public class DBBuilder {
	Connection con;
	Statement stat;
	
	public DBBuilder() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
	}
	
	public void addTable(String username) throws SQLException {
		 openConStat();
		 stat.execute("CREATE TABLE " + username + "Schedule (Time INTEGER, Monday STRING, Tuesday STRING, Wednesday STRING, Thursday STRING, Friday STRING)");
		 for (int time = 8; time < 23; time++) {
			 stat.execute("INSERT INTO " + username + "Schedule (Time) VALUES ("+ Integer.toString(time) + ")");
		 }
		 con.close();
	}
	
	public void openConStat() throws SQLException {
		con = DriverManager.getConnection("jdbc:sqlite:project4.db");
		stat = con.createStatement();
	}
	public void modifySchedule (String day, int time, String busy) throws SQLException {
		openConStat();
		stat.execute("UPDATE " + ScheduleController.USERNAME + " Schedule SET " + day + " = " + busy + "WHERE Time = " + Integer.toString(time));
		con.close();
	}
}
