package com.depas.hackerrank;

import java.util.Scanner;

public class Datatypes {

	public static void main(String[] args) {


//        Scanner sc = new Scanner(System.in);
//        int t=sc.nextInt();
//
//        for(int i=0;i<t;i++)
//        {
//            try
//            {
//                long x=sc.nextLong();
//                System.out.println(x+" can be fitted in:");
//                if(x>=-128 && x<=127)System.out.println("* byte");
//                //Complete the code
//                if(x>=Short.MIN_VALUE && x<=Short.MIN_VALUE)System.out.println("* short");
//                if(x>-Integer.MIN_VALUE && x< Integer.MAX_VALUE)System.out.println("* int");
//                System.out.println("* long");
//            }
//            catch(Exception e)
//            {
//                System.out.println(sc.next()+" can't be fitted anywhere.");
//            }
//
//        }
		
		
	    int i = 4;
        double d = 4.0;
        String s = "HackerRank ";
		
        Scanner scan = new Scanner(System.in);
        
        /* Declare second integer, double, and String variables. */
	    int i2 = 0;
        double d2 = 0;
        String s2 = "";
        
        /* Read and save an integer, double, and String to your variables.*/
        i2=scan.nextInt();
        d2=scan.nextDouble();
        scan.nextLine();        
        s2 = scan.nextLine();
                             
        /* Print the sum of both integer variables on a new line. */
        System.out.println(i + i2);

        /* Print the sum of the double variables on a new line. */
        System.out.println(d + d2);
		
        /* Concatenate and print the String variables on a new line; 
        	the 's' variable above should be printed first. */
        System.out.println(s + s2);
        
        
        scan.close();
	}

}
