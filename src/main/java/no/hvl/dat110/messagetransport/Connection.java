package no.hvl.dat110.messagetransport;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.Socket;

public class Connection {

	private DataOutputStream outStream;
	private DataInputStream inStream;
	private Socket socket;

	public Connection(Socket socket) {
		try {
			this.socket = socket;
			outStream = new DataOutputStream(socket.getOutputStream());
			inStream = new DataInputStream(socket.getInputStream());
		} catch (IOException ex) {
			System.out.println("Connection initialization failed: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public void send(TransportMessage message) {
		try {
			byte[] sendbuf = message.encapsulate();
			outStream.writeInt(sendbuf.length);
			outStream.write(sendbuf);
			outStream.flush(); // Ensure data is fully sent before closing
		} catch (IOException ex) {
			System.out.println("Error sending message: " + ex.getMessage());
			ex.printStackTrace();
		}
	}

	public boolean hasData() {
		boolean hasData = false;
		try {
			hasData = inStream.available() > 0;
		} catch (IOException ex) {
			System.out.println("Error checking data availability: " + ex.getMessage());
			ex.printStackTrace();
		}
		return hasData;
	}

	public TransportMessage receive() {
		TransportMessage message = new TransportMessage();
		try {
			int length = inStream.readInt();
			byte[] recvbuf = new byte[length];
			inStream.readFully(recvbuf);
			message.decapsulate(recvbuf);
		} catch (IOException ex) {
			System.out.println("Error receiving message: " + ex.getMessage());
			ex.printStackTrace();
		}
		return message;
	}

	public void close() {
		try {
			outStream.close();
			inStream.close();
			socket.close();
		} catch (IOException ex) {
			System.out.println("Error closing connection: " + ex.getMessage());
			ex.printStackTrace();
		}
	}
}
