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

package com.kurento.commons.media.format.payload;

import java.io.Serializable;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.kurento.commons.media.format.exceptions.ArgumentNotSetException;

public class PayloadRtp implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String codecName;
	private int clockRate;

	private Integer channels = null;
	private Integer width = null;
	private Integer height = null;
	private Integer bitrate = null;

	private Map<String, String> params = new HashMap<String, String>();

	/**
	 * This constructor should not be used, just for serialization
	 * */
	public PayloadRtp() {

	}

	public PayloadRtp(int id, String codecName, int clockRate) {
		setCodecName(codecName);

		this.id = id;
		this.clockRate = clockRate;
	}

	public PayloadRtp(PayloadRtp rtp) {
		this(rtp.id, rtp.codecName, rtp.clockRate);

		channels = rtp.channels;
		width = rtp.width;
		height = rtp.height;
		bitrate = rtp.bitrate;

		params = new HashMap<String, String>(rtp.params);
	}

	public synchronized void setId(int id) {
		this.id = id;
	}

	public synchronized int getId() {
		return id;
	}

	public synchronized void setCodecName(String codecName) {
		if (codecName == null)
			throw new NullPointerException("codecName can not be null");

		this.codecName = codecName;
	}

	public synchronized String getCodecName() {
		return this.codecName;
	}

	public synchronized void setClockRate(int clockRate) {
		this.clockRate = clockRate;
	}

	public synchronized int getClockRate() {
		return this.clockRate;
	}

	public synchronized void setChannels(int channels) {
		this.channels = channels;
	}

	public synchronized int getChannels() throws ArgumentNotSetException {
		if (channels == null)
			throw new ArgumentNotSetException("Channels is not set");

		return channels;
	}

	public synchronized void setWidth(int width) {
		this.width = width;
	}

	public synchronized int getWidth() throws ArgumentNotSetException {
		if (width == null)
			throw new ArgumentNotSetException("Width is not set");

		return width;
	}

	public synchronized void setHeight(int height) {
		this.height = height;
	}

	public synchronized int getHeight() throws ArgumentNotSetException {
		if (height == null)
			throw new ArgumentNotSetException("Height is not set");

		return height;
	}

	public synchronized void setBitrate(int bitrate) {
		this.bitrate = bitrate;
	}

	public synchronized int getBitrate() throws ArgumentNotSetException {
		if (bitrate == null)
			throw new ArgumentNotSetException("Bitrate is not set");

		return bitrate;
	}

	public synchronized Set<String> getParametersKeys() {
		return Collections.unmodifiableSet(params.keySet());
	}

	public synchronized String getParemeterValue(String key) {
		return params.get(key);
	}

	public synchronized void setParameterValue(String key, String value) {
		params.put(key, value);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PayloadRtp [id=");
		builder.append(id);
		builder.append(", ");
		if (codecName != null) {
			builder.append("codecName=");
			builder.append(codecName);
			builder.append(", ");
		}
		builder.append("clockRate=");
		builder.append(clockRate);
		builder.append(", ");
		if (channels != null) {
			builder.append("channels=");
			builder.append(channels);
			builder.append(", ");
		}
		if (width != null) {
			builder.append("width=");
			builder.append(width);
			builder.append(", ");
		}
		if (height != null) {
			builder.append("height=");
			builder.append(height);
			builder.append(", ");
		}
		if (bitrate != null) {
			builder.append("bitrate=");
			builder.append(bitrate);
			builder.append(", ");
		}
		if (params != null) {
			builder.append("params=");
			builder.append(params);
		}
		builder.append("]");
		return builder.toString();
	}

	public static PayloadRtp intersect(PayloadRtp answerer, PayloadRtp offerer) {
		if (answerer == null || offerer == null)
			return null;

		if (!answerer.codecName.equalsIgnoreCase(offerer.codecName)
				|| answerer.clockRate != offerer.clockRate) {
			return null;
		}

		PayloadRtp rtp = new PayloadRtp(offerer.id, offerer.codecName,
				offerer.clockRate);

		rtp.channels = selectMinor(answerer.channels, offerer.channels);
		rtp.width = selectMinor(answerer.width, offerer.width);
		rtp.height = selectMinor(answerer.height, offerer.height);
		rtp.bitrate = selectMinor(answerer.bitrate, offerer.bitrate);

		for (String key : offerer.params.keySet()) {
			rtp.params.put(key, offerer.params.get(key));
		}

		for (String key : answerer.params.keySet()) {
			if (!rtp.params.containsKey(key))
				rtp.params.put(key, answerer.params.get(key));
		}

		return rtp;
	}

	private static Integer selectMinor(Integer a, Integer b) {
		if (a != null) {
			if (b != null)
				return a < b ? a : b;
			else
				return a;
		}
		return b;
	}
}
