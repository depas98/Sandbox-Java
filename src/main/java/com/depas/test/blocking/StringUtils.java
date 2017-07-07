package com.depas.test.blocking;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.sql.Blob;
import java.sql.Clob;
import java.sql.SQLException;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;

//import java.math.BigInteger;

public class StringUtils {

	private static final char[] CR_LF_CHAR_ARRAY = {(char)13,(char)10};
	private static final String CR_LF = new String(CR_LF_CHAR_ARRAY);
	private static final char[] LF_CHAR_ARRAY = {(char)10};
	private static final String LF = new String(LF_CHAR_ARRAY);
    private static final char[] HEX_DIGITS = {'0','1','2','3','4','5','6','7',
                                              '8','9','a','b','c','d','e','f'};

    public static final String NEW_LINE = "\n";
	public static final String PIPE = "|";
	public static final String PIPE_SPLITTER = "\\|";
	private static final String BACKSLASH_REG_EX = "\\\\";
	private static final String BACKSLASH_REPLACE_TEXT = "\\\\\\\\";


	/**
	 * Returns carriage return (chr 13) and line feed (chr 10) characters as string
	 */
	public static String getCarriageReturnLineFeed() {
		return CR_LF;
	}

	/**
	 * Returns line feed character (chr 10) as string
	 */
	public static String getLineFeed() {
		return LF;
	}

	/** Returns true if string is null or zero-length */
	public static boolean isEmpty(String s) {
		return (s==null || s.trim().length()==0);
	}

	/** Returns true if string is not null and is not zero-length */
	public static boolean isNotEmpty(String s) {
		return (s!=null && s.trim().length()>0);
	}

	/** Returns true if the Strings are equal, case insensitive, or both null */
    public static boolean equalsIgnoreCase(String s1, String s2) {
        return equals(s1==null?s1:s1.toUpperCase(),s2==null?s2:s2.toUpperCase());
    }

    /** Returns true if the Strings are equal, case sensitive, or both null */
    public static boolean equals(String s1, String s2) {
        if (s1==null && s2==null) return true;
        return s1!=null && s2!=null && s1.equals(s2);
    }

    /** Replaces all instances of the 'toReplace' string in the 'text' string
     * with the 'replacementText' provided.  If replacementText is null, the
     * toReplace text will be removed.
     */
    public static final String replaceAll(String text, String toReplace, String replacementText)
    {
		if (text == null) {
			return null;
		} else if (toReplace == null || toReplace.length() == 0) {
			return text;
		}

		if (replacementText == null) {
			replacementText = "";
		}

		int index = text.indexOf(toReplace);
		int length = toReplace.length();
		int startIndex = 0;

		while (index >= 0) {
			startIndex = index + replacementText.length();
			text = text.substring(0, index) + replacementText + text.substring(index + length);
			index = text.indexOf(toReplace, startIndex);
		}

		return text;
	}

    /**
     * This method is copied directly from Java 1.6_17 java.util.regex.Matcher class
     * Returns a literal replacement <code>String</code> for the specified
     * <code>String</code>.
     *
     * This method produces a <code>String</code> that will work
     * as a literal replacement <code>s</code> in the
     * <code>appendReplacement</code> method of the {@link Matcher} class.
     * The <code>String</code> produced will match the sequence of characters
     * in <code>s</code> treated as a literal sequence. Slashes ('\') and
     * dollar signs ('$') will be given no special meaning.
     *
     * @param  s The string to be literalized
     * @return  A literal string replacement
     * @since 1.5
     */
    public static String quoteReplacement(String s) {
        if ((s.indexOf('\\') == -1) && (s.indexOf('$') == -1))
            return s;
        StringBuffer sb = new StringBuffer();
        for (int i=0; i<s.length(); i++) {
            char c = s.charAt(i);
            if (c == '\\') {
                sb.append('\\'); sb.append('\\');
            } else if (c == '$') {
                sb.append('\\'); sb.append('$');
            } else {
                sb.append(c);
            }
        }
        return sb.toString();
    }

