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
 * coding schema this class would have been declared abstract, and specific
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
	 * Creates an empty payload container instance.
	 */
	// TODO: Change visibility to private?
	public Payload() {
	}

	/**
	 * Create a payload container initialized with a duplicate of specified
	 * parameter.
	 * 
	 * @param payload
<<<<<<< HEAD
	 *             Payload to be cloned.
=======
	 *            - Payload to be cloned.
>>>>>>> f13a704c8df53b5547f5d107bae5bfb4d03c96e5
	 */
	public Payload(Payload payload) {
		if (payload.rtp != null)
			rtp = new PayloadRtp(payload.rtp);
	}

	/**
	 * Set a new RTP payload descriptor. Old descriptor is replaced with new
	 * one if previously assigned.
	 * 
	 * @param rtp
<<<<<<< HEAD
	 *             Payload descriptor to be stored within this container.
=======
	 *            - Payload descriptor to be stored within this container.
>>>>>>> f13a704c8df53b5547f5d107bae5bfb4d03c96e5
	 */
	public synchronized void setRtp(PayloadRtp rtp) {
		this.rtp = rtp;
	}

	/**
	 * Return RTP payload descriptor contained within this payload.
	 * 
	 * @return Stored payload descriptor.
	 * @throws ArgumentNotSetException
	 *             If argument hasn't been initialized.
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
