package com.depas.functional;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.PriorityQueue;
import java.util.TreeMap;
import java.util.concurrent.atomic.AtomicInteger;

import org.springframework.util.StringUtils;

public class WordCountImperative {

	//  tallyUp - given an array of words, returns a tally object
	//  where each key of the object is a word, and the corresponding
	//  value is the number of times that word appeared in the array
	public static Map<String,Integer> tallyUp(String[] words) {
		Map<String,Integer> tallyMap = new HashMap<>();
		
		for (int i = 0; i < words.length; i++) {
			String word = words[i].toLowerCase();
			Integer wordCount = tallyMap.get(word);
			if (wordCount==null){
				wordCount=new Integer(0);
			}
			wordCount = new Integer(wordCount.intValue() + 1);
//			System.out.println(word + ":" + wordCount);
			if (StringUtils.hasLength(word)){
				tallyMap.put(word, wordCount);	
			}
		}
		
		return tallyMap;
	}
	
	public static Map<String,AtomicInteger> tallyUpAtomicInteger(String[] words) {
		Map<String,AtomicInteger> tallyMap = new HashMap<>();
		
		for (int i = 0; i < words.length; i++) {
			String word = words[i].toLowerCase();
			tallyMap.putIfAbsent(word, new AtomicInteger(0));
			tallyMap.get(word).incrementAndGet();
		}
		
		return tallyMap;
	}
	
	public static List<Entry<String, Integer>> getTop10Sort(Map<String, Integer> tally) {		
		ValueComparator<String,Integer> vComp = new ValueComparator<String,Integer> (tally);
		Map<String, Integer> sortedtally = new TreeMap<String, Integer>(vComp);
		sortedtally.putAll(tally);
		
		List<Entry<String, Integer>> topTen = new ArrayList<>();
		for (Map.Entry<String, Integer> entry : sortedtally.entrySet()) {
		  if (topTen.size() > 9) break;
		  topTen.add(entry);
		}
		
		return topTen;		
	}
	
	// getTop10 - given the tally object (in the format returned by tallyUp)
	//  return the top 10 words in the tally as an array of objects containing
	//  the properties "word" and "count", where "word" contains the word and
	//  "count" contains the number of times the word appeared.
	public static List<Entry<String, Integer>> getTop10(Map<String,Integer> tally) {
		return findGreatest(tally,10);
	}
	
	private static <K, V extends Comparable<? super V>> List<Entry<K, V>> 
       findGreatest(Map<K, V> map, int n) {
       
		Comparator<? super Entry<K, V>> comparator = 
           new Comparator<Entry<K, V>>()
       {
           @Override
           public int compare(Entry<K, V> e0, Entry<K, V> e1)
           {
               V v0 = e0.getValue();
               V v1 = e1.getValue();
               return v0.compareTo(v1);
           }
       };
       PriorityQueue<Entry<K, V>> highest = 
           new PriorityQueue<Entry<K,V>>(n, comparator);
       for (Entry<K, V> entry : map.entrySet())
       {
           highest.offer(entry);
           while (highest.size() > n) {
               highest.poll();
           }
       }
       
       List<Entry<K, V>> result = new ArrayList<Map.Entry<K,V>>();
       while (highest.size() > 0)
       {
           result.add(highest.poll());
       }
       return result;
	}
	
	// a comparator using generic type
	static class ValueComparator<K, V extends Comparable<V>> implements Comparator<K>{
	 
		Map<K, V> map = new HashMap<K, V>();
	 
		public ValueComparator(Map<K, V> map){
			this.map.putAll(map);
		}
	 
		@Override
		public int compare(K s1, K s2) {
			int comp = -map.get(s1).compareTo(map.get(s2));//descending order
			
			
			return comp==0 ? 1 : comp;	
		}
	}
	
	
	public static void main(String[] args) { 
//		String fileName = "c://source//workspace one//DePasTestProject//words.txt";
		String fileName = "words.txt";
		
		String content = "";
		try {
			content = new String(Files.readAllBytes(Paths.get(fileName)));
		} catch (Exception e) {
			// TODO: handle exception
		}
		
		String[] words = content.split(" ");
		
		Map<String,Integer> tally = WordCountImperative.tallyUp(words);	
//		Map<String,Integer> tally = WordCountImperative.tallyUpAtomicInteger(words);
		
//		System.out.println(tally);
		
		List<Entry<String, Integer>> top10 = getTop10(tally);
		
		System.out.println(top10);
		
		top10 = getTop10Sort(tally);
		System.out.println("next top 10");
		System.out.println(top10);
		
//		printTop10(top10)
	}

}