    /**
     * This method tests to see if a string is null or empty, if so, then the
     * default value supplied is returned.
     * <br/><br/>
     * This method also tests whether the string's length exceeds the maxLength
     * value and if so, it returns a truncated version of the one passed in.
     * <br/><br/>
     * This method will always returned a "trimmed" version of the String.
     *
     * @param s The string to test
     * @param defaultValue The default value to use when the string is null or empty
     * @param maxLength The maximum length that the string is allowed to be before truncation occurs
     * @return A trimmed string
     */
    public static String ifNull(String s, String defaultValue, int maxLength) {
        String result = StringUtils.trimWhitespace(s);
        if (false == StringUtils.hasLength(result)) {
            result = defaultValue;
        }
        else if (result.length() > maxLength) {
            result = result.substring(0, maxLength);
        }
        if (result != null) {
        	result = result.trim();
        }
        return result;
    }

    /**
     *
     * @param s
     * @param length
     * @return
     */
    public static String safeSubString(String s, int length) {
    	String substring = null;
    	if (s != null) {
    		if (s.length() > length) {
    			substring = s.substring(0, length);
    		}
    		else {
    			substring = s;
    		}
    	}
    	return substring;
    }

    /***
     * Convert hex string to byte array
     * @param hexStr
     * @return
     */
    public static byte[] toByteArray( String hex ){
        byte[] byteArray = new byte[hex.length() / 2];
        for (int i = 0; i < byteArray.length; i++) {
            byteArray[i] = (byte)Integer.parseInt(hex.substring((i*2), ((i*2)+2)), 16);
        }
        return byteArray;
    }

    /**
     * Convert byte array to hex string
     * @param bytes
     * @return
     */
    public static String toHexString(byte bytes[]) {
        return toHexString(bytes, "");
    }

    /**
     * Convert byte array to hex string, separating each byte with given separator.
     * @param bytes
     * @param separator
     * @return
     */
    public static String toHexString(byte bytes[], String separator) {
        byte ch = 0x00;
        int i = 0;
        if (bytes == null || bytes.length <= 0) {
            return null;
        }
        StringBuffer out = new StringBuffer(bytes.length * 2 + (bytes.length - 1) * separator.length());
        while (i < bytes.length) {
            if (i != 0) {
                out.append(separator);
            }
            ch = (byte) (bytes[i] & 0xF0);      // strip off high nibble
            ch = (byte) (ch >>> 4);          // shift the bits down
            ch = (byte) (ch & 0x0F);         // must do this if high order bit is on
            out.append(HEX_DIGITS[(int)ch]); // convert the nibble to a hex digit
            ch = (byte) (bytes[i] & 0x0F);      // strip off low nibble
            out.append(HEX_DIGITS[(int)ch]); // convert the nibble to a hex digit
            i++;
        }
        return out.toString();
    }

    /**
     * Convert byte array to hex string
     * @param bytes
     * @return
     */
    /* This implementation is simpler but probably not as fast
    public static String toHexString(byte[] bytes) {
        StringBuffer sb = new StringBuffer();
        if (bytes!=null && bytes.length > 0) {
            sb.append(new BigInteger(bytes).toString(16));
        }
        return sb.toString();
    }
    */

    /**
     * Return a string representation of an object
     * @param o
     * @return
     * @throws SQLException
     */
    public static String getStringValue(Object o) throws SQLException {
        String s = null;
        if (o != null) {
            if (o instanceof Clob) {
                Clob clob = (Clob) o;
                s = clob.getSubString(1, (int)clob.length());
            }
            else if (o instanceof String) {
                s = (String) o;
            }
            else {
                s = o.toString();
            }
        }
        return s;
    }

    /**
     * Return a string representation of an object
     * @param blob
     * @return
     * @throws SQLException
     */
    public static String getStringValue(Blob blob) throws SQLException {
	    String s = null;
		InputStream is = null;
		if (blob != null) {
			is = blob.getBinaryStream();
		}
		if (is != null) {
			BufferedInputStream bis = new BufferedInputStream(is, 4096);
			StringBuffer sb = new StringBuffer();
			int ch = 0;
			try {
				while ((ch = bis.read()) != -1) {
					try {
						sb.append((char)ch);
					}
					catch (Exception e) {
						sb.append(' ');
					}
				}
			} catch (IOException e) {
				throw new SQLException(e.getMessage());
			}
			s = sb.toString();
		}
		return s;
    }


    /**
     * Generate a 64 bit hash code for the specified string
     * @param s
     * @return
     */
    public static long hashCode64(String s) {
        long h = 0;
    	if (s != null && s.length() > 0) {
		    int off = 0;
		    int len = s.length();
	        for (int i = 0; i < len; i++) {
	             h = 31*h + s.charAt(off++);
	        }
    	}
        return h;
    }

