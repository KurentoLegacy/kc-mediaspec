package com.kurento.mediaspec;

import java.util.ArrayList;

public class MediaSpecUtils {

	protected static boolean checkTransportCompatible(MediaSpec answerer,
			MediaSpec offerer) {
		boolean ret = false;

		if (answerer.getTransport().isSetRtmp()
				&& offerer.getTransport().isSetRtmp())
			ret = answerer.getTransport().getRtmp() != null
					&& offerer.getTransport().getRtmp() != null;

		if (answerer.getTransport().isSetRtp()
				&& offerer.getTransport().isSetRtp())
			ret = ret || answerer.getTransport().getRtp() != null
					&& offerer.getTransport().getRtp() != null;

		return ret;
	}

	protected static MediaSpec[] intersect(MediaSpec answerer, MediaSpec offerer) {
		ArrayList<Payload> answererPayloads = new ArrayList<Payload>();
		ArrayList<Payload> offererPayloads = new ArrayList<Payload>();
		Direction answererDir = answerer.getDirection();
		Direction offererDir = offerer.getDirection();

		if (!offerer.getType().containsAll(answerer.getType())) {
			return null;
		}

		if (answererDir == Direction.INACTIVE
				|| offererDir == Direction.INACTIVE
				|| answererDir == Direction.RECVONLY
				&& offererDir == Direction.RECVONLY
				|| answererDir == Direction.SENDONLY
				&& offererDir == Direction.SENDONLY) {
			answererDir = Direction.INACTIVE;
			offererDir = Direction.INACTIVE;
		} else if (answererDir == Direction.SENDONLY
				|| offererDir == Direction.RECVONLY) {
			answererDir = Direction.SENDONLY;
			offererDir = Direction.RECVONLY;
		} else if (answererDir == Direction.RECVONLY
				|| offererDir == Direction.SENDONLY) {
			answererDir = Direction.RECVONLY;
			offererDir = Direction.SENDONLY;
		} else {
			answererDir = Direction.SENDRECV;
			offererDir = Direction.SENDRECV;
		}

		if (!checkTransportCompatible(answerer, offerer)) {
			return null;
		}

		for (Payload ansPayload : answerer.getPayloads()) {
			for (Payload offPayload : offerer.getPayloads()) {
				Payload intersect = PayloadUtils.intersect(ansPayload,
						offPayload);
				if (intersect != null) {
					answererPayloads.add(intersect);
					offererPayloads.add(new Payload(intersect));
					break;
				}
			}
		}

		if (answererPayloads.size() == 0) {
			answererDir = Direction.INACTIVE;
			offererDir = Direction.INACTIVE;
		}

		Transport[] transports = TransportUtils.intersect(
				answerer.getTransport(),
				offerer.getTransport());

		if (transports == null)
			return null;

		MediaSpec newAnswerer = new MediaSpec(answererPayloads,
				answerer.getType(), transports[0], answererDir);
		MediaSpec newOfferer = new MediaSpec(offererPayloads,
				offerer.getType(), transports[1], offererDir);
		return new MediaSpec[] { newAnswerer, newOfferer };
	}
}
