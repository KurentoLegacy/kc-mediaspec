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

/**
 * 
 * PayloadRtp represents a RTP payload descriptor. it contains the specification
 * of an RTP payload, including:
 * <ul>
 * <li>Payload number (id).
 * <li>Media codec name.
 * <li>Media sample rate.
 * <li>Stream bit rate.
 * <li>Codec frame rate. This is applicable only to VIDEO type.
 * <li>Video format: width x height.
 * </ul>
 * 
 */
public class PayloadRtp implements Serializable {

	private static final long serialVersionUID = 1L;

	private int id;
	private String codecName;
	private int clockRate;

	private Fraction framerate = null;
	private Integer channels = null;
	private Integer width = null;
	private Integer height = null;
	private Integer bitrate = null;

	private Map<String, String> params = new HashMap<String, String>();

	/**
	 * This constructor should not be used, just for serialization
	 * */
	// TODO: Change visibility to private
	public PayloadRtp() {

	}

	/**
	 * Creates an RTP payload instance initialized with specified parameters.
	 * 
	 * @param id
<<<<<<< HEAD
	 *            Payload number assigned to this descriptor.
	 * @param codecName
	 *            Name of the codec.
	 * @param clockRate
	 *            Codec sample rate.
=======
	 *            - Payload number assigned to this descriptor.
	 * @param codecName
	 *            - Name of the codec.
	 * @param clockRate
	 *            - Codec sample rate.
>>>>>>> f13a704c8df53b5547f5d107bae5bfb4d03c96e5
	 */
	public PayloadRtp(int id, String codecName, int clockRate) {
		setCodecName(codecName);

		this.id = id;
		this.clockRate = clockRate;
	}

	/**
	 * Creates an RTP payload cloned from the given one.
	 * 
	 * @param rtp
<<<<<<< HEAD
	 *             RTP payload to be cloned.
=======
	 *            - RTP payload to be cloned.
>>>>>>> f13a704c8df53b5547f5d107bae5bfb4d03c96e5
	 */
	public PayloadRtp(PayloadRtp rtp) {
		this(rtp.id, rtp.codecName, rtp.clockRate);

		channels = rtp.channels;
		width = rtp.width;
		height = rtp.height;
		bitrate = rtp.bitrate;

		params = new HashMap<String, String>(rtp.params);
	}

	/**
	 * Set payload number.
	 * 
	 * @param id
<<<<<<< HEAD
	 *            Payload number.
=======
	 *            - Payload number.
>>>>>>> f13a704c8df53b5547f5d107bae5bfb4d03c96e5
	 */
	public synchronized void setId(int id) {
		this.id = id;
	}

	/**
	 * Get payload number.
	 * 
	 * @return Payload number.
	 */
	public synchronized int getId() {
		return id;
	}

	/**
	 * Sets codec name. <code>Null</code> value is not permitted.
	 * 
	 * @param codecName
<<<<<<< HEAD
	 *             Name of the codec to be assigned.
=======
	 *            - Name of the codec to be assigned.
>>>>>>> f13a704c8df53b5547f5d107bae5bfb4d03c96e5
	 */
	public synchronized void setCodecName(String codecName) {
		if (codecName == null)
			throw new NullPointerException("codecName can not be null");

		this.codecName = codecName;
	}

	/**
	 * Return codec name or <code>null</code> if not previously assigned.
	 * 
	 * @return Codec name.
	 */
	public synchronized String getCodecName() {
		return this.codecName;
	}

	/**
	 * Sets stream sample rate. Only applicable to video codecs.
	 * 
	 * @param clockRate
<<<<<<< HEAD
	 *            Sample rate.
=======
	 *            - Sample rate.
>>>>>>> f13a704c8df53b5547f5d107bae5bfb4d03c96e5
	 */
	public synchronized void setClockRate(int clockRate) {
		this.clockRate = clockRate;
	}

	/**
	 * Get stream sample rate.
	 * 
	 * @return Sample rate.
	 */
	public synchronized int getClockRate() {
		return this.clockRate;
	}

	/**
	 * Sets the number of streams associated to this payload. All them will be
	 * multiplexed within the same payload.
	 * 
	 * @param channels
<<<<<<< HEAD
	 *             Number of streams multiplexed within this payload.
=======
	 *            - Number of streams multiplexed within this payload.
>>>>>>> f13a704c8df53b5547f5d107bae5bfb4d03c96e5
	 */
	public synchronized void setChannels(int channels) {
		this.channels = channels;
	}

	/**
	 * Return the number of streams multiplexed within this payload.
	 * 
	 * @return Number of streams.
	 * @throws ArgumentNotSetException
	 *             If this attribute was not set before.
	 */

	public synchronized int getChannels() throws ArgumentNotSetException {
		if (channels == null)
			throw new ArgumentNotSetException("Channels is not set");

		return channels;
	}

	/**
	 * Sets width magnitude of a video stream.
	 * 
	 * @param width
<<<<<<< HEAD
	 *             Video width magnitude.
=======
	 *            - Video width magnitude.
>>>>>>> f13a704c8df53b5547f5d107bae5bfb4d03c96e5
	 */
	public synchronized void setWidth(int width) {
		this.width = width;
	}

