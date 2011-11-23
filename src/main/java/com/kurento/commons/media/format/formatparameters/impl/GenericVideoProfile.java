package com.kurento.commons.media.format.formatparameters.impl;

import com.kurento.commons.media.format.formatparameters.VideoProfile;
import com.kurento.commons.types.Fraction;

public class GenericVideoProfile extends VideoProfileBase {

	public GenericVideoProfile() {
		this.width = 0;
		this.height = 0;
		this.frameRate = new Fraction(1, 1);
	}

	public GenericVideoProfile(int width, int height, Fraction frameRate) {
		this.width = width;
		this.height = height;
		this.frameRate = frameRate;
	}

	@Override
	public VideoProfile intersect(VideoProfile other) {
		if (other == null)
			return this;

		if (this.width == other.getWidth() && this.height == other.getHeight()) {
			return this.frameRate.compareTo(other.getMaxFrameRate()) > 0 ? other : this;
		}

		return null;
	}
}
