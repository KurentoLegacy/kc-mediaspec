package com.kurento.mediaspec;

import java.io.Serializable;


/**
 * 
 * This class provides a description mechanism for RTMP transport channels. From
 * Kurento point of view RTMP is just transport mechanism analogous to RTP,
 * where the transport address takes the form of an RTMP URL. Play attribute
 * indicates local descriptors the reception URL and Publish attribute provides
 * the transmission URL in remote descriptors. Notice that local and remote
 * descriptor role depends on the peer.
 * 
 */
public class TransportRtmp implements Serializable {

	private static final long serialVersionUID = 1L;

	private String url;
	private String play;
	private String publish;

	public TransportRtmp() {
	}

	/**
	 * Creates an RTMP transport initialized with a duplicate of the specified
	 * parameter.
	 * 
	 * @param rtmp
	 *            RTMP transport to be cloned.
	 */
	public TransportRtmp(TransportRtmp rtmp) {
		url = rtmp.url;
		play = rtmp.play;
		publish = rtmp.publish;
	}

	/**
	 * Returns the RTMP server URL (transport address).
	 * 
	 * @return RTMP server URL.
	 * @throws ArgumentNotSetException
	 *             If argument hasn't been initialized.
	 */
	public String getUrl() throws ArgumentNotSetException {
		if (url == null)
			throw new ArgumentNotSetException("url attribute is not set");
		return url;
	}

	/**
	 * Sets the RTMP server URL.
	 * 
	 * @param url
	 *            RTMP server URL to be set.
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Returns reception stream ID for local descriptors. Its value does not
	 * make sense for remote descriptors.
	 * 
	 * @return RTMP stream id.
	 * @throws ArgumentNotSetException
	 *             If argument hasn't been initialized.
	 */
	public String getPlay() throws ArgumentNotSetException {
		if (play == null)
			throw new ArgumentNotSetException("play attribute is not set");
		return play;
	}

	/**
	 * Set the stream id for media reception in local descriptors.
	 * 
	 * @param play
	 *             ID of reception stream.
	 */
	public void setPlay(String play) {
		this.play = play;
	}

	/**
	 * Return the stream id for media transmission in remote descriptors. Its
	 * value is of no use for local descriptors.
	 * 
	 * @return Stream id for media transmission.
	 * @throws ArgumentNotSetException
	 *             If argument hasn't been initialized.
	 */
	public String getPublish() throws ArgumentNotSetException {
		if (publish == null)
			throw new ArgumentNotSetException("publish attribute is not set");
		return publish;
	}

	/**
	 * Set the stream id for media transmission in remote descriptors.
	 * 
	 * @param publish
	 *             Transmission stream id.
	 */
	public void setPublish(String publish) {
		this.publish = publish;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("TransportRtmp [");
		if (url != null) {
			builder.append("url=");
			builder.append(url);
			builder.append(", ");
		}
		if (play != null) {
			builder.append("play=");
			builder.append(play);
			builder.append(", ");
		}
		if (publish != null) {
			builder.append("publish=");
			builder.append(publish);
		}
		builder.append("]");
		return builder.toString();
	}

	protected static TransportRtmp[] instersect(TransportRtmp answerer,
			TransportRtmp offerer) {
		TransportRtmp neg_answ = new TransportRtmp(answerer);
		TransportRtmp neg_off = new TransportRtmp(offerer);

		if (neg_off.publish != null)
			neg_answ.play = neg_off.publish;
		else
			return null;

		if (neg_answ.publish != null)
			neg_off.play = neg_answ.publish;
		else
			return null;

		if (neg_off.url != null) {
			neg_answ.url = neg_off.url;
		} else if (neg_answ.url != null) {
			neg_off.url = neg_answ.url;
		} else {
			return null;
		}

		return new TransportRtmp[] { neg_answ, neg_off };
	}

}
