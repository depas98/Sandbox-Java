package com.depas.functional.flatmap;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import static java.util.stream.Collectors.toList;

/**
 * Created by mike.depasquale on 7/7/2017.
 */
@SuppressWarnings("Duplicates")
public class Triples {

    // Euclid's algorithm Pythagorean Triples
    public static String getTriple(int m, int n) {
        int a = m * m - n * n;
        int b = 2 * m * n;
        int c = m * m + n * n;

        return String.format("%d %d %d", a, b, c);

    }

    public static List<String> computeTriples(int numberOfValues) {
        List<String> triples = new ArrayList<>();
        int count = 1;

        for (int m = 2; ; m++) {
            for (int n = 1; n < m; n++) {
                triples.add(getTriple(m, n));
                count++;

                if (count > numberOfValues) {
                    break;
                }
            }

            if (count > numberOfValues) {
                break;
            }
        }

        return triples;
    }

    public static List<String> computeTriplesFunc(int numberOfValues) {
        return Stream.iterate(2, e -> e + 1)
                .flatMap(m -> IntStream.range(1, m)
                        .mapToObj(n -> getTriple(m, n))
                )
                .limit(numberOfValues)
                .collect(toList());
    }


    public static void main(String[] args) {
        // values of a, b, c where a**2 + b**2 = c**2
        computeTriples(10).forEach(System.out::println);

        System.out.println("***********************");

        computeTriplesFunc(10).forEach(System.out::println);
    }


}
