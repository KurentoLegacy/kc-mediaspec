package com.kurento.commons.media.format;

import java.io.Serializable;

import com.kurento.commons.media.format.exceptions.ArgumentNotSetException;
import com.kurento.commons.media.format.payload.PayloadRtp;

public class Payload implements Serializable {

	private static final long serialVersionUID = 1L;

	private PayloadRtp rtp = null;

	public Payload() {
	}

	public synchronized void setRtp(PayloadRtp rtp) {
		this.rtp = rtp;
	}

	public synchronized PayloadRtp getRtp() throws ArgumentNotSetException {
		if (rtp == null)
			throw new ArgumentNotSetException("Rtp is not set");
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

	public static Payload intersect(Payload ansPayload, Payload offPayload) {
		if (ansPayload == null || offPayload == null)
			return null;
		Payload intersect = new Payload();
		intersect.rtp = PayloadRtp.intersect(ansPayload.rtp, offPayload.rtp);

		if (intersect.rtp == null)
			return null;

		return intersect;
	}

}
