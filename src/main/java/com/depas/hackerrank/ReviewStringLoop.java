package com.depas.hackerrank;

import java.util.Scanner;
import java.util.function.IntPredicate;
import java.util.stream.IntStream;

public class ReviewStringLoop {

	private static StringBuilder filter(String input, IntPredicate predicate) {
	    return IntStream.range(0, input.length())
	             .filter(predicate)
	             .map(input::charAt)
	             .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append);
	}
	
	public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t=in.nextInt();
        in.nextLine();
        for(int i=0;i<t;i++){
            String inStr = in.nextLine();
            
            System.out.print(filter(inStr, index -> index % 2 == 0) + " ");
            System.out.println(filter(inStr, index -> index % 2 != 0));
        }
        in.close();

	}

}
