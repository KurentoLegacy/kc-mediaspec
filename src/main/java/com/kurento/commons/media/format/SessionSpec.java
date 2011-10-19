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

import gov.nist.javax.sdp.fields.SDPFieldNames;
import gov.nist.javax.sdp.fields.SDPKeywords;

import java.io.Serializable;
import java.util.List;
import java.util.Vector;

import javax.sdp.BandWidth;
import javax.sdp.Connection;
import javax.sdp.MediaDescription;
import javax.sdp.Origin;
import javax.sdp.SdpException;
import javax.sdp.SdpFactory;
import javax.sdp.SessionDescription;
import javax.sdp.Version;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;


/**
 * <p>
 * A SessionSpec represents an encapsulation of SessionDescription for hold information about the originator of session 
 * the media types that a client can support and the information of connection.
 * Refer to IETF RFC 2327 for a description of  SDP. 
 * 
 * </p>
 * @see {@link SessionDescription}
 * @see{@link MediaSpec}
 * @author Qiang
 *
 */
public class SessionSpec implements Serializable{


	private static final long serialVersionUID = 1L;
	private static final Log log = LogFactory.getLog(SessionSpec.class);

	private static final String ENDLINE = "\r\n";

	private String remoteHandlerId="";
	private String remoteHandler="";
	private List<MediaSpec> mediaSpec;

	private String sessionName;
	private String userName;
	private String originAddress;
	private long version = 654321;
	private long sessionId = 12345;
	private int sdpVersion;
	Connection connection;
	

	// Value just for debug
	SessionDescription sessionRecived;
	
	

	/**
	 * Constructs an empty instance of SessionSpec.
	 */
	public SessionSpec() {
		mediaSpec = new Vector<MediaSpec>();
		userName="-";
		originAddress = "--";
		version = 654321;
		sessionId = 123456;
	}
	
	/**
	 * Constructs an instance of SessionSpec from a SessionDescription.
	 * 
	 * @param sdp SessionDescription containing the media format information.
	 * @throws SdpException
	 * @see {@link SessionDescription}
	 */
	public SessionSpec(SessionDescription sdp) throws SdpException {
		init(sdp);
	}

	private void init(SessionDescription sdp) throws SdpException {
		mediaSpec = new Vector<MediaSpec>();
		sessionRecived = sdp;

		try {
			/*
			 * Session data
			 */
			Version sessionVersion = sdp.getVersion();
			sdpVersion = sessionVersion.getVersion();
			Origin origen = sdp.getOrigin();
			userName = origen.getUsername() != null ? origen.getUsername() : "";
			sessionId = origen.getSessionId();
			version = origen.getSessionVersion();
			originAddress = origen.getAddress() != null ? origen.getAddress() : "-";
			sdp.getZoneAdjustments(false);

			sessionName = sdp.getSessionName().getValue();
			connection = sdp.getConnection();

			for (Object o : sdp.getAttributes(true)) {
				log.debug("Getting: " + o);
			}

			remoteHandlerId = originAddress;

			remoteHandler = connection.getAddress() != null ? connection.getAddress()
					: "";

			int bandWidth = sdp.getBandwidth(BandWidth.AS);

			@SuppressWarnings("unchecked")
			Vector<MediaDescription> mediaDescriptions = sdp.getMediaDescriptions(true);
			for (MediaDescription media : mediaDescriptions) {
				int mediaBandWidth = media.getBandwidth(BandWidth.AS);
				if (bandWidth >= 0 && (mediaBandWidth <= 0 || mediaBandWidth > bandWidth))
					media.setBandwidth(BandWidth.AS, bandWidth);
				mediaSpec.add(new MediaSpec(media));
			}
		} catch (Exception e) {
			throw new SdpException(e);
		}

	}

	/**
	 * Constructs an instance of SessionSpec from a SessionDescription.
	 * 
	 * @param sdp SessionDescription containing the media format information.
	 * @throws SdpException
	 * @see {@link SessionDescription}
	 */
	public SessionSpec(String sdp) throws SdpException {
		init(SdpFactory.getInstance().createSessionDescription(sdp));
	}

