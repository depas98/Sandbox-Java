package com.depas.functional.optional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;

public class OptionalExample {

    public static String getCarInsuranceName(Person person) {
        return Optional.ofNullable(person)
//                .map(Person::getCar)
                .flatMap(Person::getCar)
//                .map(Car::getInsurance)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown");                // default value if the resulting Optional is empty
    }

    public static String getCarInsuranceName(Person person, int minAge) {
        return Optional.ofNullable(person)
                .filter(p -> p.getAge() >= minAge)
                .flatMap(Person::getCar)
                .flatMap(Car::getInsurance)
                .map(Insurance::getName)
                .orElse("Unknown or age is too low");
    }


    public static void main(String[] args) {

        List<Person> personList = PersonFactory.getPersonList();

        // Optionals with Map
        System.out.println("################### Optionals with Map ##############################");
        Insurance insurance1 = new Insurance("ins name test");
        Optional<Insurance> optInsurance = Optional.ofNullable(insurance1);
        Optional<String> name = optInsurance.map(Insurance::getName);
//        String name = optInsurance.map(Insurance::getName).orElse("");
        System.out.println(name);

        optInsurance = Optional.ofNullable(null);
        name = optInsurance.map(Insurance::getName);
//        String name = optInsurance.map(Insurance::getName).orElse("");
        System.out.println(name);

        // Optionals with flatMap
        System.out.println("################### Optionals with flatMap ##############################");
        personList.stream()
                .map(OptionalExample::getCarInsuranceName)
//                .distinct()
                .forEach(System.out::println);

        // Optional filter
        System.out.println("################### Optionals with filter ##############################");
        personList.stream()
                .map(p -> getCarInsuranceName(p, 25))
//                .distinct()
                .forEach(System.out::println);


//
//        personList.stream()
//                .map(Person::getCar)
//
//                .filter(p -> p.getCar())
//                .findAny();

    }
}
