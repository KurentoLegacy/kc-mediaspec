package com.kurento.commons.media.format.formatparameters.impl;

import javax.sdp.SdpException;

public class H263CPCF {

	private int cd;
	private int cf;
	private int[] mpis;

	public int getCd() {
		return cd;
	}

	public void setCd(int cd) {
		this.cd = cd;
	}

	public int getCf() {
		return cf;
	}

	public void setCf(int cf) {
		this.cf = cf;
	}

	public int[] getMpis() {
		return mpis;
	}

	public void setMpis(int[] mpis) {
		this.mpis = mpis;
	}

	public H263CPCF(int cd, int cf, int[] mpis) throws SdpException {
		this.cd = cd;
		this.cf = cf;
		this.mpis = mpis;
	}

	public String toString() {
		String str = "CPCF=" + cd + "," + cf;
		for (int i = 0; i < mpis.length; i++)
			str += "," + mpis[i];
		return str;
	}
}
