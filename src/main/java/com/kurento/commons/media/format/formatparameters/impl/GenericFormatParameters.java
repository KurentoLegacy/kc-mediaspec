package com.kurento.commons.media.format.formatparameters.impl;

import javax.sdp.SdpException;

import com.kurento.commons.media.format.formatparameters.FormatParameters;

public class GenericFormatParameters extends FormatParametersBase {

	public GenericFormatParameters(String formatParamsStr) {
		super(formatParamsStr);
	}

	@Override
	public FormatParameters intersect(FormatParameters other)
			throws SdpException {
		return null;
	}

	@Override
	public boolean equals(FormatParameters o) {
		return false;
	}
}
