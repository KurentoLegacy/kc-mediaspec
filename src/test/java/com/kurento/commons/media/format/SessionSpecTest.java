package com.kurento.commons.media.format;

import javax.sdp.SdpException;

import junit.framework.TestCase;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;


public class SessionSpecTest extends TestCase {

	private static String sdp = "v=0\r\n" +
			"o=- 123456 0 IN IP4 193.147.51.16\r\n" +
			"s=TestSession\r\n" +
			"c=IN IP4 193.147.51.16\r\n" +
			"t=0 0\r\n" +
			"m=video 32954 RTP/AVP 96\r\n" +
			"a=rtpmap:96 MP4V-ES/90000\r\n" +
			"a=sendrecv\r\n" +
			"m=audio 47523 RTP/AVP 14\r\n" +
			"a=sendrecv\r\n";

	private static String sdp2 = "v=0\r\n" +
			"o=- 123456 0 IN IP4 193.147.51.16\r\n" +
			"s=TestSession\r\n" +
			"c=IN IP4 193.147.51.16\r\n" +
			"t=0 0\r\n" +
			"m=video 32954 RTP/AVP 96\r\n" +
			"a=rtpmap:96 MP4V-ES/90000\r\n" +
			"m=audio 47523 RTP/AVP 14\r\n";

	private static String sdp3 = "v=0\r\n" +
			"o=alice 2890844526 2890844526 IN IP4 host.atlanta.example.com\r\n" +
			"s=\r\n" +
			"c=IN IP4 host.atlanta.example.com\r\n" +
			"t=0 0\r\n" +
			"m=audio 49170 RTP/AVP 0 8 97\r\n" +
			"a=rtpmap:0 PCMU/8000\r\n" +
			"a=rtpmap:8 PCMA/8000\r\n" +
			"a=rtpmap:97 iLBC/8000\r\n" +
			"m=video 51372 RTP/AVP 31 32\r\n" +
			"a=rtpmap:31 H261/90000\r\n" +
			"a=fmtp:31 QCIF=1;CIF=1\r\n" +
			"a=rtpmap:32 MPV/90000\r\n";

	private static String sdp4 = "v=0\r\n" +
			"o=jcadenlin 123456 654321 IN IP4 193.147.51.16\r\n" +
			"s=A conversation\r\n" +
			"c=IN IP4 193.147.51.16\r\n" +
			"t=0 0\r\n" +
			"m=audio 7078 RTP/AVP 8 101\r\n" +
			"a=rtpmap:8 PCMA/8000/1\r\n" +
			"a=rtpmap:101 telephone-event/8000/1\r\n" +
			"a=fmtp:101 0-11\r\n" +
			"m=video 9078 RTP/AVP 98\r\n" +
			"a=rtpmap:98 H263-1998/90000\r\n" +
			"a=fmtp:98 CIF=1;QCIF=1\r\n";

	private static String sdp5 = "v=0\r\n" +
			"o=jcadenlin 123456 654321 IN IP4 193.147.51.16\r\n" +
			"s=A conversation\r\n" +
			"c=IN IP4 193.147.51.16\r\n" +
			"t=0 0\r\n" +
			"m=audio 0 RTP/AVP\r\n" +
			"m=video 9078 RTP/AVP 98\r\n" +
			"a=rtpmap:98 H263-1998/90000\r\n" +
			"a=fmtp:98 CIF=1;QCIF=1\r\n";

	public void testInit() throws Exception {
		PatternLayout layout = new PatternLayout("[%c{1}.%M:%L]-%-5p - %m%n");
		Logger logger = Logger.getRootLogger();
		logger.addAppender(new ConsoleAppender(layout));
		logger.setLevel(Level.TRACE);

		try {
			SessionSpec spec = new SessionSpec(sdp);

			System.out.println("---------------------------------");
			System.out.println(spec.toString());
			System.out.println("---------------------------------");
		} catch (SdpException e) {
			e.printStackTrace();
		}

		try {
			SessionSpec spec = new SessionSpec(sdp2);

			System.out.println("---------------------------------");
			System.out.println(spec.toString());
			System.out.println("---------------------------------");
		} catch (SdpException e) {
			e.printStackTrace();
		}

		try {
			SessionSpec spec = new SessionSpec(sdp3);

			System.out.println("---------------------------------");
			System.out.println(spec.toString());
			System.out.println("---------------------------------");
		} catch (SdpException e) {
			e.printStackTrace();
		}

		try {
			SessionSpec spec = new SessionSpec(sdp4);

			System.out.println("---------------------------------");
			System.out.println(spec.toString());
			System.out.println("---------------------------------");
		} catch (SdpException e) {
			e.printStackTrace();
		}

		try {
			SessionSpec spec = new SessionSpec(sdp5);

			System.out.println("---------------------------------");
			System.out.println(spec.toString());
			System.out.println("---------------------------------");
		} catch (SdpException e) {
			e.printStackTrace();
		}
	}
}
