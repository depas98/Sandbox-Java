package com.depas.test.lamda;

import java.util.function.BiFunction;

public class BiFunctionTest {

	public static void main(String[] args) {

		 BiFunction<Integer, Integer, Integer> addition = ( x, y ) ->  Integer.valueOf(x.intValue() + y.intValue());
	     System.out.println( "calling addition of 2 and 3 resulting: " + addition.apply( Integer.valueOf(2), Integer.valueOf(3)) );

	}

}
