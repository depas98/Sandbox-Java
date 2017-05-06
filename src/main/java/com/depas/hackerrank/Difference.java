package com.depas.hackerrank;

public class Difference {
  	private int[] elements;
  	public int maximumDifference;
  	
	// Add your code here
  	public Difference(int[] a){
  		elements=a;
  	} 
  	
  	public void computeDifference(){
  		for (int i = 0; i < elements.length; i++) {
			for (int j = i + 1; j < elements.length; j++){
				int temp = Math.abs(elements[i] - elements[j]);
				if (temp>maximumDifference){
					maximumDifference=temp;
				}
			}
		}
  	}
}
