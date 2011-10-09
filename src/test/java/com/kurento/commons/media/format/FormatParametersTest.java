package com.kurento.commons.media.format;

import java.util.ArrayList;

import junit.framework.TestCase;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.kurento.commons.media.format.formatparameters.FormatParameters;
import com.kurento.commons.media.format.formatparameters.impl.H263CPCF;
import com.kurento.commons.media.format.formatparameters.impl.H263FormatParameters;
import com.kurento.commons.media.format.formatparameters.impl.H263FormatParametersProfile;
import com.kurento.commons.media.format.formatparameters.impl.PictureSize;
import com.kurento.commons.media.format.formatparameters.impl.ResolutionMPI;

public class FormatParametersTest extends TestCase {

	public void testInit() throws Exception {
		PatternLayout layout = new PatternLayout("[%c{1}.%M:%L]-%-5p - %m%n");
		Logger logger = Logger.getRootLogger();
		logger.addAppender(new ConsoleAppender(layout));
		logger.setLevel(Level.TRACE);

		createFromParametersProfiles();
		System.out.println("\n\n\n");
		createFromString();
		System.out.println("\n\n\n");
		createFromCPCFandResolutionsMPI();
		System.out.println("\n\n\n");
		intersection();

	}

	private void createFromParametersProfiles() throws Exception {
		System.out.println("createFromParametersProfiles");

		String fmtpStr = "CUSTOM=640,480,2;CIF=1;QCIF=1";

		ArrayList<H263FormatParametersProfile> profilesList = new ArrayList<H263FormatParametersProfile>();
		profilesList.add(new H263FormatParametersProfile(640, 480, 15));
		profilesList.add(new H263FormatParametersProfile(352, 288, 30));
		profilesList.add(new H263FormatParametersProfile(176, 144, 30));

		H263FormatParameters h263fp = new H263FormatParameters(profilesList);

		System.out.println("---------------------------------");
		System.out.println(fmtpStr);
		System.out.println("---------------------------------");
		System.out.println(h263fp.toString());
		assertEquals(fmtpStr, h263fp.toString());

		System.out.println("\n");

		String fmtpStr2 = "CUSTOM=640,480,6;CIF=3;QCIF=2";

		ArrayList<H263FormatParametersProfile> profilesList2 = new ArrayList<H263FormatParametersProfile>();
		profilesList2.add(new H263FormatParametersProfile(640, 480, 5));
		profilesList2.add(new H263FormatParametersProfile(352, 288, 10));
		profilesList2.add(new H263FormatParametersProfile(176, 144, 15));

		H263FormatParameters h263fp2 = new H263FormatParameters(profilesList2);

		System.out.println("---------------------------------");
		System.out.println(fmtpStr2);
		System.out.println("---------------------------------");
		System.out.println(h263fp2.toString());
		assertEquals(fmtpStr2, h263fp2.toString());

		System.out.println("\n");

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
		System.out.println(h263fp3.toString());
		assertEquals(fmtpStr3, h263fp3.toString());
	}

