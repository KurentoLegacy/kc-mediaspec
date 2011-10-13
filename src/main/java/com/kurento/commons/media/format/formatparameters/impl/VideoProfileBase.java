package com.kurento.commons.media.format.formatparameters.impl;

import com.kurento.commons.media.format.formatparameters.VideoProfile;
import com.kurento.commons.types.Fraction;

public abstract class VideoProfileBase implements VideoProfile {

	protected int width;
	protected int height;
	protected Fraction frameRate;

	@Override
	public int getWidth() {
		return width;
	}

	@Override
	public int getHeight() {
		return height;
	}

	@Override
	public Fraction getMaxFrameRate() {
		return frameRate;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((frameRate == null) ? 0 : frameRate.hashCode());
		result = prime * result + height;
		result = prime * result + width;
		return result;
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		VideoProfileBase other = (VideoProfileBase) obj;
		if (frameRate == null) {
			if (other.frameRate != null)
				return false;
		} else if (!frameRate.equals(other.frameRate))
			return false;
		if (height != other.height)
			return false;
		if (width != other.width)
			return false;
		return true;
	}

}
