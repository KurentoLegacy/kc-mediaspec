package com.kurento.commons.media.format;

import gov.nist.javax.sdp.fields.SDPFieldNames;

import java.util.Properties;
import java.util.StringTokenizer;

import javax.sdp.SdpConstants;
import javax.sdp.SdpException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.kurento.commons.sdp.enums.MediaType;

/**
 * <p>
 * A payload indentify the format of the RTP connection protocol and determines
 * its interpretation by the application.*
 * </p>
 * Example : m=audio 60000 RPT/AVP 0 a=rtpmap:0 PCMU/8000
 * 
 * This audio channel accept payload 0 and it identify with PCMU codec working
 * in 8000Hz
 * 
 * Refer to RFC 1889 for RTP payloads specifications
 * 
 * 
 * @author Qiang
 * 
 */
public class PayloadSpec {
	private static Log log = LogFactory.getLog(PayloadSpec.class);

	private static final String ENDLINE = "\r\n";

	// Media Info
	private MediaType mediaType;
	private String encodingName;
	private Integer clockRate;
	private String encodingParams = "";

	private String formatParams;

	// RTP parameters
	private Integer payload;
	private Integer port;

	// Code parameters
	private CodecFormat codecFormat;

	public PayloadSpec() {

	}

	public PayloadSpec(int payload) {
		this.payload = payload;

		fillStaticPayloadParameters();
	}

	/**
	 * Creates a PayloadSpec from a string. Format of the string must be: [
	 * payloadNumber encodingName/clockRate]
	 * 
	 * @param info
	 * @throws SdpException
	 */
	public PayloadSpec(String info) throws SdpException {
		payload = getPayloadFromString(info);

		setRtpMap(info);
	}

	public void setRtpMap(String info) {
		StringTokenizer token = new StringTokenizer(info);
		int payloadId = Integer.parseInt(token.nextToken());

		if (payloadId != payload)
			return;

		if (!token.hasMoreTokens()) {
			fillStaticPayloadParameters();
			log.debug("Payload without encoding name and clock rate, it must be an specific payload");
			return;
		}

		String stringToken = token.nextToken();
		String[] values = stringToken.split("/");
		if (values.length != 2 && values.length != 3) {
			log.warn("Format not spected encodingName/clockRate ["
					+ stringToken + "]");
		}
		encodingName = values[0];
		if (encodingName == null || encodingName == "") {
			log.warn("Media without encoding name");
		}

		if (values.length >= 2)
			clockRate = Integer.parseInt(values[1]);

		if (values.length >= 3)
			encodingParams = values[2];
	}

	private void fillStaticPayloadParameters() {
		/*
	      According to RFC 3551

        PT   encoding    media type  clock rate   channels
              name                    (Hz)
         ___________________________________________________
         0    PCMU        A            8,000       1
         1    reserved    A
         2    reserved    A
         3    GSM         A            8,000       1
         4    G723        A            8,000       1
         5    DVI4        A            8,000       1
         6    DVI4        A           16,000       1
         7    LPC         A            8,000       1
         8    PCMA        A            8,000       1
         9    G722        A            8,000       1
         10   L16         A           44,100       2
         11   L16         A           44,100       1
         12   QCELP       A            8,000       1
         13   CN          A            8,000       1
         14   MPA         A           90,000       (see text)
         15   G728        A            8,000       1
         16   DVI4        A           11,025       1
         17   DVI4        A           22,050       1
         18   G729        A            8,000       1
         19   reserved    A
         20   unassigned  A
         21   unassigned  A
         22   unassigned  A
         23   unassigned  A
         dyn  G726-40     A            8,000       1
         dyn  G726-32     A            8,000       1
         dyn  G726-24     A            8,000       1
         dyn  G726-16     A            8,000       1
         dyn  G729D       A            8,000       1
         dyn  G729E       A            8,000       1
         dyn  GSM-EFR     A            8,000       1
         dyn  L8          A            var.        var.
         dyn  RED         A                        (see text)
         dyn  VDVI        A            var.        1

         Table 4: Payload types (PT) for audio encodings

         PT      encoding    media type  clock rate
                 name                    (Hz)
         _____________________________________________
         24      unassigned  V
         25      CelB        V           90,000
         26      JPEG        V           90,000
         27      unassigned  V
         28      nv          V           90,000
         29      unassigned  V
         30      unassigned  V
         31      H261        V           90,000
         32      MPV         V           90,000
         33      MP2T        AV          90,000
         34      H263        V           90,000
         35-71   unassigned  ?
         72-76   reserved    N/A         N/A
         77-95   unassigned  ?
         96-127  dynamic     ?
         dyn     H263-1998   V           90,000

         Table 5: Payload types (PT) for video and combined
                  encodings

	 */

		switch (payload) {
		case 0:
		case 3:
		case 4:
		case 5:
		case 7:
		case 8:
		case 9:
		case 12:
		case 13:
		case 15:
		case 18:
			clockRate = 8000;
			break;
		case 16:
			clockRate = 11025;
			break;
		case 6:
			clockRate = 16000;
			break;
		case 17:
			clockRate = 22050;
			break;
		case 10:
		case 11:
			clockRate = 44100;
			break;
		case 14:
			encodingName = "MPA";
		case 25:
		case 26:
		case 28:
		case 31:
		case 32:
		case 33:
		case 34:
			clockRate = 90000;
			break;
		}
	}

