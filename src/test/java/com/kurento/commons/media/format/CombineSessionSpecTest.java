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


public class CombineSessionSpecTest extends TestCase {

	private static String sdp = "v=0\r\n" +
			"o=- 123456 0 IN IP4 193.147.51.16\r\n" +
			"s=TestSession\r\n" +
			"c=IN IP4 193.147.51.16\r\n" +
			"t=0 0\r\n" +
			"m=video 32954 RTP/AVP 96\r\n" +
			"a=rtpmap:96 MP4V-ES/90000\r\n" +
			"m=audio 47523 RTP/AVP 14\r\n" +
			"a=recvonly\r\n" +
			"m=audio 1234 RTP/AVP 8\r\n" +
			"a=rtpmap:8 PCMA/8000/1\r\n";

	private static String sdp2 = "v=0\r\n" +
			"o=alice 2890844526 2890844526 IN IP4 host.atlanta.example.com\r\n" +
			"s=\r\n" +
			"c=IN IP4 host.atlanta.example.com\r\n" +
			"t=0 0\r\n" +
			"m=audio 49170 RTP/AVP 0 8 97 110\r\n" +
			"a=rtpmap:0 PCMU/8000\r\n" +
			"a=rtpmap:97 iLBC/8000\r\n" +
			"a=rtpmap:110 MPA/90000\r\n" +
			"m=video 51372 RTP/AVP 31 32 100\r\n" +
			"a=rtpmap:100 MP4V-ES/90000\r\n" +
			"a=rtpmap:31 H261/90000\r\n" +
			"a=fmtp:31 QCIF=1;CIF=1\r\n" +
			"a=rtpmap:32 MPV/90000\r\n";

	private static String sdp3 = "v=0\r\n" +
			"o=- 123456 0 IN IP4 193.147.51.16\r\n" +
			"s=TestSession\r\n" +
			"c=IN IP4 193.147.51.16\r\n" +
			"t=0 0\r\n" +
			"m=video 32954 RTP/AVP 96\r\n" +
			"a=rtpmap:96 H263-2000/90000\r\n" +
			"a=fmtp:96 QCIF=1;CIF=1\r\n" +
			"m=audio 47523 RTP/AVP 14\r\n" +
			"a=recvonly\r\n" +
			"m=audio 1234 RTP/AVP 8\r\n" +
			"a=rtpmap:8 PCMA/8000/1\r\n";

	private static String sdp4 = "v=0\r\n" +
			"o=- 123456 0 IN IP4 193.147.51.16\r\n" +
			"s=TestSession\r\n" +
			"c=IN IP4 193.147.51.16\r\n" +
			"t=0 0\r\n" +
			"m=video 32954 RTP/AVP 96\r\n" +
			"a=rtpmap:96 H263-2000/90000\r\n" +
			"m=audio 47523 RTP/AVP 14\r\n" +
			"a=recvonly\r\n" +
			"m=audio 1234 RTP/AVP 8\r\n" +
			"a=rtpmap:8 PCMA/8000/1\r\n";


	public void testInit() throws Exception {
		PatternLayout layout = new PatternLayout("[%c{1}.%M:%L]-%-5p - %m%n");
		Logger logger = Logger.getRootLogger();
		logger.addAppender(new ConsoleAppender(layout));
		logger.setLevel(Level.TRACE);

		try {
			SessionSpec spec = new SessionSpec(sdp);
			SessionSpec spec2 = new SessionSpec(sdp2);

			SessionSpec intersect[] = SpecTools.intersectSessionSpec(spec, spec2);
			SessionSpec intersect2[] = SpecTools.intersectSessionSpec(spec2, spec);

			System.out.println("---------------------------------");
			System.out.println(spec.toString());
			System.out.println("---------------------------------");
			System.out.println(spec2.toString());
			System.out.println("---------------------------------");
			System.out.println(intersect[0].toString());
			System.out.println("---------------------------------");
			System.out.println(intersect[1].toString());
			System.out.println("---------------------------------");
			System.out.println(intersect2[0].toString());
			System.out.println("---------------------------------");
			System.out.println(intersect2[1].toString());
			System.out.println("---------------------------------");
		} catch (SdpException e) {
			e.printStackTrace();
		}

		try {
			SessionSpec spec = new SessionSpec(sdp3);
			SessionSpec spec2 = new SessionSpec(sdp4);

			SessionSpec intersect[] = SpecTools.intersectSessionSpec(spec,
					spec2);
			SessionSpec intersect2[] = SpecTools.intersectSessionSpec(spec2,
					spec);

			System.out.println("---------------------------------");
			System.out.println(spec.toString());
			System.out.println("---------------------------------");
			System.out.println(spec2.toString());
			System.out.println("---------------------------------");
			System.out.println(intersect[0].toString());
			System.out.println("---------------------------------");
			System.out.println(intersect[1].toString());
			System.out.println("---------------------------------");
			System.out.println(intersect2[0].toString());
			System.out.println("---------------------------------");
			System.out.println(intersect2[1].toString());
			System.out.println("---------------------------------");
		} catch (SdpException e) {
			e.printStackTrace();
		}

	}

}
