package com.kurento.mediaspec;

public class PayloadUtils {

	protected static Payload intersect(Payload ansPayload, Payload offPayload) {
		if (ansPayload == null || offPayload == null)
			return null;
		Payload intersect = new Payload();
		intersect.rtp = intersect(ansPayload.rtp, offPayload.rtp);

		if (intersect.rtp == null)
			return null;

		return intersect;
	}

	private static PayloadRtp intersect(PayloadRtp answerer, PayloadRtp offerer) {
		if (answerer == null || offerer == null)
			return null;

		if (!answerer.codecName.equalsIgnoreCase(offerer.codecName)
				|| answerer.clockRate != offerer.clockRate) {
			return null;
		}

		PayloadRtp rtp = new PayloadRtp(offerer.id, offerer.codecName,
				offerer.clockRate);

		rtp.framerate = FractionUtils.intersect(answerer.framerate,
				offerer.framerate);
		rtp.channels = selectMinor(answerer.channels, offerer.channels);
		rtp.width = selectMinor(answerer.width, offerer.width);
		rtp.height = selectMinor(answerer.height, offerer.height);
		rtp.bitrate = selectMinor(answerer.bitrate, offerer.bitrate);

		for (String key : offerer.extraParams.keySet()) {
			rtp.extraParams.put(key, offerer.extraParams.get(key));
		}

		for (String key : answerer.extraParams.keySet()) {
			if (!rtp.extraParams.containsKey(key))
				rtp.extraParams.put(key, answerer.extraParams.get(key));
		}

		return rtp;
	}

	private static Integer selectMinor(Integer a, Integer b) {
		if (a != null) {
			if (b != null)
				return a < b ? a : b;
			else
				return a;
		}
		return b;
	}
}
