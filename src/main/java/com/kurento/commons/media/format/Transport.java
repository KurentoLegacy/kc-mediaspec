package com.kurento.commons.media.format;

import java.io.Serializable;

import com.kurento.commons.media.format.transport.TransportRtp;

public class Transport implements Serializable {

	private static final long serialVersionUID = 1L;

	private TransportRtp rtp = null;

	public Transport() {

	}

	public synchronized void setRtp(TransportRtp rtp) {
		this.rtp = rtp;
	}

	public synchronized TransportRtp getRtp() {
		return rtp;
	}
}
