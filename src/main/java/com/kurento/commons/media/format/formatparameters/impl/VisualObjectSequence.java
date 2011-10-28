package com.kurento.commons.media.format.formatparameters.impl;

import javax.sdp.SdpException;

import com.kurento.commons.types.Fraction;

/**
 * ISO/IEC 14496-2 Second edition 2001-12-01
 * 
 * Information technology — Coding of audio-visual objects — Part 2: Visual
 * 
 * 6.2.2 Visual Object Sequence and Visual Object
 * 
	VisualObjectSequence() {
 		do {
			visual_object_sequence_start_code
			profile_and_level_indication
			while ( next_bits()== user_data_start_code){
				user_data()
			}
			VisualObject()
		} while ( next_bits() != visual_object_sequence_end_code)
		visual_object_sequence_end_code
	}
 */
public class VisualObjectSequence {

	private int profile;
	private int profileLevel;
	private VisualObject visualObject;
	// private ArrayList<VisualObject> listVisualObject = new
	// ArrayList<VisualObject>();

	public static String START_CODE_PREFIX = "000001";
	public static String VISUAL_OBJECT_SEQUENCE_START_CODE = START_CODE_PREFIX + "B0";
	public static String VISUAL_OBJECT_SEQUENCE_END_CODE = START_CODE_PREFIX + "B1";

	/**
	 * DECODE
	 * 
	 * @param configStr
	 * @throws SdpException
	 */
	protected VisualObjectSequence(String configStr) throws SdpException {
		MPEG4ConfigDec config = new MPEG4ConfigDec(configStr);
		if (!VISUAL_OBJECT_SEQUENCE_START_CODE.equalsIgnoreCase(config.takeSubstring(8)))
			throw new SdpException("VISUAL_OBJECT_SEQUENCE_START_CODE incorrect."); // TODO: complete
		this.profile = config.takeBits(4);
		this.profileLevel = config.takeBits(4);

		visualObject = new VisualObject(config);
	}

	public GenericVideoProfile generateGenericVideoProfile() {
		int width = visualObject.getVideoObjectLayer().getWidth();
		int height = visualObject.getVideoObjectLayer().getHeight();
		Fraction frameRate = visualObject.getVideoObjectLayer().getFrameRate();

		return new GenericVideoProfile(width, height, frameRate);
	}

}
