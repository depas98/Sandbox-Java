package com.depas.hackerrank;

import java.util.Arrays;

public class Gernerics {

    public static <E> void printArray(E[] arr){
//        for (E e : arr) {
//			System.out.println(e);
//		}
        
        Arrays.asList(arr).stream().forEach(System.out::println);
    }
	
	public static void main(String[] args) {		
        Integer[] intArray = { 1, 2, 3 };
        String[] stringArray = { "Hello", "World" };
        
        printArray( intArray  );
        printArray( stringArray );
        
        if(Gernerics.class.getDeclaredMethods().length > 2){
            System.out.println("You should only have 1 method named printArray.");
        }
	}

}
