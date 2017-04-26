package gui;

import java.io.IOException;
import java.net.UnknownHostException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

//import Controllers.CourseInfo;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
//import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import network.Client;
import network.NetworkData;
import network.Server;

public class ScheduleController {

	// FXML Objects under "Home" tab go here:
	@FXML VBox peerList;
	@FXML DatePicker datepicker;
	@FXML ChoiceBox<String> hour;
	@FXML ChoiceBox<String> minute;
	@FXML ChoiceBox<String> am_pm;
	@FXML Button reset;
	@FXML Button check;
	@FXML VBox freePeers;

	// FXML Objects under "Schedule a Meeting" tab go here:

	// FXML Objects under "My Schedule" tab go here:
	@FXML TableView<ScheduleTable> table;
	@FXML TableColumn<TableView<ScheduleTable>, String> time;
	@FXML TableColumn<TableView<ScheduleTable>, String> mon;
	@FXML TableColumn<TableView<ScheduleTable>, String> tue;
	@FXML TableColumn<TableView<ScheduleTable>, String> wed;
	@FXML TableColumn<TableView<ScheduleTable>, String> thu;
	@FXML TableColumn<TableView<ScheduleTable>, String> fri;
	@FXML Button updateSchedule;

	// FXML Objects under "Connect with a Peer" tab go here:
	@FXML TextField ip;
	@FXML Button  connect;
	@FXML Label connectMessage;

//	ScheduleController schedule;
	// All other fields go here:
	private Server server;
	private Client client;
	private ArrayBlockingQueue<NetworkData> dataCollection;
	public static String USERNAME;

	@FXML
	public void initialize() {
		client = new Client();
		dataCollection = new ArrayBlockingQueue<>(20);

		new Thread(() -> startServer()).start();
		new Thread(() -> getData()).start();

		time.setCellValueFactory(new PropertyValueFactory<>("time"));
		mon.setCellValueFactory(new PropertyValueFactory<>("mon"));
		tue.setCellValueFactory(new PropertyValueFactory<>("tue"));
		wed.setCellValueFactory(new PropertyValueFactory<>("wed"));
		thu.setCellValueFactory(new PropertyValueFactory<>("thu"));
		fri.setCellValueFactory(new PropertyValueFactory<>("fri"));
//		table.getSelectionModel().selectedItemProperty().addListener((obs, oldSelection, newSelection) -> {
//		    if (newSelection != null) {
//		        schedule = table.getSelectionModel().getSelectedItem();
//		     }
//		});
	}

	private void getData() {
		for (;;) {
			try {
				NetworkData data = dataCollection.take();
				unpackData(data);
			} catch (InterruptedException e) {
				Platform.runLater(() -> displayError(e.getMessage()));
				e.printStackTrace();
			}
		}
	}

	private void unpackData(NetworkData data) {
		if (data.getTag().equals(NetworkData.CONNECT_TAG)) {
			confirmConnection(data);
			Platform.runLater(() -> showPeers());
		} else if (data.getTag().equals(NetworkData.MEETING_TAG)) {
			// TODO
		} else if (data.getTag().equals(NetworkData.SCHEDULE_TAG)) {
			// TODO
		}
	}

	private void confirmConnection(NetworkData data) {
		try {
			String msg = client.confirmConnection(data, ip.getText());
			Platform.runLater(() -> connectMessage.setText(msg));
		} catch (InterruptedException e) {
			Platform.runLater(() -> displayError(e.getMessage()));
			e.printStackTrace();
		}
	}

	private void showPeers() {
		HashMap<String, String> peers = client.getPeers();
		peerList.getChildren().clear();
		for (String peer : peers.keySet()) {
			Label label = new Label();
			label.setText(peer);
			peerList.getChildren().add(label);
		}
	}

	private void startServer() {
		try {
			server = new Server();
			server.listen();
		} catch (IOException e) {
			displayError(e.getMessage());
			e.printStackTrace();
		}
	}

	public void setUsername(String username) {
		USERNAME = username;
	}

	// This can be used for displaying all errors, from any class
	public static void displayError(String errorMessage) {
		Alert alert = new Alert(AlertType.ERROR, errorMessage, ButtonType.OK);
		Platform.runLater(() -> alert.showAndWait());
	}

	@FXML
	void connect() {
		if (!isValidIP(ip.getText())) {
			displayError("IP address in incorrect format, must be X.X.X.X, where X is any number from 0 to 255");
		} else {
			new Thread(() -> {
				try {
					NetworkData justReceived = client.requestConnection(ip.getText());
					dataCollection.add(justReceived);
				} catch (IOException | ClassNotFoundException e) {
					e.printStackTrace();
				}
			}).start();
		}
	}

	private boolean isValidIP(String ip) {
		// Regular expression determines IP address input is in correct IPv4 address format
		Pattern pattern = Pattern.compile("\\b([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])" +
											"\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])" +
											"\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])" +
											"\\.([0-9]|[1-9][0-9]|1[0-9][0-9]|2[0-4][0-9]|25[0-5])\\b");
		Matcher matcher = pattern.matcher(ip);
		return matcher.lookingAt();
	}

	@FXML
	void checkSchedules() {
		LocalDate date = datepicker.getValue();
		if (isWeekday(date)) {
				try {
					client.checkPeerSchedules(hour.getValue(), minute.getValue(), am_pm.getValue(), date);
				} catch (IOException | ClassNotFoundException e) {
					displayError(e.getMessage());
					e.printStackTrace();
				}
		} else {
			displayError("Please only select weekdays");
		}
	}

	private boolean isWeekday(LocalDate date) {
		int dateAsInt = date.getDayOfWeek().getValue();

		if (dateAsInt == 6 || dateAsInt == 7) {
			return false;
		} else {
			return true;
		}
	}

	@FXML
	void update() {
		try{
			FXMLLoader loader = new FXMLLoader();
			loader.setLocation(Main.class.getResource("UpdateGUI.fxml"));
			AnchorPane root = (AnchorPane) loader.load();

			Stage secondStage = new Stage();
			Scene scene = new Scene(root);

			UpdateController updater = (UpdateController)loader.getController();
			updater.importVal(this);

			secondStage.setScene(scene);
			secondStage.show();
		} catch(Exception e) {
			e.printStackTrace();
			Alert r = new Alert(AlertType.NONE, "TESTING." , ButtonType.OK);
			r.setTitle("ERROR");
			r.showAndWait();
		}
	}

}
