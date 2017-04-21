package gui;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Arrays;
import java.util.List;

//import Controllers.CourseInfo;
//import gui.ScheduleController;
import database.DBBuilder;
import database.DBConstructor;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
//import javafx.scene.control.DatePicker;
import javafx.scene.control.TextArea;
import javafx.stage.Stage;

public class UpdateController {

	UpdateHelper helper = new UpdateHelper();
	DBConstructor data = new DBConstructor();
	DBBuilder db;

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

	ScheduleController tableList;

	List<String> dayPicker = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");

	List<String> hourPicker = helper.hourPickerCreator();

	List<String> minPicker = helper.minPickerCreator();

	List<String> perPicker = Arrays.asList("AM", "PM");

	@FXML
	public void initialize() throws ClassNotFoundException, SQLException {
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

		for (String m: minPicker) {
			min.getItems().add(m);
		}
		min.getSelectionModel().selectFirst();

//		day.setDisable(true);
		db = new DBBuilder();
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
		String pickedDay = new String(day.getSelectionModel().getSelectedItem());
		String time = new String(hour.getSelectionModel().getSelectedItem());
//		String busy = new String(des.getText());

		db.modifySchedule(pickedDay, time, "BUSY");

		System.out.println("OK");
		populate();

		Stage stage = (Stage) add.getScene().getWindow();
		stage.close();
	}

	private void populate() throws SQLException {
		tableList.table.getItems().clear();
		Connection con = data.connectDB();
		Statement stat = data.editDB(con);
		if (stat.execute("select * from " + ScheduleController.USERNAME + "Schedule")) {
			ResultSet results = stat.getResultSet();
			while (results.next()) {
	        	tableList.table.getItems().add(new ScheduleTable(results.getString(1), results.getString(2),
		        		results.getString(3), results.getString(4), results.getString(5), results.getString(6)));
	        }
		}
	}

	public void importVal(ScheduleController tb) {
		this.tableList = tb;
	}

}
