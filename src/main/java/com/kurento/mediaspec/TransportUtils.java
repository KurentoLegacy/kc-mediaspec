package com.kurento.mediaspec;

public class TransportUtils {

	protected static Transport[] intersect(Transport answerer, Transport offerer) {
		Transport neg_answ = new Transport(answerer);
		Transport neg_off = new Transport(offerer);


		TransportRtmp answ_rtmp = neg_answ.getRtmp();
		TransportRtmp off_rtmp = neg_off.getRtmp();

		TransportRtmp[] rtmps = instersect(answ_rtmp, off_rtmp);

		if (rtmps != null) {
			neg_answ.rtmp = rtmps[0];
			neg_off.rtmp = rtmps[1];
		} else {
			neg_answ.rtmp = null;
			neg_off.rtmp = null;
		}

		if (!neg_answ.isSetIce())
			neg_off.setIceIsSet(false);

		if (!neg_off.isSetIce())
			neg_answ.setIceIsSet(false);

		if (neg_answ.rtmp == null && neg_answ.rtp == null
				&& neg_answ.ice == null || neg_off.rtmp == null
				&& neg_off.rtp == null && neg_off.ice == null)
			return null;

		return new Transport[] { neg_answ, neg_off };
	}

	private static TransportRtmp[] instersect(TransportRtmp answerer,
			TransportRtmp offerer) {
		TransportRtmp neg_answ;
		TransportRtmp neg_off;

		if (answerer != null)
			neg_answ = new TransportRtmp(answerer);
		else
			neg_answ = new TransportRtmp();

		if (offerer != null)
			neg_off = new TransportRtmp(offerer);
		else
			neg_off = new TransportRtmp();

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
