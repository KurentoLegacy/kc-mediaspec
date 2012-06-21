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
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.kurento.commons.media.format.enums.MediaType;
import com.kurento.commons.media.format.enums.Mode;
import com.kurento.commons.media.format.exceptions.ArgumentNotSetException;

/**
 * <p>
 * MediaSpec represents a channel media descriptor. it contains one payload
 * descriptor for each supported format and a the transport descriptor that will
 * support media delivery
 * </p>
 * 
 * @see SessionSpec
 * @see Payload
 * @see Transport
 */
public class MediaSpec implements Serializable {

	private static final long serialVersionUID = 1L;

	private Vector<Payload> payloads = new Vector<Payload>();

	private Set<MediaType> types = new HashSet<MediaType>();
	private Transport transport;

	private Mode mode;

	/**
	 * Creates a empty instance of MediaSpec.
	 */
	// TODO: Change visibility to private?
	public MediaSpec() {
	}

	/**
	 * Creates one MediaSpec instance initialized with provided parameters
	 * 
	 * @param payloads
	 *            List of format descriptors this media channel will support.
	 *            They all should have the same media type
	 * @param types
	 *            List of media types supported by this media channel. Currently
	 *            only AUDIO and VIDEO are valid media types. Media channel
	 *            descriptor can announce more than one different media type.
	 *            This is intended for multiplex payloads like RTMP that
	 *            provides at the same time audio and video
	 * @param transport
	 *            Descriptor for the transport layer supporting media delivery
	 * @param mode
	 *            This channel delivery mode. It can be: SendOnly, ReceiveOnly.
	 *            SendReceived or inactive. If multiple medias are supported
	 *            within this channel they all have the same mode
	 */
	public MediaSpec(Collection<Payload> payloads, Collection<MediaType> types,
			Transport transport, Mode mode) {
		setTypes(types);
		addPayloads(payloads);
		setTransport(transport);
		setMode(mode);
	}

	/**
	 * Set channel mode.
	 * 
	 * @param mode
	 *            Delivery mode to be set to this channel
	 */
	public synchronized void setMode(Mode mode) {
		if (mode == null)
			throw new NullPointerException("Mode can not be null");
		this.mode = mode;
	}

	/**
	 * Retuns this channel transmission mode
	 * 
	 * @return Channel transmission mode or null if not previously assigned
	 */
	public synchronized Mode getMode() {
		return mode;
	}

	/**
	 * Sets the transport descriptor. Previous transport descriptor will be
	 * override
	 * 
	 * @param transport
	 *            transport descriptor to be associated to this channel
	 *            descriptor
	 */
	public synchronized void setTransport(Transport transport) {
		if (transport == null)
			throw new NullPointerException("Transport can not be null");
		this.transport = transport;
	}

	/**
	 * Returns this channel's current transport descriptor or null if not
	 * previously any assigned
	 * 
	 * @return Transport descriptor
	 */
	public synchronized Transport getTransport() {
		return transport;
	}

	/**
	 * Incorporates a new payload descriptor. Duplicates and <code>null</code>
	 * values are not permitted
	 * 
	 * @param payload
	 */
	public void addPayload(Payload payload) {
		if (payload != null)
			payloads.add(payload);
	}

	/**
	 * Incorporates a list of payload descriptors.Duplicates and
	 * <code>null</code> values are silently ignored
	 * 
	 * @param payloads
	 */
	public void addPayloads(Collection<Payload> payloads) {
		if (payloads != null)
			this.payloads.addAll(payloads);
	}

	/**
	 * Deletes given payload descriptor from this channel. No action taken if
	 * not found
	 * 
	 * @param payload
	 *            Payload descriptor to be included in this media channel
	 */
	public void deletePayload(Payload payload) {
		payloads.remove(payload);
	}

	/**
	 * Deletes a list of payload descriptors from this channel. Payloads not
	 * found are silently ignored
	 * 
	 * @param payloads
	 *            Payload descriptor list to be included in this media channel
	 */
	public void deletePayloads(Collection<Payload> payloads) {
		if (payloads != null)
			this.payloads.removeAll(payloads);
	}

	/**
	 * Flushes payload list from this media descriptor
	 */
	public void deleteAllPayloads() {
		payloads.removeAllElements();
	}

	/**
	 * Returns a list with this channel's payload descritors
	 * 
	 * @return List of payload descritors
	 */
	public List<Payload> getPayloads() {
		return Collections.unmodifiableList(payloads);
	}

	/**
	 * Adds a media type to this channel descriptor. Duplicates and
	 * <code>null</code> values are silently ignored.
	 * 
	 * @param type
	 */
	public synchronized void addType(MediaType type) {
		if (type != null)
			types.add(type);
	}

	/**
	 * Adds a list of media types to this channel descriptor. Duplicates and
	 * <code>null</code> values are not permitted
	 * 
	 * @param types
	 */
	public synchronized void addTypes(Collection<MediaType> types) {
		if (types != null)
			this.types.addAll(types);
	}