	/**
	 * Creates a SessionDescrition from SessionSpec.
	 * @return
	 * @throws SdpException
	 */
	public SessionDescription getSessionDescription() throws SdpException {
		return SdpFactory.getInstance().createSessionDescription(toString());
	}
	
	/**
	 * Gets the Handler id  which identify the SessionSpec.
	 * @return remoteHandlerId
	 */
	public String getRemoteHandlerId() {
		return remoteHandlerId;
	}
	
	/**
	 * Sets the Handler id  which identify the SessionSpec.
	 * @param remoteHandlerId
	 */
	public void setRemoteHandlerId(String remoteHandlerId) {
		this.remoteHandlerId = remoteHandlerId;
	}
	/**
	 * Gets a list of MediaSpec supported.
	 * @return
	 */
	public List<MediaSpec> getMediaSpec() {
		return mediaSpec;
	}
	
	/**
	 * Sets the list of MediaSpec supported. Old MediaSpec list will be replaced.
	 * @param MediaSpec List. to set
	 */
	public void setMediaSpec(List<MediaSpec> mediaSpec) {
		this.mediaSpec = mediaSpec;
	}

	/**
	 * Inserts a MediaSpec to medias supported list.
	 * @param mediaSpec
	 */
	public void addMediaSpec(MediaSpec mediaSpec) {
		this.mediaSpec.add(mediaSpec);

	}
	
	
	/**
	 * Sets Session name.
	 * @param name
	 */
	public void setSessionName(String name){
		sessionName = name;
	}
	
	/**
	 * Gets session name
	 * @return
	 */
	public String getSessionName(){
		return sessionName;
	}
	
	/**
	 * Sets session version.
	 * @param version
	 */
	public void setVersion(long version) {
		this.version = version;
	}

	/**
	 * Gets session version.
	 * @return
	 */
	public long getVersion() {
		return version;
	}
	
	/**
	 * Gets Origin session Id
	 * @return
	 */
	public long getSessionId() {
		return sessionId;
	}

	/**
	 * Sets origin session Id
	 * @param sessionId
	 */
	public void setSessionId(long sessionId) {
		this.sessionId = sessionId;
	}
	
	/**
	 * Gets origin address.
	 * @return
	 */
	public String getOriginAddress() {
		return originAddress;
	}

	/**
	 * Sets origin address.
	 * @param originLocation
	 */
	public void setOriginAddress(String originLocation) {
		this.originAddress = originLocation;
	}

	/**
	 * Gets user who created the session.
	 * @return
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * Sets user who created the session.
	 * @return
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	public String getRemoteHandler() {
		return remoteHandler;
	}

	public void setRemoteHandler(String remoteHandler) {
		this.remoteHandler = remoteHandler;
	}

	@Override
	public String toString(){
		String value = SDPFieldNames.PROTO_VERSION_FIELD + this.sdpVersion + ENDLINE
				+ SDPFieldNames.ORIGIN_FIELD + this.userName + " " + sessionId + " "
				+ version + " " + SDPKeywords.IN + " " + SDPKeywords.IPV4 + " "
				+ originAddress + ENDLINE + SDPFieldNames.SESSION_NAME_FIELD
				+ sessionName + ENDLINE + SDPFieldNames.CONNECTION_FIELD + SDPKeywords.IN
				+ " " + SDPKeywords.IPV4 + " " + this.remoteHandler + ENDLINE
				+ SDPFieldNames.TIME_FIELD + "0 0" + ENDLINE;

		for (MediaSpec media: mediaSpec){
			value=value+media.toString();
		}

		return value;
	}
	
	@Override 
	public Object clone(){
		SessionSpec clone = new SessionSpec();

		clone.setVersion(version);
		clone.setRemoteHandlerId(remoteHandlerId);
		clone.setRemoteHandler(remoteHandler);
		clone.setOriginAddress(originAddress);
		clone.setSessionId(sessionId);
		clone.setSessionName(sessionName);
		clone.setVersion(sdpVersion);
		clone.setUserName(userName);
		List<MediaSpec> mediaList = new Vector<MediaSpec>();
		for (MediaSpec media : mediaSpec) {
			mediaList.add((MediaSpec) media.clone());
		}

		clone.setMediaSpec(mediaList);
		return clone;
	
	}	
}
