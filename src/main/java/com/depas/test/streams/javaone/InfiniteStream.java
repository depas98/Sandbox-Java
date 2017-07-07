package com.depas.test.streams.javaone;

import java.util.stream.Stream;

/**
 * Created by mike.depasquale on 7/7/2017.
 */
public class InfiniteStream {
    public static double computeSumOfSqrtOfPrimes(int start, int count){
        int index = start;
        int computedCount = 0;
        double sum = 0;

		while(computedCount < count) {
            if (Prime.isPrimeImperative(index)) {
                sum += Math.sqrt(index);
                computedCount++;
            }
            index++;
        }

		return sum;
    }

    public static double computeSumOfSqrtOfPrimesFunc(int start, int count){
		return Stream.iterate(start, e -> e + 1)
                .filter(Prime::isPrimeFunctional)
                .mapToDouble(Math::sqrt)
                .limit(count)
                .sum();
    }

    public static void main(String[] args) {
        System.out.println(computeSumOfSqrtOfPrimes(101, 51));
        System.out.println("********************");
        System.out.println(computeSumOfSqrtOfPrimesFunc(101, 51));
    }

}
