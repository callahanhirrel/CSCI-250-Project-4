package gui;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;

public class SignInController {
	
	@FXML
	Button signIn;
	@FXML
	Button create;
	@FXML
	TextField enterUsername;
	@FXML
	TextField createUsername;
	
	@FXML
	public void initialize() {
		
	}
	
	/* Check the database that the name exists. Set the static variable ScheduleController.USERNAME to input text. Then, load the correct
	 * schedule.
	 */
	
	@FXML
	public void signIn() {
		
	}
	
	/* When a user creates a profile, check the database to verify the name does not exist. Then, add a new record with the input name 
	 * to each of the 5 tables.
	 */
	@FXML
	public void create() {
		
	}

}
