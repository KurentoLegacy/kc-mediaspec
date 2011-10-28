package com.kurento.commons.media.format.formatparameters.impl;

import javax.sdp.SdpException;

import com.kurento.commons.types.Fraction;

/**
 * ISO/IEC 14496-2 Second edition 2001-12-01
 * 
 * Information technology — Coding of audio-visual objects — Part 2: Visual
 * 
 * 6.2.3 Video Object Layer
 * 
 */
public class VideoObjectLayer {

	public static String VIDEO_OBJECT_LAYER_START_CODE = VisualObjectSequence.START_CODE_PREFIX
			+ "20"; // TO 2F

	private static int EXTENDED_PAR = 15;

	private static int RECTANGULAR = 0;
	private static int BINARY_ONLY = 2;

	private int width;
	private int height;
	private Fraction frameRate;

	/**
	 * DECODE
	 * 
	 * @param config
	 * @throws SdpException
	 */
	protected VideoObjectLayer(MPEG4ConfigDec config) throws SdpException {
		int is_object_layer_identifier;
		int aspect_ratio_info;
		int vol_control_parameters;
		int vbv_parameters;
		int video_object_layer_shape;
		int vop_time_increment_resolution = 0;
		int fixed_vop_rate;

		int video_object_layer_width = 0;
		int video_object_layer_height = 0;

		// INCOMPLETE
		if (!VIDEO_OBJECT_LAYER_START_CODE.equalsIgnoreCase(config.takeSubstring(8)))
			throw new SdpException("VIDEO_OBJECT_LAYER_START_CODE incorrect."); // TODO: complete

		config.skipBits(1); // random_accessible_vol
		config.skipBits(8); // video_object_type_indication

		is_object_layer_identifier = config.takeBits(1);
		if (is_object_layer_identifier == 1) {
			config.skipBits(4); // video_object_layer_verid
			config.skipBits(3); // video_object_layer_priority
		}

		aspect_ratio_info = config.takeBits(4);
		if (aspect_ratio_info == EXTENDED_PAR) {
			config.skipBits(8); // par_width
			config.skipBits(8); // par_height
		}

		vol_control_parameters = config.takeBits(1);
		if (vol_control_parameters == 1) {
			config.skipBits(2); // chroma_format
			config.skipBits(1); // low_delay
			vbv_parameters = config.takeBits(1);
			if (vbv_parameters == 1) {
				config.skipBits(15); // first_half_bit_rate
				config.skipBits(1); // marker_bit
				config.skipBits(15); // latter_half_bit_rate
				config.skipBits(1); // marker_bit
				config.skipBits(15); // first_half_vbv_buffer_size
				config.skipBits(1); // marker_bit
				config.skipBits(3); // latter_half_vbv_buffer_size
				config.skipBits(11); // first_half_vbv_occupancy
				config.skipBits(1); // marker_bit
				config.skipBits(15); // latter_half_vbv_occupancy
				config.skipBits(1); // marker_bit
			}
		}

		video_object_layer_shape = config.takeBits(2);

		// ...

		config.skipBits(1); // marker_bit
		vop_time_increment_resolution = config.takeBits(16);
		config.skipBits(1); // marker_bit

		fixed_vop_rate = config.takeBits(1);
		if (fixed_vop_rate == 1)
			config.skipBits(1); // fixed_vop_time_increment ¿1-16 bits?

		if (video_object_layer_shape != BINARY_ONLY) {
			if (video_object_layer_shape == RECTANGULAR) {
				config.skipBits(1); // marker_bit
				video_object_layer_width = config.takeBits(13);
				config.skipBits(1); // marker_bit
				video_object_layer_height = config.takeBits(13);
				config.skipBits(1); // marker_bit
			}

			// ...
		}
		// ...

		this.width = video_object_layer_width;
		this.height = video_object_layer_height;
		// TODO: complete den
		this.frameRate = new Fraction(vop_time_increment_resolution, 1000);
	}

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public Fraction getFrameRate() {
		return frameRate;
	}

}
