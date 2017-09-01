package com.depas.test.streams.javaone;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import static java.util.stream.Collectors.*;

public class BeerEx {

    public static List<String> getBeer(final int numberOfBeers) {
        int n = 1;
        List<String> beerList = new ArrayList<>();
        while (n <= numberOfBeers) {
            beerList.add("I drank "+ n + " glasses of beer");
            n++;
        }
        return beerList;
    }

    public static List<String> getBeerFun(final int numberOfBeers) {
        return IntStream.rangeClosed(1, numberOfBeers)
                .mapToObj(n -> "I drank " + n + " glasses of beer")
                .collect(toList());
    }


    public static void main(String[] args) {

        System.out.println(getBeer(5));

        System.out.println("--------------- The Fun way -----------------------");

        System.out.println(getBeerFun(5));
    }
}
