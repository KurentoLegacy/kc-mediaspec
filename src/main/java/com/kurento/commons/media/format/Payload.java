package com.kurento.commons.media.format;

import java.io.Serializable;

import com.kurento.commons.media.format.payload.PayloadRtp;

public class Payload implements Serializable {

	private static final long serialVersionUID = 1L;

	private PayloadRtp rtp = null;

	public Payload() {
	}

	public synchronized void setRtp(PayloadRtp rtp) {
		this.rtp = rtp;
	}

	public synchronized PayloadRtp getRtp() {
		return rtp;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Payload [");
		if (rtp != null) {
			builder.append("rtp=");
			builder.append(rtp);
		}
		builder.append("]");
		return builder.toString();
	}

}
