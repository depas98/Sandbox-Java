package com.test.map;

import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

public class MapTest {

	public static void main(String[] args) {
		Map<Integer, String> map = new HashMap<>();

		for (int i = 0; i < 10; i++) {
		    map.putIfAbsent(Integer.valueOf(i), "val" + i);

		    map.putIfAbsent(Integer.valueOf(i), "val2" + i);
		}

		map.forEach((id, val) -> System.out.println("id: " + id + " val: " + val));

		System.out.println("-----------------------------------");


		map.computeIfPresent(Integer.valueOf(3), (key, val) -> val + key);
		System.out.println(map.get(Integer.valueOf(3)));             // val33

		String test = "Test";
		map.computeIfPresent(Integer.valueOf(3), (key, val) -> val + test);
		System.out.println(map.get(Integer.valueOf(3)));             // val33

		map.computeIfPresent(Integer.valueOf(9), (key, val) -> null);
		System.out.println(map.containsKey(Integer.valueOf(9)));     // false

		map.computeIfPresent(Integer.valueOf(29), (key, val) -> val + key);
		System.out.println(map.containsKey(Integer.valueOf(29)));     // false

		map.computeIfAbsent(Integer.valueOf(23), key -> "val" + key);
		System.out.println(map.containsKey(Integer.valueOf(23)));    // true
		System.out.println(map.get(Integer.valueOf(23)));

		map.computeIfAbsent(Integer.valueOf(3), key -> "bam");
		System.out.println(map.get(Integer.valueOf(3)));             // val33

		Map<Integer, Integer> map2 = new HashMap<>();

		map2.computeIfPresent(null, (key, val) -> Integer.valueOf(1 + val.intValue()));
		System.out.println(map2.get(null));

		map2.put(null, Integer.valueOf(0));
		map2.computeIfPresent(null, (key, val) -> Integer.valueOf(1 + val.intValue()));
		System.out.println(map2.get(null));

		int total = 10;
		map2.computeIfPresent(null, (key, val) -> Integer.valueOf(total + val.intValue()));
		System.out.println(map2.get(null));


		Map<String,HashSet<Long>> blockingSumMap = new HashMap<>();

		HashSet<Long> totalRows = blockingSumMap.getOrDefault(null, new HashSet<Long>());

		blockingSumMap.computeIfAbsent(null, k -> new HashSet<>(Arrays.asList(Long.valueOf(0))));
		System.out.println(blockingSumMap.get(null));
	}

}
