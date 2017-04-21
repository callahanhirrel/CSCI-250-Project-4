package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
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
		 stat.execute("CREATE TABLE " + username + " (Time TEXT, Monday TEXT, Tuesday TEXT, Wednesday TEXT, Thursday TEXT, Friday TEXT)");
		 for (int time = 8; time < 23; time++) {
			 stat.execute("INSERT INTO " + username + " VALUES ('"+ Integer.toString(time) + "', '', '', '', '', '')");
		 }
		 con.close();
	}

	private void openConStat() throws SQLException {
		con = DriverManager.getConnection("jdbc:sqlite:project4.db");
		stat = con.createStatement();
	}


	public void modifySchedule (String day, String time, String busy) throws SQLException {
		openConStat();
		stat.execute("UPDATE " + ScheduleController.USERNAME + " SET " + day + " = '" + busy + "' WHERE Time = '" + time + "'");
		con.close();
	}
	
//	public void insertSchedule(String day, String time, String busy) throws SQLException {
//		System.out.println(ScheduleController.USERNAME);
//		openConStat();
//<<<<<<< HEAD
//		stat.execute("insert into " + ScheduleController.USERNAME + "Schedule (Time) values (" + "'" + time + "'" + ")");
//		stat.execute("update " + ScheduleController.USERNAME + "Schedule set " + day + " = '" + busy + "' where Time = '" + time + "'");
//=======
//		stat.execute("UPDATE " + ScheduleController.USERNAME + " SET " + day + " = " + busy + " WHERE Time = " + Integer.toString(time));
//>>>>>>> master
//		con.close();
//	}

	// Figure out how to do this from here:
	// http://stackoverflow.com/questions/2942788/check-if-table-exists
	public boolean isTable(String username) throws SQLException {
		openConStat();
		DatabaseMetaData soMetaBro = con.getMetaData();
		ResultSet tables = soMetaBro.getTables(null, null, username, null);
		con.close();
		if (tables.next()) {
			return true;
		} else {
			return false;
		}
	}

	public void removeTable(String username) throws SQLException {
		openConStat();
		stat.executeQuery("DROP TABLE IF EXISTS " + username + ";");
		con.close();
	}
}
