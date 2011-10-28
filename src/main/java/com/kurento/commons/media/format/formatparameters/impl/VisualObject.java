package com.kurento.commons.media.format.formatparameters.impl;

import javax.sdp.SdpException;

/**
 * ISO/IEC 14496-2 Second edition 2001-12-01
 * 
 * Information technology — Coding of audio-visual objects — Part 2: Visual
 * 
 * 6.2.2 Visual Object Sequence and Visual Object
 * 
	VisualObject() {
		visual_object_start_code
		is_visual_object_identifier
		if (is_visual_object_identifier) {
			visual_object_verid
			visual_object_priority
		}
		visual_object_type
		if (visual_object_type == “video ID” || visual_object_type == “still texture ID“) {
			video_signal_type()
		}
		next_start_code()
		while ( next_bits()== user_data_start_code){
			user_data()
		}
		if (visual_object_type == “video ID”) {
			video_object_start_code
			VideoObjectLayer()
		}
		else if (visual_object_type == “still texture ID”) {
			StillTextureObject()
		}
		else if (visual_object_type == “mesh ID”) {
			MeshObject()
		}
		else if (visual_object_type == “FBA ID”) {
			FBAObject()
		}
		else if (visual_object_type == “3D mesh ID”) {
			3D_Mesh_Object()
		}
		if (next_bits() != “0000 0000 0000 0000 0000 0001”)
			next_start_code()
	}
 */
public class VisualObject {

	public static String VISUAL_OBJECT_START_CODE = VisualObjectSequence.START_CODE_PREFIX + "B5";
	public static String VIDEO_OBJECT_START_CODE = VisualObjectSequence.START_CODE_PREFIX + "00"; // to
																									// 1F

	private static int videoID = 1;

	private VideoObjectLayer videoObjectLayer;

	// private ArrayList<VideoObjectLayer> listVideoObjectLayer = new
	// ArrayList<VideoObjectLayer>();

	/**
	 * DECODE
	 * 
	 * @param config
	 * @throws SdpException
	 */
	protected VisualObject(MPEG4ConfigDec config) throws SdpException {
		int visual_object_type;

		// INCOMPLETE
		if (!VISUAL_OBJECT_START_CODE.equalsIgnoreCase(config.takeSubstring(8)))
			throw new SdpException("VISUAL_OBJECT_START_CODE incorrect."); // TODO: complete

		int is_visual_object_identifier = config.takeBits(1);
		if (is_visual_object_identifier == 1) {
			config.skipBits(4); // visual_object_verid
			config.skipBits(3); // visual_object_priority
		}
		visual_object_type = config.takeBits(4);

		if (visual_object_type == videoID)
			videoSignalType(config);

		config.skipBitsToNextStartCode();

		if (visual_object_type == videoID) {
			if (!VIDEO_OBJECT_START_CODE.equalsIgnoreCase(config.takeSubstring(8)))
				throw new SdpException("TODO. VIDEO_OBJECT_START_CODE incorrect."); // TODO
			videoObjectLayer = new VideoObjectLayer(config);
		}

	}

	/**
	 * 
		video_signal_type() {
			video_signal_type
			if (video_signal_type) {
				video_format
				video_range
				colour_description
				if (colour_description) {
					colour_primaries
					transfer_characteristics
					matrix_coefficients
				}
			}
		 }
	 * 
	 * @param config
	 */
	private void videoSignalType(MPEG4ConfigDec config) {
		config.skipBits(1); // video_signal_type
	}

	public VideoObjectLayer getVideoObjectLayer() {
		return videoObjectLayer;
	}

}
