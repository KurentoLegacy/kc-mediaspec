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
			"b=AS:256\n\r" +
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
			"a=fmtp:98 CIF=1;QCIF=1\r\n" +
			"b=AS:256\n\r";

	private static String sdp6 = "v=0\n\r" +
			"o=mhandley 2890844526 2890842807 IN IP4 126.16.64.4\n\r" +
			"s=SDP Seminar\n\r" +
			"i=A Seminar on the session description protocol\n\r" +
			"c=IN IP4 224.2.17.12/127\n\r" +
			"t=2873397496 2873404696\n\r" +
			"b=AS:100\n\r" +
			"m=audio 49170 RTP/AVP 0\n\r" +
			"b=AS:64\n\r" +
			"b=RS:800\n\r" +
			"b=RR:2400\n\r" +
			"m=video 51372 RTP/AVP 31\n\r" +
			"b=AS:256\n\r" +
			"b=RS:800\n\r" +
			"b=RR:2400\n\r";

	private static String sdp7 = "v=0\n\r" +
			"o=- 783800654 0 IN IP4 193.147.51.27\n\r" +
			"s=http://www.portsip.com\n\r" +
			"c=IN IP4 193.147.51.27\n\r" +
			"t=0 0\n\r" +
			"m=video 40032 RTP/AVP 115\n\r" +
			"a=rtpmap:115 H263-1998/90000\n\r" +
			"a=FMTP:115 QCIF=1 CIF=1\n\r" +
			"a=sendrecv\n\r";

	private static String sdp8 = "v=0\n\r" +
			"o=- 783800654 0 IN IP4 193.147.51.27\n\r" +
			"s=http://www.portsip.com\n\r" +
			"c=IN IP4 193.147.51.27\n\r" +
			"t=0 0\n\r" +
			"m=null 0 RTP/AVP 96\n\r" +
			"a=rtpmap:96 RTMP/90000\n\r" +
			"a=FMTP:96 url=rtmp://myserver.com/application,publish=publishStream,play=playStream,width=320,heigh=240\n\r";

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
			throw e;
		}

		try {
			SessionSpec spec = new SessionSpec(sdp2);

			System.out.println("---------------------------------");
			System.out.println(spec.toString());
			System.out.println("---------------------------------");
		} catch (SdpException e) {
			e.printStackTrace();
			throw e;
		}

		try {
			SessionSpec spec = new SessionSpec(sdp3);

			System.out.println("---------------------------------");
			System.out.println(spec.toString());
			System.out.println("---------------------------------");
		} catch (SdpException e) {
			e.printStackTrace();
			throw e;
		}

		try {
			SessionSpec spec = new SessionSpec(sdp4);

			System.out.println("---------------------------------");
			System.out.println(spec.toString());
			System.out.println("---------------------------------");
		} catch (SdpException e) {
			e.printStackTrace();
			throw e;
		}

		try {
			SessionSpec spec = new SessionSpec(sdp5);

			System.out.println("---------------------------------");
			System.out.println(spec.toString());
			System.out.println("---------------------------------");
		} catch (SdpException e) {
			e.printStackTrace();
			throw e;
		}

		try {
			SessionSpec spec = new SessionSpec(sdp6);

			System.out.println("---------------------------------");
			System.out.println(spec.toString());
			System.out.println("---------------------------------");
		} catch (SdpException e) {
			e.printStackTrace();
			throw e;
		}

		try {
			SessionSpec spec = new SessionSpec(sdp7);

			System.out.println("---------------------------------");
			System.out.println(spec.toString());
			System.out.println("---------------------------------");
		} catch (SdpException e) {
			e.printStackTrace();
			throw e;
		}

		try {
			SessionSpec spec = new SessionSpec(sdp8);

			System.out.println("---------------------------------");
			System.out.println(spec.toString());
			System.out.println("---------------------------------");
		} catch (SdpException e) {
			e.printStackTrace();
			throw e;
		}
	}
}
