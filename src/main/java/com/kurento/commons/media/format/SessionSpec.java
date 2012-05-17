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

import com.kurento.commons.media.format.enums.Mode;
import com.kurento.commons.media.format.exceptions.ArgumentNotSetException;

/**
 * <p>
 * A SessionSpec represents generic media descriptions that is suitable for
 * negotiation media formats with multiple different transports and media
 * description formats
 * </p>
 * 
 * @see{@link MediaSpec}
 * 
 */
public class SessionSpec implements Serializable {

	private static final long serialVersionUID = 1L;

	private Vector<MediaSpec> mediaSpecs = new Vector<MediaSpec>();
	private String id = "";
	private String version;

	/**
	 * Constructs an empty instance of SessionSpec.
	 */
	public SessionSpec() {
	}

	/**
	 * Creates a SessionSpec with the given medias
	 * 
	 */
	public SessionSpec(List<MediaSpec> medias, String id) {
		addMediaSpecs(medias);
		setId(id);
	}

	public void addMediaSpec(MediaSpec spec) {
		if (spec != null)
			mediaSpecs.add(spec);
	}

	public void addMediaSpecs(Collection<MediaSpec> medias) {
		if (medias == null)
			throw new NullPointerException("Medias can not be null");

		mediaSpecs.addAll(medias);
	}

	public void deleteMediaSpec(MediaSpec spec) {
		mediaSpecs.remove(spec);
	}

	public void deleteMediaSpecs(Collection<MediaSpec> specs) {
		mediaSpecs.removeAll(specs);
	}

	public void deleteAllMediaSpecs() {
		mediaSpecs.removeAllElements();
	}

	public List<MediaSpec> getMediaSpecs() {
		return Collections.unmodifiableList(mediaSpecs);
	}

	public synchronized void setId(String id) {
		if (id == null)
			throw new NullPointerException("Id can not be null");

		this.id = id;
	}

	public synchronized String getId() {
		return id;
	}

	public synchronized void setVersion(String version) {
		this.version = version;
	}

	public synchronized String getVersion() throws ArgumentNotSetException {
		if (version == null)
			throw new ArgumentNotSetException();

		return version;
	}

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
						ofMedia.getTypes(), new Transport(),
						Mode.INACTIVE);
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
		}

		SessionSpec newOffererSpec = new SessionSpec(newOffererSpecList,
				offerer.getId());
		try {
			newOffererSpec.setVersion(offerer.getVersion());
		} catch (ArgumentNotSetException e) {
		}

		return new SessionSpec[] { newAnswererSpec, newOffererSpec };
	}

}
