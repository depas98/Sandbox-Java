package com.depas.functional.optional;

import java.util.Optional;

public class Person {
    private Car car;  // a person may or may not have a car so declare the field Optional
    private int age;

    // a car of null is allowed
    public Person(int age){
        this(null,age);
    };

    public Person(Car car, int age){
        this.car=car;
        this.age=age;
    }

    //  this makes it explicit that a person might or might not have a car
    public Optional<Car> getCar() { return  Optional.ofNullable(car);}

    public int getAge(){ return age;}
}
