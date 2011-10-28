package com.kurento.commons.media.format.formatparameters.impl;

import java.util.StringTokenizer;

import javax.sdp.SdpException;

import com.kurento.commons.media.format.formatparameters.FormatParameters;

/**
 * 
 * rfc rfc3016 section 5.1
 * 
 */
public class MPEG4FormatParameters extends VideoFormatParametersBase {

	private GenericVideoProfile videoProfile;
	private int profileLevel;

	private VisualObjectSequence visualObjectSequence;

	/**
	 * Creates a MPEG4FormatParameters from a string. Format of the string must
	 * be as for example: [profile-level-id=1;
	 * config=000001B001000001B5090000010000000120008440FA282C2090A21F]
	 * 
	 * @param formatParamsStr
	 * @throws SdpException
	 */
	public MPEG4FormatParameters(String formatParamsStr) throws SdpException {
		super(formatParamsStr);

		StringTokenizer tokenizer = new StringTokenizer(formatParamsStr, ";");
		profileLevel = Integer.parseInt(tokenizer.nextToken().split("=")[1]);

		String configStr = tokenizer.nextToken().split("=")[1];

		visualObjectSequence = new VisualObjectSequence(configStr);
		videoProfile = visualObjectSequence.generateGenericVideoProfile();
	}

	public GenericVideoProfile getVideoProfile() {
		return videoProfile;
	}

	public int getProfileLevel() {
		return profileLevel;
	}

	@Override
	public FormatParameters intersect(FormatParameters other) throws SdpException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(FormatParameters other) {
		// TODO Auto-generated method stub
		return false;
	}

}
