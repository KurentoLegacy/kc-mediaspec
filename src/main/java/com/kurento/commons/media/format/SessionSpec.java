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

//TODO: Consolidate all packages to: com.kurento.commons.media.format

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
 * SessionSpec is designed to extend <a
 * href="http://www.ietf.org/rfc/rfc2327.txt">SDP</a> with new transports and
 * media types, providing a generic mechanism for Session Description
 * specification. Figure below shows data model components and their
 * interaction.
 * <p>
 * 
 * <img src="doc-files/classdiagram.png"/>
 * 
 * <p>
 * 
 * SessionSpec is intended to be directly used in multimedia negotiation. For
 * that purpose an intersection feature is implemented to calculate common
 * capabilities on both sides of a communication.
 * 
 * <p>
 * 
 * SessionSpec data model design has avoided inheritance in order to facilitate
 * object serialization and transfer in a multi-platform environment. Many
 * protocols can be used for delivery, including: THRIFT, JSON or even SDP (only
 * with RTP channels). Kurento provides a set of <a href="??">Conversion
 * utilities</a> for this purpose
 * 
 * @see MediaSpec
 * 
 */
public class SessionSpec implements Serializable {

	private static final long serialVersionUID = 1L;

	protected static final Logger log = LoggerFactory
			.getLogger(SessionSpec.class);

	private Vector<MediaSpec> mediaSpecs = new Vector<MediaSpec>();
	private String id = "";
	private String version;

	/**
	 * Creates an empty SessionSpec instance.
	 */
	// TODO: Change visibility to private?
	public SessionSpec() {
	}

	/**
	 * Create a SessionSpect instance initialized with the specified list of
	 * media channels.
	 * 
	 * @param medias
	 *            List of media channels.
	 * @param id
	 *            Identification code used by application layer. It is of no use
	 *            to SessionSpec.
	 */
	public SessionSpec(List<MediaSpec> medias, String id) {
		addMediaSpecs(medias);
		setId(id);
	}

	/**
	 * Add a media channel descriptor to the session descriptor. No action takes
	 * place if already belongs to this session descriptor. That means a session
	 * descriptor will store each channel descriptor just once.
	 * 
	 * 
	 * @param spec
	 *            Media channel descriptor added to this session descriptor.
	 */
	public void addMediaSpec(MediaSpec spec) {
		if (spec != null)
			mediaSpecs.add(spec);
	}

	/**
	 * Add a list of media channel descriptor to this session descriptor.
	 * Channel descriptor already stored within the session descriptor are
	 * ignored.
	 * 
	 * @param medias
	 *            List of media channel descriptors to be added to this session
	 *            descriptor.
	 */
	public void addMediaSpecs(Collection<MediaSpec> medias) {
		if (medias == null)
			throw new NullPointerException("Medias can not be null");
		mediaSpecs.addAll(medias);
	}

	/**
	 * Remove specified media channel descriptor from this session descriptor.
	 * Channel descriptor is silently ignored if not contained within this
	 * instance, and no modification takes place within the session descriptor.
	 * 
	 * @param spec
	 *            Media channel descriptor to be removed from this session
	 *            descriptor.
	 */
	public void deleteMediaSpec(MediaSpec spec) {
		mediaSpecs.remove(spec);
	}

	/**
	 * Remove from this session descriptor a list of media channel descriptors.
	 * Those channel instances not contained within this instances are silently
	 * ignored.
	 * 
	 * @param specs
	 *            List of media channel descriptors to be removed from this
	 *            session descriptor.
	 */
	public void deleteMediaSpecs(Collection<MediaSpec> specs) {
		mediaSpecs.removeAll(specs);
	}

	/**
	 * Flush all channel descriptors from this session descriptor.
	 */
	public void deleteAllMediaSpecs() {
		mediaSpecs.removeAllElements();
	}

	/**
	 * Return a list of media descriptors handled by this session descriptor.
	 * 
	 * @return List of channel descriptors handled by this session descriptor.
	 */
	public List<MediaSpec> getMediaSpecs() {
		return Collections.unmodifiableList(mediaSpecs);
	}

