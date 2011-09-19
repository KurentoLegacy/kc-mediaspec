package com.kurento.commons.media.format;

import javax.sdp.SdpException;

import junit.framework.TestCase;

import org.apache.log4j.ConsoleAppender;
import org.apache.log4j.Level;
import org.apache.log4j.Logger;
import org.apache.log4j.PatternLayout;


public class IntersectionTest extends TestCase {

	private static String sdp = "v=0\r\n" +
			"o=- 123456 654321 IN IP4 193.147.51.18\r\n" +
			"s=TestSession\r\n" +
			"c=IN IP4 193.147.51.18\r\n" +
			"t=0 0\r\n" +
			"m=video 2323 RTP/AVP 96\r\n" +
			"a=rtpmap:96 H263-1998/90000\r\n" +
			"a=sendonly\r\n" +
			"m=audio 5555 RTP/AVP 100\r\n" +
			"a=rtpmap:100 AMR/8000/1\r\n" +
			"a=FMTP:100 octet-align=1\r\n" +
			"a=sendonly\r\n";
	
	private static String sdp2 = "v=0\r\n" +
			"o=- 123456 654321 IN IP4 193.147.51.44\r\n" +
			"s=TestSession\r\n" +
			"c=IN IP4 193.147.51.44\r\n" +
			"t=0 0\r\n" +
			"m=video 3434 RTP/AVP 96\r\n" +
			"a=rtpmap:96 H263-1998/90000\r\n" +
			"a=sendrecv\r\n" +
			"m=audio 7777 RTP/AVP 100\r\n" +
			"a=rtpmap:100 AMR/8000/1\r\n" +
			"a=FMTP:100 octet-align=1\r\n" +
			"a=sendonly\r\n";
	
	
	private static String sdpIntersectLocal = "v=0\r\n" +
			"o=- 123456 654321 IN IP4 193.147.51.44\r\n" +
			"s=TestSession\r\n" +
			"c=IN IP4 193.147.51.44\r\n" +
			"t=0 0\r\n" +
			"m=video 3434 RTP/AVP 96\r\n" +
			"a=rtpmap:96 H263-1998/90000\r\n" +
			"a=recvonly\r\n" +
			"m=audio 7777 RTP/AVP 100\r\n" +
			"a=rtpmap:100 AMR/8000/1\r\n" +
			"a=FMTP:100 octet-align=1\r\n" +
			"a=inactive\r\n";
	
	private static String sdpIntersectRemote = "v=0\r\n" +
			"o=- 123456 654321 IN IP4 193.147.51.18\r\n" +
			"s=TestSession\r\n" +
			"c=IN IP4 193.147.51.18\r\n" +
			"t=0 0\r\n" +
			"m=video 2323 RTP/AVP 96\r\n" +
			"a=rtpmap:96 H263-1998/90000\r\n" +
			"a=sendonly\r\n" +
			"m=audio 5555 RTP/AVP 100\r\n" +
			"a=rtpmap:100 AMR/8000/1\r\n" +
			"a=FMTP:100 octet-align=1\r\n" +
			"a=inactive\r\n";
	
	

	public void testInit() throws Exception {
		PatternLayout layout = new PatternLayout("[%c{1}.%M:%L]-%-5p - %m%n");
		Logger logger = Logger.getRootLogger();
		logger.addAppender(new ConsoleAppender(layout));
		logger.setLevel(Level.TRACE);

		try {
			SessionSpec spec = new SessionSpec(sdp);
			SessionSpec spec2 = new SessionSpec(sdp2);
			
			SessionSpec intersect[] = SpecTools.intersectSessionSpec(spec, spec2);

			System.out.println("---------------------------------");
			System.out.println(spec.toString());
			System.out.println("---------------------------------");
			System.out.println(spec2.toString());
			System.out.println("---------------------------------");
			System.out.println(intersect[0].toString());
			assertEquals(sdpIntersectLocal, intersect[0].toString());
			System.out.println("---------------------------------");
			System.out.println(intersect[1].toString());
			assertEquals(sdpIntersectRemote, intersect[1].toString());
			System.out.println("---------------------------------");
		} catch (SdpException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