	public static String trimAllWhitespace(String s) {
		if (isEmpty(s)) {
			return s;
		}
		StringBuffer buf = new StringBuffer(s);
		int index = 0;
		while (buf.length() > index) {
			if (Character.isWhitespace(buf.charAt(index))) {
				buf.deleteCharAt(index);
			}
			else {
				index++;
			}
		}
		return buf.toString();
	}

    /**
     * Utility method to check if SQL handle is all zeroes
     *
     * @param s the String to check
     * @return true if it is all zeroes
     */
    public static boolean isAllZeros(String s) {
        if (s == null) {
        	return true;
        }
        for (int i = s.length() - 1; i >= 0; i--) {
            if (s.charAt(i) != '0') {
                return false;
            }
        }
        return s.length() > 0;
    }

    /**
     * Returns true if and only if this string contains the specified
     * sequence of char values. Borrowed from String.contains(s) in java 1.5+
     *
     * @param string the string to search
     * @param searchString the string to search for
     * @return true if the string contains the searchString, false otherwise
     */
    public static boolean stringContains(String string, String searchString) {
        return string.indexOf(searchString) > -1;
    }

	/**
	 * Check whether the given CharSequence has actual text.
	 * More specifically, returns <code>true</code> if the string not <code>null</code>,
	 * its length is greater than 0, and it contains at least one non-whitespace character.
	 * <p><pre>
	 * StringUtils.hasText(null) = false
	 * StringUtils.hasText("") = false
	 * StringUtils.hasText(" ") = false
	 * StringUtils.hasText("12345") = true
	 * StringUtils.hasText(" 12345 ") = true
	 * </pre>
	 * @param str the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not <code>null</code>,
	 * its length is greater than 0, and it does not contain whitespace only
	 * @see java.lang.Character#isWhitespace
	 */
	public static boolean hasText(String s) {
		return hasText((CharSequence)s);
	}


	/**
	 * Replace non-displayable characters in the specified string with ?.
	 *
	 * @param s the s
	 * @param replacementString the replacement char
	 * @return the cleaned string
	 */
	public static String replaceNonDisplayableChars(final String s, final String replacementString) {
		// Replace non-displayable control characters with ? (clientInfo=??)
		StringBuffer sb = new StringBuffer(s == null ? "" : s);
		if (s != null) {
			for (int i = 0; i < sb.length(); i++) {
				char c = sb.charAt(i);
				if (Character.isISOControl(c)) {
					sb.replace(i, i+1, replacementString);
				}
			}
		}
		return sb.toString();
	}

	/**
	 * Limit the given string to first <arg>lineCountLimit</arg> lines, not including character terminating the
	 * last line. If the string gets truncated, appends the <arg>truncationIndicatingSuffix</arg> string
	 * to the truncated result.
	 * @param str String possibly containing a large number of lines (with CR/LF/CRLF endings).
	 * @param lineCountLimit Number of lines to return.
	 * @param truncationIndicatingSuffix Is appended to the truncated result (if truncation occurred).
	 * @return The truncated string.
	 */
	public static String limitLineCount(String str, int lineCountLimit, String truncationIndicatingSuffix) {
		if(str == null)
			return null;

		if(truncationIndicatingSuffix == null)
			truncationIndicatingSuffix = "";

		int lfCount = 0;
		int crCount = 0;

		for(int i = 0 ; i < str.length() ; i++) {
			if(str.charAt(i) == '\n')
				lfCount++;
			else if(str.charAt(i) == '\r')
				crCount++;

			int currentLine = Math.max(lfCount, crCount) + 1;

			if(currentLine > lineCountLimit) {
				StringBuilder sb = new StringBuilder(str.length() + truncationIndicatingSuffix.length());
				sb.append(str, 0, i);
				sb.append(truncationIndicatingSuffix);
				return sb.toString();
			}
		}

		return str;
	}

	//////////////FROM IWC//////////////////