	/**
	 * Set this session descriptor identification.
	 * 
	 * @param id
	 *            Identification
	 */
	public synchronized void setId(String id) {
		if (id == null)
			throw new NullPointerException("Id can not be null");

		this.id = id;
	}

	/**
	 * Return this session descriptor identification
	 * 
	 * @return Session descriptor identification
	 */
	public synchronized String getId() {
		return id;
	}

	/**
	 * Session descriptor enables a versioning mechanism intended for
	 * applications to keep track of changes.
	 * 
	 * @param version
	 *            Version code to be assigned to this session descriptor.
	 */
	public synchronized void setVersion(String version) {
		this.version = version;
	}

	/**
	 * Return current version code of this session descriptor.
	 * 
	 * @return Current version code
	 * @throws ArgumentNotSetException
	 *             If argument hasn't been initialized.
	 */
	public synchronized String getVersion() throws ArgumentNotSetException {
		if (version == null)
			throw new ArgumentNotSetException();

		return version;
	}

	/**
	 * Provide a string with a pretty print of this session descriptor.
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
	 * Method <code>intersect</code> is the basis to the SessionSpec negotiation
	 * process. It takes two parameters: <code>answerer</code> and
	 * <code>offerer</code> and returns a <code>SessionSpec</code> array with
	 * the intersected answerer (index 0) and offerer (index 1). Answerer always
	 * represents the local descriptor while offerer is the remote one. Notice
	 * this method must be executed by caller and called party, so it is
	 * important not to associate offerer with caller and answerer with called,
	 * as they relate to remote and local respectively and the same descriptor
	 * swaps roles (remote/local) depending on the peer.
	 * 
	 * In addition, this method also enforces following set of negotiation rules
	 * inherited from SDP negotiation:
	 * <ul>
	 * <li>Answerer must contain a response for each offerer channel. Media
	 * channel is declared <code>INCATIVE</code> if peer does not support
	 * specified media type.
	 * <li>Answerer must not include channels or formats not present at offerer.
	 * </ul>
	 * 
	 * Multimedia negotiation procedures might vary, but it will contain at
	 * least following steps:
	 * 
	 * <ul>
	 * <li>Caller party generates an offer descriptor announcing its multimedia
	 * capabilities.
	 * <li>Upon reception, called party assigns received offer to.
	 * <code>offerer</code> and its own capabilities descriptor to
	 * <code>answerer</code>. Returned answerer is sent back to caller and
	 * becomes the local session descriptor of called party. Returned offered
	 * becomes the remote descriptor of called party.
	 * <li>Caller receives answer from called and performs its own intersection,
	 * assigning the offer descriptor (sent to called party) to
	 * <code>answered</code> and received answer from called to
	 * <code>offerer</code>. Returned answerer becomes the caller local
	 * descriptor while returned offerer is the caller remote descriptor
	 * </ul>
	 * After negotiation local and remote descriptors on each party must be
	 * Interchangeable. i.e. caller's local descriptor is identical to called's
	 * remote descriptor and vice versa.
	 * 
	 * @param answerer
	 *            Session descriptor from local party to be intersected. Local
	 *            can be caller or called, depending where the operation is
	 *            executed.
	 * @param offerer
	 * 
	 *            Session descriptor from remote party to be intersected. Remote
	 *            can be caller or called, depending where the operation is
	 *            executed.
	 * 
	 * @return SessionSpec array containing intersected answerer (index 0) and
	 *         offerer (index 1) descriptors, respectively
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
			log.error(
					"Unable to set ansewer version during session descriptor intersection",
					e);
		}

		SessionSpec newOffererSpec = new SessionSpec(newOffererSpecList,
				offerer.getId());
		try {
			newOffererSpec.setVersion(offerer.getVersion());
		} catch (ArgumentNotSetException e) {
			log.error(
					"Unable to set offerer version during session descriptor intersection",
					e);

		}

		return new SessionSpec[] { newAnswererSpec, newOffererSpec };
	}

}
