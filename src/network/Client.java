package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.HashMap;


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
		System.out.println(ip);
		NetworkData request = new NetworkData(NetworkData.CONNECT_TAG);
		sendRequest(target, request);
		NetworkData justReceived = receiveData(target);
		target.close();
		return justReceived;
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