	/**
	 * Returns true if string can be converted to a integer (decimals will return false)
	 */
	public static boolean isInteger(String s) {
		try {
			Integer.parseInt(s);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	/**
	 * Returns true if string can be converted to a long (decimals will return false)
	 */
	public static boolean isLong(String s) {
		try {
			Long.parseLong(s);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	/**
	 * Returns true if string can be converted to a double (allows decimals)
	 */
	public static boolean isNumber(String s) {
		try {
			Double.parseDouble(s);
			return true;
		} catch (NumberFormatException ex) {
			return false;
		}
	}

	public static String trimLeadingWhitespace(String s) {
		if (isEmpty(s)) {
			return s;
		}
		StringBuffer buf = new StringBuffer(s);
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(0))) {
			buf.deleteCharAt(0);
		}
		return buf.toString();
	}

	public static String trimInteriorSpace(String s, int numPasses) {
		String t = s;
		for (int i=0; i<numPasses; i++) {
			t = t.replaceAll("  "," ");
		}
		return t;
	}

	public static String trimTrailingWhitespace(String s) {
		if (isEmpty(s)) {
			return s;
		}
		StringBuffer buf = new StringBuffer(s);
		while (buf.length() > 0 && Character.isWhitespace(buf.charAt(buf.length() - 1))) {
			buf.deleteCharAt(buf.length() - 1);
		}
		return buf.toString();
	}


    public static String trimTrailingCharacter(String str, char trailingCharacter) {
		if (isEmpty(str)) {
			return str;
		}
        StringBuffer buf;
        for(buf = new StringBuffer(str); buf.length() > 0 && buf.charAt(buf.length()-1) == trailingCharacter; buf.deleteCharAt(buf.length()-1)) { }
        return buf.toString();
    }


    public static final String escapeXML(String value){
	   	 value = replaceAll(value,"&","&amp;");
	   	 value = replaceAll(value,"<","&lt;");
	   	 value = replaceAll(value,">","&gt;");
	   	 value = replaceAll(value,"\"","&quot;");
	   	 value = replaceAll(value,"'","&apos;");
	   	 return value;
    }

    public static final String escapeHTML(String value){
	   	 value = replaceAll(value,"&","&amp;");
	   	 value = replaceAll(value,"<","&lt;");
	   	 value = replaceAll(value,">","&gt;");
	   	 value = replaceAll(value,"\"","&quot;");
	   	 return value;
   }

    public static final String escapeBackslash(String value) {
    	return value.replaceAll(BACKSLASH_REG_EX,BACKSLASH_REPLACE_TEXT);
    }

    public static final String escapeQuotes(String value) {
    	value = replaceAll(value,"'", "\\'");
    	return replaceAll(value,"\"","\\\"");
    }


    public static final String escapeBackslashAndQuotes(String value) {
    	return escapeHTML(escapeQuotes(escapeBackslash(value)));
    }

    public static final String escapeTooltipValue(String value) {
	   	 value = replaceAll(value,"&","&ampConfio;");
	   	 value = replaceAll(value,"<","&ltConfio;");
	   	 value = replaceAll(value,">","&gtConfio;");
	   	 value = replaceAll(value,"\"","&quotConfio;");
	   	 value = replaceAll(value,"'","&aposConfio;");
    	return escapeBackslash(value);
    }

    /*
     * This will ensure that there is at least one space every x characters.
     * If there is not it will insert one.
     *
     * @param   the value of the string you want spaces in
     *          spaceInsertIndex is the how often you want spaces, every X characters
     *
     * @return  The resulting <tt>String</tt>
     *
     */
    public static final String insertSpacesEveryXChars(String value, int spaceInsertIndex){

    	try {
        	if (!hasText(value)){
        		return null;
        	}
        	StringBuffer returnValue = new StringBuffer(256);

        	if(spaceInsertIndex>value.length()+1){
        		return value;
        	}
        	String valueToCheck = value.substring(0, spaceInsertIndex);
        	int index = valueToCheck.lastIndexOf(" ");

        	boolean foundSpace=true;
        	if (index<0){
        		// check the next char if it is a space
        		String nextChar = value.substring(spaceInsertIndex, spaceInsertIndex+1);
        		if (!nextChar.equals(" ")){
        			foundSpace=false;
        		}
        		else{
        			index++;
        		}
        	}

        	if (!foundSpace){
        		// need to insert a space
        		returnValue.append(valueToCheck).append(" ");

        		String newValue = value.substring(spaceInsertIndex);
        		newValue=insertSpacesEveryXChars(newValue,spaceInsertIndex);
        		if (hasText(newValue)){
        			returnValue.append(newValue);
        		}
        	}
        	else{
        		String preValue = valueToCheck.substring(0,index+1);
        		returnValue.append(preValue);
        		String newValue = value.substring(index+1);
        		newValue=insertSpacesEveryXChars(newValue,spaceInsertIndex);
        		if (hasText(newValue)){
        			returnValue.append(newValue);
        		}
        	}

        	return returnValue.toString();
		} catch (Exception e) {
			return value;
		}
    }

    public static boolean stringToBoolean(String s) {
    	return (s!=null && s.equals("1"));
    }
    public static String booleanToIntString(boolean val) {
    	return (val ? "1" : "0");
    }
    public static String booleanToCharString(boolean val) {
    	return (val ? "Y" : "N");
    }

    /**
     * Converts a String to int
     *
     * @param s the string to convert
     * @return the int, if parsable, otherwise 0
     */
    public static int stringToInt(String s) {
    	if (!isEmpty(s)) {
    		try {
    			return Integer.parseInt(s);
    		} catch (NumberFormatException ex) {}
    	}
    	return 0;
    }

    /**
     * Converts a String to int and if it fails returns the given int value
     *
     * @param s the s
     * @param defValue the default value
     * @return the long
     */
    public static int stringToInt(String s, int defValue) {
    	if (!isEmpty(s)) {
    		try {
    			return Integer.parseInt(s);
    		} catch (NumberFormatException ex) {}
    	}
    	return defValue;
    }

    /**
     * Converts a String to long
     *
     * @param s the string to convert
     * @return the long, if parsable, otherwise 0
     */
    public static long stringToLong(String s) {
    	if (!isEmpty(s)) {
    		try {
    			return Long.parseLong(s);
    		} catch (NumberFormatException ex) {}
    	}
    	return 0;
    }

    /**
     * Converts a String to long and if it fails returns the given long value
     *
     * @param s the s
     * @param defValue the default value
     * @return the long
     */
    public static long stringToLong(String s, long defValue) {
    	if (!isEmpty(s)) {
    		try {
    			return Long.parseLong(s);
    		} catch (NumberFormatException ex) {}
    	}
    	return defValue;
    }

    /**
     * Formats the provided number with the specified number of fractional digits
     * If number arg is not a number, returns the String
     */
    public static String formatNumber(String number, int maxFractionalDigits) {

    	try {
    		NumberFormat nf = NumberFormat.getNumberInstance();
    		nf.setMaximumFractionDigits(maxFractionalDigits);
    		return nf.format(Double.parseDouble(number));
    	} catch (Exception ex) {
    		return number;
    	}
    }
    public static String formatNumber(double number, int maxFractionalDigits) {
    	try {
    		NumberFormat nf = NumberFormat.getNumberInstance();
    		nf.setMaximumFractionDigits(maxFractionalDigits);
    		return nf.format(number);
    	} catch (Exception ex) {
    		return String.valueOf(number);
    	}
    }


    public static String leftPad(String toPad, int numPads) {
    	return leftPad(toPad,numPads," ");
    }

    public static String leftPad(String toPad, int padLength, String padString) {

    	if (toPad==null || toPad.length()==0) return toPad;
    	if (padLength<1) return "";

    	int stringToPadLength = toPad.length();

    	// if pad length < toPad's length, truncate toPad string
    	if (stringToPadLength>padLength) {
    		return toPad.substring(0,padLength);
    	}
    	// if pad length = toPad's length, return toPad
    	else if (stringToPadLength==padLength) {
    		return toPad;
    	}

    	// build padding until amount to pad is met (or exceeded)
    	int numCharsToPad = padLength - stringToPadLength;
    	String padding = "";
    	while (padding.length()<numCharsToPad) {
    		padding += padString;
    	}
    	// if padding exceeds pad length, trim it
    	if (padding.length()>numCharsToPad) {
    		padding = padding.substring(0,numCharsToPad);
    	}
    	return padding + toPad;

    }

    public static String rightPad(String toPad, int totalLength) {
    	if (toPad==null) return null;

    	if (toPad.length()>=totalLength) {
    		return toPad.substring(0,totalLength);
    	}

    	StringBuffer buf = new StringBuffer(toPad);
    	while (buf.length()<totalLength) {
    		buf.append(" ");
    	}
    	return buf.toString();


    }

    public static String[] chunkStringBySpaces(String s, int approximateChunkSize) {

    	if (s.length()<approximateChunkSize*1.25) {
    		return new String[]{s};
    	}

    	List<String> chunks = new ArrayList<String>();
    	String[] parts = s.split(" ");

    	int chunkSize = approximateChunkSize;

    	String part = "";
    	for (int i=0; i<parts.length; i++) {
    		if (part.length()<=chunkSize) {
    			part += parts[i] + " ";
    		} else {
    			// new part
    			part = part.trim();
    			chunks.add(part);
    			if (part.length()>chunkSize) {
    				chunkSize = part.length();
    			}
    			part = parts[i] + " ";
    		}

    	}
    	if (part.trim().length()>0) {
    		chunks.add(part.trim());
    	}


    	return (String[])chunks.toArray(new String[chunks.size()]);


    }

    public static String[] chunkString(String s, int chunkSize) {

    	String tempString = s;
		List<String> chunks = new ArrayList<String>();

		while (tempString.length()>chunkSize) {
			chunks.add(tempString.substring(0,chunkSize));
			tempString = tempString.substring(chunkSize);
		}
		if (tempString.length()>0) {
			chunks.add(tempString);
		}

		return (String[])chunks.toArray(new String[chunks.size()]);

    }

    /**
     * This method will search a String for consecutive characters
     * without a space exceeding the consecutiveCharLimit. If any
     * are found, the String will be replaced with the specified
     * delimString at the location of the consecutiveCharLimit.
     *
     * @param s the String to check
     * @param consecutiveCharCount the number of consecutive characters
     *        without a space allowed before inserting delimString
     * @param delimString the string value to insert at the consecutiveCharLimit
     *
     * @return the string chunked at the consecutiveCharLimit
     */
    public static String chunkString(String s, int consecutiveCharLimit, String delimString) {
    	StringBuilder sb = new StringBuilder();
    	char[] ca = s.toCharArray();
    	int consecutiveCharCount = 0;
    	for (char c : ca) {
    		consecutiveCharCount++;
    		sb.append(c);
    		if (c == ' ') {
    			consecutiveCharCount = 0;
    		}
    		else if (consecutiveCharCount >= consecutiveCharLimit) {
    			sb.append(delimString);
    			consecutiveCharCount = 0;
    		}
		}
    	return sb.toString();
    }

    public static List<String> splitToList(String s, String delimRegEx) {

    	List<String> l = new ArrayList<String>();

    	if (!isEmpty(s)) {
    		if (isEmpty(delimRegEx)) {
    			l.add(s);
    		} else {
    			String[] parts = s.split(delimRegEx);
    			l = Arrays.asList(parts);
    		}
    	}

    	return l;
    }

	public static String getStackTrace(Throwable aThrowable) {
		final Writer result = new StringWriter();
	    final PrintWriter printWriter = new PrintWriter(result);
	    aThrowable.printStackTrace(printWriter);
	    return result.toString();
	}

    public static int compareWithNullCheck(String s1, String s2, boolean nullFirst, boolean ignoreCase) {
    	if (s1!=null && s2!=null) {
    		if (ignoreCase) {
    			return s1.compareToIgnoreCase(s2);
    		} else {
    			return s1.compareTo(s2);
    		}
    	} else {
    		if (s1==null && s2==null) return 0;
    		//here, one is null, other is not
    		if (s1==null) {
    			// s1 is null
    			return (nullFirst ? -1 : 1);
    		} else {
    			// s2 is null
    			return (nullFirst ? 1 : -1);
    		}
    	}
    }

	public static int findSpaceAfterNChars(String text, int numChars){

		if (text.length()<numChars){
			return -1;
		}

		while (true) {
			if (text.length()>numChars){
				if (text.substring(numChars,numChars+1).equals(" ") || text.substring(numChars,numChars+1).equals(",")){
					return numChars;
				}
				else{
					numChars++;
				}
			}
			else{
				return numChars;
			}
		}
	}

    /**
     * This method ensures that the output String has only valid XML unicode characters as specified by the
     * XML 1.0 standard. For reference, please see the
     * standard. This method will return an empty String if the input is null or empty.
     * @param  The String whose non-valid characters we want to remove.
     * @return The in String, stripped of non-valid characters.
     */
    public static String removeInvalidXMLCharacters(String s) {

    	if (s==null) return s;

        StringBuilder out = new StringBuilder();
    	int codePoint;
		int i=0;

    	while(i<s.length()) {
    		// This is the unicode code of the character.
    		codePoint = s.codePointAt(i);
			if ((codePoint == 0x9) ||
					(codePoint == 0xA) ||
					(codePoint == 0xD) ||
					((codePoint >= 0x20) && (codePoint <= 0xD7FF)) ||
					((codePoint >= 0xE000) && (codePoint <= 0xFFFD)) ||
					((codePoint >= 0x10000) && (codePoint <= 0x10FFFF))) {
				out.append(Character.toChars(codePoint));
			}
			// Increment with the number of code units(java chars) needed to represent a Unicode char.
			i+= Character.charCount(codePoint);
    	}

    	return out.toString();

    }

	/**
	 * Will take a string and if it contains unicode strings will convert them to the actual string.
	 * This function looks fore \\u and then the next four characters as the unicode string
	 *
	 * @param str
	 * @return a string with all unicode strings it contains switched to the actual character
	 */
	public static String convertUnicodeToText(String str) {
		StringBuilder returnVal = new StringBuilder();

		boolean haveSlash=false;
		boolean haveUnicode=false;
		int unicodeCount = 0;
		String unicode = "";
		for(int i=0; i<str.length(); i++) {
			char ch = str.charAt(i);
			if (haveUnicode){
				unicode=unicode + str.charAt(i);
				unicodeCount++;
				if (unicodeCount==4){
					unicode= "\\u" + unicode;
					Integer code = Integer.valueOf(unicode.substring(2), 16); // the integer 65 in base 10
					char unicodeChar = Character.toChars(code.intValue())[0];
					returnVal.append(unicodeChar);
					haveUnicode=false;
					unicodeCount = 0;
					unicode="";
				}
			}
			else if (haveSlash){
				if ('u' == ch || 'U' == ch){
					haveUnicode=true;
					haveSlash=false;
				}
				else{
					returnVal.append(str.charAt(i-1));
					returnVal.append(str.charAt(i));
					haveSlash=false;
				}
			}
			else if ('\\' == ch){
				haveSlash=true;
			}
			else{
				returnVal.append(ch);
			}
		}
		return returnVal.toString();
	}

	/**
	 * Will take a string and switch characters outside of the printable ASCII set
	 * to a unicode format.
	 *
	 * Printable ASCII characters Dec 32 - 126(0x0020 - 0x007e) except Dec 92(0x005c)
	 * will not be converted to unicode, backslashes get converted to unicode.
	 * This is so we can decrypt unicode knowing a \\u really is unicode and not the actual string
	 *
	 * @param str
	 * @return Unciode format of the string for non printable ASCII characters
	 */
	public static String convertToUnicode(String str) {
		StringBuilder unicodeString = new StringBuilder();
		for(int i=0; i<str.length(); i++) {
			char ch = str.charAt(i);
			if ((ch >= 0x0020) && (ch <= 0x007e) && ch != 0x005c) {	// Does the char need to be converted to unicode?  0x005c is \
				unicodeString.append(ch);					// No.
			}
			else 	{								// Yes.
	        	unicodeString.append("\\u") ;				// standard unicode format.
				String hex = Integer.toHexString(str.charAt(i) & 0xFFFF);	// Get hex value of the char.
				for(int j=0; j<4-hex.length(); j++)	// Prepend zeros because unicode requires 4 digits
					unicodeString.append("0");
				unicodeString.append(hex.toLowerCase());		// standard unicode format.
				//ostr.append(hex.toLowerCase(Locale.ENGLISH));
			}
		}
		return (new String(unicodeString));		//Return the stringbuffer cast as a string
	}

	public static String convertUTF8ToQuestionMark(String str) {
		StringBuilder unicodeString = new StringBuilder();
		for(int i=0; i<str.length(); i++) {
			char ch = str.charAt(i);
			if ((ch >= 0x0020) && (ch <= 0x007e) && ch != 0x005c) {
				unicodeString.append(ch);			// No.
			}
			else 	{								// Yes.
	        	unicodeString.append("?") ;
			}
		}
		return (new String(unicodeString));		//Return the stringbuffer cast as a string
	}

	public static boolean containsDoubleByteChars(String str) {
		for(int i=0; i<str.length(); i++)
		{
			char ch = str.charAt(i);

			if ((ch >= 0x0020) && (ch <= 0x007e)) {	// Does the char need to be converted to unicode?
				continue;		// not double byte char continue searching
			} else {
				return true;	// Yes double byte char
			}
		}

		return false;
	}

	public static boolean stringExistInList(String value, List<String> searchList){
		for (String search : searchList) {
			if (value!=null && value.equals(search)){
				return true;
			}
			else if (value==null && search==null){
				return true;
			}
		}

		return false;
	}

	//////////////FROM IWC//////////////////

	//////////////FROM SPRING///////////////////////////////////////////////////
	// If/When this stuff is moved to Ignite_PI, this stuff should go away.
	// Instead, delegate to the Spring classes since that project already
	// has Spring as a dependency.
	////////////////////////////////////////////////////////////////////////////
	/**
	 * Check whether the given CharSequence has actual text.
	 * More specifically, returns <code>true</code> if the string not <code>null</code>,
	 * its length is greater than 0, and it contains at least one non-whitespace character.
	 * <p><pre>
	 * StringUtils.hasText(null) = false
	 * StringUtils.hasText("") = false
	 * StringUtils.hasText(" ") = false
	 * StringUtils.hasText("12345") = true
	 * StringUtils.hasText(" 12345 ") = true
	 * </pre>
	 * @param str the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not <code>null</code>,
	 * its length is greater than 0, and it does not contain whitespace only
	 * @see java.lang.Character#isWhitespace
	 */
	private static boolean hasText(CharSequence str) {
		if (!hasLength(str)) {
			return false;
		}
		int strLen = str.length();
		for (int i = 0; i < strLen; i++) {
			if (!Character.isWhitespace(str.charAt(i))) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Check that the given CharSequence is neither <code>null</code> nor of length 0.
	 * Note: Will return <code>true</code> for a CharSequence that purely consists of whitespace.
	 * <p><pre>
	 * StringUtils.hasLength(null) = false
	 * StringUtils.hasLength("") = false
	 * StringUtils.hasLength(" ") = true
	 * StringUtils.hasLength("Hello") = true
	 * </pre>
	 * @param str the CharSequence to check (may be <code>null</code>)
	 * @return <code>true</code> if the CharSequence is not null and has length
	 * @see #hasText(String)
	 */
	public static boolean hasLength(CharSequence str) {
		return (str != null && str.length() > 0);
	}


	/**
	 * Trim leading and trailing whitespace from the given String.
	 * @param str the String to check
	 * @return the trimmed String
	 * @see java.lang.Character#isWhitespace
	 */
	public static String trimWhitespace(String str) {
		if (!hasLength(str)) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str);
		while (sb.length() > 0 && Character.isWhitespace(sb.charAt(0))) {
			sb.deleteCharAt(0);
		}
		while (sb.length() > 0 && Character.isWhitespace(sb.charAt(sb.length() - 1))) {
			sb.deleteCharAt(sb.length() - 1);
		}
		return sb.toString();
	}

	/**
	 * Capitalize a <code>String</code>, changing the first letter to
	 * upper case as per {@link Character#toUpperCase(char)}.
	 * No other letters are changed.
	 * @param str the String to capitalize, may be <code>null</code>
	 * @return the capitalized String, <code>null</code> if null
	 */
	public static String capitalize(String str) {
		return changeFirstCharacterCase(str, true);
	}

	/**
	 * Uncapitalize a <code>String</code>, changing the first letter to
	 * lower case as per {@link Character#toLowerCase(char)}.
	 * No other letters are changed.
	 * @param str the String to uncapitalize, may be <code>null</code>
	 * @return the uncapitalized String, <code>null</code> if null
	 */
	public static String uncapitalize(String str) {
		return changeFirstCharacterCase(str, false);
	}

	private static String changeFirstCharacterCase(String str, boolean capitalize) {
		if (str == null || str.length() == 0) {
			return str;
		}
		StringBuilder sb = new StringBuilder(str.length());
		if (capitalize) {
			sb.append(Character.toUpperCase(str.charAt(0)));
		}
		else {
			sb.append(Character.toLowerCase(str.charAt(0)));
		}
		sb.append(str.substring(1));
		return sb.toString();
	}
	//////////////FROM SPRING///////////////////////////////////////////////////


}

