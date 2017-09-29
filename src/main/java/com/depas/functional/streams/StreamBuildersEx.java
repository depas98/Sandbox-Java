package com.depas.functional.streams;

import com.depas.functional.Beer;
import com.depas.functional.BeerFactory;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class StreamBuildersEx {
    public static void main(String[] args) {

        // Using Stream.of(val, val2, val3...)
//        Stream<Integer> stream1 = Stream.of(1,2,3,4,5,6,7,8,9);
//        stream1.forEach(p -> System.out.println(p));

        // Using Stream.of(arrayOfElements)
//        Integer[] arr1 = new Integer[]{1,2,3,4,5,6,7,8,9};
//        Stream<Integer> stream2 = Stream.of(arr1);
//        stream2.forEach(p -> System.out.println(p));

        // Using someList.stream()
//        List<Integer> list = new ArrayList<>();
//        for(int i = 1; i< 10; i++){
//            list.add(i);
//        }
//        Stream<Integer> stream3 = list.stream();
//
//        stream3.forEach(p -> System.out.println(p));

        // Using Stream.generate() function, infinite loop if no limit it will run indefinitely
//        Stream<Date> stream4 = Stream.generate(() -> { return new Date();});
//        stream4
//                .limit(20)
//                .forEach(System.out::println);
//
//
//        Stream<Integer> streamGenNum = Stream.generate(() -> (int) (Math.random() * 100)).limit(10);
//        streamGenNum.forEach(System.out::println);


        // Stream.iterate() functions, infinite loop
//        Stream.iterate(1, e -> e * 2)
//                .limit(20)
//                .forEach(System.out::println);

        // Special kinds of streams for  primitive data types int, long and double: IntStream, LongStream and DoubleStream.
        // IntStreams can replace the regular for-loop utilizing IntStream.range():

//        IntStream.range(1, 4)
//                .forEach(System.out::println);
//
//        IntStream.rangeClosed(1, 4)
//                .forEach(System.out::println);


        // using the Stream.Builder
//        Stream.Builder <String> beers = Stream.builder();
//        beers.accept("IPA");
//        beers.accept("Lager");
//        beers.accept("Porter");
//
//        Stream <String> stream = beers.build();
//        stream.forEach(System.out::println);

        // Streaming maps
        List<Beer> beers = BeerFactory.getBeers();

        Map<String, Beer> beerMap = beers.stream().collect(
                Collectors.toMap(Beer::getName, b -> b ));

        System.out.println("-----Keyset-----");
        beerMap.keySet().forEach(System.out::println);
        System.out.println("-----Values-----");
        beerMap.values().forEach(System.out::println);
        System.out.println("-----Entryset-----");
        beerMap.entrySet().forEach(e -> System.out.println(e.getKey() + " " + e.getValue()));

        //or

        beerMap.forEach((k, v) -> System.out.println(k + " " + v));

    }
}
