/*
 * kc-mediaspec: Media description common format for java
 * Copyright (C) 2012 Tikal Technologies
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.kurento.mediaspec;

import java.io.Serializable;

/**
 * This class provides a description mechanism for RTP transport. It basically
 * contains the transport address, consisting of IP address and UDP port.
 * Transport instance represent the reception address for local descriptors and
 * transmission address for remote descriptors.<br>
 * Remember each peer in a communication must handle to descriptor: local,
 * specifying reception information and remote, with transmission configuration.
 * The same descriptor swaps its role on each side of the communication.
 * 
 */
public class TransportRtp implements Serializable {

	private static final long serialVersionUID = 1L;

	private String address;
	private int port;

	/**
	 * This constructor should not be used, just for serialization
	 */
	@SuppressWarnings("unused")
	private TransportRtp() {
		address = "localhost";
	}

	/**
	 * Creates a new RTP transport descriptor initialized with the given
	 * transport address information
	 * 
	 * @param address
	 *            IP address
	 * @param port
	 *            UDP port
	 */
	public TransportRtp(String address, int port) {
		setAddress(address);
		this.port = port;
	}

	/**
	 * Creates a duplicate RTP transport instance of the one given as parameter
	 * 
	 * @param rtp
	 *            RTP transport to be cloned
	 */
	public TransportRtp(TransportRtp rtp) {
		this.address = rtp.address;
		this.port = rtp.port;
	}

	/**
	 * Returns the IP address of this RTP transport
	 * 
	 * @return IP address
	 */
	public synchronized String getAddress() {
		return address;
	}

	/**
	 * Returns the UDP port of this RTP transport
	 * 
	 * @return UDP port
	 */
	public synchronized int getPort() {
		return port;
	}

	/**
	 * Sets a new IP address
	 * 
	 * @param address
	 *            IP address
	 * 
	 * @throws NullPointerException
	 *             if address is null
	 */
	public synchronized void setAddress(String address) {
		if (address == null)
			throw new NullPointerException("address can not be null");
		this.address = address;
	}

	/**
	 * Sets a new UDP port
	 * 
	 * @param port
	 *            UDP port
	 */
	public synchronized void setPort(int port) {
		this.port = port;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TransportRtp [");
		if (address != null) {
			builder.append("address=");
			builder.append(address);
			builder.append(", ");
		}
		builder.append("port=");
		builder.append(port);
		builder.append("]");
		return builder.toString();
	}
}
