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
import java.util.List;
import java.util.Vector;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.kurento.commons.media.format.enums.Mode;
import com.kurento.commons.media.format.exceptions.ArgumentNotSetException;

/**
 * <p>
 * This class provides a generic data model for Session Description
 * specification. SessionSpec is designed to extend <a
 * href="http://www.ietf.org/rfc/rfc2327.txt">SDP</a> with new transports and
 * media types.
 * 
 * SessionSpec is able to calculate intersection between offer and capabilities,
 * enabling channel negotiation between peers in a multimedia communication.
 * 
 * </p>
 * 
 * @see{@link MediaSpec}
 * 
 */
public class SessionSpec implements Serializable {

	private static final long serialVersionUID = 1L;

	protected static final Logger log = LoggerFactory.getLogger(SessionSpec.class);
	
	private Vector<MediaSpec> mediaSpecs = new Vector<MediaSpec>();
	private String id = "";
	private String version;

	/**
	 * Creates an empty SessionSpec instance.
	 */
	public SessionSpec() {
	}

	/**
	 * Creates a SessionSpect instance initialized with the given list of media
	 * channels
	 * 
	 * @param medias
	 *            List of media channels
	 * @param id
	 *            Identification code used by application layer. It is of no use
	 *            to SessionSpec
	 */
	public SessionSpec(List<MediaSpec> medias, String id) {
		addMediaSpecs(medias);
		setId(id);
	}

	/**
	 * Adds a media channel descriptor to the session descriptor. No action
	 * takes place if already belongs to this session descriptor. That means a
	 * session descriptor will store each channel descriptor just once
	 * 
	 * 
	 * @param spec
	 *            Media channel descriptor added to this session descriptor
	 */
	public void addMediaSpec(MediaSpec spec) {
		if (spec != null && !mediaSpecs.contains(spec))
			mediaSpecs.add(spec);
	}

	/**
	 * Adds a list of media channel descriptor to this session descriptor.
	 * Channel descriptor already stored within the session descriptor are
	 * ignored
	 * 
	 * @param medias
	 */
	public void addMediaSpecs(Collection<MediaSpec> medias) {
		if (medias == null)
			throw new NullPointerException("Medias can not be null");
		for (MediaSpec m : medias) {
			if (!mediaSpecs.contains(m))
				mediaSpecs.add(m);
		}
	}

	/**
	 * Removes the given media channel descriptor from this session descriptor.
	 * Unknown channel descriptors are silently ignored and no modification
	 * takes place within the session descriptor
	 * 
	 * @param spec
	 *            Media channel descriptor to be removed from this session
	 *            descriptor
	 */
	public void deleteMediaSpec(MediaSpec spec) {
		mediaSpecs.remove(spec);
	}

	/**
	 * Removes from this session descriptor a list of media channel descriptors.
	 * Those channel instances unknown to this session descriptor are silently
	 * ignored
	 * 
	 * @param specs
	 *            List of media channel descriptors to be removed from this
	 *            session descriptor
	 */
	public void deleteMediaSpecs(Collection<MediaSpec> specs) {
		mediaSpecs.removeAll(specs);
	}

	/**
	 * Flushes all channel descriptors from this session descriptor
	 */
	public void deleteAllMediaSpecs() {
		mediaSpecs.removeAllElements();
	}

	/**
	 * Returns a list of media descriptors handled by this session descriptor
	 * 
	 * @return List of channel descriptors handled by this session descriptor
	 */
	public List<MediaSpec> getMediaSpecs() {
		return Collections.unmodifiableList(mediaSpecs);
	}

	/**
	 * Sets this session descriptor identification
	 * 
	 * @param id
	 */
	public synchronized void setId(String id) {
		if (id == null)
			throw new NullPointerException("Id can not be null");

		this.id = id;
	}

	/**
	 * Returns this session descriptor identification
	 * 
	 * @return
	 */
	public synchronized String getId() {
		return id;
	}

	/**
	 * Session descriptor enables a timeline mechanism intended for applications
	 * to keep track of session descriptor changes
	 * 
	 * @param version
	 *            Version code to be assigned to this session descriptor
	 */
	public synchronized void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Returns current version code of this session descriptor
	 * 
	 * @return Current version code
	 * @throws ArgumentNotSetException
	 *             Thrown when version has not been set at least once
	 */
	public synchronized String getVersion() throws ArgumentNotSetException {
		if (version == null)
			throw new ArgumentNotSetException();

		return version;
	}

	/**
	 * Provides a string with a pretty print of this session descriptor
	 */
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("SessionSpec [");
		if (mediaSpecs != null) {
			builder.append("mediaSpecs=");
			builder.append(mediaSpecs);
			builder.append(", ");
		}
		if (id != null) {
			builder.append("id=");
			builder.append(id);
			builder.append(", ");
		}
		if (version != null) {
			builder.append("version=");
			builder.append(version);
		}
		builder.append("]");
		return builder.toString();
	}

	/**
	 * Calculate intersection between to session descriptors: answered and offerer
	 * 
	 * ???????
	 * 
	 * @param answerer
	 * @param offerer
	 * @return
	 */
	public static SessionSpec[] intersect(SessionSpec answerer,
			SessionSpec offerer) {
		List<MediaSpec> offererList = offerer.getMediaSpecs();
		List<MediaSpec> answererList = answerer.getMediaSpecs();
		List<MediaSpec> newAnswererSpecList = new Vector<MediaSpec>();
		List<MediaSpec> newOffererSpecList = new Vector<MediaSpec>();
		List<MediaSpec> usedMedias = new Vector<MediaSpec>();

		MediaSpec answererMedia = null;
		MediaSpec offererMedia = null;
		for (MediaSpec ofMedia : offererList) {
			MediaSpec[] medias = null;

			for (MediaSpec ansMedia : answererList) {
				if (!(ofMedia.getTypes().containsAll(ansMedia.getTypes()))
						|| usedMedias.contains(ansMedia))
					continue;

				medias = MediaSpec.intersect(ansMedia, ofMedia);

				if (medias != null) {
					usedMedias.add(ansMedia);
					break;
				}
			}

			if (medias == null) {
				answererMedia = new MediaSpec(new ArrayList<Payload>(),
						ofMedia.getTypes(), new Transport(), Mode.INACTIVE);
				offererMedia = new MediaSpec(new ArrayList<Payload>(),
						ofMedia.getTypes(), new Transport(), Mode.INACTIVE);
			} else {
				answererMedia = medias[0];
				offererMedia = medias[1];
			}

			if (answererMedia != null)
				newAnswererSpecList.add(answererMedia);
			if (offererMedia != null)
				newOffererSpecList.add(offererMedia);
		}

		SessionSpec newAnswererSpec = new SessionSpec(newAnswererSpecList,
				offerer.getId());
		try {
			newAnswererSpec.setVersion(answerer.getVersion());
		} catch (ArgumentNotSetException e) {
			log.error("Unable to set ansewer version during session descriptor intersection",e);
		}

		SessionSpec newOffererSpec = new SessionSpec(newOffererSpecList,
				offerer.getId());
		try {
			newOffererSpec.setVersion(offerer.getVersion());
		} catch (ArgumentNotSetException e) {
			log.error("Unable to set offerer version during session descriptor intersection",e);

		}

		return new SessionSpec[] { newAnswererSpec, newOffererSpec };
	}

}
