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
import javafx.scene.control.TableView;
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
	ChoiceBox<String> begHour;

	@FXML
	ChoiceBox<String> begMin;

	@FXML
	ChoiceBox<String> begPer;

	@FXML
	ChoiceBox<String> endHour;

	@FXML
	ChoiceBox<String> endMin;

	@FXML
	ChoiceBox<String> endPer;

	ScheduleController tableList;

	List<String> dayPicker = Arrays.asList("Monday", "Tuesday", "Wednesday", "Thursday", "Friday");

	List<String> hourPicker = helper.hourPickerCreator();

	List<String> minPicker = Arrays.asList("00", "30");

	List<String> perPicker = Arrays.asList("AM", "PM");

	@FXML
	public void initialize() throws ClassNotFoundException, SQLException {
		for (String d: dayPicker) {
			day.getItems().add(d);
		}
		day.getSelectionModel().selectFirst();

		for (String h: hourPicker) {
			begHour.getItems().add(h);
			endHour.getItems().add(h);
		}
		begHour.getSelectionModel().select(7);
		endHour.getSelectionModel().select(8);

		for (String p: perPicker) {
			begPer.getItems().add(p);
			endPer.getItems().add(p);
		}
		begPer.getSelectionModel().selectFirst();
		endPer.getSelectionModel().selectFirst();

		for (String m: minPicker) {
			begMin.getItems().add(m);
			endMin.getItems().add(m);

		}
		begMin.getSelectionModel().selectFirst();
		endMin.getSelectionModel().selectFirst();

		db = new DBBuilder();
	}

	@FXML
	void addSchedule() throws SQLException {
		String pickedDay = new String(day.getSelectionModel().getSelectedItem());
		int begH = Integer.parseInt(begHour.getSelectionModel().getSelectedItem());
		int endH = Integer.parseInt(endHour.getSelectionModel().getSelectedItem());
		String begM = new String(begMin.getSelectionModel().getSelectedItem());
		String endM = new String(endMin.getSelectionModel().getSelectedItem());
		String begP = new String(begPer.getSelectionModel().getSelectedItem());
		String busy = new String(des.getText());
		String endP = new String(endPer.getSelectionModel().getSelectedItem());
		
		helper.fillSchedule(pickedDay, begM, endM, begP, endP, busy, begH, endH, db);
		helper.populateTable(tableList.table);

		Stage stage = (Stage) add.getScene().getWindow();
		stage.close();
	}

	public void importVal(ScheduleController tb) {
		this.tableList = tb;
	}

}
