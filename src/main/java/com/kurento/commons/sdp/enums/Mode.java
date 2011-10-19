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

public enum Mode {
	SENDRECV("sendrecv"),
	SENDONLY("sendonly"),
	RECVONLY("recvonly"),
	INACTIVE("inactive");

	private String desc;

	private Mode(String desc) {
		this.desc = desc;
	}

	public String toString() {
		return desc;
	}

	public static Mode getInstance(String value) {
		if (value.equalsIgnoreCase(SENDRECV.toString())) {
			return SENDRECV;
		} else if (value.equalsIgnoreCase(SENDONLY.toString())) {
			return SENDONLY;
		} else if (value.equalsIgnoreCase(RECVONLY.toString())) {
			return RECVONLY;
		} else if (value.equalsIgnoreCase(INACTIVE.toString())) {
			return INACTIVE;
		}
		return null;
	}
}
