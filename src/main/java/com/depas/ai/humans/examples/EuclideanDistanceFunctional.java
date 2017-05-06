package com.depas.ai.humans.examples;

import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class EuclideanDistanceFunctional {

	public static double getDistance (double[] pos1, double[] pos2){
		return Math.sqrt(
					IntStream.range(0,pos2.length)
						.mapToDouble(i -> pos1[i] - pos2[i])
						.map(i -> i * i)
						.sum()
//						.reduce(Double::sum).orElse(0)
				);
			
	}
	
	public static void main(String[] args) {
		double d = EuclideanDistanceFunctional.getDistance(IrisData.IRIS_SETOSA, IrisData.IRIS_VERSICOLOR);
		System.out.println("Distance between setosa and versicolor: " + d);
		
		d = EuclideanDistanceFunctional.getDistance(IrisData.IRIS_SETOSA, IrisData.IRIS_VIRGINICA);
		System.out.println("Distance between setosa and versicolor: " + d);
		
		
	    Stream<String> s = Stream.of("1","2","3");
	    
	    double o =  s.collect(Collectors.summingDouble(n->Double.parseDouble(n)));

	    System.out.println(o);

	}

}
