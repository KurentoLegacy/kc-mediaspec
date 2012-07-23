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

package com.kurento.mediaspec;

public enum MediaType {
	AUDIO("audio"), VIDEO("video");

	private String name;

	private MediaType(String name) {
		this.name = name;
	}

	public static MediaType getInstance(String mediaType) {
		if (AUDIO.toString().equalsIgnoreCase(mediaType)) {
			return AUDIO;
		} else if (VIDEO.toString().equalsIgnoreCase(mediaType)) {
			return VIDEO;
		}
		return null;
	}

	@Override
	public String toString() {
		return name;
	}

}