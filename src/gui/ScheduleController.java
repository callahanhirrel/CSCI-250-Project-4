package gui;

import java.io.IOException;
import java.util.HashMap;
import java.util.concurrent.ArrayBlockingQueue;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import network.Client;
import network.NetworkData;
import network.Server;

public class ScheduleController {

	// FXML Objects under "Home" tab go here:
	@FXML VBox peerList;

	// FXML Objects under "Schedule a Meeting" tab go here:

	// FXML Objects under "My Schedule" tab go here:

	// FXML Objects under "Connect with a Peer" tab go here:
	@FXML TextField ip;
	@FXML Button  connect;
	@FXML Label connectMessage;

	// All other fields go here:
	private Server server;
	private Client client;
	private ArrayBlockingQueue<NetworkData> dataCollection;
	public static String USERNAME;


	public void initialize() {
		client = new Client();
		dataCollection = new ArrayBlockingQueue<>(20);

		new Thread(() -> startServer()).start();
		new Thread(() -> getData()).start();
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
			showPeers();
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
		alert.showAndWait();
	}

	@FXML
	void connect() {
		new Thread(() -> {
			try {
				NetworkData justReceived = client.requestConnection(ip.getText());
				dataCollection.add(justReceived);
			} catch (IOException | ClassNotFoundException e) {
				Platform.runLater(() -> displayError(e.getMessage()));
				e.printStackTrace();
			}
		}).start();
	}

}
