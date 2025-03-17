package no.hvl.dat110.iotsystem;

import no.hvl.dat110.client.Client;

public class TemperatureDevice {

	private static final int COUNT = 10;

	public static void main(String[] args) {

		// Simulated / virtual temperature sensor
		TemperatureSensor sn = new TemperatureSensor();

		System.out.println("Temperature device starting ...");

		// Create a client object and connect to the broker
		Client client = new Client("sensor", "localhost", 8080);
		client.connect();

		String topic = "temperature";

		// Publish the temperature(s)
		for (int i = 0; i < COUNT; i++) {
			int temp = sn.read();
			System.out.println("Publishing: " + temp);
			client.publish(topic, Integer.toString(temp));
			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}

		// Disconnect from the broker
		client.disconnect();

		System.out.println("Temperature device stopping ...");
	}
}
