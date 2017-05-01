package gui;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import database.DBBuilder;
import javafx.scene.control.TableView;

public class UpdateHelper {
	int halfDay = 12;
	int morningHour = 4;
	int afternoonHour = 10;
	int min = 60;
	
	public List<String> hourPickerCreator() {
		List<String> beginningHour = new ArrayList<String>();
		int hour = 0;
		
		for (int i = 0; i < halfDay; i++) {
			hour = hour + 1;
			beginningHour.add(Integer.toString(hour));
		}
		
		return beginningHour;
	}
	
	public void fillSchedule(String pickedDay, String begM, String endM, String begP, String endP, String busy, int begH, int endH, DBBuilder db) throws SQLException {
		if (begH == 12) {
			String time = new String(Integer.toString(begH) + ":" +  begM + " " + begP);
			db.modifySchedule(pickedDay, time, busy);
			if (begM.equals("00")) {
				begM = "30";
				time = new String(Integer.toString(begH) + ":" +  begM + " " + begP);
				db.modifySchedule(pickedDay, time, busy);
				begH = 1;
				begM = "00";
			}
		}
		
		if (begP.equals("AM") && endP.equals("PM")) {
			while (begH < 13) {
				String time = new String(Integer.toString(begH) + ":" +  begM + " " + begP);
				db.modifySchedule(pickedDay, time, busy);
				if (begM == "30") {
					begH += 1;
					begM = "00";
					if (begH == 12) {
						begP = "PM";
					}
				} else {
					begM = "30";
				}
			}
			if (begH == 13) {
				begH = 1;
			}
		}

		while (begH < endH) {
			String time = new String(Integer.toString(begH) + ":" +  begM + " " + endP);
			db.modifySchedule(pickedDay, time, busy);
			if (begM == "30") {
				begH += 1;
				begM = "00";
			} else {
				begM = "30";

			}
		}
		if (endM.equals("30")) {
			String time = new String(Integer.toString(endH) + ":00 " + endP);
			db.modifySchedule(pickedDay, time, busy);
		}
	}
	
	public void populateTable(TableView<ScheduleTable> table) throws SQLException {
		table.getItems().clear();
		Connection con = DriverManager.getConnection("jdbc:sqlite:project4.db");
		Statement stat = con.createStatement();
		if (stat.execute("select * from " + ScheduleController.USERNAME)) {
			ResultSet results = stat.getResultSet();
			while (results.next()) {
	        	table.getItems().add(new ScheduleTable(results.getString(1), results.getString(2),
		        	results.getString(3), results.getString(4), results.getString(5), results.getString(6)));
	        }
		}
	}
	
	public List<String> am() {
		List<String> beginningHour = new ArrayList<String>();
		int hour = 7;
		
		for (int i = 0; i < morningHour; i++) {
			hour = hour + 1;
			beginningHour.add(Integer.toString(hour));
		}
		
		return beginningHour;
	}
	
	public List<String> pm() {
		List<String> beginningHour = new ArrayList<String>();
		int hour = 0;
		
		for (int i = 0; i < afternoonHour; i++) {
			hour = hour + 1;
			beginningHour.add(Integer.toString(hour));
		}
		
		return beginningHour;
	}
	
}
