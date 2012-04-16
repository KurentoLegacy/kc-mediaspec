package com.kurento.commons.media.format.transport;

import java.io.Serializable;

public class TransportRtp implements Serializable {

	private static final long serialVersionUID = 1L;

	private String address;
	private int port;

	/**
	 * This constructor should not be used, just for serialization
	 */
	public TransportRtp() {
		address = "localhost";
	}

	public TransportRtp(String address, int port) {
		this.address = address;
		this.port = port;
	}

	public synchronized String getAddress() {
		return new String(address);
	}

	public synchronized int getPort() {
		return port;
	}

	public synchronized void setAddress(String address) {
		if (address == null)
			throw new NullPointerException("Address can not be null");
		this.address = new String(address);
	}

	public synchronized void setPort(int port) {
		this.port = port;
	}
}
