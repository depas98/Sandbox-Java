package com.depas.functional.optional;

import java.util.Optional;

public class Car {

    private Insurance insurance;                   // a car may or may not have insurance so declare Optional

    // an insurance of null is allowed
    public Car(){}

    public Car(Insurance insurance){
        this.insurance=insurance;
    }

    //  this makes it explicit that a car might or might not have a insurance
    public Optional<Insurance> getInsurance(){ return  Optional.ofNullable(insurance); }

}
