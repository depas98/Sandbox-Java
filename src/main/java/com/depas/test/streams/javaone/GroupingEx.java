package com.depas.test.streams.javaone;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static java.util.stream.Collectors.groupingBy;

/**
 * Created by mike.depasquale on 7/7/2017.
 */
public class GroupingEx {

    public static Map<Integer, List<String>> groupByScores(Map<String, Integer> scores){
        Map<Integer, List<String>> byScores = new HashMap<>();
        for(String name : scores.keySet()) {
            int score = scores.get(name);

            List<String> names = new ArrayList<>();
            if (byScores.containsKey(score)){
                names = byScores.get(score);
            }

            names.add(name);
            byScores.put(score, names);
        }

        return byScores;
    }

    public static Map<Integer, List<String>> groupByScoresFun2(Map<String, Integer> scores){
        return scores.keySet()
                    .stream()
                    .collect(groupingBy(scores::get));
    }

    public static Map<Integer, List<String>> groupByScoresFun(Map<String, Integer> scores){
        return scores.keySet().stream()
                .collect(groupingBy(scores::get));
    }

    public static void main(String[] args) {
        Map<String, Integer> scores = new HashMap<>();

        scores.put("Jack", 12);
        scores.put("Jill", 15);
        scores.put("Tom", 11);
        scores.put("Darla", 15);
        scores.put("Nick", 15);
        scores.put("Nancy", 11);

        System.out.println(groupByScores(scores));

        System.out.println("********** The Fun Way ***********");

        System.out.println(groupByScoresFun2(scores));

    }

}
