package com.tikal.sdp.enums;

public enum Mode {
	SENDRECV(0, "sendrecv"),
	SENDONLY(1, "sendonly"),
	RECVONLY(2, "recvonly"),
	INACTIVE(3, "inactive");

	private int id;
	private String desc;

	private Mode(int id, String desc) {
		this.id = id;
		this.desc = desc;
	}

	public int getId() {
		return id;
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
