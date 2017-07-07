package com.depas.test.streams.javaone.decorator;

/**
 * Created by mike.depasquale on 7/7/2017.
 */
public class Applicant {
    public boolean isCredible() {
        return true;
    }

    public int getCreditScore() {
        return 700;
    }

    public int getEmploymentYear(){
        return 10;
    }

    public boolean hasCriminalRecord(){
        return false;
    }

}
