package com.depas.hackerrank;

import java.util.Scanner;

public class RunningTimeComplexity {

	public static boolean isPrime(int n){
		if (n == 1) return false;
	    if (n == 2) return true;
	    if (n == 3) return true;
	    if (n % 2 == 0) return false;
	    if (n % 3 == 0) return false;

	    int i = 5;
	    int w = 2;

	    while (i * i <= n){
	        if (n % i == 0)
	            return false;
	        i += w;
	        w = 6 - w;	    	
	    }
	    return true;
	}
	
	public static void main(String[] args) {
        Scanner in = new Scanner(System.in);
        int t=in.nextInt();
        for(int i=0;i<t;i++){
            int n = in.nextInt();
            
            String s = isPrime(n) ? "Prime" : "Not prime";            
            System.out.println(s);
        }
        in.close();

	}

}
