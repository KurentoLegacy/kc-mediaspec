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

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Vector;

import javax.sdp.SdpException;

import com.kurento.commons.sdp.enums.MediaType;
import com.kurento.commons.sdp.enums.Mode;

public class SpecTools {
	
	public static SessionSpec joinSessionSpec(SessionSpec session1, SessionSpec session2){
	 List<MediaSpec> media1 = session1.getMediaSpec();
	 List<MediaSpec> media2 = session2.getMediaSpec();
	 Set<MediaSpec> mixedMedia = new HashSet<MediaSpec>(media1);
	 mixedMedia.addAll(media2);
	 
	 List<MediaSpec> mixedList = new ArrayList<MediaSpec>(mixedMedia);
	 SessionSpec spec = (SessionSpec) session1.clone();
	 spec.setMediaSpec(mixedList);
		return spec;
	}	
	
	public static Collection<MediaType> getMediaTypesFromSession(SessionSpec session) {
		Set<MediaType> mediaTypes = new HashSet<MediaType>();
		for (MediaSpec media : session.getMediaSpec()){
			mediaTypes.add(media.getMediaType());
		}
		return mediaTypes;
	}
	
	public static SessionSpec filterMediaByType(SessionSpec session, String etype){
		 List<MediaSpec> mediaList = new ArrayList<MediaSpec>();
		 MediaType type  = MediaType.getInstance(etype);
		for (MediaSpec media : session.getMediaSpec()){
			if (media.getMediaType() == type)
			mediaList.add(media);
		}
		 SessionSpec spec = (SessionSpec) session.clone();
		 spec.setMediaSpec(mediaList);
		return spec;			
	}
	
	public static PayloadSpec getPayloadByName(MediaSpec media, String encodingName){
		List<PayloadSpec> payloadlist = media.getPayloadList();
		for (PayloadSpec  payload : payloadlist) {
			if (encodingName.equalsIgnoreCase(payload.getEncodingName())){
				return payload;
			}
					
		}
		return null;
	}
	
	public static SessionSpec getCopyWithSessionInfo(SessionSpec original) {
		SessionSpec clone = new SessionSpec();

		clone.setRemoteHandlerId(original.getRemoteHandlerId());
		clone.setRemoteHandler(original.getRemoteHandler());
		clone.setOriginAddress(original.getOriginAddress());
		clone.setSessionId(original.getSessionId());
		clone.setSessionName(original.getSessionName());
		clone.setVersion(original.getVersion());
		clone.setUserName(original.getUserName());
		return clone;
	}
	
	/**
	 * Merge two sessionSpecs in order to do the negotiation. The
	 * 
	 * @param answerer
	 *            The answerer session spec offer
	 * @param offerer
	 *            The original session spec offer
	 * @return An array of new created session spec with the negotiated medias.
	 *         The first one is the new answer spec and the second the offer
	 *         just with the intersected medias.
	 */
	public static SessionSpec[] intersectSessionSpec(SessionSpec answerer, SessionSpec offerer) {
		List<MediaSpec> offererList = offerer.getMediaSpec();
		List<MediaSpec> answererList = answerer.getMediaSpec();
		SessionSpec newAnswererSpec = getCopyWithSessionInfo(offerer);
		SessionSpec newOffererSpec = getCopyWithSessionInfo(offerer);
		List<MediaSpec> newAnswererSpecList = new Vector<MediaSpec>();
		List<MediaSpec> newOffererSpecList = new Vector<MediaSpec>();
		List<MediaSpec> usedMedias = new Vector<MediaSpec>();

		newAnswererSpec.setRemoteHandler(answerer.getRemoteHandler());
		newAnswererSpec.setOriginAddress(answerer.getOriginAddress());

		MediaSpec answererMedia, offererMedia;
		for (MediaSpec media1 : offererList) {
			answererMedia = null;
			offererMedia = null;
			for (MediaSpec media2 : answererList) {
				if (media1.getMediaType() != media2.getMediaType() || usedMedias.contains(media2))
					continue;

				answererMedia = media2.intersecPayload(media1, false);
				offererMedia = (MediaSpec) answererMedia.clone();
				changeMode(offererMedia);
				offererMedia.setPort(media1.getPort());

				if (answererMedia.getPort() != 0) {
					usedMedias.add(media2);
					break;
				}
			}

			if (answererMedia == null) {
				MediaSpec ms = new MediaSpec();
				try {
					ms.setMediaType(media1.getMediaType());
				} catch (SdpException e) {
					e.printStackTrace();
				}
				answererMedia = ms.intersecPayload(media1, false);
				offererMedia = (MediaSpec) answererMedia.clone();
			}

			newAnswererSpecList.add(answererMedia);
			newOffererSpecList.add(offererMedia);
		}
		newAnswererSpec.setMediaSpec(newAnswererSpecList);
		newOffererSpec.setMediaSpec(newOffererSpecList);
		return new SessionSpec[] { newAnswererSpec, newOffererSpec };

	}

	private static void changeMode(MediaSpec spec) {
		if (spec.getMode() == Mode.SENDONLY)
			spec.setMode(Mode.RECVONLY);
		else if (spec.getMode() == Mode.RECVONLY)
			spec.setMode(Mode.SENDONLY);
	}
	
	public static Map<MediaType, Mode> getModesOfFirstMediaTypes(
			SessionSpec session) {
		Map<MediaType, Mode> map = new HashMap<MediaType, Mode>();
		for (MediaSpec media : session.getMediaSpec()) {
			if (!map.containsValue(media.getMediaType()))
				map.put(media.getMediaType(), media.getMode());
		}
		return map;
	}

}
