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
import java.util.List;
import java.util.Vector;

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

	/**
	 * Constructs an empty instance of SessionSpec.
	 */
	public SessionSpec() {
	}

	/**
	 * Creates a SessionSpec with the given medias
	 * 
	 */
	public SessionSpec(List<MediaSpec> medias) {
		addMediaSpecs(medias);
	}

	public void addMediaSpec(MediaSpec spec) {
		if (spec != null)
			mediaSpecs.add(spec);
	}

	public void addMediaSpecs(Collection<MediaSpec> medias) {
		if (medias != null)
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
}
