package com.kurento.commons.media.format.formatparameters.impl;

import com.kurento.commons.media.format.formatparameters.FormatParameters;

public abstract class FormatParametersBase implements FormatParameters {

	protected String formatParamsStr;

	public FormatParametersBase() {

	}

	public FormatParametersBase(String formatParamsStr) {
		this.formatParamsStr = formatParamsStr;
	}

	public String toString() {
		return this.formatParamsStr;
	}

}
