package com.depas.functional.optional;

import java.util.Optional;

public class OptionalUtility {

    public static Optional<Integer> stringToInt(String s){
        try {
            return Optional.of(Integer.parseInt(s));
        } catch (NumberFormatException e) {
            return Optional.empty();
        }
    }

    public static void main(String[] args) {

        System.out.println(stringToInt(null));
        System.out.println(stringToInt("t"));
        System.out.println(stringToInt("-99"));
        System.out.println(stringToInt("99"));
    }
}
