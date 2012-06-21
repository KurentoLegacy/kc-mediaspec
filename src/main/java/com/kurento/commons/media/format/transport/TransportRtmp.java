package com.kurento.commons.media.format.transport;

import java.io.Serializable;

import com.kurento.commons.media.format.exceptions.ArgumentNotSetException;

/**
 * 
 * This class provides a description mechanism for RTMP transport channels. From
 * Kurento point of view RTMP is just transport mechanism analogous to RTP,
 * where the transport address takes the form of an RTMP URL. URL of RTMP
 * transport descriptors included in local SessionSpec will be regarded as
 * reception transport address, i.e. the URL used to receive media (play) from
 * the transport network (RTMP server). URL of RTMP transport descriptor
 * included in remote SessionSpec will be regarded as transmission transport
 * address, i.e. the URL used to emit media (publish) to the transport network
 * (RTMP server)
 * 
 */
public class TransportRtmp implements Serializable {

	private static final long serialVersionUID = 1L;

	private String url;
	private String play;
	private String publish;

	/**
	 * This constructor should not be used, just for serialization
	 */
	// TODO: Change visibility of this method to private or protected
	public TransportRtmp() {
	}

	/**
	 * Creates an RTMP transport cloned from the one given as parameter
	 * 
	 * @param rtmp
	 *            RTMP transport to be cloned
	 */
	public TransportRtmp(TransportRtmp rtmp) {
		url = rtmp.url;
		play = rtmp.play;
		publish = rtmp.publish;
	}

	/**
	 * Returns the RTMP server URL (transport address)
	 * 
	 * @return RTMP server URL
	 * @throws ArgumentNotSetException
	 *             If argument has not been set previously
	 */
	public String getUrl() throws ArgumentNotSetException {
		if (url == null)
			throw new ArgumentNotSetException("url attribute is not set");
		return url;
	}

	/**
	 * Sets the RTMP server URL
	 * 
	 * @param url
	 *            RTMP server URL to be set
	 */
	public void setUrl(String url) {
		this.url = url;
	}

	/**
	 * Returns reception stream ID for local descriptors. Its value does not make send for remote descriptors. 
	 * 
	 * @return RTMP stream id
	 * @throws ArgumentNotSetException
	 *             If this parameter has not been set previously
	 */
	public String getPlay() throws ArgumentNotSetException {
		if (play == null)
			throw new ArgumentNotSetException("play attribute is not set");
		return play;
	}

	/**
	 * Sets the stream id for media reception in local descriptors
	 * 
	 * @param play
	 *            ID of reception stream
	 */
	public void setPlay(String play) {
		this.play = play;
	}

	/**
	 * Returns the stream id for media transmission in remote descriptors. Its value is of no use for local descriptors
	 * 
	 * @return Stream id for media transmission
	 * @throws ArgumentNotSetException
	 *             If this argument has not been set previously
	 */
	public String getPublish() throws ArgumentNotSetException {
		if (publish == null)
			throw new ArgumentNotSetException("publish attribute is not set");
		return publish;
	}

	/**
	 * Sets the stream id for media transmission in remote descriptors.
	 * 
	 * @param publish
	 *            transmission stream id
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

	//TODO: Change method visibility to private / protected
	public static TransportRtmp[] instersect(TransportRtmp answerer,
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
