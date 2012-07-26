package com.kurento.mediaspec;

public class FractionUtils {

	/**
	 * Trunks the rational number into a <code>Double</code>.
	 * 
	 * @return Number truncation to <code>Double</code> value.
	 */
	public static double getValue(Fraction fraction) {
		return (double) fraction.num / (double) fraction.denom;
	}

	protected static Fraction intersect(Fraction answerer, Fraction offerer) {
		if (answerer == null)
			return offerer;
		if (offerer == null)
			return answerer;

		if (getValue(answerer) < getValue(offerer))
			return new Fraction(answerer);
		else
			return new Fraction(offerer);
	}
}
