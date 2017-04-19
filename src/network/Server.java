package network;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private ServerSocket accepter;
	static int PORT = 8881;

	public Server() throws IOException {
		accepter = new ServerSocket(PORT);
		System.out.println("Server: IP Address: " + accepter.getInetAddress() + " (" + PORT + ")");
	}

	public void listen() throws IOException {
		System.out.println("Server: Now listening");
		for (;;) {
			Socket socket = accepter.accept();
			SocketCommunicationThread communicator = new SocketCommunicationThread(socket);
			System.out.println("Server: Incoming request from " + socket.getInetAddress());
			communicator.start();
		}
	}

	private class SocketCommunicationThread extends Thread {

		private Socket socket;

		public SocketCommunicationThread(Socket socket) {
			this.socket = socket;
		}

		public void run() {
			try {
				NetworkData justReceived = getData();
				System.out.println("Server: Received [" + justReceived.getTag() + "]");
				unpackData(justReceived);
			} catch (IOException | ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

		private NetworkData getData() throws IOException, ClassNotFoundException {
			ObjectInputStream sockin = new ObjectInputStream(socket.getInputStream());
			NetworkData justReceived = (NetworkData) sockin.readObject();
			return justReceived;
		}

		private void unpackData(NetworkData data) throws IOException {
			if (data.getTag().equals(NetworkData.CONNECT_TAG)) {
				confirmConnection(data);
			}
		}

		private void confirmConnection(NetworkData data) throws IOException {
			ObjectOutputStream sockout = new ObjectOutputStream(socket.getOutputStream());
			NetworkData toSend = new NetworkData(NetworkData.CONNECT_TAG);
			sockout.writeObject(toSend);
			System.out.println("Server: Sent [" + toSend.getTag() + "]");
		}
	}
}
