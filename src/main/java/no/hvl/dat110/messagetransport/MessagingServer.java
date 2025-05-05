package no.hvl.dat110.messagetransport;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;

public class MessagingServer {

	private ServerSocket welcomeSocket;

	public MessagingServer(int port) {
		if (port < 0 || port > 65535) {
			throw new IllegalArgumentException("Port number must be between 0 and 65535");
		}

		try {
			this.welcomeSocket = new ServerSocket(port);
			System.out.println("Messaging server started on port " + port);
		} catch (IOException ex) {
			System.err.println("Messaging server: Failed to start on port " + port);
			ex.printStackTrace();
			throw new RuntimeException("Could not start server", ex);
		}
	}

	// Accept an incoming connection from a client
	public Connection accept() {
		Connection connection = null;

		try {
			System.out.println("Waiting for client connection...");
			Socket connectionSocket = welcomeSocket.accept();
			System.out.println("Client connected: " + connectionSocket.getInetAddress());

			connection = new Connection(connectionSocket);
		} catch (IOException ex) {
			System.err.println("Messaging server: Error accepting connection");
			ex.printStackTrace();
		}

		return connection;
	}

	// Stop the server and close the socket
	public void stop() {
		if (welcomeSocket != null && !welcomeSocket.isClosed()) {
			try {
				welcomeSocket.close();
				System.out.println("Messaging server stopped.");
			} catch (IOException ex) {
				System.err.println("Messaging server: Error closing server socket");
				ex.printStackTrace();
			}
		}
	}
}
