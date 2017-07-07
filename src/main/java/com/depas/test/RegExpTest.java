package com.depas.test;

import java.util.regex.Pattern;

public class RegExpTest {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String pattern = ".*\\d.*";
		String input = "ajhsahjdasjkkjsd";
//		
//		System.out.println("Contains numeric chars: " + Pattern.matches(pattern, input)); 		
//
//		input = "ajhsahj5dasjkkjsd";
//		
//		System.out.println("Contains numeric chars: " + Pattern.matches(pattern, input)); 
//		
//		pattern = ".*[a-zA-Z].*";
//		
//		input = "123456789";		
//		System.out.println("Contains alpha chars: " + Pattern.matches(pattern, input));
//		
//		input = "12b3456789";
//		
//		System.out.println("Contains alpha chars: " + Pattern.matches(pattern, input)); 
//		
//		input = "123456789A";
//		
//		System.out.println("Contains alpha chars: " + Pattern.matches(pattern, input)); 		
		

//		pattern = "^[\\s\\w#$*~.,+^@!<>\\\\\\-/]+$";
////		
////		//#$*_-~/.,+^@!<>\\
//		input = "12b3456789#$*_-~/.,+^@!<>\\";
//		
//		System.out.println("Contains valid chars: " + Pattern.matches(pattern, input)); 
//		
//		
//		input = "12b3456789%";
//		
//		System.out.println("Contains valid chars: " + Pattern.matches(pattern, input));
//		
//		input = "12b:3456789";
//		
//		System.out.println("Contains valid chars: " + Pattern.matches(pattern, input));
//		
//		input = "12b;3456789";
//		
//		System.out.println("Contains valid chars: " + Pattern.matches(pattern, input)); 		
//		
//		input = "12b'3456789";
//		
//		System.out.println("Contains valid chars: " + Pattern.matches(pattern, input)); 
//		
//		input = "12b\"3456789";
//		
//		System.out.println("Contains valid chars: " + Pattern.matches(pattern, input));
//		
//		input = "=12b3456789";
//		
//		System.out.println("Contains valid chars: " + Pattern.matches(pattern, input));
//		
//		input = "12`3456789";
//		
//		System.out.println("Contains valid chars: " + Pattern.matches(pattern, input));
//		
//		input = "12�3456789";
//		
//		System.out.println("Contains valid chars: " + Pattern.matches(pattern, input));
		
		String in = "[CONN_SERVICE_NAME]   NVarchar(200)    NULL,";
		in = in.replaceAll("NVARCHAR", "VARCHAR");
		in = in.replaceAll("NVarchar", "Varchar");
	
		System.out.println(in);
	}

}