	/**
	 * Gets media type
	 * 
	 * @see MediaType
	 * @param mediaType
	 */
	public MediaType getMediaType() {
		return mediaType;
	}

	/**
	 * Sets media type.
	 * 
	 * @see MediaType
	 * @return MediaType
	 */
	public void setMediaType(MediaType mediaType) {
		this.mediaType = mediaType;
	}

	/**
	 * Gets encoding name
	 * 
	 * @return Encoding name
	 */
	public String getEncodingName() {
		return encodingName;
	}

	/**
	 * Sets media's encoding name
	 * 
	 * @param encodingName
	 */
	public void setEncodingName(String encodingName) {
		this.encodingName = encodingName;
	}

	/**
	 * Gets clock rate of encoding
	 * 
	 * @return
	 */
	public Integer getClockRate() {
		return clockRate;
	}

	/**
	 * Sets clock rate of encoding
	 * 
	 * @param clockRate
	 */
	public void setClockRate(Integer clockRate) {
		this.clockRate = clockRate;
	}

	/**
	 * Returns a payload associated with the encoding
	 * 
	 * @return
	 */
	public Integer getPayload() {
		return payload;
	}

	/**
	 * Sets the payload of encoding
	 * 
	 * @param payload
	 */
	public void setPayload(Integer payload) {
		this.payload = payload;
	}

	/**
	 * Returns the port of communication.
	 * 
	 * @return Port
	 */
	public Integer getPort() {
		return port;
	}

	/**
	 * Sets the port of communication
	 * 
	 * @param port
	 */
	public void setPort(Integer port) {
		this.port = port;
	}

	/**
	 * Returns codec format of the media
	 * 
	 * @return CodecFormat
	 */
	public CodecFormat getCodecFormat() {
		return codecFormat;
	}

	/**
	 * Sets the media codec format.
	 * 
	 * @param codecFormat
	 */
	public void setCodecFormat(CodecFormat codecFormat) {
		this.codecFormat = codecFormat;
	}

	/**
	 * Sets the encoding params
	 * 
	 * @param params
	 *            The encoding parameters
	 */
	public void setEncodingParams(String params) {
		encodingParams = params;
	}

	/**
	 * Gets the Encoding parameters
	 * 
	 * @return The encoding parameters
	 */
	public String getEncodingParams() {
		return encodingParams;
	}

	public String getFormatParams() {
		return formatParams;
	}

	public void setFormatParams(Properties params) {
		if (params == null)
			return;

		StringBuilder sb = new StringBuilder();

		for (Object key : params.keySet()) {
			params.get(key);
			if (!sb.toString().equals("")) {
				sb.append(";");
			}
			sb.append(key.toString());
			sb.append("=");
			sb.append(params.get(key));
		}

		this.formatParams = sb.toString();
	}

	public void setFormatParams(String params) {
		if (params == null)
			return;

		this.formatParams = params;
	}

	/**
	 * Two PayloadSpec are equals if they have the same media type and the same
	 * encoding name. Or their payload < 96 and equals
	 */
	@Override
	public boolean equals(Object obj) {
		PayloadSpec cmpObj = (PayloadSpec) obj;
		if (this.payload < 96 && this.payload == cmpObj.payload)
				return true;

		return mediaType.equals(cmpObj.mediaType)
				&& encodingName.equalsIgnoreCase(cmpObj.encodingName)
				&& clockRate.equals(cmpObj.getClockRate());
	}

	@Override
	public String toString() {
		if (encodingName == null || encodingName == "") {
			return "";
		}
		String sdpLine = SDPFieldNames.ATTRIBUTE_FIELD + SdpConstants.RTPMAP
				+ ":" + payload + " " + encodingName + "/" + clockRate;

		if (encodingParams != null && encodingParams != "") {
			sdpLine += "/" + encodingParams;
		}

		sdpLine += ENDLINE;
		if (formatParams != null)
			sdpLine += SDPFieldNames.ATTRIBUTE_FIELD + SdpConstants.FMTP + ":"
					+ payload + " " + formatParams + ENDLINE;
		return sdpLine;
	}

	@Override
	public Object clone() {
		PayloadSpec obj = new PayloadSpec();
		obj.clockRate = clockRate;
		obj.codecFormat = codecFormat;
		obj.mediaType = mediaType;
		obj.port = port;
		obj.payload = payload;
		obj.encodingName = encodingName;
		obj.encodingParams = encodingParams;
		obj.formatParams = formatParams;
		return obj;
	}

	public static int getPayloadFromString(String info) throws SdpException {
		StringTokenizer token = new StringTokenizer(info);
		if (!token.hasMoreTokens())
			throw new SdpException("Error getting payload from string");
		return Integer.parseInt(token.nextToken());
	}

	public static String removePayloadFromString(String info) throws SdpException {
		StringTokenizer token = new StringTokenizer(info);
		if (!token.hasMoreTokens()) {
			return info;
		}


		try {
			Integer.parseInt(token.nextToken());

			StringBuilder sb = new StringBuilder();
			while (token.hasMoreTokens()) {
				sb.append(token.nextToken());
				if (token.hasMoreTokens())
					sb.append(" ");
			}

			return sb.toString();
		} catch (Exception e) {
			return info;
		}
	}

}
