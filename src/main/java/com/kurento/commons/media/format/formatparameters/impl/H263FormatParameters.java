package com.kurento.commons.media.format.formatparameters.impl;

import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.sdp.SdpException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kurento.commons.media.format.formatparameters.FormatParameters;

/**
 * rfc 4629 section 8
 * 
 */
public class H263FormatParameters extends VideoFormatParametersBase {

	private static Log log = LogFactory.getLog(H263FormatParameters.class);

	private H263CPCF cpcf;
	private ArrayList<ResolutionMPI> resolutionsList = new ArrayList<ResolutionMPI>();

	private ArrayList<H263FormatParametersProfile> profilesList = new ArrayList<H263FormatParametersProfile>();

	public H263CPCF getCpcf() {
		return cpcf;
	}

	public ArrayList<ResolutionMPI> getResolutionsList() {
		return resolutionsList;
	}

	public ArrayList<H263FormatParametersProfile> getProfilesList() {
		return profilesList;
	}

	public H263FormatParameters(String formatParamsStr) throws SdpException {
		super(formatParamsStr);
		init(formatParamsStr);
	}

	public H263FormatParameters(H263CPCF cpcf,
			ArrayList<ResolutionMPI> resolutionsList) throws SdpException {
		super();
		init(cpcf, resolutionsList);
	}

	public H263FormatParameters(
			ArrayList<H263FormatParametersProfile> profilesList)
			throws SdpException {
		super();
		init(profilesList);
	}

	@Override
	public FormatParameters intersect(FormatParameters other) {
		// TODO Auto-generated method stub
		return null;
	}

	private void init(H263CPCF cpcf, ArrayList<ResolutionMPI> resolutionsList) {
		this.cpcf = cpcf;
		this.resolutionsList = resolutionsList;

		StringBuffer str = new StringBuffer();
		if (cpcf != null)
			str.append(cpcf.toString()).append(";");
		for (ResolutionMPI rmpi : resolutionsList)
			str.append(rmpi.toString()).append(";");
		str.deleteCharAt(str.length() - 1);
		this.formatParamsStr = str.toString();
		createProfiles(cpcf, resolutionsList);
	}

	private void createProfiles(H263CPCF cpcf,
			ArrayList<ResolutionMPI> resolutionsList) {
		int frameRateBase = 30;
		if (cpcf != null)
			frameRateBase = 1800000 / (cpcf.getCd() * cpcf.getCf());

		for (ResolutionMPI rmpi : resolutionsList) {
			if (PictureSize.CUSTOM.equals(rmpi.getPictureSize())) {
				profilesList.add(new H263FormatParametersProfile(rmpi
						.getWidth(), rmpi.getHeight(), frameRateBase
						/ rmpi.getMpi())); // TODO: improve
			} else {
				if (frameRateBase == 30)
					profilesList.add(new H263FormatParametersProfile(rmpi
							.getPictureSize(), rmpi.getMpi()));
				else
					profilesList.add(new H263FormatParametersProfile(rmpi
							.getWidth(), rmpi.getHeight(), frameRateBase
							/ rmpi.getMpi()));
			}
		}
	}

	/**
	 * Creates a H263FormatParameters from a string. Format of the string must
	 * be: [CPCF=36,1000,0,1,1,0,0,2;CUSTOM=640,480,2;CIF=1;QCIF=1]
	 * 
	 * @param formatParamsStr
	 */
	private void init(String formatParamsStr) throws SdpException {
		this.formatParamsStr = formatParamsStr;

		ArrayList<H263FormatParametersProfile> profilesList = new ArrayList<H263FormatParametersProfile>();
		StringTokenizer tokenizer = new StringTokenizer(formatParamsStr, ";");

		int frameRateBase = 30;
		while (tokenizer.hasMoreTokens()) {
			StringTokenizer tokenizer2 = new StringTokenizer(
					tokenizer.nextToken(), "=");

			String first = tokenizer2.nextToken();
			if (first.equalsIgnoreCase("CPCF")) {
				StringTokenizer tokenizer3 = new StringTokenizer(
						tokenizer2.nextToken(), ",");
				int cd = Integer.parseInt(tokenizer3.nextToken());
				if (cd < 1 || cd > 127)
					throw new SdpException(
							"The cd parameter in CPCF must be an integer from 1 to 127");
				int cf = Integer.parseInt(tokenizer3.nextToken());
				if (cf != 1000 && cf != 1001)
					throw new SdpException(
							"The cf parameter in CPCF must be 1000 or 1001");
				int[] mpis = new int[6];
				for (int i = 0; i < 6; i++) {
					int mpi = Integer.parseInt(tokenizer3.nextToken());
					if (cd < 1 || cd > 2048)
						throw new SdpException(
								"The MPI parameters in CPCF must have a value in the range from from 1 to 2048");
					mpis[i] = mpi;
				}
				this.cpcf = new H263CPCF(cd, cf, mpis);
				frameRateBase = 1800000 / (cd * cf);
			} else {
				PictureSize pictSize = PictureSize
						.getPictureSizeFromString(first);
				if (pictSize == null)
					throw new SdpException("Incorrect fmtp string");

				ResolutionMPI resolutionMPI;
				if (PictureSize.CUSTOM.equals(pictSize)) {
					StringTokenizer tokenizer3 = new StringTokenizer(
							tokenizer2.nextToken(), ",");
					int width = Integer.parseInt(tokenizer3.nextToken());
					int height = Integer.parseInt(tokenizer3.nextToken());
					int mpi = Integer.parseInt(tokenizer3.nextToken());
					profilesList.add(new H263FormatParametersProfile(width,
							height, frameRateBase / mpi)); // TODO: improve
					resolutionMPI = new ResolutionMPI(pictSize, mpi);
					resolutionMPI.setWidth(width);
					resolutionMPI.setHeight(height);
				} else {
					int mpi = Integer.parseInt(tokenizer2.nextToken());
					resolutionMPI = new ResolutionMPI(pictSize, mpi);
					if (frameRateBase == 30)
						profilesList.add(new H263FormatParametersProfile(
								pictSize, mpi));
					else
						profilesList.add(new H263FormatParametersProfile(
								pictSize.getWidth(), pictSize.getHeight(),
								frameRateBase / mpi));
				}
				this.resolutionsList.add(resolutionMPI);
			}
		}

		this.profilesList = profilesList;
	}

	/**
	 * Creates a H263FormatParameters from a list of profiles
	 * 
	 * @param profilesList
	 */
	private void init(ArrayList<H263FormatParametersProfile> profilesList) {
		this.profilesList = profilesList;

		if (profilesList == null)
			this.formatParamsStr = "";
		else {
			StringBuffer str = new StringBuffer();

			int[] numbers = new int[profilesList.size()];
			for (int i = 0; i < profilesList.size(); i++)
				numbers[i] = profilesList.get(i).getMaxFrameRate();
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

					if (30 % p.getMaxFrameRate() == 0)
						str.append(30 / p.getMaxFrameRate()); // TODO if not
																// belong
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

					if (lcm % p.getMaxFrameRate() == 0) {
						int mpi = lcm / p.getMaxFrameRate();
						// TODO if mpi not belong [1, 2048] throw excepcion
						str.append(mpi);
						sizesSupported[pictSize.ordinal()] = mpi;
					}
				}

				int cf = 1000; // 1000 or 1001
				int cd = 1800000 / (cf * lcm);
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
