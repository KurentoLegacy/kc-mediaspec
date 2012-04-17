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
}
