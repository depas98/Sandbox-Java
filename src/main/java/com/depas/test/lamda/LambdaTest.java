package com.depas.test.lamda;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

public class LambdaTest {
		
	public static void main(String[] args) {
		// *** supplier -->	Supplier<T> supp = () -> T;
		System.out.println("Supplier test");
		Supplier<String> stringSupp = () -> "test"; 
		System.out.println(stringSupp.get());
				
		// used as a factory
		Supplier<Integer> intSupp5 = () -> {return new Integer(5);};				
		Supplier<Map> hashMapSupp = () -> new HashMap<>();
		// using a Method Reference
		Supplier<Map> hashMapSuppMF = HashMap::new;	
								
		// *** consumer --> Consumer<T> cons =  t -> {};
		System.out.println("\nConsumer test");
		Consumer cons =  t -> {};
		Consumer<String> commaStringOut = s -> System.out.print(s + ", ");
		commaStringOut.accept("This is a test");
		System.out.println("");
		
		// andThen - returns a composed Consumer
		Consumer<String> suffixStringOut = s -> System.out.println("of lambdas");		
		commaStringOut.andThen(suffixStringOut).accept("This is a test");	
		
//		Consumer<String> fullStringOut = commaStringOut.andThen(suffixStringOut);
//		fullStringOut.accept("This is a test");

		// *** function --> Function(T,T) func =  t -> t;
		System.out.println("\nFunction test");
		Function func = t -> t;
				
		Function<String, Integer> stringLength = (s) -> s.length();		
		System.out.println(stringLength.apply("Hello world"));
		
		// andThen
		System.out.println("andThen test");
		Function<Integer, Boolean> lowerThanTen = (i) -> i < 10;				
		Function<String, Boolean> function = stringLength.andThen(lowerThanTen);
		// Will print false
		System.out.println(function.apply("Hello world"));

		// compose 
		System.out.println("compose test");
		Function<Integer, Integer> times2 = e -> e * 2;
		Function<Integer, Integer> squared = e -> e * e;
		
		// returns 64
		System.out.println(times2.andThen(squared).apply(4));	
		
		// returns 32
		System.out.println(times2.compose(squared).apply(4));	
		
		// identity 
		Function<String,String> strIdenityFunc = Function.identity();
		System.out.println(strIdenityFunc.apply("Identity Test"));	
		
		// *** predicate --> Predicate<T> pred = t -> 1 == 1;
		System.out.println("\nPredicate test");
		Predicate<String> lengthTest5  = (s)-> s.length() > 5;
		System.out.println(lengthTest5.test("longer than 5 test "));
		
		// and
		System.out.println("and test");
		Predicate<String> startsWith  = (s)-> s.startsWith("t");
		System.out.println(lengthTest5.and(startsWith).test("longer than 5 test "));

		// or
		System.out.println("or test");
		System.out.println(lengthTest5.or(startsWith).test("longer than 5 test "));
		
		// negate
		System.out.println("negate test");
		System.out.println(startsWith.negate().test("longer than 5 test "));
		
		// isEqual
		System.out.println("isEquale test");
	    Predicate<String> strEqual  = Predicate.isEqual("asdf");
	    
	    System.out.println(strEqual.test("basddfs"));
	    System.out.println(strEqual.test("asdf"));
	}

}
