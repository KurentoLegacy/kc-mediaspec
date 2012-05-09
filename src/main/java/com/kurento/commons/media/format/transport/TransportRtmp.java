package com.kurento.commons.media.format.transport;

import java.io.Serializable;

import com.kurento.commons.media.format.exceptions.ArgumentNotSetException;

public class TransportRtmp implements Serializable {

	private static final long serialVersionUID = 1L;

	private String url;
	private String play;
	private String publish;

	/**
	 * This constructor should not be used, just for serialization
	 */
	public TransportRtmp() {
	}

	public TransportRtmp(TransportRtmp rtmp) {
		url = rtmp.url;
		play = rtmp.play;
		publish = rtmp.publish;
	}

	public String getUrl() throws ArgumentNotSetException {
		if (url == null)
			throw new ArgumentNotSetException("url attribute is not set");
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public String getPlay() throws ArgumentNotSetException {
		if (play == null)
			throw new ArgumentNotSetException("play attribute is not set");
		return play;
	}

	public void setPlay(String play) {
		this.play = play;
	}

	public String getPublish() throws ArgumentNotSetException {
		if (publish == null)
			throw new ArgumentNotSetException("publish attribute is not set");
		return publish;
	}

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
