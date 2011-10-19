package com.kurento.commons.media.format.formatparameters.impl;

import com.kurento.commons.media.format.formatparameters.VideoProfile;
import com.kurento.commons.types.Fraction;

public class GenericVideoProfile extends VideoProfileBase {

	public GenericVideoProfile(int width, int height, Fraction frameRate) {
		this.width = width;
		this.height = height;
		this.frameRate = frameRate;
	}

	@Override
	public VideoProfile intersect(VideoProfile other) {
		return null;
	}
}
