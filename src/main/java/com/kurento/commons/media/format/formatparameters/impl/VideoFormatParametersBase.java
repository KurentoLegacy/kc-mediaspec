package com.kurento.commons.media.format.formatparameters.impl;

import com.kurento.commons.media.format.formatparameters.FormatParameters;
import com.kurento.commons.media.format.formatparameters.VideoFormatParameters;

public class VideoFormatParametersBase extends FormatParametersBase implements
		VideoFormatParameters {

	public VideoFormatParametersBase() {
		super();
	}

	public VideoFormatParametersBase(String formatParamsStr) {
		super(formatParamsStr);
	}

	@Override
	public FormatParameters intersect(FormatParameters other) {
		// TODO Auto-generated method stub
		return null;
	}

}
