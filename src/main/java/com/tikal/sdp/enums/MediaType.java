package com.tikal.sdp.enums;

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
