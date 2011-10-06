package com.kurento.commons.media.format.formatparameters.impl;

public class H263FormatParametersProfile {

	private int width;
	private int height;
	private int maxFrameRate;

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getMaxFrameRate() {
		return maxFrameRate;
	}

	public H263FormatParametersProfile(int width, int height, int frameRate) {
		this.width = width;
		this.height = height;
		this.maxFrameRate = frameRate;
	}
}
