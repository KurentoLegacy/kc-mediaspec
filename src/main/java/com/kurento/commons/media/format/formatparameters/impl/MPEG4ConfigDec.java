package com.kurento.commons.media.format.formatparameters.impl;

public class MPEG4ConfigDec {

	private String configStr;
	private int bitsTaken = 0;
	private int fullBitsTaken = 0;
	private int[] maskBitsTaken = { 0x7, 0x3, 0x1 };

	public MPEG4ConfigDec(String configStr) {
		this.configStr = configStr;
	}

	public String takeSubstring(int numChars) {
		String str = configStr.substring(0, numChars);
		configStr = configStr.substring(numChars);
		fullBitsTaken += numChars * 4;
		return str;
	}

	public int takeBits(int numBits) {
		// int: 32 bits
		if (numBits > 32) {
			System.out.println("numbits must be >0 and <=32");
			return -1; // TODO: throw excpetion
		}

		int n = 0;
		int numChars = numBits / 4;
		int restBits = numBits % 4;
		boolean first = true;

		int i;
		for (i = 0; i < numChars; i++) {
			n <<= 4;
			n |= Character.digit(configStr.charAt(i), 16);
			if (first && bitsTaken > 0) {
				n &= maskBitsTaken[bitsTaken - 1];
				first = false;
			}
		}

		restBits += bitsTaken;
		if (restBits > 0) {
			n <<= restBits;
			int d = Character.digit(configStr.charAt(i), 16);
			if (first && bitsTaken > 0) {
				d &= maskBitsTaken[bitsTaken - 1];
				first = false;
			}
			n |= (d >> (4 - restBits));
		}

		bitsTaken += numBits;
		fullBitsTaken += numBits;
		clearBits();

		return n;
	}

	public void skipBits(int numBits) {
		// int: 32 bits
		if (numBits > 32) {
			System.out.println("numbits must be >0 and <=32");
			return; // TODO: throw excpetion
		}
		int numChars = numBits / 4;
		int restBits = numBits % 4;
		configStr = configStr.substring(numChars);
		bitsTaken += restBits;
		fullBitsTaken += numBits;
		clearBits();
	}

	public void skipBitsToNextStartCode() {
		skipBits(8 - fullBitsTaken % 8);
	}

	private void clearBits() {
		configStr = configStr.substring(bitsTaken / 4);
		bitsTaken = bitsTaken % 4;
	}

}
