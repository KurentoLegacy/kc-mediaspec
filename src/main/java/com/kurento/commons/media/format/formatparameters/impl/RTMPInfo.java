package com.kurento.commons.media.format.formatparameters.impl;

public class RTMPInfo {

	private String url;
	private String publish;
	private String play;

	public RTMPInfo() {
		this.url = "";
		this.publish = "";
		this.play = "";
	}

	public RTMPInfo(String url, String publish, String play) {
		this.url = url;
		this.publish = publish;
		this.play = play;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPublish() {
		return publish;
	}

	public void setPublish(String publish) {
		this.publish = publish;
	}

	public String getPlay() {
		return play;
	}

	public void setPlay(String play) {
		this.play = play;
	}

}
