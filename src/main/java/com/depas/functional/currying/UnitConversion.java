package com.depas.functional.currying;

import java.util.function.DoubleUnaryOperator;

/**
 * Created by mike.depasquale on 7/7/2017.
 */
public class UnitConversion {
    public static double converter(double x, double f, double b) {
        return x * f + b;
    }

    public static double convertCtoF(double x) {
        return x * 9.0/5 + 32;
    }


    public static DoubleUnaryOperator curriedConverter(double f, double b){
        return (double x) -> x * f + b;
    }

    public static void main(String[] args) {
        double convertCtoF23 = converter(23, 9.0/5, 32);
        System.out.println("23 Celsisus is " + convertCtoF23 + "F");

        double convertKmtoMi1500=converter(1500,0.6214,0);
        System.out.println("1500 Km is " + convertKmtoMi1500 + " m");

        // or create methods for each
        System.out.println("23 Celsisus is " + convertCtoF(23) + "F");

        System.out.println("************************");

        // using currying
        DoubleUnaryOperator convertCtoF = curriedConverter(9.0/5, 32);
        System.out.println("23 Celsisus is " + convertCtoF.applyAsDouble(23) + "F");

        DoubleUnaryOperator convertKmtoMi = curriedConverter(0.6214,0);
        System.out.println("23 Celsisus is " + convertKmtoMi.applyAsDouble(1500) + " m");
    }

}
