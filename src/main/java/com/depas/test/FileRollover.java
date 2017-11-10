package com.depas.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class FileRollover {

	private static final String FILE_NAME = "systemInfo.log";

	private static final Map<String, String> files = new HashMap<>();

	private static void writeFile(String entryName, String file, int maxFiles) {

		if (!files.containsKey(file)) {
			files.put(file, entryName);
			return;

		}

		String oldEntry = files.get(file);
		String[] fileArr = file.split("\\.");
		Arrays.stream(fileArr).forEach(System.out::println);

		String oldFile;
		if (fileArr.length > 1) {
			try {
				int suffix = Integer.parseInt(fileArr[fileArr.length - 1]) + 1;
				if (suffix > maxFiles) {
					return;
				}
				oldFile = fileArr[0] + "." + fileArr[1] + "." + suffix;
			} catch (NumberFormatException e) {
				return;
			}

		} else {
			oldFile = file + ".1";
		}
		writeFile(oldEntry, oldFile, maxFiles);
		files.put(file, entryName);
	}

	public static void main(String[] args) {

		writeFile("filea", FILE_NAME, 3);

		files.entrySet().forEach(e -> System.out.println(e.getKey() + ":" + e.getValue()));

		System.out.println("----------- second file --------------------");

		writeFile("fileb", FILE_NAME, 3);

		files.entrySet().forEach(e -> System.out.println(e.getKey() + ":" + e.getValue()));

		System.out.println("----------- thrid file --------------------");

		writeFile("filec", FILE_NAME, 3);

		files.entrySet().forEach(e -> System.out.println(e.getKey() + ":" + e.getValue()));
	}
}
