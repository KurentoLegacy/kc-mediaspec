package com.kurento.commons.media.format.payload;

import java.io.Serializable;

/**
 * This class provides accurate presentation of rational numbers as num/denom.
 */
public class Fraction implements Serializable {

	private static final long serialVersionUID = 1L;

	private int num;
	private int denom;

	/**
	 * This constructos is for serialization only, should not be used
	 */
	// TODO: Change visibility to private
	public Fraction() {

	}

	/**
	 * Creates a new rational number represented by num/denom.
	 * 
	 * @param num
	 *            Numerator of the rational number
	 * @param denom
	 *            Denominator of the rational number
	 * @throws IllegalArgumentException
	 *             Denominator can not be 0
	 */
	public Fraction(int num, int denom) throws IllegalArgumentException {
		if (denom == 0) {
			throw new IllegalArgumentException("denom cannot be null");
		}

		this.num = num;
		this.denom = denom;
	}

	/**
	 * Create a duplicate fraction of specified parameter.
	 * 
	 * @param other
	 *            Fraction number to be cloned.
	 */
	public Fraction(Fraction other) {
		num = other.num;
		denom = other.denom;
	}

	/**
	 * Return fraction numerator.
	 * 
	 * @return Fraction numerator.
	 */
	public int getNum() {
		return num;
	}

	/**
	 * Set fraction numerator.
	 * 
	 * @param num
	 *            Fraction numerator.
	 */
	public void setNum(int num) {
		this.num = num;
	}

	/**
	 * Return fraction denominator.
	 * 
	 * @return Fraction denominator.
	 */
	public int getDenom() {
		return denom;
	}

	/**
	 * Set fraction denominator.
	 * 
	 * @param denom
	 *            Fraction denominator.
	 * @throws IllegalArgumentException
	 *             Denominator can not be null
	 */
	public void setDenom(int denom) throws IllegalArgumentException {
		if (denom == 0)
			throw new IllegalArgumentException("denom cannot be null");
		this.denom = denom;
	}

	@Override
	public String toString() {
		return num + "/" + denom;
	}

	/**
	 * Trunks the rational number into a <code>Double</code>.
	 * 
	 * @return Number truncation to <code>Double</code> value.
	 */
	public double getValue() {
		return (double) num / (double) denom;
	}

	// TODO: Change visibililty to protected
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
