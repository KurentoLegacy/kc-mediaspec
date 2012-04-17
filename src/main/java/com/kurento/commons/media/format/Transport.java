package com.kurento.commons.media.format;

import java.io.Serializable;

import com.kurento.commons.media.format.transport.TransportRtp;

public class Transport implements Serializable {

	private static final long serialVersionUID = 1L;

	private TransportRtp rtp = null;

	public Transport() {

	}

	public Transport(Transport transport) {
		if (transport.getRtp() != null)
			this.rtp = new TransportRtp(transport.getRtp());
	}

	public synchronized void setRtp(TransportRtp rtp) {
		this.rtp = rtp;
	}

	public synchronized TransportRtp getRtp() {
		return rtp;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Transport [");
		if (rtp != null) {
			builder.append("rtp=");
			builder.append(rtp);
		}
		builder.append("]");
		return builder.toString();
	}
}
