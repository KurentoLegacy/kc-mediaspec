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
		Integer minor;
		minor = selectMinor(answerer.channels, answerer.isSetChannels(),
				offerer.channels, offerer.isSetChannels());

		if (minor != null)
			rtp.setChannels(minor);

		minor = selectMinor(answerer.width, answerer.isSetChannels(),
				offerer.width, offerer.isSetChannels());

		if (minor != null)
			rtp.setWidth(minor);

		minor = selectMinor(answerer.height, answerer.isSetChannels(),
				offerer.height, offerer.isSetChannels());

		if (minor != null)
			rtp.setHeight(minor);

		minor = selectMinor(answerer.bitrate, answerer.isSetChannels(),
				offerer.bitrate, offerer.isSetChannels());

		if (minor != null)
			rtp.setBitrate(minor);

		if (offerer.isSetExtraParams()) {
			for (String key : offerer.extraParams.keySet()) {
				rtp.putToExtraParams(key, offerer.extraParams.get(key));
			}
		}

		if (answerer.isSetExtraParams()) {
			for (String key : answerer.extraParams.keySet()) {
				if (!rtp.extraParams.containsKey(key))
					rtp.putToExtraParams(key, answerer.extraParams.get(key));
			}
		}

		return rtp;
	}

	private static Integer selectMinor(int a, boolean isSetA, int b,
			boolean isSetB) {
		if (isSetA) {
			if (isSetB)
				return a < b ? a : b;
			else
				return a;
		}
		return isSetB ? b : null;
	}
}
