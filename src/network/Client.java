package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.sql.ResultSet;
import java.util.HashMap;
import java.time.LocalDate;
import java.util.ArrayList;


public class Client {

	private HashMap<String, String> peers;

	public Client() {
		peers = new HashMap<>();
	}

	public HashMap<String, String> getPeers() {
		return this.peers;
	}

	public String confirmConnection(NetworkData data, String ip) throws InterruptedException {
		String peerUsername = data.getUsername();
		peers.put(peerUsername, ip);
		return "You are now connected with " + peerUsername;
	}

	public NetworkData requestConnection(String ip) throws UnknownHostException, IOException, ClassNotFoundException {
		Socket target = new Socket(ip, Server.PORT);
		NetworkData request = new NetworkData(NetworkData.CONNECT_TAG);
		sendRequest(target, request);
		NetworkData justReceived = receiveData(target);
		target.close();
		return justReceived;
	}

	public ArrayList<String> checkPeerSchedules(String hour, String minute, String am_pm, LocalDate date) throws UnknownHostException, IOException, ClassNotFoundException {
		String day = reformatDay(date.getDayOfWeek().getValue());
		String time = reformatTime(hour, minute, am_pm);
		ArrayList<String> whoIsFree = new ArrayList<>();

		for (String peerUsername : peers.keySet()) {
			String query = buildQuery(day, time, peerUsername);
			NetworkData justReceived = requestMeeting(peerUsername, peers.get(peerUsername), query);
			String message = buildMessage(justReceived, day, time);
			whoIsFree.add(message);
		}

		return whoIsFree;
	}

	public void getMeetingRecommendations() throws UnknownHostException, ClassNotFoundException, IOException {
		HashMap<String, ResultSet> peerTables = new HashMap<>();
		for (String peerUsername : peers.keySet()) {
			String query = "SELECT * FROM " + peerUsername;
			NetworkData justReceived = requestRecommendations(peerUsername, peers.get(peerUsername), query);
			peerTables.put(peerUsername, justReceived.getResultSet());
		}

	}

	private String buildMessage(NetworkData justReceived, String day, String time) {
		String msg;
		String peerName = justReceived.getUsername();

		if (justReceived.getIsFree()) {
			msg = peerName + " is free on " + day + " at " + time;
		} else {
			msg = peerName + " is busy on " + day + " at " + time;
		}
		return msg;
	}

	private NetworkData requestRecommendations(String peerName, String ip, String query) throws UnknownHostException, IOException, ClassNotFoundException {
		NetworkData request = new NetworkData(NetworkData.SCHEDULE_TAG);
		request.setQuery(query);
		Socket target = new Socket(ip, Server.PORT);
		sendRequest(target, request);
		NetworkData justReceived = receiveData(target);
		return justReceived;
	}

	private NetworkData requestMeeting(String peerName, String ip, String query) throws UnknownHostException, IOException, ClassNotFoundException {
		NetworkData request = new NetworkData(NetworkData.MEETING_TAG);
		request.setQuery(query);
		Socket target = new Socket(ip, Server.PORT);
		sendRequest(target, request);
		NetworkData justReceived = receiveData(target);
		return justReceived;
	}

	private String reformatDay(int day) {
		if (day == 1) {
			return "Monday";
		} else if (day == 2) {
			return "Tuesday";
		} else if (day == 3) {
			return "Wednesday";
		} else if (day == 4) {
			return "Thursday";
		} else {
			return "Friday";
		}
	}

	private String reformatTime(String hour, String minute, String am_pm) {
		String time = hour + ":" + minute + " " + am_pm;
		return time;
	}

	private String buildQuery(String day, String time, String peerName) {
		String query = "SELECT " + day + " FROM " + peerName + " WHERE Time = '" + time + "'";
		return query;
	}

	private void sendRequest(Socket target, NetworkData request) throws IOException {
		ObjectOutputStream sockout = new ObjectOutputStream(target.getOutputStream());
		sockout.writeObject(request);
		sockout.flush();
		System.out.println("Client: Sent [" + request.getTag() + "]" );
	}

	private NetworkData receiveData(Socket target) throws IOException, ClassNotFoundException {
		ObjectInputStream sockin = new ObjectInputStream(target.getInputStream());
		NetworkData justReceived = (NetworkData) sockin.readObject();
		System.out.println("Client: Received [" + justReceived.getTag() + "]");
		return justReceived;
	}

}
