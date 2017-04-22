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
		day.getSelectionModel().select(1);

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

		System.out.println("OK");
		populate();

		Stage stage = (Stage) add.getScene().getWindow();
		stage.close();
	}

	private void populate() throws SQLException {
		tableList.table.getItems().clear();
		Connection con = data.connectDB();
		Statement stat = data.editDB(con);
		if (stat.execute("select * from " + ScheduleController.USERNAME)) {
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
