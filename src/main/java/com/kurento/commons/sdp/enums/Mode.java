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
