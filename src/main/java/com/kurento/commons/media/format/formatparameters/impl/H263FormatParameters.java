package com.kurento.commons.media.format.formatparameters.impl;

import java.util.ArrayList;
import java.util.StringTokenizer;

import javax.sdp.SdpException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kurento.commons.media.format.formatparameters.FormatParameters;
import com.kurento.commons.media.format.formatparameters.VideoProfile;
import com.kurento.commons.types.Fraction;

/**
 * rfc 4629 section 8
 * 
 */
public class H263FormatParameters extends VideoFormatParametersBase {

	@SuppressWarnings("unused")
	private static Log log = LogFactory.getLog(H263FormatParameters.class);

	private H263CPCF cpcf;
	private ArrayList<ResolutionMPI> resolutionsList = new ArrayList<ResolutionMPI>();

	private ArrayList<H263VideoProfile> profilesList = new ArrayList<H263VideoProfile>();
	
	private static final int DEFAULT_CD = 60;
	private static final int DEFAULT_CF = 1001;
	private static final Fraction FRAME_RATE_BASE = new Fraction(1800000, DEFAULT_CD * DEFAULT_CF);

	/**
	 * Creates a H263FormatParameters from a string. Format of the string must
	 * be as: [CPCF=36,1000,0,1,1,0,0,2;CUSTOM=640,480,2;CIF=1;QCIF=1]
	 * 
	 * @param formatParamsStr
	 */
	public H263FormatParameters(String formatParamsStr) throws SdpException {
		super(formatParamsStr);
		this.formatParamsStr = formatParamsStr;

		ArrayList<H263VideoProfile> profilesList = new ArrayList<H263VideoProfile>();
		StringTokenizer tokenizer = new StringTokenizer(formatParamsStr, ";");

		Fraction frameRateBase = FRAME_RATE_BASE;
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
				frameRateBase = new Fraction(1800000, (cd * cf));
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
					profilesList.add(new H263VideoProfile(width, height, frameRateBase
							.divide(mpi)));
					resolutionMPI = new ResolutionMPI(pictSize, mpi);
					resolutionMPI.setWidth(width);
					resolutionMPI.setHeight(height);
				} else {
					int mpi = Integer.parseInt(tokenizer2.nextToken());
					resolutionMPI = new ResolutionMPI(pictSize, mpi);
					if (frameRateBase == FRAME_RATE_BASE)
						profilesList.add(new H263VideoProfile(pictSize, mpi));
					else
						profilesList.add(new H263VideoProfile(pictSize.getWidth(),
								pictSize.getHeight(), frameRateBase.divide(mpi)));
				}
				this.resolutionsList.add(resolutionMPI);
			}
		}

		this.profilesList = profilesList;
	}

	public H263FormatParameters(H263CPCF cpcf,
			ArrayList<ResolutionMPI> resolutionsList) throws SdpException {
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

	/**
	 * Creates a H263FormatParameters from a list of profiles
	 * 
	 * @param profilesList
	 */
	public H263FormatParameters(ArrayList<H263VideoProfile> profilesList)
			throws SdpException {
		this.profilesList = profilesList;

		if (profilesList == null)
			this.formatParamsStr = "";
		else {
			StringBuffer str = new StringBuffer();

			boolean first = true;
			for (H263VideoProfile p : profilesList) {
				if (first)
					first = false;
				else
					str.append(";");

				PictureSize pictSize = PictureSize.getPictureSizeFromSize(p.getWidth(),
						p.getHeight());
				str.append(pictSize.toString());
				str.append("=");

				if (PictureSize.CUSTOM.equals(pictSize))
					str.append(p.getWidth()).append(",").append(p.getHeight()).append(",");
				if (!FRAME_RATE_BASE.isMultiple(p.getMaxFrameRate()))
					throw new SdpException(
							"Only framerates that can adjust to base framerate can be used.\n" +
							"Framerate must be submultiple of " + FRAME_RATE_BASE);
				int mpi = FRAME_RATE_BASE.divide(p.getMaxFrameRate()).getDouble().intValue();
				if (mpi < 1 || mpi > 32)
					throw new SdpException(
							"The resul mpi parameters must be an integer from 1 to 32");
				str.append(mpi);
			}
			this.formatParamsStr = str.toString();
		}
	}

	@Override
	public FormatParameters intersect(FormatParameters other)
			throws SdpException {
		if (other == null)
			return new H263FormatParameters("");

		ArrayList<H263VideoProfile> intersectProfilesList = new ArrayList<H263VideoProfile>();
		for (H263VideoProfile myProfile : profilesList) {
			for (H263VideoProfile otherProfile : ((H263FormatParameters) other)
					.getProfilesList()) {
				H263VideoProfile vp = (H263VideoProfile)myProfile.intersect(otherProfile);
				if (vp != null)
					intersectProfilesList.add(vp);
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

	public ArrayList<H263VideoProfile> getProfilesList() {
		return profilesList;
	}

	@Override
	public boolean equals(FormatParameters o) {
		if (!(o instanceof H263FormatParameters))
			return false;

		boolean existProfile = false;
		for (H263VideoProfile myProfile : profilesList) {
			for (H263VideoProfile otherProfile : ((H263FormatParameters) o)
					.getProfilesList()) {
				existProfile = myProfile.equals(otherProfile);
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
		Fraction frameRateBase = FRAME_RATE_BASE;
		if (cpcf != null)
			frameRateBase = new Fraction(1800000, (cpcf.getCd() * cpcf.getCf()));

		for (ResolutionMPI rmpi : resolutionsList) {
			if (PictureSize.CUSTOM.equals(rmpi.getPictureSize())) {
				profilesList.add(new H263VideoProfile(rmpi.getWidth(), rmpi.getHeight(),
						frameRateBase.divide(rmpi.getMpi())));
			} else {
				if (frameRateBase.equals(FRAME_RATE_BASE))
					profilesList.add(new H263VideoProfile(rmpi
							.getPictureSize(), rmpi.getMpi()));
				else
					profilesList.add(new H263VideoProfile(rmpi.getWidth(), rmpi
							.getHeight(), frameRateBase.divide(rmpi.getMpi())));
			}
		}
	}

}
