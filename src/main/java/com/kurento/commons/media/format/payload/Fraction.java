package com.kurento.commons.media.format.payload;

import java.io.Serializable;

public class Fraction implements Serializable {

	private static final long serialVersionUID = 1L;

	private int num;
	private int denom;

	/**
	 * This constructos is for serialization only, should not be used
	 */
	public Fraction() {

	}

	public Fraction(int num, int denom) throws IllegalArgumentException {
		if (denom == 0) {
			throw new IllegalArgumentException("denom cannot be null");
		}

		this.num = num;
		this.denom = denom;
	}

	public Fraction(Fraction other) {
		num = other.num;
		denom = other.denom;
	}

	public int getNum() {
		return num;
	}

	public void setNum(int num) {
		this.num = num;
	}

	public int getDenom() {
		return denom;
	}

	public void setDenom(int denom) throws IllegalArgumentException {
		if (denom == 0)
			throw new IllegalArgumentException("denom cannot be null");
		this.denom = denom;
	}

	@Override
	public String toString() {
		return num + "/" + denom;
	}

	public double getValue() {
		return (double) num / (double) denom;
	}

	public static Fraction intersect(Fraction answerer, Fraction offerer) {
		if (answerer == null)
			return offerer;
		if (offerer == null)
			return answerer;

		if (answerer.getValue() < offerer.getValue())
			return new Fraction(answerer);
		else
			return new Fraction(offerer);
	}
}
