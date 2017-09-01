package com.depas.test.streams.pipeline;

public class Car {
    private String make;
    private String model;
    private int year;

    public Car(String theMake, String theModel, int yearOfMake) {
        this.make = theMake;
        this.model = theModel;
        this.year = yearOfMake;
    }

    public String getMake() { return make; }
    public String getModel() { return model; }
    public int getYear() { return year; }
}
