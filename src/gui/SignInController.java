package gui;

import java.sql.SQLException;
import java.util.ArrayList;

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
	
	private ArrayList<String> usernames;
	
	@FXML
	public void initialize() throws ClassNotFoundException, SQLException {
		dbBuilder = new DBBuilder();
		usernames = new ArrayList<String>();
	}
	
	
	@FXML
	public void signIn() {
		if (usernames.contains(enterUsername.getText())) {
			ScheduleController.USERNAME = createUsername.getText();
			loadNext();
		} else {
			Alert alert = new Alert(AlertType.ERROR, "Username does not exist. Create a new one!", ButtonType.OK);
			alert.showAndWait();
		}
	}
	

	@FXML
	public void create() throws SQLException {
		String newuser = new String();
		newuser = createUsername.getText();
		if (!usernames.contains(newuser)) {
			usernames.add(newuser);
			dbBuilder.addTable(newuser);
			ScheduleController.USERNAME = newuser;
			loadNext();
		} else {
			Alert alert = new Alert(AlertType.ERROR, "Username already exists. Sign in!", ButtonType.OK);
			alert.showAndWait();
		}
	}
	
	private void loadNext() {
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

}
