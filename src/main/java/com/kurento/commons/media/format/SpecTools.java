package com.kurento.commons.media.format;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import javax.sdp.SdpException;

import com.kurento.commons.sdp.enums.MediaType;

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
	 * Merge two sessionSpecs in order to do the negotiation. This function
	 * works when the local party is the acceptor.
	 * 
	 * @param remote
	 *            Remote session spec
	 * @param local
	 *            Local Session spec
	 * @return A new created session spec with the negotiated medias
	 */
	public static SessionSpec intersectionSessionSpec(SessionSpec remote, SessionSpec local) {
		List<MediaSpec> remoteList = remote.getMediaSpec();
		List<MediaSpec> localList = local.getMediaSpec();
		SessionSpec newSpec = getCopyWithSessionInfo(remote);
		List<MediaSpec> newSpecList = new Vector<MediaSpec>();
		List<MediaSpec> usedMedias = new Vector<MediaSpec>();

		newSpec.setRemoteHandler(local.getRemoteHandler());
		newSpec.setOriginAddress(local.getOriginAddress());

		MediaSpec media = null;
		for (MediaSpec media1 : remoteList) {
			media = null;
			for (MediaSpec media2 : localList) {
				if (media1.getMediaType() != media2.getMediaType() || usedMedias.contains(media2))
					continue;

				media = media2.intersecPayload(media1, false);
				if (media.getPort() != 0) {
					usedMedias.add(media2);
					break;
				}
			}

			if (media != null) {
				newSpecList.add(media);
			} else {
				MediaSpec ms = new MediaSpec();
				try {
					ms.setMediaType(media1.getMediaType());
				} catch (SdpException e) {
					e.printStackTrace();
				}
				newSpecList.add(ms.intersecPayload(media1, false));
			}
		}
		newSpec.setMediaSpec(newSpecList);
		return newSpec;

	}

}
