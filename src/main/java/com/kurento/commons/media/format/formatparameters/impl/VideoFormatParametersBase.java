package com.kurento.commons.media.format.formatparameters.impl;

import com.kurento.commons.media.format.formatparameters.VideoFormatParameters;

public abstract class VideoFormatParametersBase extends FormatParametersBase implements
		VideoFormatParameters {

	public VideoFormatParametersBase() {
		super();
	}

	public VideoFormatParametersBase(String formatParamsStr) {
		super(formatParamsStr);
	}

}
