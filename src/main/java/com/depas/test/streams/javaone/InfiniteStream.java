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

    public static double computeSumOfSqrtOfPrimesFun2(int start, int count){
        return Stream.iterate(start, e -> e + 1)
                .filter(Prime::isPrimeFunctional2)
                .limit(count)
                .mapToDouble(Math::sqrt)
                .sum();
    }

    public static double computeSumOfSqrtOfPrimesFun(int start, int count){
		return Stream.iterate(start, e -> e + 1)
                .filter(Prime::isPrimeFunctional)
                .mapToDouble(Math::sqrt)
                .limit(count)
                .sum();
    }

    public static void main(String[] args) {
        System.out.println(computeSumOfSqrtOfPrimes(101, 51));
        System.out.println("*********  The Fun Way ***********");
        System.out.println(computeSumOfSqrtOfPrimesFun2(101, 51));
    }

}
