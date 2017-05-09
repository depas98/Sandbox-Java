package com.depas.hackerrank;

import java.util.Scanner;

interface AdvancedArithmetic {
	   int divisorSum(int n);
}

class Calculator implements AdvancedArithmetic { 

	@Override
	public int divisorSum(int n) {
		
//		int sum = 0;
//		for (int i = 1; i <= n; i++) {
//			if (i==1 || n % i == 0)
//			sum = sum + i;
//		}
//		return sum;
		
		return java.util.stream.IntStream.rangeClosed(1, n)
				.filter(i -> n % i == 0)
				.sum();
	}
	
}

public class Interfaces {

	public static void main(String[] args) {
        Scanner scan = new Scanner(System.in);
        int n = scan.nextInt();
        scan.close();
        
      	AdvancedArithmetic myCalculator = new Calculator(); 
        int sum = myCalculator.divisorSum(n);
        System.out.println("I implemented: " + myCalculator.getClass().getInterfaces()[0].getName() );
        System.out.println(sum);
	}

}