	/**
	 * Replaces current media types list with a new one
	 * 
	 * @param types
	 *            list of tipes to replace current media type list
	 */
	public synchronized void setTypes(Collection<MediaType> types) {
		if (types == null)
			throw new NullPointerException("Types can not be null");
		this.types = new HashSet<MediaType>();
		this.types.addAll(types);
	}

	/**
	 * Deletes given media type. Not found and <code>null</code> values are
	 * ignored
	 * 
	 * @param type
	 *            Media type to be removed from this channel descriptor
	 */
	public void deleteType(MediaType type) {
		types.remove(type);
	}

	/**
	 * Deletes given media type list from this channel descriptor. Not found and
	 * <code>null</code> values are ignored
	 * 
	 * @param types
	 *            List of media types to be removed from this channel descriptor
	 */
	public void deleteTypes(Collection<MediaType> types) {
		if (types != null)
			this.types.removeAll(types);
	}

	/**
	 * Returns the list of media types included within this channel descriptor
	 * 
	 * @return List of media types included in this channel descriptor
	 */
	public synchronized Set<MediaType> getTypes() {
		return Collections.unmodifiableSet(types);
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("MediaSpec [");
		if (payloads != null) {
			builder.append("payloads=");
			builder.append(payloads);
			builder.append(", ");
		}
		if (types != null) {
			builder.append("types=");
			builder.append(types);
			builder.append(", ");
		}
		if (transport != null) {
			builder.append("transport=");
			builder.append(transport);
			builder.append(", ");
		}
		if (mode != null) {
			builder.append("mode=");
			builder.append(mode);
		}
		builder.append("]");
		return builder.toString();
	}

	// TODO: Change method visibility to protected
	public static boolean checkTransportCompatible(MediaSpec answerer,
			MediaSpec offerer) {
		boolean ret = false;

		try {
			ret = answerer.getTransport().getRtmp() != null
					&& offerer.getTransport().getRtmp() != null;
		} catch (ArgumentNotSetException e) {
		}

		try {
			ret = ret || answerer.getTransport().getRtp() != null
					&& offerer.getTransport().getRtp() != null;
		} catch (ArgumentNotSetException e) {
		}

		return ret;
	}

	/**
	 * Calculates common formats and transport between a local (answered) and
	 * remote (offerer) channel descriptor. This method is not intended to be
	 * used by application
	 * 
	 * 
	 * 
	 * @param answerer
	 * @param offerer
	 * @return SessionSpec array [answerer, offerer]
	 */
	// TODO: change method visibility to protected
	public static MediaSpec[] intersect(MediaSpec answerer, MediaSpec offerer) {
		ArrayList<Payload> answererPayloads = new ArrayList<Payload>();
		ArrayList<Payload> offererPayloads = new ArrayList<Payload>();
		Mode answererMode = answerer.getMode();
		Mode offererMode = offerer.getMode();

		if (!offerer.getTypes().containsAll(answerer.getTypes())) {
			return null;
		}

		if (answererMode == Mode.INACTIVE || offererMode == Mode.INACTIVE
				|| answererMode == Mode.RECVONLY
				&& offererMode == Mode.RECVONLY
				|| answererMode == Mode.SENDONLY
				&& offererMode == Mode.SENDONLY) {
			answererMode = Mode.INACTIVE;
			offererMode = Mode.INACTIVE;
		} else if (answererMode == Mode.SENDONLY
				|| offererMode == Mode.RECVONLY) {
			answererMode = Mode.SENDONLY;
			offererMode = Mode.RECVONLY;
		} else if (answererMode == Mode.RECVONLY
				|| offererMode == Mode.SENDONLY) {
			answererMode = Mode.RECVONLY;
			offererMode = Mode.SENDONLY;
		} else {
			answererMode = Mode.SENDRECV;
			offererMode = Mode.SENDRECV;
		}

		if (!checkTransportCompatible(answerer, offerer)) {
			return null;
		}

		for (Payload ansPayload : answerer.getPayloads()) {
			for (Payload offPayload : offerer.getPayloads()) {
				Payload intersect = Payload.intersect(ansPayload, offPayload);
				if (intersect != null) {
					answererPayloads.add(intersect);
					offererPayloads.add(new Payload(intersect));
					break;
				}
			}
		}

		if (answererPayloads.size() == 0) {
			answererMode = Mode.INACTIVE;
			offererMode = Mode.INACTIVE;
		}

		Transport[] transports = Transport.intersect(answerer.getTransport(),
				offerer.getTransport());

		if (transports == null)
			return null;

		MediaSpec newAnswerer = new MediaSpec(answererPayloads,
				answerer.getTypes(), transports[0], answererMode);
		MediaSpec newOfferer = new MediaSpec(offererPayloads,
				offerer.getTypes(), transports[1], offererMode);
		return new MediaSpec[] { newAnswerer, newOfferer };
	}

}
