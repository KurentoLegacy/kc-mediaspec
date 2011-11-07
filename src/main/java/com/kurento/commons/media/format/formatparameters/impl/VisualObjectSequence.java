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
	private String configString;
	private VisualObject visualObject;
	// private ArrayList<VisualObject> listVisualObject = new
	// ArrayList<VisualObject>();
	private GenericVideoProfile videoProfile;

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
		this.configString = configStr;

		MPEG4ConfigDec config = new MPEG4ConfigDec(configStr);
		if (!VISUAL_OBJECT_SEQUENCE_START_CODE.equalsIgnoreCase(config.takeSubstring(8)))
			throw new SdpException("VISUAL_OBJECT_SEQUENCE_START_CODE incorrect."); // TODO:
																					// complete
		this.profile = config.takeBits(4);
		this.profileLevel = config.takeBits(4);

		visualObject = new VisualObject(config);

		int width = visualObject.getVideoObjectLayer().getWidth();
		int height = visualObject.getVideoObjectLayer().getHeight();
		Fraction frameRate = visualObject.getVideoObjectLayer().getFrameRate();
		videoProfile = new GenericVideoProfile(width, height, frameRate);
	}

	protected VisualObjectSequence(GenericVideoProfile videoProfile) {
		this.profile = 0;
		this.profileLevel = 1;
		this.videoProfile = videoProfile;

		String configStr = "";

		MPEG4ConfigEnc configEnc = new MPEG4ConfigEnc();

		configEnc.putSubstring(VISUAL_OBJECT_SEQUENCE_START_CODE);
		configEnc.putBits(4, this.profile); // profile
		configEnc.putBits(4, this.profileLevel); // profile_level

		this.visualObject = new VisualObject(videoProfile, configEnc);

		configString = visualObject.getVideoObjectLayer().getConfigEnc().getConfigStr();
	}

	protected GenericVideoProfile getVideoProfile() {
		return videoProfile;
	}

	protected int getProfile() {
		return profile;
	}

	protected int getProfileLevel() {
		return profileLevel;
	}

	protected String getConfigString() {
		return configString;
	}

}
