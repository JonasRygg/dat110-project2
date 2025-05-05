package no.hvl.dat110.messagetransport;

import java.io.IOException;
import java.net.Socket;

public class MessagingClient {

	private String server;
	private int port;

	public MessagingClient(String server, int port) {
		if (server == null || server.isEmpty()) {
			throw new IllegalArgumentException("Server address cannot be null or empty");
		}
		if (port < 0 || port > 65535) {
			throw new IllegalArgumentException("Port number must be between 0 and 65535");
		}

		this.server = server;
		this.port = port;
	}

	public Connection connect() {
		try {
			Socket clientSocket = new Socket(server, port);
			return new Connection(clientSocket);
		} catch (IOException ex) {
			System.err.println("Client failed to connect to " + server + " on port " + port);
			ex.printStackTrace();
			return null;
		}
	}
}
