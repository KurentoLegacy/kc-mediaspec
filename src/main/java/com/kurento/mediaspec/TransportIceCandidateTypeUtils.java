package com.kurento.mediaspec;

public class TransportIceCandidateTypeUtils {

	public static TransportIceCandidateType getFromString(String str) {

		if (str == null)
			return null;

		if (str.equalsIgnoreCase("host"))
			return TransportIceCandidateType.HOST;
		else if (str.equalsIgnoreCase("srflx"))
			return TransportIceCandidateType.SERVER_REFLEXIVE;
		else if (str.equalsIgnoreCase("prflx"))
			return TransportIceCandidateType.PEER_REFLEXIVE;
		else if (str.equalsIgnoreCase("relay"))
			return TransportIceCandidateType.RELAYED;

		return null;
	}

	public static String toSdpString(TransportIceCandidateType type) {
		switch (type) {
		case HOST:
			return "host";
		case SERVER_REFLEXIVE:
			return "srflx";
		case PEER_REFLEXIVE:
			return "prflx";
		case RELAYED:
			return "relay";
		default:
			return "";
		}
	}
}
