package network;

import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;

public class Server {

	private ServerSocket accepter;

	public Server(int port) throws IOException {
		accepter = new ServerSocket(port);
		System.out.println("Server: IP Address: " + accepter.getInetAddress() + " (" + port + ")");
		System.out.println("Server: Now listening");
	}

	public void listen() throws IOException {
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
				ObjectOutputStream sockout = new ObjectOutputStream(socket.getOutputStream());
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
