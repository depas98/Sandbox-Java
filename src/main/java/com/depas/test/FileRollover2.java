package com.depas.test;

import com.depas.utils.FileUtils;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.util.stream.IntStream;

public class FileRollover2 {

    public static void writeTextToRolloverFile(final String fileName, final String content, final int maxNumberOfFiles) throws IOException, SecurityException {

        if (maxNumberOfFiles < 1) {
            return;
        }

        // delete last item
        File fileLast = new File(fileName + "." + (maxNumberOfFiles - 1));
        if (fileLast.exists()) {
            if (!fileLast.delete()){
                System.out.println("Unable to delete file: " + fileLast);
            }
            System.out.println("delete " + fileName + "." + (maxNumberOfFiles - 1));
        }

        IntStream.rangeClosed(2, maxNumberOfFiles)
                .map(n -> maxNumberOfFiles - n)
                .forEach(n -> {
                    System.out.println(n);
                    String fullFileName = fileName;
                    if (n != 0) {
                        fullFileName = fileName + "." + n;
                    }
                    String renameFile = fileName + "." + (n + 1);
                    File oldFile = new File(fullFileName);
                    if (oldFile.exists()){
                        if (!oldFile.renameTo(new File(renameFile))){
                            System.out.println("Unable to rename " + fullFileName + " to " + renameFile);
                        }
                        System.out.println("rename " + fullFileName + " to " + renameFile);
                    }
                });

        File file = new File(fileName);
        if (file.createNewFile()){
            FileUtils.writeTextToFile(fileName, content);
        }

        // FileWriter is more efficient when doing one write before close
        // if doing multiple writes before a close bufferedWriter is more efficient
        // see https://stackoverflow.com/questions/12350248/java-difference-between-filewriter-and-bufferedwriter
//        try( FileWriter writer = new FileWriter(fileName, true)){
//            writer.write(content);
//            System.out.println("Create file " + fileName);
//        }

    }


    public static void main(String[] args) {

        try {
            writeTextToRolloverFile("c:/temp/test.txt", "some content" + new Timestamp(System.currentTimeMillis()), 3);
        }
        catch (Exception e) {
            System.out.println("Unable to rollover system log file " + e);
        }


    }
}