	/**
	 * Return configured video width magnitude.
	 * 
	 * @return video width.
	 * @throws ArgumentNotSetException
	 *             If this attribute was not set before.
	 */
	public synchronized int getWidth() throws ArgumentNotSetException {
		if (width == null)
			throw new ArgumentNotSetException("Width is not set");

		return width;
	}

	/**
	 * Sets height magnitude of a video stream.
	 * 
	 * @param height
<<<<<<< HEAD
	 *             Video height magnitude
=======
	 *            - Video height magnitude
>>>>>>> f13a704c8df53b5547f5d107bae5bfb4d03c96e5
	 */

	public synchronized void setHeight(int height) {
		this.height = height;
	}

	/**
	 * Return configured video height magnitude
	 * 
	 * @return video height
	 * 
	 * @throws ArgumentNotSetException
	 *             If this attribute was not set before
	 */

	public synchronized int getHeight() throws ArgumentNotSetException {
		if (height == null)
			throw new ArgumentNotSetException("Height is not set");

		return height;
	}

	/**
	 * Sets maximum allowed bitrate for this payload.
	 * 
	 * @param bitrate
<<<<<<< HEAD
	 *             Maximum bit rate.
=======
	 *            - Maximum bit rate.
>>>>>>> f13a704c8df53b5547f5d107bae5bfb4d03c96e5
	 */
	public synchronized void setBitrate(int bitrate) {
		this.bitrate = bitrate;
	}

	/**
	 * Return maximum allowed bit rate configured for this payload.
	 * 
	 * @return Maximum bit rate.
	 * @throws ArgumentNotSetException
	 *             If this attribute hasn't been initialized.
	 */
	public synchronized int getBitrate() throws ArgumentNotSetException {
		if (bitrate == null)
			throw new ArgumentNotSetException("Bitrate is not set");

		return bitrate;
	}

	/**
	 * Sets this payload target frame rate.
	 * 
	 * @param framerate
<<<<<<< HEAD
	 *             Target frame rate.
=======
	 *            - Target frame rate.
>>>>>>> f13a704c8df53b5547f5d107bae5bfb4d03c96e5
	 */
	public synchronized void setFramerate(Fraction framerate) {
		this.framerate = framerate;
	}

	/**
	 * Return payload target frame rate.
	 * 
	 * @return Configured target frame rate.
	 * @throws ArgumentNotSetException
	 *             If this attribute hasn't been initialized .
	 */
	public synchronized Fraction getFramerate() throws ArgumentNotSetException {
		if (framerate == null)
			throw new ArgumentNotSetException("framerate is not set");

		return framerate;
	}

	/**
	 * Parameters represent the list of <code>ftmp</code> configurations
	 * associated to the payload. This method returns the key set of all
	 * configuration mappings stored in this payload.
	 * 
	 * @return Set of parameters.
	 */
	public synchronized Set<String> getParametersKeys() {
		return Collections.unmodifiableSet(params.keySet());
	}

	/**
	 * Parameters represent the list of <code>ftmp</code> configurations
	 * associated to the payload. This method retrieves the value mapped to
	 * specified key or <code>null</code> if this payload does not hold any
	 * association for the key.
	 * 
	 * @param key
<<<<<<< HEAD
	 *            Key whose associated value is to be returned.
=======
	 *            - Key whose associated value is to be returned.
>>>>>>> f13a704c8df53b5547f5d107bae5bfb4d03c96e5
	 * @return Value associated to specified key or <code>null</code> if no
	 *         mapping is found.
	 */
	public synchronized String getParemeterValue(String key) {
		return params.get(key);
	}

	/**
	 * 
	 * Parameters represent the list of <code>ftmp</code> configurations
	 * associated to the payload. This method associates to this paylod a new
	 * key=value pair. Previous associations of the same key are replaced.
	 * 
	 * @param key
<<<<<<< HEAD
	 *             Configuration field name.
	 * @param value
	 *             Configuration field value.
=======
	 *            - Configuration field name.
	 * @param value
	 *            - Configuration field value.
>>>>>>> f13a704c8df53b5547f5d107bae5bfb4d03c96e5
	 */
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
		if (framerate != null) {
			builder.append("framerate=");
			builder.append(framerate);
			builder.append(", ");
		}
		if (params != null) {
			builder.append("params=");
			builder.append(params);
		}
		builder.append("]");
		return builder.toString();
	}

	// TODO: change method visibility to protected or private
	public static PayloadRtp intersect(PayloadRtp answerer, PayloadRtp offerer) {
		if (answerer == null || offerer == null)
			return null;

		if (!answerer.codecName.equalsIgnoreCase(offerer.codecName)
				|| answerer.clockRate != offerer.clockRate) {
			return null;
		}

		PayloadRtp rtp = new PayloadRtp(offerer.id, offerer.codecName,
				offerer.clockRate);

		rtp.framerate = Fraction.intersect(answerer.framerate,
				offerer.framerate);
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
