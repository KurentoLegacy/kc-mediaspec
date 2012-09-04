package com.kurento.mediaspec;

public class TransportIceCandidateTransportUtils {

	public static TransportIceCandidateTransport getFromString(String str) {

		if (str == null)
			return null;

		if (str.equalsIgnoreCase("UDP"))
			return TransportIceCandidateTransport.UDP;

		return null;
	}

	public static String toSdpString(TransportIceCandidateTransport type) {
		switch (type) {
		case UDP:
			return "UDP";
		default:
			return "";
		}
	}
}
