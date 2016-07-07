package ar.edu.itba.it.paw.util;

import java.util.regex.Pattern;

public class EmailUtils {
	
	private static final String EMAIL_PATTERN = "[A-Z0-9a-z._%+-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,6}";

	public static boolean isEmail(String s) {
		if (s == null) {
			return false;
		}
	    return Pattern.matches(EMAIL_PATTERN, s);
	}
}
