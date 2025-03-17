package no.hvl.dat110.iotsystem;

import no.hvl.dat110.client.Client;
import no.hvl.dat110.messages.PublishMsg;

public class DisplayDevice {

	private static final int COUNT = 10;

	public static void main (String[] args) {
		System.out.println("Display starting ...");

		// Create a client object and connect to the broker
		Client client = new Client("display", "localhost", 8080);
		client.connect();

		// Create the temperature topic on the broker
		String topic = "temperature";
		client.createTopic(topic);

		// Subscribe to the topic
		client.subscribe(topic);

		// Receive messages on the topic
		for (int i = 0; i < COUNT; i++) {
			PublishMsg msg = (PublishMsg) client.receive();
			if (msg != null) {
				System.out.println("Received: " + msg.getMessage());
			}
		}

		// Unsubscribe from the topic
		client.unsubscribe(topic);

		// Disconnect from the broker
		client.disconnect();

		System.out.println("Display stopping ... ");
	}
}