	private void createFromString() throws Exception {
		System.out.println("createFromString");

		String fmtpStr = "CIF=1;QCIF=1";
		H263FormatParameters h263fpFromStr = new H263FormatParameters(fmtpStr);
		H263FormatParameters h263fp = new H263FormatParameters(
				h263fpFromStr.getProfilesList());
		System.out.println("---------------------------------");
		System.out.println(fmtpStr);
		System.out.println("---------------------------------");
		System.out.println(h263fp.toString());
		assertEquals(fmtpStr, h263fp.toString());

		System.out.println("\n");
		String fmtpStr4 = "CUSTOM=640,480,3;CIF=2;QCIF=1";
		H263FormatParameters h263fpFromStr4 = new H263FormatParameters(fmtpStr4);
		H263FormatParameters h263fp4 = new H263FormatParameters(
				h263fpFromStr4.getProfilesList());
		System.out.println("---------------------------------");
		System.out.println(fmtpStr4);
		System.out.println("---------------------------------");
		System.out.println(h263fp4.toString());
		assertEquals(fmtpStr4, h263fp4.toString());

		System.out.println("\n");
		String fmtpStr2 = "CUSTOM=640,480,6;CIF=3;QCIF=2";
		H263FormatParameters h263fpFromStr2 = new H263FormatParameters(fmtpStr2);
		H263FormatParameters h263fp2 = new H263FormatParameters(
				h263fpFromStr2.getProfilesList());
		System.out.println("---------------------------------");
		System.out.println(fmtpStr2);
		System.out.println("---------------------------------");
		System.out.println(h263fp2.toString());
		assertEquals(fmtpStr2, h263fp2.toString());

		System.out.println("\n");
		String fmtpStr3 = "CPCF=6,1000,12,20,25,0,0,30;CUSTOM=640,480,30;CIF=25;QCIF=20;SQCIF=12";
		H263FormatParameters h263fpFromStr3 = new H263FormatParameters(fmtpStr3);
		H263FormatParameters h263fp3 = new H263FormatParameters(
				h263fpFromStr3.getProfilesList());
		System.out.println("---------------------------------");
		System.out.println(fmtpStr3);
		System.out.println("---------------------------------");
		System.out.println(h263fp3.toString());
		assertEquals(fmtpStr3, h263fp3.toString());

		// FAIL FIX this case
		// System.out.println("\n");
		// String fmtpStr5 =
		// "CPCF=6,1001,12,20,25,0,0,30;CUSTOM=640,480,30;CIF=25;QCIF=20;SQCIF=12";
		// H263FormatParameters h263fpFromStr5 = new
		// H263FormatParameters(fmtpStr5);
		// H263FormatParameters h263fp5 = new H263FormatParameters(
		// h263fpFromStr5.getProfilesList());
		// System.out.println("---------------------------------");
		// System.out.println(fmtpStr5);
		// System.out.println("---------------------------------");
		// System.out.println(h263fp5.getFormatParamsStr());
		// assertEquals(fmtpStr5, h263fp5.getFormatParamsStr());
	}

	private void createFromCPCFandResolutionsMPI() throws Exception {
		System.out.println("createFromCPCFandResolutionsMPI");

		String fmtpStr = "CIF=1;QCIF=1";
		ArrayList<ResolutionMPI> resolutionsList = new ArrayList<ResolutionMPI>();
		resolutionsList.add(new ResolutionMPI(PictureSize.CIF, 1));
		resolutionsList.add(new ResolutionMPI(PictureSize.QCIF, 1));
		H263FormatParameters h263fp = new H263FormatParameters(null,
				resolutionsList);
		H263FormatParameters h263fpProfiles = new H263FormatParameters(
				h263fp.getProfilesList());
		System.out.println("---------------------------------");
		System.out.println(fmtpStr);
		System.out.println(h263fp.toString());
		System.out.println(h263fpProfiles.toString());
		System.out.println("---------------------------------");
		assertEquals(fmtpStr, h263fp.toString());
		assertEquals(fmtpStr, h263fpProfiles.toString());

		System.out.println("\n");
		String fmtpStr2 = "CUSTOM=640,480,3;CIF=2;QCIF=1";
		ArrayList<ResolutionMPI> resolutionsList2 = new ArrayList<ResolutionMPI>();
		ResolutionMPI rmpiCustom2 = new ResolutionMPI(PictureSize.CUSTOM, 3);
		rmpiCustom2.setWidth(640);
		rmpiCustom2.setHeight(480);
		resolutionsList2.add(rmpiCustom2);
		resolutionsList2.add(new ResolutionMPI(PictureSize.CIF, 2));
		resolutionsList2.add(new ResolutionMPI(PictureSize.QCIF, 1));
		H263FormatParameters h263fp2 = new H263FormatParameters(null,
				resolutionsList2);
		H263FormatParameters h263fpProfiles2 = new H263FormatParameters(
				h263fp2.getProfilesList());
		System.out.println("---------------------------------");
		System.out.println(fmtpStr2);
		System.out.println(h263fp2.toString());
		System.out.println(h263fpProfiles2.toString());
		System.out.println("---------------------------------");
		assertEquals(fmtpStr2, h263fp2.toString());
		assertEquals(fmtpStr2, h263fpProfiles2.toString());

		System.out.println("\n");
		String fmtpStr3 = "CPCF=6,1000,12,20,25,0,0,30;CUSTOM=640,480,30;CIF=25;QCIF=20;SQCIF=12";
		H263CPCF cpcf3 = new H263CPCF(6, 1000,
				new int[] { 12, 20, 25, 0, 0, 30 });
		ArrayList<ResolutionMPI> resolutionsList3 = new ArrayList<ResolutionMPI>();
		ResolutionMPI rmpiCustom3 = new ResolutionMPI(PictureSize.CUSTOM, 30);
		rmpiCustom3.setWidth(640);
		rmpiCustom3.setHeight(480);
		resolutionsList3.add(rmpiCustom3);
		resolutionsList3.add(new ResolutionMPI(PictureSize.CIF, 25));
		resolutionsList3.add(new ResolutionMPI(PictureSize.QCIF, 20));
		resolutionsList3.add(new ResolutionMPI(PictureSize.SQCIF, 12));
		H263FormatParameters h263fp3 = new H263FormatParameters(cpcf3,
				resolutionsList3);
		H263FormatParameters h263fpProfiles3 = new H263FormatParameters(
				h263fp3.getProfilesList());
		System.out.println("---------------------------------");
		System.out.println(fmtpStr3);
		System.out.println(h263fp3.toString());
		System.out.println(h263fpProfiles3.toString());
		System.out.println("---------------------------------");
		assertEquals(fmtpStr3, h263fp3.toString());
		assertEquals(fmtpStr3, h263fpProfiles3.toString());

	}

