package com.depas.hackerrank;

import java.util.Scanner;

public class BubbleSort {

	public static int sort(int[] a){
		int totalSwaps=0;
		for (int i = 0; i < a.length; i++) {
		    // Track number of elements swapped during a single array traversal
		    int numberOfSwaps = 0;
		    
		    for (int j = 0; j < a.length - 1; j++) {
		        // Swap adjacent elements if they are in decreasing order
		        if (a[j] > a[j + 1]) {
		            //swap(a[j], a[j + 1]);
		        	int temp = a[j];
		        	a[j] = a[j + 1];
		        	a[j + 1] = temp;
		        	
		            numberOfSwaps++;
		        }
		    }
		    
		    // If no elements were swapped during a traversal, array is sorted
		    if (numberOfSwaps == 0) {
		        break;
		    }
		    
		    totalSwaps=totalSwaps+numberOfSwaps;
		}		
		
		return totalSwaps;
	}
		
	public static void main(String[] args) {
		int[] a=null;
        try (Scanner sc = new Scanner(System.in);){
            int n = sc.nextInt();
            a = new int[n];
            for (int i = 0; i < n; i++) {
                a[i] = sc.nextInt();
            }        	
        }
        
        int swaps = sort(a);
        System.out.println("Array is sorted in " + swaps + " swaps.");
        System.out.println("First Element: " + a[0]);
        System.out.println("Last Element: " + a[a.length-1]);
	}

}
