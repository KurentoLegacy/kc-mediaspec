package com.kurento.commons.media.format.formatparameters.impl;

import gov.nist.core.Match;

import java.util.ArrayList;

import com.kurento.commons.media.format.formatparameters.FormatParameters;

/**
 * rfc 4629 section 8
 * 
 */
public class H263FormatParameters extends VideoFormatParametersBase {



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
