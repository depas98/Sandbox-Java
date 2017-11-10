package com.depas.functional.grouping;

import com.depas.functional.Beer;
import com.depas.functional.BeerFactory;
import com.depas.functional.BeerType;
import com.depas.functional.CaloricLevel;

import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;

import static java.util.Comparator.comparingDouble;
import static java.util.Comparator.comparingInt;
import static java.util.stream.Collectors.*;

@SuppressWarnings("Duplicates")
public class BeerGrouping {

    private static Function<Beer, CaloricLevel> getBeerCaloricLevel(){
        return b -> {
            if (b.getCalories() <= 175) {
                return CaloricLevel.DIET;
            } else if (b.getCalories() <= 200) {
                return CaloricLevel.NORMAL;
            } else {
                return CaloricLevel.FAT;
            }
        };
    }

    public static void main(String[] args) {
        List<Beer> beers = BeerFactory.getBeers();


        // Method Reference Grouping, get beer by type
        Map<BeerType, List<Beer>> beersByType = beers.stream()
                .collect(groupingBy(Beer::getType));

        System.out.println("********* Method Reference ************");
        // we can do better print out than this
        System.out.println(beersByType);
        System.out.println("********* Method Reference better print out ************");
        beersByType.forEach((key, value) -> System.out.println(key +
                ": " +
                value.stream()
                        .map(Beer::getName)
                        .collect(joining(", "))));

        // Decision Grouping
        Map<CaloricLevel, List<Beer>> beerByCaloricLevel = beers.stream()
                .collect(groupingBy(b -> {
                            if (b.getCalories() <= 175){
                                return CaloricLevel.DIET;
                            }
                            else if (b.getCalories() <= 200){
                                return CaloricLevel.NORMAL;
                            }
                            else{
                                return CaloricLevel.FAT;
                            }
                        }));

        System.out.println("********* Decision Grouping 1 ************");
        beerByCaloricLevel.forEach((key, value) -> System.out.println(key +
                ": " +
                value.stream()
                        .map(b -> b.getName() + "(" + b.getCalories() + ")")
                        .collect(joining(", "))));

        // don't do above have helper methods mush more easier to read, create a getCaloricLevel function on Beer

        beerByCaloricLevel = beers.stream()
                .collect(groupingBy(Beer::getCaloricLevel));

        System.out.println("********* Decision Grouping 2 ************");
        beerByCaloricLevel.forEach((key, value) -> System.out.println(key +
                ": " +
                value.stream()
                        .map(b -> b.getName() + "(" + b.getCalories() + ")")
                        .collect(joining(", "))));

        // or

        beerByCaloricLevel = beers.stream()
                .collect(groupingBy(getBeerCaloricLevel()));

        System.out.println("********* Decision Grouping 3 ************");
        beerByCaloricLevel.forEach((key, value) -> System.out.println(key +
                ": " +
                value.stream()
                        .map(b -> b.getName() + "(" + b.getCalories() + ")")
                        .collect(joining(", "))));

        // You can extend to any n-levels of grouping which will give you a n-level Map


        // Multi_Level Grouping
        Map<BeerType, Map<CaloricLevel, List<Beer>>> beerByTypeCaloricLevel = beers.stream()
                .collect(groupingBy(Beer::getType,
                        groupingBy(b -> {
                            if (b.getCalories() <= 175){
                                return CaloricLevel.DIET;
                            }
                            else if (b.getCalories() <= 200){
                                return CaloricLevel.NORMAL;
                            }
                            else{
                                return CaloricLevel.FAT;
                            }
                        })));

        System.out.println("********* Multi_Level Grouping 1 ************");
        System.out.println(beerByTypeCaloricLevel);

        // again replace with a helper reference method

        beerByTypeCaloricLevel = beers.stream()
                .collect(groupingBy(Beer::getType,
                        groupingBy(Beer::getCaloricLevel)));

        System.out.println("********* Multi_Level Grouping 2 ************");
        System.out.println(beerByTypeCaloricLevel);

        // Collecting Data in Subgroups
        // Counting, Summing, Max, Min items via a group


        // Counting
        Map<BeerType, Long> typesCount = beers.stream()
                .collect(groupingBy(Beer::getType, counting()));

        System.out.println("********* Counting ************");
        System.out.println(typesCount);

        // Summing
        Map<BeerType, Integer> totalCaloriesByType = beers.stream()
                .collect(groupingBy(Beer::getType, summingInt(Beer::getCalories)));

        System.out.println("********* Summing ************");
        System.out.println(totalCaloriesByType);

        // MaxBy
        Map<BeerType, Optional<Beer>> mostCaloricByType  = beers.stream()
                .collect(groupingBy(Beer::getType, maxBy(comparingInt(Beer::getCalories))));

        System.out.println("********* Max By ************");
        System.out.println(mostCaloricByType);

        // can do better by using collectingAndThen to get the data from the Optional
        Map<BeerType, Beer> mostCaloricByType2  = beers.stream()
                .collect(groupingBy(Beer::getType,
                        collectingAndThen(
                            maxBy(comparingInt(Beer::getCalories)),
                            Optional::get)));

        System.out.println("********* Max By with collectingAndThen ************");
        mostCaloricByType2.forEach((key, value) -> System.out.println(key +
                ": " +
                value.getName() + "(" + value.getCalories() + ")"));

        // using the mapping method
        // the method takes two argument a function transforming the elements in the stream
        // and a further collector accumulating the objects resulting from this transformation.
        Map<BeerType, Set<CaloricLevel>> caloricLevelsByType =
                beers.stream()
                        .collect(groupingBy(Beer::getType, mapping(
                                Beer::getCaloricLevel, toSet())));

        System.out.println("********* Mapping function ************");
        System.out.println(caloricLevelsByType);


        // using toCollection for more control last example no telling what type of set we have
        Map<BeerType, Set<CaloricLevel>> caloricLevelsByType2 =
                beers.stream()
                        .collect(groupingBy(Beer::getType, mapping(
                                Beer::getCaloricLevel, toCollection(HashSet::new))));

        System.out.println("********* Mapping function using toCollection ************");
        System.out.println(caloricLevelsByType2);

        // Partitioning
        // A special case of grouping having a predicate, the resulting grouping Map
        // will have a Boolean as a key type therefor there can be at most two different groups.

        Map<Boolean, List<Beer>> partitionedBeer =
                beers.stream().collect(partitioningBy(Beer::isDrinkable));

        System.out.println("********* Partitioned ************");
        partitionedBeer.forEach((key, value) -> System.out.println((key ? "Drinkable" : "UnDrinkable") +
                ": " +
                value.stream()
                        .map(Beer::getName)
                        .collect(joining(", "))));

        Map<Boolean, Long> partitionedBeerCount =
                beers.stream().collect(partitioningBy(Beer::isDrinkable, counting()));

        System.out.println("********* Partitioned Counting ************");
        System.out.println(partitionedBeerCount);

        // Note: could have filtered to get the list, but this gives both list

        // Also the partitionBy factory method has an overloaded version to which you can pass a second collector
        Map<Boolean, Map<BeerType, List<Beer>>> drinkableBeerByType = beers.stream().collect(
                partitioningBy(Beer::isDrinkable,
                        groupingBy(Beer::getType)));

        System.out.println("********* Partitioned and Grouping ************");
        System.out.println(drinkableBeerByType);


        Map<Boolean, Beer> highestRatingPartitionByDrinkable = beers.stream().collect(
                partitioningBy(Beer::isDrinkable,
                        collectingAndThen(
                            maxBy(comparingDouble(Beer::getRating)),
                            Optional::get
                        )));

        System.out.println("********* Partitioned and CollectingAndThen ************");
        System.out.println(highestRatingPartitionByDrinkable);

    }
}
