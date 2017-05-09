package com.depas.hackerrank;

import java.util.Scanner;

public class NestedLogic {

	public static void main(String[] args) {
	      Scanner scan = new Scanner(System.in);
	      int d1 = scan.nextInt(); 
	      int m1 = scan.nextInt(); 
	      int y1 = scan.nextInt();
	      
	      scan.nextLine();
	      
	      int d2 = scan.nextInt(); 
	      int m2 = scan.nextInt(); 
	      int y2 = scan.nextInt();
	      
	      int diffD = d1 - d2;
	      int diffM = m1 - m2;
	      int diffY = y1 - y2;
	      
	      int fine = 0;
	      if (diffY > 0){
	    	  fine = 10000;
	      }
	      else if (diffM>0 && diffY==0){
	    	  fine = 500 * diffM;
	      }
	      else if (diffD > 0 && diffM==0 && diffY==0){
	    	  fine = 15 * diffD;
	      }
	    	  
	      System.out.println(fine);	      	      
	      
	      scan.close();
	}

}
