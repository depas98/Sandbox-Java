package com.test.lamda;

import java.util.function.Supplier;

public class LazyLamdaTest {
  public static int expensiveComputation(int input) {
    System.out.println("called expensive computation");    
    return input;
  }
  
  public static void main(String[] args) {
    int somevalue = 5;
    
    // Short-circuit evaluation, minimal evaluation, or McCarthy evaluation
    System.out.println("Short circuit");
    if(somevalue > 5 && expensiveComputation(5) > 0)
      System.out.println("");
    else
      System.out.println("");
      
    System.out.println("Lazy with Lambda");
    Supplier<Integer> temp = () -> expensiveComputation(5);
    if(somevalue > 5 && temp.get() > 0)
      System.out.println("");
    else
      System.out.println("");
  }
}