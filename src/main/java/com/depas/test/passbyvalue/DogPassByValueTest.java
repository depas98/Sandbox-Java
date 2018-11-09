package com.depas.test.passbyvalue;

public class DogPassByValueTest {

	Dog myDog;

	public static void fooInt(int x){
		x=x*2;
		System.out.println("inside fooInt x: " + x);
	}

	public static void foo(Dog someDog) {
	    someDog.setName("Max");     // AAA
	    someDog = new Dog("Fifi");  // BBB
	    someDog.setName("Rowlf");   // CCC
	}

	public static void main(String[] args) {
		Dog myDog = new Dog("Rover");
		foo(myDog);
		System.out.println(myDog.getName());		// Max will print out, proving Java passes by value

		int x = 2;
		fooInt(x);
		System.out.println("x: " + x);
	}

}
