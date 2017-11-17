package com.depas.functional.optional;

import java.util.Objects;

public class Insurance {

    private String name;

    // null are not allowed throw an error if null
    public Insurance(String name){
        Objects.requireNonNull(name, "The insurance name must not be null.");
        this.name=name;
    }

    // you don't have to add a null check because doing so will just hide the problem instead of fixing it
    public String getName() { return name; }      // an insurance company must have a name

}