	private void intersection() throws Exception {
		System.out.println("intersection");

		String fmtpStrA = "CIF=1;QCIF=1";
		String fmtpStrB = "CUSTOM=640,480,3;CIF=2;QCIF=1";
		String fmtpStrIntersect = "CIF=2;QCIF=1";
		H263FormatParameters h263fpA = new H263FormatParameters(fmtpStrA);
		H263FormatParameters h263fpB = new H263FormatParameters(fmtpStrB);
		FormatParameters h263fpIntersect = h263fpA.intersect(h263fpB);
		System.out.println("---------------------------------");
		System.out.println("A: " + fmtpStrA);
		System.out.println("B: " + fmtpStrB);
		System.out.println("Expected intersect: " + fmtpStrIntersect);
		System.out.println("Obtained intersect: " + h263fpIntersect.toString());
		System.out.println("---------------------------------");
		assertEquals(fmtpStrIntersect, h263fpIntersect.toString());

		String fmtpStrA2 = "CUSTOM=640,480,3;CIF=2;QCIF=1";
		String fmtpStrB2 = "CPCF=6,1000,12,20,25,0,0,30;CUSTOM=640,480,30;CIF=25;QCIF=20;SQCIF=12";
		String fmtpStrIntersect2 = "CPCF=30,1000,0,4,5,0,0,6;CUSTOM=640,480,6;CIF=5;QCIF=4";
		H263FormatParameters h263fpA2 = new H263FormatParameters(fmtpStrA2);
		H263FormatParameters h263fpB2 = new H263FormatParameters(fmtpStrB2);
		H263FormatParameters h263fpIntersectExpected2 = new H263FormatParameters(
				fmtpStrIntersect2);
		FormatParameters h263fpIntersect2 = h263fpA2.intersect(h263fpB2);
		System.out.println("---------------------------------");
		System.out.println("A: " + fmtpStrA2);
		System.out.println("B: " + fmtpStrB2);
		System.out.println("Expected intersect: " + fmtpStrIntersect2);
		System.out
				.println("Obtained intersect: " + h263fpIntersect2.toString());
		System.out.println("compare: "
				+ h263fpIntersect2.equals(h263fpIntersectExpected2));
		System.out.println("---------------------------------");
		assertEquals(fmtpStrIntersect2, h263fpIntersect2.toString());
		assertTrue(h263fpIntersect2.equals(h263fpIntersectExpected2));

	}

}
