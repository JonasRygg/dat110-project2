package no.hvl.dat110.messages;

import com.google.gson.*;
import no.hvl.dat110.common.Logger;
import no.hvl.dat110.messagetransport.Connection;
import no.hvl.dat110.messagetransport.TransportMessage;

public class MessageUtils {

	public static Message fromJson(String msg) {
		JsonObject json = JsonParser.parseString(msg).getAsJsonObject();
		String typestr = json.get("type").getAsString();
		MessageType type = MessageType.valueOf(typestr);
		Gson gson = new Gson();
		Message message = null;

		switch (type) {
			case CONNECT:
				message = gson.fromJson(json, ConnectMsg.class);
				break;
			case DISCONNECT:
				message = gson.fromJson(json, DisconnectMsg.class);
				break;
			case CREATETOPIC:
				message = gson.fromJson(json, CreateTopicMsg.class);
				break;
			case DELETETOPIC:
				message = gson.fromJson(json, DeleteTopicMsg.class);
				break;
			case SUBSCRIBE:
				message = gson.fromJson(json, SubscribeMsg.class);
				break;
			case UNSUBSCRIBE:
				message = gson.fromJson(json, UnsubscribeMsg.class);
				break;
			case PUBLISH:
				message = gson.fromJson(json, PublishMsg.class);
				break;
			default:
				Logger.log("fromJson - unknown message type");
				break;
		}
		return message;
	}

	public static Message fromBytes(byte[] payload) {
		return fromJson(new String(payload));
	}

	public static String toJson(Message msg) {
		Gson gson = new Gson();
		return gson.toJson(msg);
	}

	public static byte[] getBytes(Message msg) {
		return toJson(msg).getBytes();
	}

	public static TransportMessage toTransportMessage(Message msg) {
		return new TransportMessage(getBytes(msg));
	}

	public static Message fromTransportMessage(TransportMessage msg) {
		return fromBytes(msg.getData());
	}

	public static void send(Connection connection, Message message) {
		connection.send(toTransportMessage(message));
		Logger.log("Sent message: " + message.toString());
	}

	public static Message receive (Connection connection) {
		Logger.log("Trying to receive...");
		TransportMessage tmsg = connection.receive();
		if (tmsg == null) {
			Logger.log("Transport message was null");
			return null;
		}
		Logger.log("Received transport msg: " + new String(tmsg.getData()));
		Message msg = fromTransportMessage(tmsg);
		Logger.log("Parsed message: " + msg);
		return msg;
	}

}
