package com.depas.functional.flatmap;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

public class WordToLetters {


    public static void main(String[] args) {

        //If have list of words {"Hello", "World"] return the list ["H", "e", "l", "o", "W", "r", "d"]
        List<String> words = Arrays.asList("Hello", "World");

        // first attempt
        System.out.println("********* First attempt ************");
        List<String[]> letters1 = words.stream()
                .map(word -> word.split(""))    // converts each word into an array of its individual letters
                .distinct()
                .collect(toList());

        letters1.forEach(l -> Arrays.stream(l).forEach(System.out::println));

        // problem lambda in the map returns a String array for each word,
        // so stream returned by the map is the type Stream<String[]>
        // what we want is Stream<String>


        // second attempt Use Array.Stream()
        System.out.println("********* Second attempt ************");
        List<Stream<String>> letters2 = words.stream()
                .map(word -> word.split(""))      // converts each word into an array of its individual letters
                //.peek(v -> System.out.println(Arrays.toString(v)))
                .map(Arrays::stream)                    // Makes each array into a separate stream
                //.peek(System.out::println)
                .distinct()
                .collect(toList());

        letters2.forEach(s -> s.forEach(System.out::println));

        // Still doesn't work end up with a list of streams, the second map returns type Stream<Stream<String>>
        // what we want is Stream<String>

        // third attempt
        System.out.println("********* Third attempt ************");
        List<String> letters3 = words.stream()
                .map(word -> word.split(""))      // converts each word into an array of its individual letters
                .flatMap(Arrays::stream)                // Flattens each generated stream into a single stream
                .distinct()
                .collect(toList());

        letters3.forEach(System.out::println);

    }
}
