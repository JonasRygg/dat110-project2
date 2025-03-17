package no.hvl.dat110.messagetransport;

import java.util.Arrays;
import java.util.Set;
import java.util.Collection;
import java.util.concurrent.ConcurrentHashMap;
import no.hvl.dat110.broker.ClientSession;
import no.hvl.dat110.common.Logger;
import no.hvl.dat110.messages.*;
import no.hvl.dat110.messagetransport.Connection;

public class TransportMessage {

	private byte[] payload;

	public TransportMessage(byte[] payload) {
		// Check for length within boundary
		if (payload == null || (payload.length + 1 > MessageConfig.SEGMENTSIZE)) {
			throw new RuntimeException("Message: invalid payload");
		}
		this.payload = payload;
	}

	public TransportMessage() {
		super();
	}

	public byte[] getData() {
		return this.payload;
	}

	public byte[] encapsulate() {
		byte[] encoded = new byte[MessageConfig.SEGMENTSIZE];
		encoded[0] = (byte) payload.length;
		System.arraycopy(payload, 0, encoded, 1, payload.length);
		return encoded;
	}

	public void decapsulate(byte[] received) {
		if (received == null || received.length == 0) {
			throw new RuntimeException("Invalid received data");
		}
		int len = received[0] & 0xFF;
		if (len + 1 > received.length) {
			throw new RuntimeException("Decapsulation error: Incorrect length");
		}
		payload = Arrays.copyOfRange(received, 1, len + 1);
	}
}