package com.tikal.media.format;

import gov.nist.javax.sdp.MediaDescriptionImpl;
import gov.nist.javax.sdp.fields.AttributeField;
import gov.nist.javax.sdp.fields.MediaField;
import gov.nist.javax.sdp.fields.SDPFieldNames;
import gov.nist.javax.sip.header.Protocol;

import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

import javax.sdp.Media;
import javax.sdp.MediaDescription;
import javax.sdp.SdpConstants;
import javax.sdp.SdpException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.tikal.sdp.enums.MediaType;
import com.tikal.sdp.enums.Mode;


/**
 * <p>
 * A MediaSpec represents an encapsulation of MediaDescrition. It identifies a
 * media and includes:
 * </p>
 * <ul>
 * <li>MediaType: Audio, Video, etc</li>
 * <li>Media encoding:PCMU,PCMA,H263, etc</li>
 * <li>Encoding clock rate</li>
 * <li>Payload associated with the encoding</li>
 * <li>Port</li>
 * <li>Protocol</li>
 * </ul>
 * <p>
 * Refer to IETF RFC 2327 for a description of SDP.
 * </p>
 * 
 * @see {@link PayloadSpec}
 * @see {@link SessionSpec}
 * @author Qiang
 */
public class MediaSpec {
	private static Log log = LogFactory.getLog(MediaSpec.class);

	private static final String ENDLINE = "\r\n";

	List<PayloadSpec> payloadList;

	// Protocol used for the transmission, now just RTP/AVP;
	Protocol protocol;

	Mode mode;

	/**
	 * Create a empty instance of MediaSpec.
	 */
	public MediaSpec() {
		payloadList = new Vector<PayloadSpec>();
		protocol = getDefaultProtocol();

	}

	/**
	 * Creates a MediaSpec from a MediaDescription.
	 */
	@SuppressWarnings("unchecked")
	public MediaSpec(MediaDescription md) throws SdpException {
		Media media = md.getMedia();
		protocol = getDefaultProtocol();
		Vector<AttributeField> atributeList = md.getAttributes(false);
		payloadList = new Vector<PayloadSpec>();

		for (Object format : media.getMediaFormats(false)) {
			try {
				int payId = PayloadSpec.getPayloadFromString((String) format);

				if (payId < 96) {
					PayloadSpec payload = new PayloadSpec(payId);
					payload.setPort(media.getMediaPort());
					payload.setMediaType(MediaType.getInstance(media.getMediaType()));
					payloadList.add(payload);
				}
			} catch (SdpException e) {
			}
		}

		if (atributeList != null && !atributeList.isEmpty()) {
			for (AttributeField field : atributeList) {
				String name = field.getName();
				PayloadSpec payload = null;
				Mode mode = Mode.getInstance(name);
				if (SdpConstants.RTPMAP.equalsIgnoreCase(name)) {
					payload = getPayloadById(payloadList,
							PayloadSpec.getPayloadFromString(field.getValue()));

					if (payload == null) {
						payload = new PayloadSpec(field.getValue());
						payload.setPort(media.getMediaPort());
						payload.setMediaType(MediaType.getInstance(media.getMediaType()));
						payloadList.add(payload);
					} else {
						payload.setRtpMap(field.getValue());
					}

				} else if (SdpConstants.FMTP.equalsIgnoreCase(name)) {
					payload = getPayloadById(payloadList,
							PayloadSpec.getPayloadFromString(field.getValue()));
					if (payload != null)
						payload.setFormatParams(PayloadSpec.removePayloadFromString(field
								.getValue()));
				} else if (mode != null) {
					this.mode = mode;
				} else {
					log.debug("Media attribute ingnored: " + field.getName());
				}
			}
		}

	}

	private PayloadSpec getPayloadById(List<PayloadSpec> list, int id) {
		for (PayloadSpec pay : list) {
			if (pay.getPayload() == id)
				return pay;
		}

		return null;
	}

	// API

	/**
	 * Creates a MediaDescription from MediaSpec
	 * 
	 * @return MediaDescription
	 * @throws SdpException
	 */
	public MediaDescription getMediaDescription() throws SdpException {
		MediaDescription mediaDs = new MediaDescriptionImpl();
		Media media = new MediaField();
		PayloadSpec payloadTemplate = null;
		Vector<Integer> payloadAcepted = new Vector<Integer>();

		if (payloadList != null && !payloadList.isEmpty()) {
			payloadTemplate = payloadList.get(0);
			media.setMediaPort(payloadTemplate.getPort());
			media.setMediaType(payloadTemplate.getMediaType().toString());
			media.setProtocol(SdpConstants.RTP_AVP);
			for (PayloadSpec payload : payloadList) {
				payloadAcepted.add(payload.getPayload());
				String mediaAtr = generateMediaSpecification(payload.getPayload(), 
						payload.getEncodingName(), payload.getClockRate());
				if (mediaAtr != "") {
					AttributeField attrField = new AttributeField();
					attrField.setName(SdpConstants.RTPMAP);
					attrField.setValue(mediaAtr);
					mediaDs.addAttribute(attrField);
				}

			}
		}
		media.setMediaFormats(payloadAcepted);
		mediaDs.setMedia(media);
		return mediaDs;
	}

