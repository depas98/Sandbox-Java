package com.depas.hackerrank;

import java.util.Scanner;
import java.util.stream.IntStream;

public class LoopsWithIntStream {

	public static void main(String[] args) {
		
		Scanner sc = new Scanner(System.in);
		int m = sc.nextInt();		
		IntStream.range(1,11).forEach(i -> System.out.println(m + " x " + i + " = " + i * m));
		
		sc.close();
	}

}
