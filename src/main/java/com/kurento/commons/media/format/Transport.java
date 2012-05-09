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

public class Transport implements Serializable {

	private static final long serialVersionUID = 1L;

	private TransportRtp rtp = null;
	private TransportRtmp rtmp = null;

	public Transport() {

	}

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

	public synchronized void setRtp(TransportRtp rtp) {
		this.rtp = rtp;
	}

	public synchronized void setRtmp(TransportRtmp rtmp) {
		this.rtmp = rtmp;
	}

	public synchronized TransportRtp getRtp() throws ArgumentNotSetException {
		if (rtp == null)
			throw new ArgumentNotSetException("Rtp is not set");
		return rtp;
	}

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
