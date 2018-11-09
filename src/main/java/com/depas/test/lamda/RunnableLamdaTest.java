package com.depas.test.lamda;

public class RunnableLamdaTest {

	public static void process (Runnable r){
		r.run();
	}

	public static void process (Runnable r, boolean isDaemon){
		Thread t = new Thread(r);
		t.setDaemon(isDaemon);
	 	t.start();
	}

	public static void process (Runnable r, String threadName){
		process (r, threadName, false);
	}

	public static void process (Runnable r, String threadName, boolean isDaemon){
		Thread t = new Thread(r,threadName);
		t.setDaemon(isDaemon);
	 	t.start();
	}

	public static void doStuff(){
		System.out.println("Hello World Java 8 lambda from method");
	}

	public static void main(String[] args) {
		// before Java 8
		new Thread(new Runnable() { @Override public void run() { System.out.println("Before Java8, too much code for too little to do in the run "); } }).start();

		//Java 8 way:
		new Thread( () -> System.out.println("In Java8, Lambda expression rocks!!") ).start();


		Thread lambdaThread = new Thread( () -> {
					int i = 0;
					while (i < 5) {
						System.out.println("In Java8, Lambda expression rocks running " + i  + " !!");
						i++;
					}
		});

		lambdaThread.start();

		// examples implementing Runnable instead of new Thread
		Runnable r1 = new Runnable(){                                                 // Using an anonymous class
			public void run() {
				System.out.println("Hello World Before Java 8");
			}
		};

		Runnable r2 = () -> System.out.println("Hello World Java 8 lambda");      // Using a lambda

		process(r1);  // prints "Hello World 1"
		process(r2);  // prints "Hello World 2"
		process(() -> System.out.println("Hello World Java 8 lambda inline"));  // prints with a lambda passed directly
		process(() -> doStuff());
		process(RunnableLamdaTest::doStuff);

	}

}
