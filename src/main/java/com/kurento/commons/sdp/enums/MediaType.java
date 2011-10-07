package com.kurento.commons.sdp.enums;

public enum MediaType {
	AUDIO("audio"), VIDEO("video");
	
	private String name;

	private MediaType(String name) {
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

}
