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

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(H263FormatParameters.class);

	private H263CPCF cpcf;
	private ArrayList<ResolutionMPI> resolutionsList = new ArrayList<ResolutionMPI>();

	private ArrayList<H263FormatParametersProfile> profilesList = new ArrayList<H263FormatParametersProfile>();
	
	private static final int FRAME_RATE_BASE = 30;

	/**
	 * Creates a H263FormatParameters from a string. Format of the string must
	 * be as: [CPCF=36,1000,0,1,1,0,0,2;CUSTOM=640,480,2;CIF=1;QCIF=1]
	 * 
	 * @param formatParamsStr
	 */
	public H263FormatParameters(String formatParamsStr) throws SdpException {
		super(formatParamsStr);
		this.formatParamsStr = formatParamsStr;

		ArrayList<H263FormatParametersProfile> profilesList = new ArrayList<H263FormatParametersProfile>();
		StringTokenizer tokenizer = new StringTokenizer(formatParamsStr, ";");

		int frameRateBase = FRAME_RATE_BASE;
		while (tokenizer.hasMoreTokens()) {
			StringTokenizer tokenizer2 = new StringTokenizer(tokenizer.nextToken(), "=");

			String first = tokenizer2.nextToken();
			if (first.equalsIgnoreCase("CPCF")) {
				StringTokenizer tokenizer3 = new StringTokenizer(tokenizer2.nextToken(), ",");
				int cd = Integer.parseInt(tokenizer3.nextToken());
				if (cd < 1 || cd > 127)
					throw new SdpException(
							"The cd parameter in CPCF must be an integer from 1 to 127");
				int cf = Integer.parseInt(tokenizer3.nextToken());
				if (cf != 1000 && cf != 1001)
					throw new SdpException("The cf parameter in CPCF must be 1000 or 1001");
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
				PictureSize pictSize = PictureSize.getPictureSizeFromString(first);
				if (pictSize == null)
					throw new SdpException("Incorrect fmtp string");

				ResolutionMPI resolutionMPI;
				if (PictureSize.CUSTOM.equals(pictSize)) {
					StringTokenizer tokenizer3 = new StringTokenizer(tokenizer2.nextToken(), ",");
					int width = Integer.parseInt(tokenizer3.nextToken());
					int height = Integer.parseInt(tokenizer3.nextToken());
					int mpi = Integer.parseInt(tokenizer3.nextToken());
					profilesList.add(new H263FormatParametersProfile(width, height, frameRateBase
							/ mpi));
					resolutionMPI = new ResolutionMPI(pictSize, mpi);
					resolutionMPI.setWidth(width);
					resolutionMPI.setHeight(height);
				} else {
					int mpi = Integer.parseInt(tokenizer2.nextToken());
					resolutionMPI = new ResolutionMPI(pictSize, mpi);
					if (frameRateBase == FRAME_RATE_BASE)
						profilesList.add(new H263FormatParametersProfile(pictSize, mpi));
					else
						profilesList.add(new H263FormatParametersProfile(pictSize.getWidth(),
								pictSize.getHeight(), frameRateBase / mpi));
				}
				this.resolutionsList.add(resolutionMPI);
			}
		}

		this.profilesList = profilesList;
	}

	public H263FormatParameters(H263CPCF cpcf,
			ArrayList<ResolutionMPI> resolutionsList) throws SdpException {
		super();
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

	public H263FormatParameters(
			ArrayList<H263FormatParametersProfile> profilesList)
			throws SdpException {
		super();
		init(profilesList);
	}

	@Override
	public FormatParameters intersect(FormatParameters other)
			throws SdpException {
		ArrayList<H263FormatParametersProfile> intersectProfilesList = new ArrayList<H263FormatParametersProfile>();
		for (H263FormatParametersProfile myProfile : profilesList) {
			for (H263FormatParametersProfile otherProfile : ((H263FormatParameters) other)
					.getProfilesList()) {
				H263FormatParametersProfile intersectProfile = myProfile
						.intersect(otherProfile);
				if (intersectProfile != null)
					intersectProfilesList.add(intersectProfile);
			}
		}

		return new H263FormatParameters(intersectProfilesList);
	}

	public H263CPCF getCpcf() {
		return cpcf;
	}

	public ArrayList<ResolutionMPI> getResolutionsList() {
		return resolutionsList;
	}

	public ArrayList<H263FormatParametersProfile> getProfilesList() {
		return profilesList;
	}

	@Override
	public boolean equals(FormatParameters o) {
		if (!(o instanceof H263FormatParameters))
			return false;

		boolean existProfile = false;
		for (H263FormatParametersProfile myProfile : profilesList) {
			for (H263FormatParametersProfile otherProfile : ((H263FormatParameters) o)
					.getProfilesList()) {
				existProfile = myProfile.compareTo(otherProfile) == 0;
				if (existProfile)
					break;
			}
			if (!existProfile)
				return false;
			existProfile = false;
		}
		return true;
	}

	private void createProfiles(H263CPCF cpcf,
			ArrayList<ResolutionMPI> resolutionsList) {
		int frameRateBase = FRAME_RATE_BASE;
		if (cpcf != null)
			frameRateBase = 1800000 / (cpcf.getCd() * cpcf.getCf());

		for (ResolutionMPI rmpi : resolutionsList) {
			if (PictureSize.CUSTOM.equals(rmpi.getPictureSize())) {
				profilesList.add(new H263FormatParametersProfile(rmpi
						.getWidth(), rmpi.getHeight(), frameRateBase
						/ rmpi.getMpi()));
			} else {
				if (frameRateBase == FRAME_RATE_BASE)
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
	 * Creates a H263FormatParameters from a list of profiles
	 * 
	 * @param profilesList
	 */
	private void init(ArrayList<H263FormatParametersProfile> profilesList)
			throws SdpException {
		this.profilesList = profilesList;

		if (profilesList == null)
			this.formatParamsStr = "";
		else {
			StringBuffer str = new StringBuffer();

			int[] numbers = new int[profilesList.size()];
			for (int i = 0; i < profilesList.size(); i++)
				numbers[i] = profilesList.get(i).getMaxFrameRate();
			int lcm = LCM(numbers);

			if (FRAME_RATE_BASE % lcm == 0) {
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
					if (FRAME_RATE_BASE % p.getMaxFrameRate() == 0) {
						int mpi = FRAME_RATE_BASE / p.getMaxFrameRate();
						if (mpi < 1 || mpi > 32)
							throw new SdpException(
									"The resul mpi parameters must be an integer from 1 to 32");
						str.append(mpi);
					}
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
						if (mpi < 1 || mpi > 2048)
							throw new SdpException(
									"The resul mpi parameters must be an integer from 1 to 2048");
						str.append(mpi);
						sizesSupported[pictSize.ordinal()] = mpi;
					}
				}

				int cf = 1000; // 1000 or 1001
				int cd = 1800000 / (cf * lcm);
				if (cd < 1 || cd > 127)
					throw new SdpException(
							"The resul cd parameter in CPCF must be an integer from 1 to 127");

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
