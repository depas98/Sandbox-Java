package com.depas.test;

public class StringTest {

	public static int findSpaceAfterNChars(String text, int numChars){
		
		if (text.length()<numChars){
			return -1;
		}
		
		while (true) {
			if (text.length()>numChars){
				if (text.substring(numChars,numChars+1).equals(" ") || text.substring(numChars,numChars+1).equals(",")){
					return numChars;				}
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
	 * @param args
	 */
	public static void main(String[] args) {
			
		int index = findSpaceAfterNChars("1234567890",30);
		System.out.println("1) index is " + index);
		
		String testStr = "12345678901234567890 1234567890 1234567890";
		index = findSpaceAfterNChars(testStr,30);
		System.out.println("2) index is " + index + " first half is [" + testStr.substring(0,index)  + "]" + " last half is [" + testStr.substring(index).trim()  + "]");		
		
		testStr = "12345678901234567890 123456789012345 1234567890";
		index = findSpaceAfterNChars(testStr,30);
		System.out.println("3) index is " + index + " first half is [" + testStr.substring(0,index)  + "]" + " last half is [" + testStr.substring(index+1).trim()  + "]");	
		
		testStr = "123456789012345678901234567890123451234567890";
		index = findSpaceAfterNChars(testStr,30);
		System.out.println("4) index is " + index + " first half is [" + testStr.substring(0,index)  + "]" + " last half is [" + testStr.substring(index).trim()  + "]");
		
		testStr = "12345678901234567890123456789012345,1234567890";
		index = findSpaceAfterNChars(testStr,30);
		System.out.println("5) index is " + index + " first half is [" + testStr.substring(0,index)  + "]" + " last half is [" + testStr.substring(index).trim()  + "]");	
		
		
		// backslash
		String s = "12345\\12345\\12345";
		System.out.println(s);
		s = s.replaceAll("\\\\", "\\\\\\\\");
		System.out.println(s);
		
		// underscore
		String escStr = "\\\\_";
		s = "12345_12345_12345";
		System.out.println(s);
		s = s.replaceAll("_", escStr);
		System.out.println(s);		

		for (int i = 0; i < 25; i++) {
			System.out.println("hi my name is Jason. I'm " + (i+1) +" years old today.");	
		}
		
		long f0=0;
		System.out.println("fibonnacci number 0 = " + f0);
		long f1=1;
		System.out.println("fibonnacci number 1 = " + f1);
		for (int i = 0; i < 80; i++) {
			long fnew = f0+f1;
			System.out.println("fibonnacci number " + (i+2) + " = " + fnew);
			f0=f1;
			f1=fnew;
		}
	}

}
