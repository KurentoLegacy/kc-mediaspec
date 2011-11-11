package com.kurento.commons.media.format.formatparameters.impl;

import java.math.BigInteger;

public class MPEG4ConfigEnc {

	public static String USER_DATA_START_CODE = VisualObjectSequence.START_CODE_PREFIX + "B2";

	private String configStr;
	private String auxStr;
	private int fullBitsPut = 0;

	public MPEG4ConfigEnc() {
		this.configStr = "";
		this.auxStr = "";
	}

	public String getConfigStr() {
		return configStr;
	}

	public void putSubstring(String str) {
		configStr += str.toUpperCase();
		fullBitsPut += str.length() * 4;
		System.out.println();
	}

	public void putBits(int numBits, int value) {
		// int: 32 bits
		if (numBits > 32) {
			System.out.println("numbits must be >0 and <=32");
			// TODO: throw excpetion
		}

		String binaryStr = Integer.toBinaryString(value);
		if (binaryStr.length() > numBits) {
			binaryStr = binaryStr.substring(binaryStr.length() - numBits);
		} else if (binaryStr.length() < numBits) {
			int it = numBits - binaryStr.length();
			for (int i = 0; i < it; i++) {
				binaryStr = "0" + binaryStr;
			}
		}
		binaryStr = auxStr + binaryStr;

		int numChars = binaryStr.length() / 4;
		for (int i = 0; i < numChars; i++) {
			String charStr = binaryStr.substring(0, 4);
			configStr += Integer.toHexString(Integer.parseInt(charStr, 2)).toUpperCase();
			binaryStr = binaryStr.substring(4);
		}
		auxStr = binaryStr;
		fullBitsPut += numBits;
	}

	public void nextStartCode() {
		int nCompleteByteAlign = 8 - fullBitsPut % 8;
		if (nCompleteByteAlign > 0) {
			putBits(1, 0);
			for (int i = 0; i < nCompleteByteAlign - 1; i++)
				putBits(1, 1);
		}
		auxStr = "";
	}

	public void putUserData(String userDataStr) {
		putSubstring(USER_DATA_START_CODE);
		putSubstring(String.format("%x", new BigInteger(1, "kurento".getBytes())));
	}

	public static void main(String args[]) {
		MPEG4ConfigEnc conf = new MPEG4ConfigEnc();
		conf.putSubstring("AA00");
		conf.putBits(2, 1);
		conf.putBits(2, 1);
		conf.putBits(5, 1);
		conf.putBits(3, 0);
		System.out.println("fullBitsPut: " + conf.fullBitsPut);
		System.out.println(conf.getConfigStr());

		System.out.println();
		conf = new MPEG4ConfigEnc();
		conf.putBits(1, 0);
		conf.putBits(8, 1);
		System.out.println("fullBitsPut: " + conf.fullBitsPut);
		System.out.println(conf.getConfigStr());

		System.out.println();
		conf = new MPEG4ConfigEnc();
		conf.putBits(1, 1);
		conf.putBits(16, 24000);
		conf.putBits(3, 5);
		System.out.println("fullBitsPut: " + conf.fullBitsPut);
		System.out.println(conf.getConfigStr());

		System.out.println(String.format("%x", new BigInteger(1, "kurento".getBytes())));
	}

}
