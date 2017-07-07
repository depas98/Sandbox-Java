package com.depas.test.streams.javaone;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

/**
 * Created by mike.depasquale on 7/6/2017.
 */
public class WordCount {

    public static long getWordCountImperative(String searchWord, String path){
        long count = 0;
        try ( BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            String line = null;

            while ((line = bufferedReader.readLine()) != null) {
                if (line.contains(searchWord))
                    count++;
            }
        }
        catch (IOException ex) {
            ex.printStackTrace();
        }

        return count;
    }

    public static long getWordCountFun(String searchWord, String path){
        try ( BufferedReader bufferedReader = new BufferedReader(new FileReader(path))) {
            return Files.lines(Paths.get(path))
                    .filter(l -> l.contains(searchWord))
                    .count();
        }
        catch (IOException ex) {
            ex.printStackTrace();
            return -1;
        }
    }

    public static void main(String[] args) {
        String searchWord = "localhost";
//            String path = "/etc/hosts";
        String path = "C:\\Windows\\System32\\drivers\\etc\\hosts";

        // Imperative
        long count = getWordCountImperative(searchWord,path);

        System.out.printf("The word %s occurred %d time\n", searchWord, count);

        System.out.println("-----------");

        // functional
        count = getWordCountFun(searchWord,path);
        System.out.printf("The word %s occurred %d time\n", searchWord, count);

    }
}
