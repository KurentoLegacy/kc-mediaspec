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
			String key = null;
			String value = null;
			if (tokenizer2.hasMoreTokens()) {
				key = tokenizer2.nextToken();
			}
			if (tokenizer2.hasMoreTokens()) {
				value = tokenizer2.nextToken();
			}
			if (key != null)
				map.put(key, value);
		}

		if (map.get("url") == null)
			throw new SdpException("fmtp has not url param.");

		this.rtmpInfo = new RTMPInfo();
		this.rtmpInfo.setUrl(map.get("url"));
		this.rtmpInfo.setOfferer(map.get("offerer"));
		this.rtmpInfo.setAnswerer(map.get("answerer"));

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

		if (rtmpInfo.getOfferer() != null) {
			strBuf.append(";offerer=");
			strBuf.append(rtmpInfo.getOfferer());
		}
		if (rtmpInfo.getAnswerer() != null) {
			strBuf.append(";answerer=");
			strBuf.append(rtmpInfo.getAnswerer());
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
		if (other == null)
			return this;
		if (!(other instanceof RTMPFormatParameters))
			return null;
		RTMPFormatParameters rtmpFormatParameters = new RTMPFormatParameters(this.formatParamsStr);
		RTMPInfo rtmpInfo = rtmpFormatParameters.getRtmpInfo();
		GenericVideoProfile genericVideoProfile = rtmpFormatParameters.getVideoProfile();
		rtmpInfo.setOfferer(((RTMPFormatParameters) other).getRtmpInfo().getOfferer());
		System.out.println(rtmpFormatParameters);
		return new RTMPFormatParameters(rtmpInfo, genericVideoProfile);
	}

	@Override
	public boolean equals(FormatParameters other) {
		// TODO Auto-generated method stub
		return false;
	}

}
