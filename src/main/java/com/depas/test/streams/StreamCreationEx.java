package com.depas.test.streams;

import java.util.Date;
import java.util.stream.Stream;

public class StreamCreationEx {

    public static void main(String[] args) {


        // will generate a stream of dates, limit 10, if no limit it will run indefinitely
        Stream<Date> streamGenDate = Stream.generate(Date::new).limit(10);
        streamGenDate.forEach(System.out::println);


        Stream<Integer> streamGenNum = Stream.generate(() -> (int) (Math.random() * 100)).limit(10);
        streamGenNum.forEach(System.out::println);
    }

}