	/**
	 * Returns the protocol of transmission.
	 * 
	 * @return
	 */
	public String getProtocol() {
		return protocol.toString();
	}

	/**
	 * Gets a list of payload that the media supports.
	 * @return
	 */
	public List<PayloadSpec> getPayloadList() {
		return payloadList;
	}

	/**
	 * 
	 * @param payloadList
	 */
	public void setPayloadList(List<PayloadSpec> payloadList) {
		this.payloadList = payloadList;
	}

	/**
	 * It set the protocol value. String format : (protocolName/protocolVersion)
	 * Example: RTP/AVP
	 * 
	 * @param protocol
	 * @throws ParseException
	 */
	public void setProtocol(String value) throws ParseException {
		protocol = new Protocol();
		protocol.setProtocol(value);
	}	
	
	/**
	 * Gets port that the media may be  received.
	 * @return
	 */
	public int getPort(){
		if (payloadList.isEmpty()) {
			return -1;
		}
		return payloadList.get(0).getPort();
	}
	
	/**
	 * Sets port that the media may be  received.
	 * @return
	 */
	public void  setPort(int port) {
		for (PayloadSpec payload : payloadList){
			payload.setPort(port);
		}
	}
	
	public MediaType getMediaType(){
		if (payloadList.isEmpty()) {
			return null;
		}
		return payloadList.get(0).getMediaType();
	}

	public Mode getMode() {
		if (mode == null)
			return Mode.values()[0];
		return mode;
	}

	public void setMode(Mode mode) {
		this.mode = mode;
	}
	
	public MediaSpec intersecPayload(MediaSpec media, boolean changePayload) {
		MediaSpec cmpObj = null;	
		//List<PayloadSpec> intersecList = new ArrayList<PayloadSpec>(media.getPayloadList());
		List<PayloadSpec> intersecList = new ArrayList<PayloadSpec>();
		int payloadNum = 0;
		for (PayloadSpec payload : media.getPayloadList()) {
			int index = payloadList.indexOf(payload);
			if (index >= 0) {
				if (changePayload) {
					payloadNum = payloadList.get(index).getPayload();
					payload.setPayload(payloadNum);
				}
				intersecList.add(payload);
			}
		}
		if (!intersecList.isEmpty()) {
			//if (changePayload){
			cmpObj	= new MediaSpec();
			cmpObj.setPayloadList(intersecList);
		}
		return cmpObj;
	}

	@Override
	public boolean equals(Object obj) {
		boolean equalValue = true;
		MediaSpec cmpObj = (MediaSpec) obj;
		List<PayloadSpec> cmpPayloadList = cmpObj.getPayloadList();
		if (payloadList.size() != cmpPayloadList.size()) {
			equalValue = false;
		}
		for (PayloadSpec payload : payloadList){
			if (!cmpPayloadList.contains(payload)) {
				equalValue = false;
			}
		}
		return equalValue;
	}
	
	@Override
	public Object clone() {		
		MediaSpec obj = new MediaSpec();
		obj.setMode(mode);
		List<PayloadSpec> objList = new Vector<PayloadSpec>();		
		for (PayloadSpec payload : payloadList){
			objList.add((PayloadSpec)payload.clone());
		}
		obj.setPayloadList(objList);
		return obj;
	}


	public String toString(){
		StringBuffer string = new StringBuffer();
		string.append(SDPFieldNames.MEDIA_FIELD)
		.append(getMediaType()).append(" ")
		.append(getPort()).append(" ").append(SdpConstants.RTP_AVP);
		StringBuffer payLoadstring = new StringBuffer();
		for (PayloadSpec payload:payloadList){			
			string.append(" ").append(payload.getPayload());
			payLoadstring.append(payload.toString());
		}
		string.append(ENDLINE);
		string.append(payLoadstring);

		if (mode != null)
			string.append(SDPFieldNames.ATTRIBUTE_FIELD).append(mode).append(ENDLINE);
		return string.toString();			   
		
	}
	
	
	/*----------------------------------------------------------------------------------------------------------------
	 * Private Methods
	 * ----------------------------------------------------------------------------------------------------------------
	 */
	private String generateMediaSpecification(Integer payload,
			String encodingName, Integer clockRate) {
		if (encodingName == null ) return "";
		StringBuffer specification = new StringBuffer();
		specification.append(payload).append(" ");
		specification.append(encodingName);
		if (clockRate != null)
			specification.append("/").append(clockRate);
		return specification.toString();
	}
	private Protocol getDefaultProtocol() {
		Protocol protocol = new Protocol();
		try {
			protocol.setProtocol(SdpConstants.RTP_AVP);
		} catch (ParseException e) {
			log.error("Error creating RTP/AVP protocol");
		}
		return protocol;
	}


}
