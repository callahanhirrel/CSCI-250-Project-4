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
		 stat.execute("CREATE TABLE " + username + "Schedule (Time STRING, Monday STRING, Tuesday STRING, Wednesday STRING, Thursday STRING, Friday STRING)");
		 for (int time = 8; time < 23; time++) {
			 stat.execute("INSERT INTO " + username + "Schedule VALUES ('"+ Integer.toString(time) + "', '', '', '', '', '')");
		 }
		 con.close();
	}
	
	public void openConStat() throws SQLException {
		con = DriverManager.getConnection("jdbc:sqlite:project4.db");
		stat = con.createStatement();
	}
	
	public void modifySchedule(String day, String time, String busy) throws SQLException {
		openConStat();
		stat.execute("UPDATE " + ScheduleController.USERNAME + "Schedule SET " + day + " = '" + busy + "' WHERE Time = '" + time + "'");
		con.close();
	}
	
	public void insertSchedule(String day, String time, String busy) throws SQLException {
		System.out.println(ScheduleController.USERNAME);
		openConStat();
		stat.execute("insert into " + ScheduleController.USERNAME + "Schedule (Time) values (" + "'" + time + "'" + ")");
		stat.execute("update " + ScheduleController.USERNAME + "Schedule set " + day + " = '" + busy + "' where Time = '" + time + "'");
		con.close();
	}
	
	public void selectAll() throws SQLException {
		openConStat();
		stat.execute("select * " + ScheduleController.USERNAME + "Schedule");
		con.close();
	}
}
