package com.depas.test.lamda;

import java.net.InetAddress;
import java.util.Arrays;
import java.util.List;

public class ForEachLambdaTest {

	public static void main(String[] args) {
		String[] atp = {"Rafael Nadal", "Novak Djokovic", "Stanislas Wawrinka", "David Ferrer", "Roger Federer", "Andy Murray", "Tomas Berdych", "Juan Martin Del Potro"};
		List<String> players =  Arrays.asList(atp);

		// Old looping
		for (String player : players) {
		     System.out.println(player + "; ");
		}

		System.out.println("-------------------------");

		// Using lambda expression and functional operations
		players.forEach(player -> System.out.println("The player is " + player));

		System.out.println("-------------------------");

		// Using method reference operator in Java 8
		players.forEach(p -> System.out.println(p));
		players.forEach(System.out::println);

		System.out.println("-------------------------");

		// another example
	     List<Integer> primes = Arrays.asList(new Integer[]{2,3,5,7});
	     int factor = 2;
	     primes.forEach(element -> {
	    	 int factor2 = 3;
	    	 System.out.println(factor*factor2*element.intValue()); });
	}

}
