package com.kurento.commons.media.format;

import junit.framework.TestCase;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;

import com.kurento.commons.media.format.formatparameters.impl.GenericVideoProfile;
import com.kurento.commons.media.format.formatparameters.impl.MPEG4FormatParameters;
import com.kurento.commons.types.Fraction;

public class MPEG4FormatParametersTest extends TestCase {

	public void testInit() throws Exception {
		PatternLayout layout = new PatternLayout("[%c{1}.%M:%L]-%-5p - %m%n");
		Logger logger = Logger.getRootLogger();
		logger.addAppender(new ConsoleAppender(layout));
		logger.setLevel(Level.TRACE);

		createFromString();
		System.out.println("\n\n\n");
		createFromVideoProfile();
	}
	
	public void createFromString() throws Exception {
		System.out.println("createFromString");
		
		
		String fmtpStr = "profile-level-id=1; config=000001B001000001B58913000001000000012000C48D8AEE050B04241463000001B24C61766335322E37322E32";
		MPEG4FormatParameters mpeg4fpFromStr = new MPEG4FormatParameters(fmtpStr);
		System.out.println(mpeg4fpFromStr);
		System.out.println("profile-level: " + mpeg4fpFromStr.getProfileLevel() );
		System.out.println("width: " +  mpeg4fpFromStr.getVideoProfile().getWidth() );
		System.out.println("height: " +  mpeg4fpFromStr.getVideoProfile().getHeight() );
		System.out.println("frame rate: " +  mpeg4fpFromStr.getVideoProfile().getMaxFrameRate() );
		
		System.out.println("\n\n");
		String fmtpStr2 = "profile-level-id=1; config=000001B001000001B5090000010000000120008440FA282C2090A21F";
		MPEG4FormatParameters mpeg4fpFromStr2 = new MPEG4FormatParameters(fmtpStr2);
		System.out.println(mpeg4fpFromStr2);
		System.out.println("profile-level: " + mpeg4fpFromStr2.getProfileLevel() );
		System.out.println("width: " +  mpeg4fpFromStr2.getVideoProfile().getWidth() );
		System.out.println("height: " +  mpeg4fpFromStr2.getVideoProfile().getHeight() );
		System.out.println("frame rate: " +  mpeg4fpFromStr2.getVideoProfile().getMaxFrameRate() );
		
		System.out.println("\n\n");
		String fmtpStr3 = "profile-level-id=1; config=000001b009000001b509000001000000012000845d4c282c2090a28f";
		MPEG4FormatParameters mpeg4fpFromStr3 = new MPEG4FormatParameters(fmtpStr3);
		System.out.println(mpeg4fpFromStr3);
		System.out.println("profile-level: " + mpeg4fpFromStr3.getProfileLevel() );
		System.out.println("width: " +  mpeg4fpFromStr3.getVideoProfile().getWidth() );
		System.out.println("height: " +  mpeg4fpFromStr3.getVideoProfile().getHeight() );
		System.out.println("frame rate: " +  mpeg4fpFromStr3.getVideoProfile().getMaxFrameRate() );
		
		System.out.println("\n\n");
		String fmtpStr4 = "profile-level-id=1; config=000001b009000001b509000001000000012000c488800c50604121463f";
		MPEG4FormatParameters mpeg4fpFromStr4 = new MPEG4FormatParameters(fmtpStr4);
		System.out.println(mpeg4fpFromStr4);
		System.out.println("profile-level: " + mpeg4fpFromStr4.getProfileLevel() );
		System.out.println("width: " +  mpeg4fpFromStr4.getVideoProfile().getWidth() );
		System.out.println("height: " +  mpeg4fpFromStr4.getVideoProfile().getHeight() );
		System.out.println("frame rate: " +  mpeg4fpFromStr4.getVideoProfile().getMaxFrameRate() );
		
		System.out.println("\n\n");
		String fmtpStr5 = "profile-level-id=1; config=000001B001000001B58913000001000000012000C4F8404D8AEE050F04281463000001B24C61766335322E37322E32";
		MPEG4FormatParameters mpeg4fpFromStr5 = new MPEG4FormatParameters(fmtpStr5);
		System.out.println(mpeg4fpFromStr5);
		System.out.println("profile-level: " + mpeg4fpFromStr5.getProfileLevel() );
		System.out.println("width: " +  mpeg4fpFromStr5.getVideoProfile().getWidth() );
		System.out.println("height: " +  mpeg4fpFromStr5.getVideoProfile().getHeight() );
		System.out.println("frame rate: " +  mpeg4fpFromStr5.getVideoProfile().getMaxFrameRate() );
	}


	public void createFromVideoProfile() throws Exception {
		System.out.println("createFromVideoProfile");

		GenericVideoProfile videoProfile = new GenericVideoProfile(352, 288, new Fraction(15000, 1001));
		MPEG4FormatParameters mpeg4fpFromVP = new MPEG4FormatParameters(videoProfile);
		String mpeg4fpStr = mpeg4fpFromVP.toString();
		System.out.println(mpeg4fpStr);
		System.out.println("profile-level: " + mpeg4fpFromVP.getProfileLevel());
		System.out.println("width: " + mpeg4fpFromVP.getVideoProfile().getWidth());
		System.out.println("height: " + mpeg4fpFromVP.getVideoProfile().getHeight());
		System.out.println("frame rate: " + mpeg4fpFromVP.getVideoProfile().getMaxFrameRate());

		System.out.println();
		MPEG4FormatParameters mpeg4fpFromStr = new MPEG4FormatParameters(mpeg4fpStr);
		System.out.println(mpeg4fpFromStr);
		System.out.println("profile-level: " + mpeg4fpFromStr.getProfileLevel() );
		System.out.println("width: " +  mpeg4fpFromStr.getVideoProfile().getWidth() );
		System.out.println("height: " +  mpeg4fpFromStr.getVideoProfile().getHeight() );
		System.out.println("frame rate: " +  mpeg4fpFromStr.getVideoProfile().getMaxFrameRate() );
	}

}
