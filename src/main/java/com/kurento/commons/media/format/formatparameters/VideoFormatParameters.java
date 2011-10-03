package com.kurento.commons.media.format.formatparameters;


/**
 * Subinterface to manage a=fmtp attribute for video payloads
 */
public interface VideoFormatParameters extends FormatParameters {

	public int getWidth();

	public int getHeight();

	public int getFrameRate();
	
}
