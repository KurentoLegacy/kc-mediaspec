package com.kurento.commons.media.format.formatparameters.impl;

import com.kurento.commons.media.format.formatparameters.VideoProfile;
import com.kurento.commons.types.Fraction;

public class H263FormatParametersProfile implements VideoProfile {

	private int width;
	private int height;
	private Fraction maxFrameRate;
	private PictureSize pictureSize;

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Fraction getMaxFrameRate() {
		return maxFrameRate;
	}

	public PictureSize getPictureSize() {
		return pictureSize;
	}

	public H263FormatParametersProfile(int width, int height, Fraction frameRate) {
		this.width = width;
		this.height = height;
		this.maxFrameRate = frameRate;
		this.pictureSize = PictureSize.getPictureSizeFromSize(width, height);
	}

	public H263FormatParametersProfile(PictureSize pictureSize, int mpi) {
		this.width = pictureSize.getWidth();
		this.height = pictureSize.getHeight();
		this.maxFrameRate = new Fraction(30 * 1000, mpi * 1001);
		this.pictureSize = pictureSize;
	}

	@Override
	public boolean equals(Object o) {
		H263FormatParametersProfile profile = null;
		if (o instanceof H263FormatParametersProfile) {
			profile = (H263FormatParametersProfile) o;
		}
		if (profile != null && this.pictureSize.equals(profile.getPictureSize())
				&& this.width == profile.getWidth() && this.height == profile.getHeight()
				&& this.maxFrameRate.equals(profile.getMaxFrameRate()))
			return true;
		return false;
	}
}
