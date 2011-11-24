/*
 * Kurento Commons MediaSpec: Library to manage SDPs.
 * Copyright (C) 2011  Tikal Technologies
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License version 3
 * as published by the Free Software Foundation.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
	
	private static String sdp3 = "v=0\n\r" +
			"o=- 783800654 0 IN IP4 193.147.51.27\n\r" +
			"s=http://www.portsip.com\n\r" +
			"c=IN IP4 193.147.51.27\n\r" +
			"t=0 0\n\r" +
			"m=null 0 RTP/AVP 96\n\r" +
			"a=rtpmap:96 RTMP/90000\n\r" +
			"a=FMTP:96 url=rtmp://myserver.com/application;offerer=publishStream;answerer=playStream;w=320;h=240;fps=15/1\n\r";

	private static String sdp4 = "v=0\n\r" +
			"o=- 783800654 0 IN IP4 193.147.51.27\n\r" +
			"s=http://www.portsip.com\n\r" +
			"c=IN IP4 193.147.51.27\n\r" +
			"t=0 0\n\r" +
			"m=null 0 RTP/AVP 96\n\r" +
			"a=rtpmap:96 RTMP/90000\n\r" +
			"a=FMTP:96 url=rtmp://myserver.com/application2;offerer=publishStream2;answerer=;w=320;h=240;fps=15/1\n\r";
	
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

			SessionSpec intersect[] = SpecTools.intersectSessionSpec(spec2, spec);

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

		try {
			SessionSpec spec3 = new SessionSpec(sdp3);
			SessionSpec spec4 = new SessionSpec(sdp4);
			System.out.println("Created OK");
			SessionSpec intersect[] = SpecTools.intersectSessionSpec(spec4, spec3);

			System.out.println("---------------------------------");
			System.out.println(spec3.toString());
			System.out.println("---------------------------------");
			System.out.println(spec4.toString());
			System.out.println("---------------------------------");
			System.out.println(intersect[0].toString());
			// assertEquals(sdpIntersectLocal, intersect[0].toString());
			System.out.println("---------------------------------");
			System.out.println(intersect[1].toString());
			// assertEquals(sdpIntersectRemote, intersect[1].toString());
			System.out.println("---------------------------------");
		} catch (SdpException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
