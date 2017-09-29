package com.depas.functional.flatmap;

import java.util.Arrays;
import java.util.List;
import static java.util.stream.Collectors.toList;


public class ListOfPairs {

    public static void main(String[] args) {
        // Given a list [1,2,3] and a list [3,4] return [(1, 3), (1,4),(2,3),(2,4),(3,3),(3,4)]
        // represent a pair as an array with two elements.

        List<Integer> numbers1 = Arrays.asList(1,2,3);
        List<Integer> numbers2 = Arrays.asList(3,4);

        List<int[]> pairs =
                numbers1.stream()
                        .flatMap(i -> numbers2.stream()
                                .map(j -> new int[] {i,j}))
                        .collect(toList());


        pairs.forEach(p -> System.out.println("(" + p[0] + ", " + p[1] + ")"));
    }
}
