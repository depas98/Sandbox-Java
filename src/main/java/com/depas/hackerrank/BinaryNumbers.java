package com.depas.hackerrank;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Scanner;

public class BinaryNumbers {

	public static int countSuccessiveValues(String in, char target){
		if (in==null || in.length()==0){
			return 0;
		}
		
		char[] chars = in.toCharArray();
		
		int max = 0;	
		int count=0;
		for (int i = 0; i < chars.length; i++) {			
			char curr = chars[i];
			if (curr==target){
				count++;
			}
			else{
				if (count>max){
					max=count;
				}
				count=0;
			}
		}
		if (count>max){
			max=count;
		}		
		
		return max;
	}
	
//	public static int consBits(int x) {
//	    int consecutiveBits = 0;
//	    for(;x;consecutiveBits++)
//	        x &= (x<<1);
//
//	    return consecutiveBits;
//	}
	
	public static void main(String[] args) {
        try (Scanner in = new Scanner(System.in)){
        	int n = in.nextInt();
        	
        	String b = Integer.toBinaryString(n);
        	System.out.println(b);
        	int count = countSuccessiveValues(b, '1');
        	System.out.println(count);

        	
        	// java 8 stream way
        	List<String> listOfConsecutiveOneStrings = Arrays.asList(b.split("0"));

        	System.out.println("test: " + listOfConsecutiveOneStrings.stream()
        	   												  .max(Comparator.comparing(String::length)) 
        													  .map(onesSequence -> new Integer(onesSequence.length()))        	                                                      	                                                  
        	                                                  .get());
        }
        

	}

}
