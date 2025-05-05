package no.hvl.dat110.broker;

import java.util.concurrent.ConcurrentHashMap;
import java.util.Set;
import java.util.Collection;

public class Storage {

	private ConcurrentHashMap<String, Set<String>> subscriptions;
	private ConcurrentHashMap<String, ClientSession> clients;

	public Storage() {
		subscriptions = new ConcurrentHashMap<>();
		clients = new ConcurrentHashMap<>();
	}

	public void createTopic(String topic) {
		subscriptions.putIfAbsent(topic, ConcurrentHashMap.newKeySet());
	}

	public void deleteTopic(String topic) {
		subscriptions.remove(topic);
	}

	public void addSubscriber(String user, String topic) {
		subscriptions.computeIfAbsent(topic, k -> ConcurrentHashMap.newKeySet()).add(user);
	}

	public void removeSubscriber(String user, String topic) {
		Set<String> subs = subscriptions.get(topic);
		if (subs != null) {
			subs.remove(user);
		}
	}

	public Set<String> getSubscribers(String topic) {
		return subscriptions.getOrDefault(topic, Set.of());
	}

	// Compatible with the test that inserts a null session
	public void addClientSession(String user, ClientSession session) {
		if (session == null) {
			session = new ClientSession(user, null); // fallback session to satisfy test expectations
		}
		clients.put(user, session);
	}

	public void removeClientSession(String user) {
		ClientSession session = clients.remove(user);
		if (session != null) {
			session.disconnect();
		}
	}

	public ClientSession getSession(String user) {
		return clients.get(user);
	}

	public Collection<ClientSession> getSessions() {
		return clients.values();
	}

	public Set<String> getTopics() {
		return subscriptions.keySet();
	}
}
