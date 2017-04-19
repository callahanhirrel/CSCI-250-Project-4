package gui;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

import database.DBConstructor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
//import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;

public class UpdateController {
	
	UpdateHelper helper = new UpdateHelper();
	DBConstructor db = new DBConstructor();
	
	@FXML
	Button add;
	
	@FXML
	TextArea des;
	
	@FXML
	ChoiceBox<String> day;
	
	@FXML
	ChoiceBox<String> hour;
	
	@FXML
	ChoiceBox<String> min;
	
	@FXML
	ChoiceBox<String> per;
	
	List<String> dayPicker = Arrays.asList("Sunday", "Monday", "Tuesday", "Wednesday", "Thursday", "Friday", "Saturday");
	
	List<String> hourPicker = helper.hourPickerCreator();
	
	List<String> perPicker = Arrays.asList("AM", "PM");
	
	@FXML
	public void initialize() throws ClassNotFoundException {
		for (String d: dayPicker) {
			day.getItems().add(d);
		}
		day.getSelectionModel().select(1);
		
		for (String h: hourPicker) {
			hour.getItems().add(h);
		}
		day.getSelectionModel().selectFirst();
		
		for (String p: perPicker) {
			per.getItems().add(p);
		}
		per.getSelectionModel().selectFirst();
		day.setDisable(true);
		db.init();
	}
	
//	@FXML
//	void ableDayPicker() {
//		if (per.getSelectionModel().isSelected(0)) {
//			hourPicker = helper.am();
//		} else {
//			hourPicker = helper.pm();
//		}
//	}
	
	@FXML
	void addSchedule() throws SQLException {
		Connection con = db.connectDB();
		Statement stat = db.editDB(con);
		db.closeDB(con);
	}

}
