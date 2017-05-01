package database;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

import gui.ScheduleController;

public class DBBuilder {
	Connection con;
	Statement stat;

	public DBBuilder() throws ClassNotFoundException, SQLException {
		Class.forName("org.sqlite.JDBC");
	}

	public void addTable(String username) throws SQLException {
		 openConStat();
		 ArrayList<String> times = buildTimeArray();
		 stat.execute("CREATE TABLE " + username + " (Time TEXT, Monday TEXT, Tuesday TEXT, Wednesday TEXT, Thursday TEXT, Friday TEXT)");
		 for (String time : times) {
			 stat.execute("INSERT INTO " + username + " VALUES ('"+ time + "', 'FREE', 'FREE', 'FREE', 'FREE', 'FREE')");
		 }
		 stat.close();
		 con.close();
	}

	private ArrayList<String> buildTimeArray() {
		ArrayList<String> times = new ArrayList<String>();
		for (int time = 8; time < 12; time ++) {
			times.add(Integer.toString(time) + ":00 AM");
			times.add(Integer.toString(time) + ":30 AM");
		}
		times.add("12:00 PM");
		times.add("12:30 PM");
		for (int time = 1; time < 11; time ++) {
			times.add(Integer.toString(time) + ":00 PM");
			times.add(Integer.toString(time) + ":30 PM");
		}
		return times;
	}


	private void openConStat() throws SQLException {
		con = DriverManager.getConnection("jdbc:sqlite:project4.db");
		stat = con.createStatement();
	}


	public void modifySchedule (String day, String time, String busy) throws SQLException {
		openConStat();
		stat.execute("UPDATE " + ScheduleController.USERNAME + " SET " + day + " = '" + busy + "' WHERE Time = '" + time + "'");
		stat.close();
		con.close();
	}


	public boolean isTable(String username) throws SQLException {
		boolean isTable;
		openConStat();
		DatabaseMetaData soMetaBro = con.getMetaData();
		ResultSet tables = soMetaBro.getTables(null, null, username, null);

		if (tables.next()) {
			isTable = true;
		} else {
			isTable = false;
		}

		stat.close();
		con.close();
		return isTable;
	}

	public void removeTable(String username) throws SQLException {
		openConStat();
		stat.executeQuery("DROP TABLE IF EXISTS " + username + ";");
		stat.close();
		con.close();
	}

	public String isFree(String query, String day) throws SQLException {
		openConStat();
		stat.execute(query);
		ResultSet rs = stat.getResultSet();
		rs.next();
		String free = rs.getString(1);
		stat.close();
		con.close();
		return free;
	}
}
