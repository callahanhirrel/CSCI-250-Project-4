package gui;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

public class ScheduleTable {
	private StringProperty time, mon, tue, wed, thu, fri;
	
	public ScheduleTable(String name) {
		this.time = new SimpleStringProperty(name);
		this.mon = new SimpleStringProperty(name);
		this.tue = new SimpleStringProperty(name);
		this.wed = new SimpleStringProperty(name);
		this.thu = new SimpleStringProperty(name);
		this.fri = new SimpleStringProperty(name);
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
