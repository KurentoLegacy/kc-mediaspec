package com.kurento.commons.media.format.formatparameters.impl;

public class RTMPInfo {

	private String url;
	private String offerer;
	private String answerer;

	public RTMPInfo() {
		this.url = "";
		this.offerer = "";
		this.answerer = "";
	}

	public RTMPInfo(String url, String offerer, String answerer) {
		this.url = url;
		this.offerer = offerer;
		this.answerer = answerer;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getOfferer() {
		return offerer;
	}

	public void setOfferer(String offerer) {
		this.offerer = offerer;
	}

	public String getAnswerer() {
		return answerer;
	}

	public void setAnswerer(String answerer) {
		this.answerer = answerer;
	}

}
