package com.kurento.commons.media.format.formatparameters;

import java.util.ArrayList;
import java.util.Collection;

import javax.sdp.SdpException;

import com.kurento.commons.media.format.formatparameters.impl.GenericFormatParameters;
import com.kurento.commons.media.format.formatparameters.impl.H263FormatParameters;

public class FormatParametersFactory {

	private enum CodecStrings {
		H263(new String[] { "H263", "H263-1998", "H263-2000" });
		private Collection<String> collectionCodecStrings;

		private CodecStrings(String[] codecStrings) {
			collectionCodecStrings = new ArrayList<String>();
			for (String s : codecStrings)
				collectionCodecStrings.add(s);
		}

		public Collection<String> getCollectionCodecStrings() {
			return collectionCodecStrings;
		}
	}

	public static FormatParameters createFormatParameters(String encodingName,
			String formatParamsStr) {
		if (encodingName == null || encodingName.equals(""))
			return null;
		try {
			if (CodecStrings.H263.getCollectionCodecStrings().contains(encodingName.toUpperCase()))
				return new H263FormatParameters(formatParamsStr);
		} catch (SdpException e) {
		}
		return new GenericFormatParameters(formatParamsStr);
	}
}
