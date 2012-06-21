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
import com.kurento.commons.media.format.transport.TransportRtmp;
import com.kurento.commons.media.format.transport.TransportRtp;

/**
 * 
 * This class provides a container to specific transport types. In a standard
 * java coding schema this class would have been declared abstract and specific
 * transport classes would ha inherit from it, but composition is used instead
 * in order to facilitate serialization.
 * 
 * @see MediaSpec
 * @see TransportRtmp
 * @see TransportRtp
 */
public class Transport implements Serializable {

	private static final long serialVersionUID = 1L;

	private TransportRtp rtp = null;
	private TransportRtmp rtmp = null;

	/**
	 * Creates an empty transport container
	 */
	//TODO: Change visibility to private?
	public Transport() {

	}

	/**
	 * Creates a new transport container as a clone of the given one
	 * 
	 * @param transport
	 *            Transport to be cloned
	 */
	public Transport(Transport transport) {
		try {
			this.rtp = new TransportRtp(transport.getRtp());
		} catch (ArgumentNotSetException e) {

		}
		try {
			this.rtmp = new TransportRtmp(transport.getRtmp());
		} catch (ArgumentNotSetException e) {

		}
	}

	/**
	 * Sets an RTP transport instance. Previous one is overriden.
	 * 
	 * @param rtp
	 *            RTP transport to be assigned to this container.
	 */
	public synchronized void setRtp(TransportRtp rtp) {
		this.rtp = rtp;
	}

	/**
	 * Sets an RTMP transport instance. Previous one is overriden.
	 * 
	 * @param rtmp
	 *            RTMP transport to be assigned to this container.
	 */
	public synchronized void setRtmp(TransportRtmp rtmp) {
		this.rtmp = rtmp;
	}

	/**
	 * Returns RTP transport stored by this container
	 * 
	 * @return RTP transport stored by this container
	 * @throws ArgumentNotSetException
	 *             if RTP transport is null
	 */
	public synchronized TransportRtp getRtp() throws ArgumentNotSetException {
		if (rtp == null)
			throw new ArgumentNotSetException("Rtp is not set");
		return rtp;
	}

	/**
	 * Returns RTMP transport store by this container
	 * 
	 * @return RTMP transport stored by this container
	 * @throws ArgumentNotSetException
	 *             if RTMP transport is null
	 */
	public synchronized TransportRtmp getRtmp() throws ArgumentNotSetException {
		if (rtmp == null)
			throw new ArgumentNotSetException("Rtmp is not set");
		return rtmp;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Transport [");
		if (rtp != null) {
			builder.append("rtp=");
			builder.append(rtp);
			builder.append(", ");
		}
		if (rtmp != null) {
			builder.append("rtmp=");
			builder.append(rtmp);
		}
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Calculates intersection between local and remote transport. This function
	 * is not intended to be used by application
	 * 
	 * @param answerer answer
	 * @param  offerer offer
	 * @return Transport [answerer, offerer]
	 */
	// TODO: Change visibility to protected
	public static Transport[] intersect(Transport answerer, Transport offerer) {
		Transport neg_answ = new Transport(answerer);
		Transport neg_off = new Transport(offerer);

		try {
			TransportRtmp answ_rtmp = neg_answ.getRtmp();
			TransportRtmp off_rtmp = neg_off.getRtmp();

			TransportRtmp[] rtmps = TransportRtmp.instersect(answ_rtmp,
					off_rtmp);

			if (rtmps == null)
				throw new ArgumentNotSetException();

			neg_answ.rtmp = rtmps[0];
			neg_off.rtmp = rtmps[1];

		} catch (ArgumentNotSetException e) {
			neg_answ.rtmp = null;
			neg_off.rtmp = null;
		}

		if (neg_answ.rtmp == null && neg_answ.rtp == null
				|| neg_off.rtmp == null && neg_off.rtp == null)
			return null;

		return new Transport[] { neg_answ, neg_off };
	}
}
