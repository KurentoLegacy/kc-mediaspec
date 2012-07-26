package com.kurento.mediaspec;

import java.util.ArrayList;
import java.util.List;
import java.util.Vector;

public class SessionSpecUtils {

	/**
	 * Method <code>intersect</code> is the basis to the SessionSpec negotiation
	 * process. It takes two parameters: <code>answerer</code> and
	 * <code>offerer</code> and returns a <code>SessionSpec</code> array with
	 * the intersected answerer (index 0) and offerer (index 1). Answerer always
	 * represents the local descriptor while offerer is the remote one. Notice
	 * this method must be executed by caller and called party, so it is
	 * important not to associate offerer with caller and answerer with called,
	 * as they relate to remote and local respectively and the same descriptor
	 * swaps roles (remote/local) depending on the peer.
	 * 
	 * In addition, this method also enforces following set of negotiation rules
	 * inherited from SDP negotiation:
	 * <ul>
	 * <li>Answerer must contain a response for each offerer channel. Media
	 * channel is declared <code>INCATIVE</code> if peer does not support
	 * specified media type.
	 * <li>Answerer must not include channels or formats not present at offerer.
	 * </ul>
	 * 
	 * Multimedia negotiation procedures might vary, but it will contain at
	 * least following steps:
	 * 
	 * <ul>
	 * <li>Caller party generates an offer descriptor announcing its multimedia
	 * capabilities.
	 * <li>Upon reception, called party assigns received offer to.
	 * <code>offerer</code> and its own capabilities descriptor to
	 * <code>answerer</code>. Returned answerer is sent back to caller and
	 * becomes the local session descriptor of called party. Returned offered
	 * becomes the remote descriptor of called party.
	 * <li>Caller receives answer from called and performs its own intersection,
	 * assigning the offer descriptor (sent to called party) to
	 * <code>answered</code> and received answer from called to
	 * <code>offerer</code>. Returned answerer becomes the caller local
	 * descriptor while returned offerer is the caller remote descriptor
	 * </ul>
	 * After negotiation local and remote descriptors on each party must be
	 * Interchangeable. i.e. caller's local descriptor is identical to called's
	 * remote descriptor and vice versa.
	 * 
	 * @param answerer
	 *            Session descriptor from local party to be intersected. Local
	 *            can be caller or called, depending where the operation is
	 *            executed.
	 * @param offerer
	 * 
	 *            Session descriptor from remote party to be intersected. Remote
	 *            can be caller or called, depending where the operation is
	 *            executed.
	 * 
	 * @return SessionSpec array containing intersected answerer (index 0) and
	 *         offerer (index 1) descriptors, respectively
	 */
	public static SessionSpec[] intersect(SessionSpec answerer,
			SessionSpec offerer) {
		List<MediaSpec> offererList = offerer.getMedias();
		List<MediaSpec> answererList = answerer.getMedias();
		List<MediaSpec> newAnswererSpecList = new Vector<MediaSpec>();
		List<MediaSpec> newOffererSpecList = new Vector<MediaSpec>();
		List<MediaSpec> usedMedias = new Vector<MediaSpec>();

		MediaSpec answererMedia = null;
		MediaSpec offererMedia = null;
		for (MediaSpec ofMedia : offererList) {
			MediaSpec[] medias = null;

			for (MediaSpec ansMedia : answererList) {
				if (!(ofMedia.getType().containsAll(ansMedia.getType()))
						|| usedMedias.contains(ansMedia))
					continue;

				medias = MediaSpecUtils.intersect(ansMedia, ofMedia);

				if (medias != null) {
					usedMedias.add(ansMedia);
					break;
				}
			}

			if (medias == null) {
				answererMedia = new MediaSpec(new ArrayList<Payload>(),
						ofMedia.getType(), new Transport(), Direction.INACTIVE);
				offererMedia = new MediaSpec(new ArrayList<Payload>(),
						ofMedia.getType(), new Transport(), Direction.INACTIVE);
			} else {
				answererMedia = medias[0];
				offererMedia = medias[1];
			}

			if (answererMedia != null)
				newAnswererSpecList.add(answererMedia);
			if (offererMedia != null)
				newOffererSpecList.add(offererMedia);
		}

		SessionSpec newAnswererSpec = new SessionSpec(newAnswererSpecList,
				offerer.getId());

		if (answerer.isSetVersion())
			newAnswererSpec.setVersion(answerer.getVersion());

		SessionSpec newOffererSpec = new SessionSpec(newOffererSpecList,
				offerer.getId());

		if (offerer.isSetVersion())
			newOffererSpec.setVersion(offerer.getVersion());

		return new SessionSpec[] { newAnswererSpec, newOffererSpec };
	}
}
