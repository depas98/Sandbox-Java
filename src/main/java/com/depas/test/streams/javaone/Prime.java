package com.depas.test.streams.javaone;

import java.util.stream.IntStream;

/**
 * Created by mike.depasquale on 7/6/2017.
 */
public class Prime {
    public static boolean isPrimeImperative(int number) {
        boolean divisible = false;  // garbage variable
        for (int i=2; i<number; i++) {
            if (number % i==0){
                divisible = true;
                break;
            }
        }

        return number > 1 && !divisible;
    }

    public static boolean isPrimeFunctional(final int number) {
        return number > 1 &&
                IntStream.range(2, number)
                        .noneMatch(i -> number % i==0);  // high order function non-match
    }

    public static void main(String[] args) {
        for(int i=1; i<8 ; i++){
            System.out.printf("isPrimeImperative(%d)? %b\n", i, isPrimeImperative(i));
            System.out.printf("isPrimeFun(%d)? %b\n", i, isPrimeFunctional(i));
        }
    }

}
