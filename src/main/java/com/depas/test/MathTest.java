package com.depas.test;

import org.apache.commons.math.util.MathUtils;

import java.math.BigDecimal;

public class MathTest {

	public MathTest() {
		
	}

	public static void main(String[] args) {
		
		
		int indexNullBitmap = 2 + ((4 + 7)/8);
		
		System.out.println(indexNullBitmap);

		int test = 5 /3 ;
		
		System.out.println(test);
		
		float test2 = 5f / 3f ;
		
		System.out.println(test2);

		double test3 = 5d / 3 ;
		
		System.out.println(test3);
		
		long long1 = 22;
		int int1 = 7;
		
		int answer = (int) Math.ceil((float)long1/int1);
		
		System.out.println("answer: " + answer);
		
		int modTest = 10 % 5;
		System.out.println(modTest);
		modTest = 10 % 4;
		System.out.println(modTest);
		
		Double logTest = new Double(Math.log(14540/10)/Math.log(10));
		
		long logLong = logTest.longValue();
		
		System.out.println("logTest: " + logTest + " long value: " + logLong);
		
		if (logTest > logLong){
			logLong++;
		}
	
		System.out.println("Long value: " + logLong);
		
		
		logTest = new Double(Math.log(1000/10)/Math.log(10));
		
		logLong = logTest.longValue();
		
		System.out.println("logTest: " + logTest + " long value: " + logLong);
		
		if (logTest > logLong){
			logLong++;
		}
	
		System.out.println("Long value: " + logLong);
		
		
		int numLeafPages = 270;
		int indexRowsPerPage = 352;

//		numLeafPages = 864;
//		indexRowsPerPage = 110;
		
		Double nonLeafLevelsCalc = new Double(Math.log(numLeafPages/indexRowsPerPage)/Math.log(indexRowsPerPage));
		System.out.println("nonLeafLevelsCalc: " + nonLeafLevelsCalc);

		
		int value = 5;
		int power = 3;
		double pAnswer = Math.pow(value, power);
		
		System.out.println("powerTest -  value: " + value + " power: " + power + " answer: " + pAnswer);


		final double qpInterval = .25;
		long rows = (long) (100/ qpInterval);
		System.out.println("division rows = " + rows);


		System.out.println("test math round precision: " + MathUtils.round(100.3455,2));
		System.out.println("test math round precision: " + MathUtils.round(100.3455,2, BigDecimal.ROUND_HALF_UP));

		System.out.println("test roundHelper precision: " + roundHelper(100.3455,2));
	}

	private static double roundHelper(double value, double precision) {
		double scale = Math.pow(10, precision);
		return ((double)Math.round(value * scale)) / scale;
	}

}
  