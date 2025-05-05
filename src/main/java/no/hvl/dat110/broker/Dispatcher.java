package no.hvl.dat110.broker;

import java.util.Collection;
import no.hvl.dat110.common.Logger;
import no.hvl.dat110.messages.ConnectMsg;
import no.hvl.dat110.messages.Message;
import no.hvl.dat110.messages.PublishMsg;
import no.hvl.dat110.messagetransport.Connection;

public class Dispatcher extends Thread {
	private Storage storage;
	private boolean running = true;

	public Dispatcher(Storage storage) {
		this.storage = storage;
	}

	@Override
	public void run() {
		Logger.log("Dispatcher running");

		while (running) {
			Collection<ClientSession> clients = storage.getSessions();

			for (ClientSession client : clients) {
				if (client.hasData()) {
					Message msg = client.receive();
					if (msg != null) {
						dispatch(client, msg);
					}
				}
			}

			try {
				Thread.sleep(1000);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	public void stopDispatcher() {
		running = false;
	}

	public void doStop() {
		running = false;
	}

	public void onConnect(ConnectMsg msg, Connection connection) {
		String user = msg.getUser();
		Logger.log("onConnect: " + msg.toString());

		ClientSession session = new ClientSession(user, connection);
		storage.addClientSession(user, session);
	}

	private void dispatch(ClientSession client, Message msg) {
		Logger.log("Dispatching message: " + msg.toString());

		switch (msg.getType()) {
			case PUBLISH:
				onPublish((PublishMsg) msg);
				break;
			default:
				Logger.log("Unhandled message type: " + msg.getType());
				break;
		}
	}

	private void onPublish(PublishMsg msg) {
		Logger.log("onPublish: " + msg.toString());
		for (String subscriber : storage.getSubscribers(msg.getTopic())) {
			ClientSession session = storage.getSession(subscriber);
			if (session != null) {
				session.send(msg);
			}
		}
	}
}
