package com.test.streams;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;



public class FileInDirectory {
	
	final static File home = new File(System.getProperty("user.home"));
	
	public static List<File> getAllFilesInDir(File dir, Predicate<File> predicate) throws IOException {
		if (dir==null){
			throw new IllegalArgumentException("The File arugment needs to be set.");	
		}
		 return getAllFilesInDir(dir.toPath(), predicate);
	}

	public static List<File> getAllFilesInDir(File dir) throws IOException {
		 return getAllFilesInDir(dir.toPath(), p -> true);
	}

	public static List<File> getAllFilesInDir(Path dir) throws IOException {
		 return getAllFilesInDir(dir, p -> true);
	}
	
	public static List<File> getAllFilesInDir(Path dir, Predicate<File> predicate) throws IOException {
		
		if (dir==null){
			throw new IllegalArgumentException("The Path arugment needs to be set.");	
		}

		if (predicate==null){
			throw new IllegalArgumentException("The Predicate arugment needs to be set.");	
		}
		
		try (Stream<Path> stream = Files.walk(dir, Integer.MAX_VALUE)) {
			List<File> files = stream
				.map(p -> p.toFile())
				.filter(predicate)		        
		        .sorted()
		        .collect(Collectors.toList());		    		    
		    
		    return files;
		}
	}
	
	public static Stream<Path> filesInDir(Path dir) {
	    return listFiles(dir)
	            .flatMap(path ->
	                    path.toFile().isDirectory() ?
	                            filesInDir(path) :
	                            	listFiles(path));
	}

	private static Stream<Path> listFiles(Path dir) {
	    try {
	        return Files.list(dir);
	    } catch (IOException e) {
	        return null;
	    }
	}
	
	
	public static void main(String[] args) {
		try (final Stream<Path> files = Files.list(home.toPath());) {
						
			files
				.filter(p -> p.getFileName().toString().contains(".m2"))				
				.forEach(p -> {
						//System.out.println(p);
						Stream<Path> files2 = filesInDir(p);
						files2
							.forEach(System.out::print);
					}					
					);

		}
		catch (IOException e) {
	        
	    }

		Path start = Paths.get("C:\\Users\\mike.depasquale\\.m2");
		int maxDepth = 5;
		try (Stream<Path> stream = Files.find(start, maxDepth, (path, attr) ->
		        String.valueOf(path).endsWith(".jar"))) {
		    String joined = stream
		        .sorted()
		        .map(String::valueOf)
		        .collect(Collectors.joining("; "));
//		    System.out.println("Found: " + joined);
		}
		catch (IOException e) {
        
		}			
		
		
		Path m2Dir = Paths.get("C:\\Users\\mike.depasquale\\.m2");
		try (Stream<Path> stream = Files.walk(m2Dir, Integer.MAX_VALUE)) {
		    String joined = stream
		        .map(String::valueOf)
		        .filter(path -> path.endsWith(".jar"))		        
		        .sorted()
		        .collect(Collectors.joining("; "));
//		    System.out.println("walk(): " + joined);
		}
		catch (IOException e) {
	        
	    }
		
		try {
			m2Dir = Paths.get("C:\\Users\\mike.depasquale\\.m2\\repository\\org\\hamcrest\\hamcrest-core\\1.3\\hamcrest-core-1.3-javadoc.jar");
			String webInfLib = "WEB-INF" + File.separator + "lib";
//			List<File> jarFiles = getAllFilesInDir(m2Dir, file -> file.getName().toLowerCase().contains(".jar"));
			
			List<File> jarFiles = getAllFilesInDir(m2Dir, file -> file.getName().toLowerCase().contains("hamcrest") && file.isFile());
			
//			List<File> jarFiles = getAllFilesInDir(m2Dir, file -> file.getName().toLowerCase().contains(".jar") && file.getParent().toLowerCase().contains(webInfLib));
//			List<File> jarFiles = getAllFilesInDir(m2Dir, file -> file.getParent().toLowerCase().contains(webInfLib));			
			
			m2Dir = Paths.get("C:\\Users\\mike.depasquale\\.m2\\settings.xml");
//			m2Dir=null;
//			List<File> jarFiles = getAllFilesInDir(m2Dir,null);
			jarFiles.forEach(System.out::println);
				
		} catch (Exception e) {
			e.printStackTrace();
		}

	}
}
