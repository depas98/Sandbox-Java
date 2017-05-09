package com.depas.hackerrank;

import java.util.Scanner;

public class MoreExceptions {

	public static void main(String[] args) {		
        Scanner in = new Scanner(System.in);
        int T=in.nextInt();
        while(T--> 0)
        {
            int n = in.nextInt();
            int p = in.nextInt();
            Calculator2 myCalculator = new Calculator2();
            try
            {
                int ans=myCalculator.power(n,p);
                System.out.println(ans);
                
            }
            catch(Exception e)
            {
                System.out.println(e.getMessage());
            }
        }
	}

}
