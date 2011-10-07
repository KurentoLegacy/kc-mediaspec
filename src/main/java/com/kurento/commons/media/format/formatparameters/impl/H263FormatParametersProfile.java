package com.kurento.commons.media.format.formatparameters.impl;

public class H263FormatParametersProfile implements
		Comparable<H263FormatParametersProfile> {

	private int width;
	private int height;
	private int maxFrameRate;
	private PictureSize pictureSize;

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public int getMaxFrameRate() {
		return maxFrameRate;
	}

	public PictureSize getPictureSize() {
		return pictureSize;
	}

	public H263FormatParametersProfile(int width, int height, int frameRate) {
		this.width = width;
		this.height = height;
		this.maxFrameRate = frameRate;
		this.pictureSize = PictureSize.getPictureSizeFromSize(width, height);
	}

	public H263FormatParametersProfile(PictureSize pictureSize, int mpi) {
		this.width = pictureSize.getWidth();
		this.height = pictureSize.getHeight();
		this.maxFrameRate = 30 / mpi;
		this.pictureSize = pictureSize;
	}

	public H263FormatParametersProfile intersect(
			H263FormatParametersProfile other) {
		if (this.pictureSize.equals(other.getPictureSize())
				&& this.width == other.getWidth()
				&& this.height == other.getHeight())
			return new H263FormatParametersProfile(this.width, this.height,
					Math.min(this.maxFrameRate, other.getMaxFrameRate()));
		return null;
	}

	@Override
	public int compareTo(H263FormatParametersProfile o) {
		if (this.pictureSize.equals(o.getPictureSize())
				&& this.width == o.getWidth() && this.height == o.getHeight()
				&& this.maxFrameRate == o.getMaxFrameRate())
			return 0;
		return -1;
	}
}
