package com.depas.hackerrank;

import java.util.Scanner;

public class Recursion {

	public static int factorial(int n){
		if (n<=1){
			return 1;
		}
		else{
			return n * factorial(n-1);
		}
	}
	
	public static void main(String[] args) {
		try (Scanner in = new Scanner(System.in)){	        
	        int n = in.nextInt();	
	        n = factorial(n);
	        System.out.println(n);
		}

	}
}
