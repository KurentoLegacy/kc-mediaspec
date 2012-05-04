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
		setAddress(address);
		this.port = port;
	}

	public TransportRtp(TransportRtp rtp) {
		this.address = rtp.address;
		this.port = rtp.port;
	}

	public synchronized String getAddress() {
		return address;
	}

	public synchronized int getPort() {
		return port;
	}

	public synchronized void setAddress(String address) {
		if (address == null)
			throw new NullPointerException("address can not be null");
		this.address = address;
	}

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
