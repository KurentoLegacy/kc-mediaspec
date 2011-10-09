package com.kurento.commons.media.format.formatparameters.impl;

public enum PictureSize {
	SQCIF(128, 96, "SQCIF"), QCIF(176, 144, "QCIF"), CIF(352, 288, "CIF"), CIF4(
			704, 576, "4CIF"), CIF16(1408, 1152, "16CIF"), CUSTOM(-1, -1,
			"CUSTOM");

	private int width;
	private int height;
	private String str;

	public int getWidth() {
		return width;
	}

	public int getHeight() {
		return height;
	}

	public String getStr() {
		return str;
	}

	private PictureSize(int width, int height, String str) {
		this.width = width;
		this.height = height;
		this.str = str;
	}

	public String toString() {
		return str;
	}

	public static PictureSize getPictureSizeFromSize(int width, int height) {
		if (SQCIF.width == width && SQCIF.height == height)
			return SQCIF;
		else if (QCIF.width == width && QCIF.height == height)
			return QCIF;
		else if (CIF.width == width && CIF.height == height)
			return CIF;
		else if (CIF4.width == width && CIF4.height == height)
			return CIF4;
		else if (CIF16.width == width && CIF16.height == height)
			return CIF16;

		return PictureSize.CUSTOM;
	}

	public static PictureSize getPictureSizeFromString(String str) {
		if (SQCIF.str.equalsIgnoreCase(str))
			return SQCIF;
		else if (QCIF.str.equalsIgnoreCase(str))
			return QCIF;
		else if (CIF.str.equalsIgnoreCase(str))
			return CIF;
		else if (CIF4.str.equalsIgnoreCase(str))
			return CIF4;
		else if (CIF16.str.equalsIgnoreCase(str))
			return CIF16;
		else if (CUSTOM.str.equalsIgnoreCase(str))
			return CUSTOM;
		return null;
	}
}