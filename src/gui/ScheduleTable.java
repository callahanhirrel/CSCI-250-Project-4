package gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ScheduleTable {
	private StringProperty time, mon, tue, wed, thu, fri;
	
	public ScheduleTable(String tname, String mname, String tuname, String wname, String thname, String fname) {
		this.time = new SimpleStringProperty(tname);
		this.mon = new SimpleStringProperty(mname);
		this.tue = new SimpleStringProperty(tuname);
		this.wed = new SimpleStringProperty(wname);
		this.thu = new SimpleStringProperty(thname);
		this.fri = new SimpleStringProperty(fname);
	}
	
	public StringProperty timeProperty() {
		return time;
	}
	
	public StringProperty monProperty() {
		return mon;
	}
	
	public StringProperty tueProperty() {
		return tue;
	}
	
	public StringProperty wedProperty() {
		return wed;
	}
	
	public StringProperty thuProperty() {
		return thu;
	}
	
	public StringProperty friProperty() {
		return fri;
	}
	
}
