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
	private static int SQUARE = 1;

	private static int RECTANGULAR = 0;
	private static int BINARY_ONLY = 2;

	private MPEG4ConfigEnc configEnc;

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
		int fixed_vop_time_increment = 1;

		int video_object_layer_width = 0;
		int video_object_layer_height = 0;

		// INCOMPLETE
		if (!VIDEO_OBJECT_LAYER_START_CODE.equalsIgnoreCase(config.takeSubstring(8)))
			throw new SdpException("VIDEO_OBJECT_LAYER_START_CODE incorrect."); // TODO:
																				// complete

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
		if (fixed_vop_rate == 1) {
			int time_increment_bits = minNumBitsToRepresent(vop_time_increment_resolution);
			fixed_vop_time_increment = config.takeBits(time_increment_bits);
		}

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
		this.frameRate = new Fraction(vop_time_increment_resolution, fixed_vop_time_increment);
	}

	/**
	 * ENCODE
	 * 
	 * @param videoProfile
	 * @param configEnc
	 */
	protected VideoObjectLayer(GenericVideoProfile videoProfile, MPEG4ConfigEnc configEnc) {
		configEnc.putSubstring(VIDEO_OBJECT_LAYER_START_CODE);

		configEnc.putBits(1, 0); // random_accessible_vol
		configEnc.putBits(8, 1); // video_object_type_indication

		configEnc.putBits(1, 1); // is_object_layer_identifier
		configEnc.putBits(4, 1); // video_object_layer_verid
		configEnc.putBits(3, 1); // video_object_layer_priority

		configEnc.putBits(4, SQUARE); // aspect_ratio_info

		configEnc.putBits(1, 1); // vol_control_parameters
		configEnc.putBits(2, 1); // chroma_format
		configEnc.putBits(1, 1); // low_delay
		configEnc.putBits(1, 0); // vbv_parameters

		int video_object_layer_shape = RECTANGULAR;
		configEnc.putBits(2, video_object_layer_shape); // video_object_layer_shape

		configEnc.putBits(1, 1); // marker_bit
		int vop_time_increment_resolution = videoProfile.getMaxFrameRate().getNumerator();
		configEnc.putBits(16, vop_time_increment_resolution); // vop_time_increment_resolution
		configEnc.putBits(1, 1); // marker_bit

		configEnc.putBits(1, 1); // fixed_vop_rate
		int time_increment_bits = minNumBitsToRepresent(vop_time_increment_resolution);
		int fixed_vop_time_increment = videoProfile.getMaxFrameRate().getDenominator();
		configEnc.putBits(time_increment_bits, fixed_vop_time_increment); // fixed_vop_time_increment

		if (video_object_layer_shape != BINARY_ONLY) {
			if (video_object_layer_shape == RECTANGULAR) {
				configEnc.putBits(1, 1); // marker_bit
				int video_object_layer_width = videoProfile.getWidth();
				configEnc.putBits(13, video_object_layer_width); // video_object_layer_width
				configEnc.putBits(1, 1); // marker_bit
				int video_object_layer_height = videoProfile.getHeight();
				configEnc.putBits(13, video_object_layer_height);
				configEnc.putBits(1, 1); // marker_bit
			}
			configEnc.putBits(1, 0); // interlaced
			configEnc.putBits(1, 1); // obmc_disable
			// if (video_object_layer_verid == ‘0001’) OK
			configEnc.putBits(1, 0); // sprite_enable)
			// ...
			configEnc.putBits(1, 0); // not_8_bit
			configEnc.putBits(1, 0); // quant_type
			// ...
			configEnc.putBits(1, 1); // complexity_estimation_disable
			// ...
			configEnc.putBits(1, 1); // resync_marker_disable
			configEnc.putBits(1, 0); // data_partitioned
			// ...
			configEnc.putBits(1, 0); // scalability
		}

		configEnc.nextStartCode();

		configEnc.putUserData("kurento");
		// ...

		this.configEnc = configEnc;
	}

	private int minNumBitsToRepresent(int n) {
		n = Math.abs(n);
		int numBits = 1;
		while ((n /= 2) > 1)
			numBits++;
		return numBits;
	}

	public MPEG4ConfigEnc getConfigEnc() {
		return configEnc;
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
