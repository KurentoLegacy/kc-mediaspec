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

package com.kurento.commons.media.format;

import java.io.Serializable;

import com.kurento.commons.media.format.exceptions.ArgumentNotSetException;
import com.kurento.commons.media.format.payload.PayloadRtp;

/**
 * 
 * This class provides a container to specific payload types. In a standard java
 * coding schema this class would have been declared abstract and specific
 * payload classes would ha inherit from it, but composition is used instead in
 * order to facilitate serialization.
 * 
 * @see MediaSpec
 * @see PayloadRtp
 * 
 */
public class Payload implements Serializable {

	private static final long serialVersionUID = 1L;

	private PayloadRtp rtp = null;

	/**
	 * Creates an empty payload container instance
	 */
	// TODO: Change visibility to private?
	public Payload() {
	}

	/**
	 * Creates a payload container as a clone of the given parameter
	 * 
	 * @param payload
	 *            Payload to be cloned
	 */
	public Payload(Payload payload) {
		if (payload.rtp != null)
			rtp = new PayloadRtp(payload.rtp);
	}

	/**
	 * Sets a new RTP payload descriptor. Previous one will be overriden
	 * 
	 * @param rtp
	 *            Payload descriptor to be stored within this container
	 */
	public synchronized void setRtp(PayloadRtp rtp) {
		this.rtp = rtp;
	}

	/**
	 * Returns stored RTP payload descriptor
	 * 
	 * @return Stored payload descriptor
	 * @throws ArgumentNotSetException
	 *             If rtp payload descriptor is null
	 */
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

	/**
	 * Calculates intersection between local and remote payload. This function
	 * is not intended to be used by application
	 * 
	 * @param ansPayload
	 * @param offPayload
	 * @return Payload[answerer,offerer]
	 */
	// TODO: Change visibility to protected
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
