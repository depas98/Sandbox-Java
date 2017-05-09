package com.depas.hackerrank;

import java.util.Arrays;

public class QuickSort {

	/*

	algorithm quicksort(A, lo, hi) is
	    if lo < hi then
	        p := partition(A, lo, hi)
	        quicksort(A, lo, p � 1)
	        quicksort(A, p + 1, hi)
	        
	algorithm partition(A, lo, hi) is
	    pivot := A[hi]
	    i := lo        // place for swapping
	    for j := lo to hi � 1 do
	        if A[j] <= pivot then
	            swap A[i] with A[j]
	            i := i + 1
	    swap A[i] with A[hi]
	    return i
	
	    		
or

	algorithm quicksort(A, lo, hi) is
	    if lo < hi then
	        p := partition(A, lo, hi)
	        quicksort(A, lo, p)
	        quicksort(A, p + 1, hi)
	
	algorithm partition(A, lo, hi) is
	    pivot := A[lo]
	    i := lo � 1
	    j := hi + 1
	    loop forever
	        do
	            i := i + 1
	        while A[i] < pivot
	        
	        do
	            j := j � 1
	        while A[j] > pivot
	        
	        if i >= j then
	            return j
	        
	        swap A[i] with A[j]
	*/
	    		
	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		String[] strings = { " Hello ", " This ", "Is ", "Sorting ", "Example" };
		Arrays.sort(strings);

	}

}
