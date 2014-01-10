package org.emath.service;

import java.util.Random;

/**
 * This class is used to generate new passwords.
 *
 */
public class PasswordGenerator {
	private static Random r = new Random();
	private static String[] chars = { "a", "A", "b", "B", "c", "C", "d", "D",
			"e", "E", "f", "F", "g", "G", "h", "H", "i", "j", "J", "k", "K",
			"L", "m", "M", "n", "N", "p", "P", "q", "Q", "r", "R", "s", "S",
			"t", "T", "u", "U", "v", "V", "w", "W", "x", "X", "y", "Y", "z",
			"Z", "1", "2", "3", "4", "5", "6", "7", "8", "9" };
	private static final int MAX = chars.length - 1;
	private static final int MIN = 0;

	public PasswordGenerator() {

	}
	/**
	 * This method generates the new password
	 * @param length The length of the new password
	 * @return The generated password
	 */
	public String generate(int length) {
		String ret = "";
		for (int i = 0; i < length; ++i) {
			ret += chars[r.nextInt(MAX - MIN) + MIN];
		}
		return ret;
	}

}
