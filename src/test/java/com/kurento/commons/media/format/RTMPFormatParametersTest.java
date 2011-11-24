package com.kurento.commons.media.format;

import junit.framework.TestCase;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.kurento.commons.media.format.formatparameters.impl.GenericVideoProfile;
import com.kurento.commons.media.format.formatparameters.impl.MPEG4FormatParameters;
import com.kurento.commons.media.format.formatparameters.impl.RTMPFormatParameters;
import com.kurento.commons.media.format.formatparameters.impl.RTMPInfo;
import com.kurento.commons.types.Fraction;

public class RTMPFormatParametersTest extends TestCase {

	public void testInit() throws Exception {
		PatternLayout layout = new PatternLayout("[%c{1}.%M:%L]-%-5p - %m%n");
		Logger logger = Logger.getRootLogger();
		logger.addAppender(new ConsoleAppender(layout));
		logger.setLevel(Level.TRACE);

		createFromString();
		System.out.println("\n\n\n");
		createFromRTMPInfoVideoProfile();
		// System.out.println("\n\n\n");
		// mergeVideoProfiles();
	}

	public void createFromString() throws Exception {
		System.out.println("createFromString");

		String fmtpStr = "url=rtmp://myserver.com/application;offerer=publishStream;answerer=playStream;w=320;h=240;fps=15/1";
		RTMPFormatParameters rtmpfpFromStr = new RTMPFormatParameters(fmtpStr);
		System.out.println(rtmpfpFromStr);
		assertEquals(fmtpStr, rtmpfpFromStr.toString());

		assertEquals("rtmp://myserver.com/application", rtmpfpFromStr.getRtmpInfo().getUrl());
		assertEquals("publishStream", rtmpfpFromStr.getRtmpInfo().getOfferer());
		assertEquals("playStream", rtmpfpFromStr.getRtmpInfo().getAnswerer());

		assertEquals(320, rtmpfpFromStr.getVideoProfile().getWidth());
		assertEquals(240, rtmpfpFromStr.getVideoProfile().getHeight());
		assertEquals(15, rtmpfpFromStr.getVideoProfile().getMaxFrameRate().getNumerator());
		assertEquals(1, rtmpfpFromStr.getVideoProfile().getMaxFrameRate().getDenominator());

	}

	public void createFromRTMPInfoVideoProfile() throws Exception {
		System.out.println("createFromVideoProfile");

		String fmtpStr = "url=rtmp://myserver.com/application;offerer=publishStream;answerer=playStream;w=320;h=240;fps=15/1";
		RTMPInfo rtmpInfo = new RTMPInfo("rtmp://myserver.com/application", "publishStream",
				"playStream");
		GenericVideoProfile videoProfile = new GenericVideoProfile(320, 240, new Fraction(15, 1));

		RTMPFormatParameters rtmpfpFromVP = new RTMPFormatParameters(rtmpInfo, videoProfile);
		String fmtpStrGen = rtmpfpFromVP.toString();
		System.out.println(fmtpStrGen);
		assertEquals(fmtpStr, fmtpStrGen);

		assertEquals("rtmp://myserver.com/application", rtmpfpFromVP.getRtmpInfo().getUrl());
		assertEquals("publishStream", rtmpfpFromVP.getRtmpInfo().getOfferer());
		assertEquals("playStream", rtmpfpFromVP.getRtmpInfo().getAnswerer());

		assertEquals(320, rtmpfpFromVP.getVideoProfile().getWidth());
		assertEquals(240, rtmpfpFromVP.getVideoProfile().getHeight());
		assertEquals(15, rtmpfpFromVP.getVideoProfile().getMaxFrameRate().getNumerator());
		assertEquals(1, rtmpfpFromVP.getVideoProfile().getMaxFrameRate().getDenominator());

		// System.out.println();
		// MPEG4FormatParameters mpeg4fpFromStr = new
		// MPEG4FormatParameters(mpeg4fpStr);
		// System.out.println(mpeg4fpFromStr);
		// System.out.println("profile-level: " +
		// mpeg4fpFromStr.getProfileLevel() );
		// System.out.println("width: " +
		// mpeg4fpFromStr.getVideoProfile().getWidth() );
		// System.out.println("height: " +
		// mpeg4fpFromStr.getVideoProfile().getHeight() );
		// System.out.println("frame rate: " +
		// mpeg4fpFromStr.getVideoProfile().getMaxFrameRate() );
	}
}
