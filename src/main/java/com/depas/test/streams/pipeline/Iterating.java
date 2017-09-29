package com.depas.test.streams.pipeline;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import static java.util.stream.Collectors.toList;

public class Iterating {

    public static List<Car> createCars() {
        return Arrays.asList(
                new Car("Jeep", "Wrangler", 2011),
                new Car("Jeep", "Comanche", 1990),
                new Car("Dodge", "Avenger", 2010),
                new Car("Buick", "Cascada", 2016),
                new Car("Ford", "Focus", 2012),
                new Car("Chevrolet", "Geo Metro", 1992)
        );
    }

    public static List<String> getModelsAfter2000UsingFor(List<Car> cars) {
        List<Car> carsSortedByYear = new ArrayList<>();

        if (cars==null){
            return new ArrayList<String>();
        }

        for(Car car : cars) {
            if(car.getYear() > 2000) {
                carsSortedByYear.add(car);
            }
        }

	    Collections.sort(carsSortedByYear, new Comparator<Car>() {
	        public int compare(Car car1, Car car2) {
	            return new Integer(car1.getYear()).compareTo(car2.getYear());
	        }
        });

	    List<String> models = new ArrayList<>();
	    for(Car car : carsSortedByYear) {
	        models.add(car.getModel());
	    }

	    return models;
    }

    public static List<String> getModelsAfter2000UsingFun2(List<Car> cars){
        if (cars==null){
            return Collections.emptyList();
        }

        return cars.stream()
                    .filter(c -> c.getYear() > 2000)
                    .sorted(Comparator.comparingInt(Car::getYear))
                    .map(Car::getModel)
                    .collect(toList());
    }

    public static List<String> getModelsAfter2000UsingFun(List<Car> cars) {
        if (cars==null){
            return new ArrayList<String>();
        }

        return  cars.stream()
                .filter(car -> car.getYear() > 2000)
                .sorted(Comparator.comparingInt(Car::getYear))
                .map(Car::getModel)
                .collect(toList());
    }

    public static void main(String[] args) {
        List<Car> cars = createCars();

        //imperative style
        System.out.println(getModelsAfter2000UsingFor(cars));

        System.out.println("************ The Fun Way **************");

        // Functional
        System.out.println(getModelsAfter2000UsingFun(cars));

    }
}
