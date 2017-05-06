package com.depas.ai.humans.examples;

public class EuclideanDistanceImperative {
	public static double getDistance (double[] pos1, double[] pos2){
		double sum=0;
		for (int i = 0; i < pos2.length; i++) {
			double d = pos1[i] - pos2[i];
			sum = sum + d * d;
		} 
		
		return Math.sqrt(sum);
	}
	
	public static void main(String[] args) {
		
		double d = EuclideanDistanceImperative.getDistance(IrisData.IRIS_SETOSA, IrisData.IRIS_VERSICOLOR);
		System.out.println("Distance between setosa and versicolor: " + d);
		
		d = EuclideanDistanceImperative.getDistance(IrisData.IRIS_SETOSA, IrisData.IRIS_VIRGINICA);
		System.out.println("Distance between setosa and versicolor: " + d);
	}

}
