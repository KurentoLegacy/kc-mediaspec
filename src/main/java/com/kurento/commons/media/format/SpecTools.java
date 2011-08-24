package com.kurento.commons.media.format;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.Vector;

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
	
	public static SessionSpec intersectionSessionSpec(SessionSpec session1, SessionSpec session2){
		List<MediaSpec> inputList = session1.getMediaSpec();
		List<MediaSpec> resourceList = session2.getMediaSpec();
		SessionSpec value = getCopyWithSessionInfo(session1);
		
		List<MediaSpec> resultList = new Vector<MediaSpec>();	
		MediaSpec media = null;
		for(MediaSpec media1 : inputList){
			for (MediaSpec media2:resourceList ) {
				media = media2.intersecPayload(media1, false);
				if (media != null && !resultList.contains(media)) {
					resultList.add(media);
				}
			}
		}
		value.setMediaSpec(resultList);
		return  value;		

	}	

}
