package com.test.lamda;

@FunctionalInterface
interface TestInterface {
    void dostuff();
}


public class FuncationalInterfaceTest {

//    private static void test(Runnable r) {
//    	r.run();
//    }
	
	private static void test(TestInterface ti) {
		ti.dostuff();
	}
	
	public static void main(String[] args) {
		test(() -> { System.out.println("test");});
	}

}
