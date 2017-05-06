package com.depas.functional;

import static java.util.Comparator.reverseOrder;
import static java.util.function.Function.identity;
import static java.util.stream.Collectors.counting;
import static java.util.stream.Collectors.groupingBy;
import static java.util.stream.Collectors.joining;
import static java.util.stream.Collectors.toList;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.stream.Stream;

public class WordCountFunctional {
	
	public static List<Entry<String,Long>> getTopNWordsNotFunctional(String fileName, int nWords){
		Objects.requireNonNull(fileName,"Need to have a file name");
		
		try{
			return Files.readAllLines(Paths.get(fileName)).stream()
			.map(line -> line.split("[\\s]+"))
			.flatMap(Arrays::stream)
			.map(s -> s.toLowerCase())
			.filter(s -> s.length() > 0)   		// filter out empty string
			.collect(groupingBy(identity(), counting()))
			.entrySet().stream()
			.sorted(Map.Entry.<String, Long>comparingByValue(reverseOrder()).thenComparing(Map.Entry.comparingByKey()))
			.limit(nWords)
			.collect(toList());
		} catch (IOException e) {
			e.printStackTrace();	// not functional, side effect
			return null;
		}
	}
	
	public static List<Entry<String,Long>> getTopNWords(Stream<String> wordStream, int nWords){
		
//		what if wordStream is null, wrap in optional check for null, make parameter an optional (optionals are better for return types)
		if (wordStream==null) {return new ArrayList<Entry<String,Long>>();}
		 
		return wordStream.map(line -> line.split("[\\s]+"))
				.flatMap(Arrays::stream)
				.map(s -> s.toLowerCase())
				.filter(s -> s.length() > 0)   		// filter out empty string
				.collect(groupingBy(identity(), counting()))
				.entrySet().stream()
				.sorted(Map.Entry.<String, Long>comparingByValue(reverseOrder()).thenComparing(Map.Entry.comparingByKey()))
				.limit(nWords)
				.collect(toList());
	}

	public static void main(String[] args) {
		String fileName = "c://source//workspace one//DePasTestProject//words.txt";
		// Files.lines doesn't close the buffer reader resource, this is lazy reading
		try (Stream<String> wordsStream = Files.lines(Paths.get(fileName))) {

//			getTopNWordsNotFunctional(fileName,10).forEach(System.out::println);
			getTopNWords(wordsStream,10).forEach(System.out::println);

		} catch (IOException e) {
			e.printStackTrace();
		}

		System.out.println("-----------------------------");
		
		
		try{
			Stream<String> wordsStream = Files.readAllLines(Paths.get(fileName)).stream();	// this closes the resource, go for small files
			getTopNWords(wordsStream,10).forEach(System.out::println);
			// fancier printing
			String words=getTopNWords(wordsStream,10).stream()
				.map(e -> e.getKey() + "=" + e.getValue())
				.collect(joining(", "));
			System.out.println("[" + words + "]");
		} catch (IOException e) {
			e.printStackTrace();
		}		
	}

}
