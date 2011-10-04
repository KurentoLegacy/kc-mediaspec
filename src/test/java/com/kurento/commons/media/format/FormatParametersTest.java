package com.kurento.commons.media.format;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.kurento.commons.media.format.formatparameters.impl.H263FormatParameters;
import com.kurento.commons.media.format.formatparameters.impl.H263FormatParametersProfile;

public class FormatParametersTest extends TestCase {

	public void testInit() throws Exception {
		PatternLayout layout = new PatternLayout("[%c{1}.%M:%L]-%-5p - %m%n");
		Logger logger = Logger.getRootLogger();
		logger.addAppender(new ConsoleAppender(layout));
		logger.setLevel(Level.TRACE);

		String fmtpStr = "CUSTOM=640,480,2;CIF=1;QCIF=1";

		ArrayList<H263FormatParametersProfile> profilesList = new ArrayList<H263FormatParametersProfile>();
		profilesList.add(new H263FormatParametersProfile(640, 480, 15));
		profilesList.add(new H263FormatParametersProfile(352, 288, 30));
		profilesList.add(new H263FormatParametersProfile(176, 144, 30));

		H263FormatParameters h263fp = new H263FormatParameters(profilesList);

		System.out.println("---------------------------------");
		System.out.println(fmtpStr);
		System.out.println("---------------------------------");
		System.out.println(h263fp.getFormatParamsStr());
		assertEquals(fmtpStr, h263fp.getFormatParamsStr());

		System.out.println("\n\n\n");

		String fmtpStr2 = "CUSTOM=640,480,6;CIF=3;QCIF=2";

		ArrayList<H263FormatParametersProfile> profilesList2 = new ArrayList<H263FormatParametersProfile>();
		profilesList2.add(new H263FormatParametersProfile(640, 480, 5));
		profilesList2.add(new H263FormatParametersProfile(352, 288, 10));
		profilesList2.add(new H263FormatParametersProfile(176, 144, 15));

		H263FormatParameters h263fp2 = new H263FormatParameters(profilesList2);

		System.out.println("---------------------------------");
		System.out.println(fmtpStr2);
		System.out.println("---------------------------------");
		System.out.println(h263fp2.getFormatParamsStr());
		assertEquals(fmtpStr2, h263fp2.getFormatParamsStr());
		
		
		System.out.println("\n\n\n");

		String fmtpStr3 = "CPCF=6,1000,12,20,25,0,0,30;CUSTOM=640,480,30;CIF=25;QCIF=20;SQCIF=12";

		ArrayList<H263FormatParametersProfile> profilesList3 = new ArrayList<H263FormatParametersProfile>();
		profilesList3.add(new H263FormatParametersProfile(640, 480, 10));
		profilesList3.add(new H263FormatParametersProfile(352, 288, 12));
		profilesList3.add(new H263FormatParametersProfile(176, 144, 15));
		profilesList3.add(new H263FormatParametersProfile(128, 96, 25));

		H263FormatParameters h263fp3 = new H263FormatParameters(profilesList3);

		System.out.println("---------------------------------");
		System.out.println(fmtpStr3);
		System.out.println("---------------------------------");
		System.out.println(h263fp3.getFormatParamsStr());
		assertEquals(fmtpStr3, h263fp3.getFormatParamsStr());
	}
}
