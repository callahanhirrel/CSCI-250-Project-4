package gui;

import java.sql.SQLException;

import database.DBBuilder;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TabPane;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;

public class SignInController {

	@FXML
	Button signIn;
	@FXML
	Button create;
	@FXML
	TextField enterUsername;
	@FXML
	TextField createUsername;

	DBBuilder dbBuilder;

	@FXML
	public void initialize() throws ClassNotFoundException, SQLException {
		dbBuilder = new DBBuilder();
	}


	@FXML
	public void signIn() {
		String username = enterUsername.getText();
		try {
			if (dbBuilder.isTable(username)) {
				ScheduleController.USERNAME = username;
				loadNext();
			} else {
				resetFields();
				Alert alert = new Alert(AlertType.ERROR, "Username does not exist. Create a new one!", ButtonType.OK);
				alert.showAndWait();
			}
		} catch (SQLException e) {
			ScheduleController.displayError(e.getMessage());
			e.printStackTrace();
		}
	}


	@FXML
	public void create() {
		String newuser = new String();
		newuser = createUsername.getText();
		try {
			if (!dbBuilder.isTable(newuser)) {
				dbBuilder.addTable(newuser);
				ScheduleController.USERNAME = newuser;
				loadNext();
			} else {
				resetFields();
				Alert alert = new Alert(AlertType.ERROR, "Username already exists. Sign in!", ButtonType.OK);
				alert.showAndWait();
			}
		} catch (SQLException e) {
			e.printStackTrace();
			resetFields();
			Alert alert = new Alert(AlertType.ERROR, "Username already exists. Sign in!", ButtonType.OK);
			alert.showAndWait();
		}
	}

	private void loadNext() {
		resetFields();
		try {
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("ScheduleGUI.fxml"));
			TabPane root = (TabPane) loader.load();
			Stage secondStage = new Stage();
			Scene scene = new Scene(root);
			secondStage.setScene(scene);
			secondStage.show();
		} catch (Exception exc) {
			exc.printStackTrace();
		}
		create.getScene().getWindow().hide();
	}

	private void resetFields() {
		this.createUsername.clear();
		this.enterUsername.clear();
	}

}
