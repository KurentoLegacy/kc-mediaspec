package com.kurento.commons.media.format;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
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
	 * Merge two sessionSpecs in order to do the negotiation. This function
	 * works when the local party is the acceptor.
	 * 
	 * @param remote
	 *            Remote session spec
	 * @param local
	 *            Local Session spec
	 * @return An array of new created session spec with the negotiated medias.
	 *         The first one is the local spec and the second the remote.
	 */
	public static SessionSpec[] intersectionSessionSpec(SessionSpec remote, SessionSpec local) {
		List<MediaSpec> remoteList = remote.getMediaSpec();
		List<MediaSpec> localList = local.getMediaSpec();
		SessionSpec newLocalSpec = getCopyWithSessionInfo(remote);
		SessionSpec newRemoteSpec = getCopyWithSessionInfo(remote);
		List<MediaSpec> newLocalSpecList = new Vector<MediaSpec>();
		List<MediaSpec> newRemoteSpecList = new Vector<MediaSpec>();
		List<MediaSpec> usedMedias = new Vector<MediaSpec>();

		newLocalSpec.setRemoteHandler(local.getRemoteHandler());
		newLocalSpec.setOriginAddress(local.getOriginAddress());

		MediaSpec localMedia, remoteMedia;
		for (MediaSpec media1 : remoteList) {
			localMedia = null;
			remoteMedia = null;
			for (MediaSpec media2 : localList) {
				if (media1.getMediaType() != media2.getMediaType() || usedMedias.contains(media2))
					continue;

				localMedia = media2.intersecPayload(media1, false);
				remoteMedia = (MediaSpec) localMedia.clone();
				changeMode(remoteMedia);
				remoteMedia.setPort(media1.getPort());

				if (localMedia.getPort() != 0) {
					usedMedias.add(media2);
					break;
				}
			}

			if (localMedia == null) {
				MediaSpec ms = new MediaSpec();
				try {
					ms.setMediaType(media1.getMediaType());
				} catch (SdpException e) {
					e.printStackTrace();
				}
				localMedia = ms.intersecPayload(media1, false);
				remoteMedia = (MediaSpec) localMedia.clone();
			}

			newLocalSpecList.add(localMedia);
			newRemoteSpecList.add(remoteMedia);
		}
		newLocalSpec.setMediaSpec(newLocalSpecList);
		newRemoteSpec.setMediaSpec(newRemoteSpecList);
		return new SessionSpec[] { newLocalSpec, newRemoteSpec };

	}

	private static void changeMode(MediaSpec spec) {
		if (spec.getMode() == Mode.SENDONLY)
			spec.setMode(Mode.RECVONLY);
		else if (spec.getMode() == Mode.RECVONLY)
			spec.setMode(Mode.SENDONLY);
	}

}
