/**
 * 
 */
package com.shinejava.itrac.core.util;

import java.security.SecureRandom;

/**
 * 
 * com.shinejava.itrac.core.util.IdGenerator.java
 *
 * @author wangcee
 *
 * @version $Revision: 28804 $
 *          $Author: wangc@SHINETECHCHINA $
 */
public class IdGenerator {
	
	// Secure random is used to generate the numbers
	protected static SecureRandom rnd = new SecureRandom(SecureRandom.getSeed(20));

	/** The default char set used to create ids. */
	public static final char[] DEFAULT_CHAR_SET = "0123456789abcdefghijklmnopqrstuvwxyz".toCharArray();
	
	public static final char[] NUMBER_CHAR_SET = "01234567899876543210".toCharArray();
	
	public static final int DEFAULT_ID_LENGTH = 11;
	
	public static final int VALIDATE_CODE_LENGTH = 6;
	
	/** The default char set used to create validate code. */
	public static final char[] VALIDATE_CODE_CHAR_SET = "0123456789abcdefghijklmnopqrstuvwxyz0123456789ABCDEFGHIJKLMNOPQRSTUVWXYZ".toCharArray();

	private IdGenerator() {}

	/**
	 * Generates a random string with given length, character set, minimum and maximum values. 
	 * @param length the length, required.
	 * @param charSet the character set to use as a char[]. Ignored if null.
	 * @param min the minimum value this number can have. Ignored if null.
	 * @param max the maximum value this number can have. Ignored if null.
	 * @return the generated string.
	 */
	public static String generateRNDString(int length, char[] charSet, String min, String max) {

		String id = "";

		// Tells if we need to check size of the number not to be bigger than max, 
		// or if we passed the threshold already.
		boolean checkMax = false;
		boolean checkMin = false;
		if(max != null)
			checkMax = true;
		if(min != null)
			checkMax = true;

		for(int i=0; i<length; i++) {

			int end;
			if(checkMax) {
				end = getPosition(charSet, max.charAt(i)) + 1;
			} else {
				end = charSet.length;
			}

			int begin;
			if(checkMin) {
				begin = getPosition(charSet, min.charAt(i));
			} else {
				begin = 0;
			}

			char nextChar = getRandomChar(charSet, begin, end);
			id = nextChar + id;

			if(checkMax && nextChar != max.charAt(i)) {
				checkMax = false;
			}
			if(checkMin && nextChar != min.charAt(i)) {
				checkMin = false;
			}
		}

		return id;
	}

	/**
	 * Generates random String with a given length using the default char set.
	 * @param length the length for the string to be generated.
	 * @return the generated string.
	 */
	public static String generateRNDString(int length) {
		return generateRNDString(length, DEFAULT_CHAR_SET, null, null);
	}
	
	public static String generateAdvRNDString(int length) {
		return generateRNDString(length, VALIDATE_CODE_CHAR_SET, null, null);
	}
	
	public static String generateNumberRandom(int length) {
		return generateRNDString(length, NUMBER_CHAR_SET, null, null);
	}

	/**
	 * Generates random String with default length using the default char set.
	 * @return the generated string.
	 */
	public static String generateRNDString() {
		return generateRNDString(DEFAULT_ID_LENGTH, DEFAULT_CHAR_SET, null, null);
	}
	
	public static String generateValidateCodeString() {
		return generateRNDString(VALIDATE_CODE_LENGTH, VALIDATE_CODE_CHAR_SET, null, null);
	}
	
	/**
	 * Chooses a random character from the list of characters between begin and end index.
	 * @param charSet the char set to choose from
	 * @param beginIndex begin index (included)
	 * @param endIndex end index (excluded)
	 * @return the char.
	 */
	private static char getRandomChar(char[] charSet, int beginIndex, int endIndex) {
		int next = rnd.nextInt(endIndex - beginIndex);
		return charSet[beginIndex + next];
	}

	/**
	 * Finds the position of a char in a char set array.
	 * @param array the char set.
	 * @param value the value to look for.
	 * @return the position.
	 */
	private static int getPosition(char[] array, char value) {
		for(int i=0; i<array.length; i++)
			if(value == array[i])
				return i;
		return -1;
	}
	
}
