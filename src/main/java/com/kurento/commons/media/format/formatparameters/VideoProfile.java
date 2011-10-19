package com.kurento.commons.media.format.formatparameters;

import com.kurento.commons.types.Fraction;

/**
 * Interface that defines a video profile.
 * 
 */
public interface VideoProfile {

	/**
	 * Returns the width of the video profile
	 * 
	 * @return The width of the video profile
	 */
	public int getWidth();

	/**
	 * Returns the height of the video profile
	 * 
	 * @return The height of the video profile
	 */
	public int getHeight();

	/**
	 * Returns a fraction that represent the maximum framerate
	 * 
	 * @return The maximum framerate of the profile
	 */
	public Fraction getMaxFrameRate();

	/**
	 * @return an intersection if it is possible and null if not.
	 */
	public VideoProfile intersect(VideoProfile other);
}
