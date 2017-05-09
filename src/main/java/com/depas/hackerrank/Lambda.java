package com.depas.hackerrank;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.StringTokenizer;
import java.util.stream.LongStream;

public class Lambda {

	public static void main(String[] args) throws IOException {
		// TODO Auto-generated method stub
		  MyMath ob = new MyMath();
		  BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
		  int T = Integer.parseInt(br.readLine());
		  PerformOperation op;
		  boolean ret = false;
		  String ans = null;
		  while (T--> 0) {
		   String s = br.readLine().trim();
		   StringTokenizer st = new StringTokenizer(s);
		   int ch = Integer.parseInt(st.nextToken());
		   int num = Integer.parseInt(st.nextToken());
		   if (ch == 1) {
		    op = ob.is_odd();
		    ret = ob.checker(op, num);
		    ans = (ret) ? "ODD" : "EVEN";
		   } else if (ch == 2) {
		    op = ob.is_prime();
		    ret = ob.checker(op, num);
		    ans = (ret) ? "PRIME" : "COMPOSITE";
		   } else if (ch == 3) {
		    op = ob.is_palindrome();
		    ret = ob.checker(op, num);
		    ans = (ret) ? "PALINDROME" : "NOT PALINDROME";

		   }
		   System.out.println(ans);
		  }
		  
		  MyMath.isPrime(11);
	}

}

interface PerformOperation {
	 boolean check(int a);
}

class MyMath {
	 public boolean checker(PerformOperation p, int num) {
	  return p.check(num);
	 }
	 
	 public PerformOperation is_odd(){
		 return i -> i %2 != 0;
	 }
	 
	 public PerformOperation is_prime(){
		 return i -> i==2 || (i %2 != 0 && LongStream.rangeClosed(2, (long)(Math.sqrt(i)))
		            .allMatch(n -> i % n != 0));	 
	 }
	 
	 public PerformOperation is_palindrome(){
		 return i -> {
			 int n = i;
			 int rev = 0;
			 while (i > 0)
			 {
			      int dig = i % 10;
			      rev = rev * 10 + dig;
			      i = i / 10;
			 }
			 return n == rev;
		 };
	 }
	 
	 public static boolean isPrime(long x) {
		    for (long n = 2; n <= Math.sqrt(x); n++) {
		        if (x % n == 0) {
		            return false;
		        }
		    }
		    return true;
	}
}
