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

package com.kurento.commons.sdp.enums;

public enum MediaType {
	AUDIO(0, "audio"),
	VIDEO(1, "video");
	
	private int id;
	private String name;

	private MediaType(int id, String name) {
		this.id = id;
		this.name = name;
	}

	public static MediaType  getInstance (String mediaType) {
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
	
	public int getId() {
		return id;
	}

}
