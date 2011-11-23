package com.kurento.commons.media.format.formatparameters.impl;

import java.util.StringTokenizer;

import javax.sdp.SdpException;

import com.kurento.commons.media.format.formatparameters.FormatParameters;
import com.kurento.commons.types.Fraction;

public class RTMPFormatParameters extends VideoFormatParametersBase {

	private RTMPInfo rtmpInfo;
	private GenericVideoProfile videoProfile;

	/**
	 * Creates a RTMPFormatParameters from a string. Format of the string must
	 * be as for example:
	 * rtmp-info:url=rtmp://server.com/application[;publish=publishStream
	 * ][;play=playStream] [video-profile:[s=320,240][;fps=15/1]]
	 * 
	 * @param formatParamsStr
	 * @throws SdpException
	 */
	public RTMPFormatParameters(String formatParamsStr) throws SdpException {
		super(formatParamsStr);

		StringTokenizer tokenizer = new StringTokenizer(formatParamsStr, " ");

		// rtmp-info
		String first = tokenizer.nextToken();
		first = first.split("rtmp-info:")[1];
		StringTokenizer tokenizer2 = new StringTokenizer(first, ";");
		StringTokenizer tokenizer3 = new StringTokenizer(tokenizer2.nextToken(), "=");
		// url
		if (!"url".equals(tokenizer3.nextToken()))
			throw new SdpException("fmtp has not url param.");

		this.rtmpInfo = new RTMPInfo();
		this.rtmpInfo.setUrl(tokenizer3.nextToken());

		while (tokenizer2.hasMoreTokens()) {
			tokenizer3 = new StringTokenizer(tokenizer2.nextToken(), "=");
			first = tokenizer3.nextToken();
			if (first.equalsIgnoreCase("publish")) {
				this.rtmpInfo.setPublish(tokenizer3.nextToken());
			} else if (first.equalsIgnoreCase("play")) {
				this.rtmpInfo.setPlay(tokenizer3.nextToken());
			}

		}

		// video-profile
		if (tokenizer.hasMoreTokens()) {
			first = tokenizer.nextToken();
			first = first.split("video-profile:")[1];
			tokenizer2 = new StringTokenizer(first, ";");
			this.videoProfile = new GenericVideoProfile();
			while (tokenizer2.hasMoreTokens()) {
				tokenizer3 = new StringTokenizer(tokenizer2.nextToken(), "=");
				first = tokenizer3.nextToken();
				if (first.equalsIgnoreCase("s")) {
					StringTokenizer tokenizer4 = new StringTokenizer(tokenizer3.nextToken(), ",");
					int w = Integer.parseInt(tokenizer4.nextToken());
					int h = Integer.parseInt(tokenizer4.nextToken());
					this.videoProfile.setWidth(w);
					this.videoProfile.setHeight(h);
				} else if (first.equalsIgnoreCase("fps")) {
					StringTokenizer tokenizer4 = new StringTokenizer(tokenizer3.nextToken(), "/");
					int num = Integer.parseInt(tokenizer4.nextToken());
					int den = Integer.parseInt(tokenizer4.nextToken());
					this.videoProfile.setFrameRate(new Fraction(num, den));
				}
			}
		}

	}

	/**
	 * Creates a RTMPFormatParameters from a rtmpInfo and a videoProfile.
	 * 
	 * @param rtmpInfo
	 * @param videoProfile
	 * @throws SdpException
	 */
	public RTMPFormatParameters(RTMPInfo rtmpInfo, GenericVideoProfile videoProfile)
			throws SdpException {
		if (rtmpInfo == null)
			throw new SdpException("rtmpInfo parameter is null.");
		if (rtmpInfo.getUrl() == null || "".equals(rtmpInfo.getUrl()))
			throw new SdpException("URL info in rtmpInfo parameter is nul or \"\".");

		StringBuffer strBuf = new StringBuffer("rtmp-info:url=");
		strBuf.append(rtmpInfo.getUrl());

		if (rtmpInfo.getPublish() != null) {
			strBuf.append(";publish=");
			strBuf.append(rtmpInfo.getPublish());
		}
		if (rtmpInfo.getPlay() != null) {
			strBuf.append(";play=");
			strBuf.append(rtmpInfo.getPlay());
		}

		if (videoProfile != null) {
			strBuf.append(" video-profile:");
			if (videoProfile.getWidth() > 0 && videoProfile.getHeight() > 0) {
				strBuf.append("s=");
				strBuf.append(videoProfile.getWidth());
				strBuf.append(",");
				strBuf.append(videoProfile.getHeight());
				strBuf.append(";");
			}
			if (videoProfile.getMaxFrameRate().getDouble() > 0) {
				strBuf.append("fps=");
				strBuf.append(videoProfile.getMaxFrameRate());
				strBuf.append(";");
			}
		}
		strBuf.deleteCharAt(strBuf.length()-1);

		this.rtmpInfo = rtmpInfo;
		this.videoProfile = videoProfile;
		this.formatParamsStr = strBuf.toString();
	}

	public RTMPInfo getRtmpInfo() {
		return rtmpInfo;
	}

	public GenericVideoProfile getVideoProfile() {
		return videoProfile;
	}

	@Override
	public FormatParameters intersect(FormatParameters other) throws SdpException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean equals(FormatParameters other) {
		// TODO Auto-generated method stub
		return false;
	}

}
