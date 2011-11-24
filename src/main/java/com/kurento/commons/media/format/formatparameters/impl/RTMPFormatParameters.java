package com.kurento.commons.media.format.formatparameters.impl;

import java.util.HashMap;
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
	 * url=rtmp://myserver.com/application;publish=publishStream
	 * ;play=playStream;w=320;h=240;fps=15/1
	 * 
	 * @param formatParamsStr
	 * @throws SdpException
	 */
	public RTMPFormatParameters(String formatParamsStr) throws SdpException {
		super(formatParamsStr);

		HashMap<String, String> map = new HashMap<String, String>();
		StringTokenizer tokenizer = new StringTokenizer(formatParamsStr, ";");
		while (tokenizer.hasMoreTokens()) {
			StringTokenizer tokenizer2 = new StringTokenizer(tokenizer.nextToken(), "=");
			map.put(tokenizer2.nextToken(), tokenizer2.nextToken());
		}

		if (map.get("url") == null)
			throw new SdpException("fmtp has not url param.");

		this.rtmpInfo = new RTMPInfo();
		this.rtmpInfo.setUrl(map.get("url"));
		this.rtmpInfo.setPublish(map.get("publish"));
		this.rtmpInfo.setPlay(map.get("play"));

		this.videoProfile = new GenericVideoProfile();
		int w = Integer.parseInt(map.get("w"));
		this.videoProfile.setWidth(w);
		int h = Integer.parseInt(map.get("h"));
		this.videoProfile.setHeight(h);

		tokenizer = new StringTokenizer(map.get("fps"), "/");
		int num = Integer.parseInt(tokenizer.nextToken());
		int den = Integer.parseInt(tokenizer.nextToken());
		this.videoProfile.setFrameRate(new Fraction(num, den));
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

		StringBuffer strBuf = new StringBuffer("url=");
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
			if (videoProfile.getWidth() > 0 && videoProfile.getHeight() > 0) {
				strBuf.append(";w=");
				strBuf.append(videoProfile.getWidth());
				strBuf.append(";h=");
				strBuf.append(videoProfile.getHeight());
				strBuf.append(";");
			}
			if (videoProfile.getMaxFrameRate().getDouble() > 0) {
				strBuf.append("fps=");
				strBuf.append(videoProfile.getMaxFrameRate());
				strBuf.append(";");
			}
		}
		strBuf.deleteCharAt(strBuf.length() - 1);

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
