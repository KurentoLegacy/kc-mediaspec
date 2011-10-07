package com.kurento.commons.media.format.formatparameters.impl;

import javax.sdp.SdpException;

import com.kurento.commons.media.format.formatparameters.FormatParameters;

public abstract class FormatParametersBase implements FormatParameters {

	protected String formatParamsStr;

	public FormatParametersBase() {

	}

	public FormatParametersBase(String formatParamsStr) {
		this.formatParamsStr = formatParamsStr;
	}

	@Override
	public FormatParameters intersect(FormatParameters other)
			throws SdpException {
		// TODO Auto-generated method stub
		return null;
	}

	public String toString() {
		return this.formatParamsStr;
	}

	@Override
	public int compareTo(FormatParameters o) {
		return -1;
	}

}
