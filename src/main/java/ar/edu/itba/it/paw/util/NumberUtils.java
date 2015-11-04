package ar.edu.itba.it.paw.util;

import java.util.regex.Pattern;

public class NumberUtils {
	
	private static final String INTEGER_PATTERN = "0|-?[1-9][0-9]*";

	public static boolean isNumber(String s) {
		if (s == null) return false;
	    return Pattern.matches(INTEGER_PATTERN, s);
	}
}
