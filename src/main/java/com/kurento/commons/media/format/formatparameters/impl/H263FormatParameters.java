package com.kurento.commons.media.format.formatparameters.impl;

import gov.nist.core.Match;

import java.util.ArrayList;

import com.kurento.commons.media.format.formatparameters.FormatParameters;

/**
 * rfc 4629 section 8
 * 
 */
public class H263FormatParameters extends VideoFormatParametersBase {

	private enum PictureSize {
		SQCIF(128, 96, "SQCIF"), QCIF(176, 144, "QCIF"), CIF(352, 288, "CIF"), CIF4(
				704, 576, "4CIF"), CIF16(1408, 1152, "16CIF"), CUSTOM(-1, -1,
				"CUSTOM");

		private int width;
		private int height;
		private String str;

		private PictureSize(int width, int height, String str) {
			this.width = width;
			this.height = height;
			this.str = str;
		}

		public String toString() {
			return str;
		}

		public static PictureSize getPictureSizeFromSize(int width, int height) {
			if (SQCIF.width == width && SQCIF.height == height)
				return SQCIF;
			else if (QCIF.width == width && QCIF.height == height)
				return QCIF;
			else if (CIF.width == width && CIF.height == height)
				return CIF;
			else if (CIF4.width == width && CIF4.height == height)
				return CIF4;
			else if (CIF16.width == width && CIF16.height == height)
				return CIF16;

			return PictureSize.CUSTOM;
		}
	}

	private ArrayList<H263FormatParametersProfile> profilesList = new ArrayList<H263FormatParametersProfile>();

	public H263FormatParameters(String formatParamsStr) {
		super(formatParamsStr);
	}

	public H263FormatParameters(
			ArrayList<H263FormatParametersProfile> profilesList) {
		super();
		init(profilesList);
	}

	@Override
	public FormatParameters intersect(FormatParameters other) {
		// TODO Auto-generated method stub
		return null;
	}

	private void init(ArrayList<H263FormatParametersProfile> profilesList) {
		this.profilesList = profilesList;

		if (profilesList == null)
			this.formatParamsStr = "";
		else {
			StringBuffer str = new StringBuffer();

			int[] numbers = new int[profilesList.size()];
			for (int i = 0; i < profilesList.size(); i++)
				numbers[i] = profilesList.get(i).getFrameRate();
			int lcm = LCM(numbers);

			if (30 % lcm == 0) {
				boolean first = true;
				for (H263FormatParametersProfile p : profilesList) {
					if (first)
						first = false;
					else
						str.append(";");

					PictureSize pictSize = PictureSize.getPictureSizeFromSize(
							p.getWidth(), p.getHeight());
					str.append(pictSize.toString());
					str.append("=");

					if (PictureSize.CUSTOM.equals(pictSize))
						str.append(p.getWidth()).append(",")
								.append(p.getHeight()).append(",");

					if (30 % p.getFrameRate() == 0)
						str.append(30 / p.getFrameRate()); // TODO if not belong
															// [1, 32] throw
															// excepcion
				}
				this.formatParamsStr = str.toString();
			} else {
				int[] sizesSupported = new int[6];
				for (int i = 0; i < 6; i++)
					sizesSupported[i] = 0;

				boolean first = true;
				for (H263FormatParametersProfile p : profilesList) {
					if (first)
						first = false;
					else
						str.append(";");

					PictureSize pictSize = PictureSize.getPictureSizeFromSize(
							p.getWidth(), p.getHeight());
					str.append(pictSize.toString());
					str.append("=");

					if (PictureSize.CUSTOM.equals(pictSize))
						str.append(p.getWidth()).append(",")
								.append(p.getHeight()).append(",");

					if (lcm % p.getFrameRate() == 0) {
						int mpi = lcm / p.getFrameRate();
						// TODO if mpi not belong [1, 2048] throw excepcion
						str.append(mpi);
						sizesSupported[pictSize.ordinal()] = mpi;
					}
				}

				int cf = 1000; // 1000 or 1001
				int cd = 1800000 / cf / lcm;
				// TODO if cd not belong [1, 127] throw excepcion

				StringBuffer initStr = new StringBuffer();
				initStr.append("CPCF=").append(cd).append(",").append(cf);
				for (int i = 0; i < 6; i++)
					initStr.append(",").append(sizesSupported[i]);
				initStr.append(";");
				this.formatParamsStr = initStr.toString() + str.toString();
			}

		}

	}

	/**
	 * LCM of numbers
	 */
	private int LCM(int[] numbers) {
		int lcm = 1;
		for (int i = 0; i < numbers.length; i++) {
			lcm = LCM(lcm, numbers[i]);
		}
		return lcm;
	}

	/**
	 * Return lowest common multiple.
	 */
	private int LCM(int a, int b) {
		return a * b / EuclidGCD(a, b);
	}

	/**
	 * Return greatest common divisor using Euclid's Algorithm.
	 */
	private int EuclidGCD(int a, int b) {
		int iaux;
		a = Math.abs(a);
		b = Math.abs(b);
		int i1 = Math.max(a, b);
		int i2 = Math.min(a, b);
		do {
			iaux = i2;
			i2 = i1 % i2;
			i1 = iaux;
		} while (i2 != 0);
		return i1;
	}

}
