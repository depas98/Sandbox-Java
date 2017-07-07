package com.depas.test;

public class DoubleByte {

	public byte[] displayBytes(String in){
		try{
			byte[] textByte = in.getBytes("UTF-8");
			for (int i = 0; i < textByte.length; i++) {
				byte tByte = textByte[i];
				System.out.println("byte " + i + " [" + tByte + "]");
			}
			return textByte;
		}
		catch(Exception e){
			e.printStackTrace();
			return null;
		}		
	}
	
	/**
	 * 
	 * @param in
	 * @return
	 */
	public String getDoubleByteConvertedText(String in){
		StringBuilder result = new StringBuilder(256);
		for (int i = 0; i < in.length(); i++) {
			char inChar = in.charAt(i);
			System.out.println(in.charAt(i));
			
			result.append(inChar);
		}
		
		return result.toString();
	}
	
	/**
	 * Will take a string and if it contains unicode strings will convert them to the actual string.
	 * This function looks fore \\u and then the next four characters as the unicode string 
	 *  
	 * @param str
	 * @return a string with all unicode strings it contains switched to the actual character
	 */
	public static String convertUnicodeToText(String str){
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
					Integer code = Integer.parseInt(unicode.substring(2), 16); // the integer 65 in base 10
					char unicodeChar = Character.toChars(code)[0];					
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
	public static String convertToUnicode(String str)
	{
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
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		DoubleByte doubleByte = new DoubleByte();
		
		String text = "\\ubackslash²®²®1";
		System.out.println(text);				
//		byte[] textByte = doubleByte.displayBytes(text);	
//		try{
//			System.out.println("byte array to String UTF-8 " + new String(textByte,"UTF-8"));
//			for (byte b: textByte){
//				System.out.printf("%2x ", b);
//			}
//			
//			String string1 = new String(textByte,"ISO8859-1");
//			 
//			System.out.println("byte array to String ISO8859-1" +string1);
						
//		}
//		catch(Exception e){e.printStackTrace();}
		String unicodeString = DoubleByte.convertToUnicode(text);
		System.out.println("unicode String: " + DoubleByte.convertToUnicode(unicodeString));		
		String convertedString = DoubleByte.convertUnicodeToText(unicodeString);
		System.out.println("convertedString: " + convertedString);	
		
		System.out.println();
		
		text = "²®";
		System.out.println(text);
//		doubleByte.displayBytes(text);		
//		System.out.println(doubleByte.getDoubleByteConvertedText(text));
		unicodeString = DoubleByte.convertToUnicode(text);
		System.out.println("unicode String: " + DoubleByte.convertToUnicode(unicodeString));		
		convertedString = DoubleByte.convertUnicodeToText(unicodeString);
		System.out.println("convertedString: " + convertedString);			
		
		System.out.println();
		
		text = "\\some°¢englishÉ©inµØhere\\backslash\\";
		System.out.println(text);		
//		doubleByte.displayBytes(text);		
//		System.out.println(doubleByte.getDoubleByteConvertedText(text));
		unicodeString = DoubleByte.convertToUnicode(text);
		System.out.println("unicode String: " + DoubleByte.convertToUnicode(unicodeString));		
		convertedString = DoubleByte.convertUnicodeToText(unicodeString);
		System.out.println("convertedString: " + convertedString);		
		
		System.out.println();
		
		text = "plain english";
		System.out.println(text);
		unicodeString = DoubleByte.convertToUnicode(text);
		System.out.println("unicode String: " + DoubleByte.convertToUnicode(unicodeString));		
		convertedString = DoubleByte.convertUnicodeToText(unicodeString);
		System.out.println("convertedString: " + convertedString);	
//		doubleByte.displayBytes(text);		
//		System.out.println(doubleByte.getDoubleByteConvertedText(text));
		
//		for (int i = 1; i < 129; i++) {
//			if (i<40 || i>126){
//				System.out.println((86 % i) + 40);	
//			}
//			else{
//				// just use that number
//				System.out.println(i);
//			}
//			
//		}
		
		
		System.out.println();
		
		text = "CONFIO-PC|2|\\u4f2f\\u4f2f|CONFIO-PC\\u005c\\u4f2f\\u4f2f|0"; 
		System.out.println(text);	
		convertedString = DoubleByte.convertUnicodeToText(text);
		System.out.println("convertedString: " + convertedString);	
		
//		
//	    StringBuffer b = new StringBuffer();
//	    for (char c = 'a'; c < 'd'; c++) {
//	      b.append(c);
//	    }
//	    b.append('\u8240');
//	    b.append('\u81A0');
//	    b.append('\u0391'); // GREEK Capital Alpha
//	    b.append('\u03A9'); // GREEK Capital Omega
//	    b.append('9');
//	    b.append('F');
//
//	    for (int i = 0; i < b.length(); i++) {
//	      System.out.println("Character #" + i + " is " + b.charAt(i));
//	    }
//	    System.out.println("Accumulated characters are " + b);	
//	    
//	    String newString = b.toString();
//	    System.out.println("newString " + newString);
	    
	    
	    
	    
	    
	    
	}

}
