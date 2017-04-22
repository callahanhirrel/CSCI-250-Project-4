package gui;

import java.util.ArrayList;
import java.util.List;

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
