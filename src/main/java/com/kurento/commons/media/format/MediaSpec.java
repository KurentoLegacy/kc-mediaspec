/*
 * Kurento Commons MediaSpec: Library to manage SDPs.
 * Copyright (C) 2011  Tikal Technologies
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
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import com.kurento.commons.media.format.enums.MediaType;

/**
 * <p>
 * A MediaSpec Represents a transport media description that associated multiple
 * media formats and a transport.
 * </p>
 * 
 * @see {@link PayloadSpec}
 * @see {@link SessionSpec}
 */
public class MediaSpec implements Serializable {

	private static final long serialVersionUID = 1L;

	private Vector<Payload> payloads = new Vector<Payload>();

	private Set<MediaType> types = new HashSet<MediaType>();
	private Transport transport;

	/**
	 * Create a empty instance of MediaSpec.
	 */
	public MediaSpec() {
	}

	public MediaSpec(Collection<MediaType> types) {
		this.types.addAll(types);
	}

	public MediaSpec(Collection<Payload> payloads, Collection<MediaType> types,
			Transport transport) {
		this.types.addAll(types);
		this.payloads.addAll(payloads);
		this.transport = transport;
	}

	public synchronized void setTransport(Transport transport) {
		this.transport = transport;
	}

	public synchronized Transport getTransport() {
		return transport;
	}

	public void addPayload(Payload payload) {
		if (payload != null)
			payloads.add(payload);
	}

	public void addPayloads(Collection<Payload> payloads) {
		if (payloads != null)
			this.payloads.addAll(payloads);
	}

	public void deletePayload(Payload payload) {
		payloads.remove(payload);
	}

	public void deletePayloads(Collection<Payload> payloads) {
		if (payloads != null)
			this.payloads.removeAll(payloads);
	}

	public void deleteAllPayloads() {
		payloads.removeAllElements();
	}

	public List<Payload> getPayloads() {
		return Collections.unmodifiableList(payloads);
	}

	public synchronized void addType(MediaType type) {
		if (type != null)
			types.add(type);
	}

	public synchronized void addTypes(Collection<MediaType> types) {
		if (types != null)
			this.types.addAll(types);
	}

	public synchronized void setTypes(Collection<MediaType> types) {
		if (types == null)
			throw new NullPointerException();
		this.types = new HashSet<MediaType>();
		this.types.addAll(types);
	}

	public void deleteType(MediaType type) {
		types.remove(type);
	}

	public void deleteTypes(Collection<MediaType> types) {
		if (types != null)
			this.types.removeAll(types);
	}

	public synchronized Set<MediaType> getTypes() {
		return Collections.unmodifiableSet(types);
	}
}
