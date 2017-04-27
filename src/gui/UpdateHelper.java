package gui;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import database.DBBuilder;

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
