package com.depas.utils;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.net.HttpURLConnection;
import java.net.Socket;
import java.net.URL;
import java.net.URLDecoder;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Properties;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

public class FileUtils {

    private static final Log logger = LogFactory.getLog(FileUtils.class);

    public static String getIDCConfigDirectoryPath(Class<?> c) {
        return getConfigDirectoryPath(c, "idc");
    }

    public static String getIWCConfigDirectoryPath(Class<?> c) {
        return getConfigDirectoryPath(c, "iwc");
    }

    public static String getConfigDirectoryPath(Class<?> c) {
        try {
            String pathToIwc = getConfigDirectoryPath(c, "iwc");
            if (pathToIwc != null) {
                File iwcDir = new File(pathToIwc);
                if (iwcDir.exists()) {
                    File configDir = iwcDir.getParentFile();
                    String path = configDir.getPath();
                    if (!path.endsWith("\\") && !path.endsWith("/")) {
                        path += "/";
                    }
                    return URLDecoder.decode(path, "UTF-8");
                }
            }
        } catch (Exception ex) {
            logger.warn("Error obtaining file path to config directory: " + ex);
        }
        return null;
    }

    private static String getConfigDirectoryPath(Class<?> c, String rootDirectory) {
        // get url to iwc/idc directory on classpath
        try {
            URL url = ClassLoader.getSystemResource(rootDirectory);
            if (url == null) {
                // try this classpath, for running in eclipse
                url = c.getClassLoader().getResource(rootDirectory);
            }
            if (url != null) {
                String path = url.getPath();
                if (!path.endsWith("\\") && !path.endsWith("/")) {
                    path += "/";
                }
                return URLDecoder.decode(path, "UTF-8");
            } else {
                return null;
            }
        } catch (Exception ex) {
            logger.warn("Error obtaining file path to '" + rootDirectory + "' config directory: " + ex);
            return null;
        }

    }

    /**
     * Gets directory where temp files are stored.
     * In a Tomcat webapp points to $CATALINA_BASE/temp by default.
     */
    public static String getTempDirectoryPath() {
        String path = System.getProperty("java.io.tmpdir");
        if (path == null) {
            logger.warn("Could not obtain file path to the temp directory.");
        }
        return path;
    }

    //	/**
    //	 * Gets the path for our license files.
    //	 *
    //	 * @deprecated this method was for the old license file downloads and should not be used anymore.
    //	 * @param c the Class to use as a resource
    //	 * @return the license directory path or null if it could not be determined
    //	 */
    //	public static String getLicenseDirectoryPath(Class<?> c) {
    //		String baseDir = getIWCConfigDirectoryPath(c);
    //		if (baseDir != null) {
    //			return baseDir + "license/";
    //		}
    //		// This would indicate an error with our call to getIWCConfigDirectoryPath()
    //		return null;
    //	}

    /**
     * Gets the root directory that we will initialize the GEN3 license framework.
     * @param c the c
     * @return the license root path
     */
    public static String getLicensingRootPath(Class<?> c) {
        try {
            // get url to iwc directory on classpath
            URL url = ClassLoader.getSystemResource("iwc");
            if (url != null) {
                File iwcDir = new File(url.toURI());
                // go up one to ignite_config
                // go up one more then to root
                File baseDir = iwcDir.getParentFile().getParentFile();

                String path = baseDir.getPath();
                if (path.endsWith("\\") == false && path.endsWith("/") == false) {
                    path += "/";
                }
                // licensing, should be at same level as ignite_config
                path += "licensing/";
                return URLDecoder.decode(path, "UTF-8");
            } else {
                return null;
            }
        } catch (Exception ex) {
            logger.warn("Error obtaining file path to license directory: " + ex);
            return null;
        }
    }

    public static File getFileFromPathRelativeToTomcatDirectory(Class<?> c, String pathRelativeToTomcatDir) {
        File igniteConfigDir = new File(FileUtils.getConfigDirectoryPath(c));
        String tomcatDir = igniteConfigDir.getParentFile().getAbsolutePath();
        return new File(tomcatDir, pathRelativeToTomcatDir);
    }

    public static String extractPathRelativeToTomcatDirectory(Class<?> c, String absolutePath) {
        String igniteConfigDirStr = getConfigDirectoryPath(c);
        if (igniteConfigDirStr != null) {
            File tomcatDir = new File(igniteConfigDirStr).getParentFile().getAbsoluteFile();
            return extractPathRelativeToDirectory(tomcatDir, new File(absolutePath));
        }
        return null;
    }

    public static String extractPathRelativeToDirectory(File chrootIn, File absolutePathIn) {
        if (chrootIn == null || absolutePathIn == null) {
            throw new IllegalArgumentException("The arguments must be non-null.");
        }
        File chroot = chrootIn;
        File absolutePath = absolutePathIn;

        // Canonization will turn things like "/home/dpa/dir/../dir" into "/home/dpa/dir".
        try {
            chroot = chroot.getCanonicalFile();
        } catch (Exception ex) {
            logger.warn("Failed to obtain canonical form of the 'chroot' file [" + chroot.toString() + "].", ex);
        }
        try {
            absolutePath = absolutePath.getCanonicalFile();
        } catch (Exception ex) {
            logger.warn("Failed to obtain canonical form of the 'absolutePath' file [" + absolutePath.toString() + "].", ex);
        }

        String result = "";
        String delim = "";

        // safety limit of 200 iterations in case there is a loop in the file system
        for (int i = 0; i < 200 && absolutePath.getParentFile() != null; i++) {
            if (chroot.equals(absolutePath)) {
                return !result.isEmpty() ? result : ".";
            }
            result = absolutePath.getName() + delim + result;
            delim = "/";
            absolutePath = absolutePath.getParentFile();
        }

        // could not find a match
        return null;
    }

    public static String getSystemPropertiesFilePath(Class<?> c) {
        return FileUtils.getIDCConfigDirectoryPath(c) + File.separator + "system.properties";
    }

    public static File getResourceFile(String resourceName) {
        if (StringUtils.hasText(resourceName)) {
            URL url = getURLFromClasspath(resourceName);
            if (url != null) {
                String filePath = url.getFile();
                try {
                    filePath = URLDecoder.decode(filePath, "UTF-8");
                } catch (Exception ex) {
                    // Ignore
                }
                return new File(filePath);
            }
        }
        return null;
    }

    public static URL getURLFromClasspath(final String fileName) {
        String resName = null;
        try {
            resName = URLDecoder.decode(fileName, "UTF-8");
        } catch (Exception e) {
        }

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        return cl.getResource(resName);
    }

    /**
     * Returns content of resource that exists on classpath
     */
    public static String getTextFromResource(String resourceName) throws IOException {

        ClassLoader cl = Thread.currentThread().getContextClassLoader();
        if (cl.getResource(resourceName) == null) {
            throw new FileNotFoundException("The resource " + resourceName + " could not be found on the current classpath.");
        }

        StringBuffer buf = new StringBuffer();
        BufferedInputStream bis = null;
        try {
            InputStream is = cl.getResourceAsStream(resourceName);
            bis = new BufferedInputStream(is);

            int i;
            while ((i = bis.read()) != -1) {
                buf.append((char) i);
            }
        } finally {
            try {
                bis.close();
            } catch (Exception ex) {
            }
        }

        return buf.toString();

    }

    public static void writeArrayToFile(String fileName, String[] contents, boolean utfEncoding) throws IOException {
        BufferedWriter out = null;
        try {
            File outFile = new File(fileName);
            OutputStreamWriter osw;
            if (utfEncoding) {
                osw = new OutputStreamWriter(new FileOutputStream(outFile), "UTF-8");
            } else {
                osw = new OutputStreamWriter(new FileOutputStream(outFile));
            }
            out = new BufferedWriter(osw);
            for (int i = 0; i < contents.length; i++) {
                out.write(contents[i]);
                out.newLine();
            }
            out.close();
            out = null;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static void writeTextToFile(String fileName, String contents) throws IOException {
        BufferedWriter out = null;
        try {
            File outFile = new File(fileName);
            out = new BufferedWriter(new FileWriter(outFile));
            out.write(contents);
            out.close();
            out = null;
        } finally {
            if (out != null) {
                out.close();
            }
        }
    }

    public static boolean deleteFile(String fileName) {
        File f = new File(fileName);
        return f.delete();
    }

    public static String getTextFromFile(String fileName) throws IOException {

        File file = new File(fileName);
        if (!file.exists()) {
            return null;
        }

        BufferedReader in = null;
        StringBuilder text = new StringBuilder(2048);
        try {
            in = new BufferedReader(new FileReader(file));
            String str;
            while ((str = in.readLine()) != null) {
                text.append(str);
            }
        } finally {
            try {
                in.close();
            } catch (Exception e) {
            }
        }
        return text.toString();
    }

    public static boolean fileExists(String fileName) {
        File file = new File(fileName);
        return file.exists();
    }

    public static boolean isDirectory(String path) {
        File f = new File(path);
        return (f.exists() && f.isDirectory());
    }

    /**
     * Deletes all files in the directory but not the directory itself
     * @param dirNameIn
     * @param filePrefixIn Null to delete all files or the prefix of the files to delete
     * @return True if all files were deleted
     */
    public static boolean deleteFilesFromDirectory(final String dirNameIn, final String filePrefixIn) {

        boolean fileNotDeleted = false;
        String filePrefix = filePrefixIn;
        if (filePrefix != null) {
            filePrefix = filePrefix.toUpperCase();
        }
        File dir = new File(dirNameIn);

        if (dir.isDirectory() == false) {
            throw new IllegalArgumentException("Specified path [" + dirNameIn + "] is not a directory");
        }

        if (dir.exists()) {
            String[] children = dir.list();
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    String fileName = children[i];
                    if (filePrefix == null || fileName.toUpperCase().startsWith(filePrefix)) {
                        File f = new File(dir, children[i]);
                        if (f.isFile()) {
                            if (f.delete() == false) {
                                fileNotDeleted = true;
                            }
                        }
                    }
                }
            }
        }
        return (fileNotDeleted == false);

    }

    public static boolean createDirectory(String path) {
        if (StringUtils.isEmpty(path)) {
            return false;
        }

        File dir = new File(path);
        if (!dir.exists()) {
            return dir.mkdir();
        } else {
            return true;
        }

    }

    /** Deletes all directory and all files/directories in it */
    public static boolean deleteDirectory(File dir) {

        if (dir == null) {
            return false;
        }
        if (dir.isDirectory()) {
            String[] children = dir.list();
            if (children != null) {
                for (int i = 0; i < children.length; i++) {
                    boolean success = deleteDirectory(new File(dir, children[i]));
                    if (!success) {
                        return false;
                    }
                }
            }
        }
        // The directory is now empty so delete it
        return dir.delete();
    }

    public static Properties getProperties(String filePath) throws IOException {
        InputStream inStream = null;
        try {
            String decodedFilePath = URLDecoder.decode(filePath, "UTF-8");
            //	    	inStream=new FileInputStream(decodedFilePath);

            ClassLoader cl = Thread.currentThread().getContextClassLoader();
            if (cl.getResource(decodedFilePath) == null) {
                throw new FileNotFoundException("Ignite was unable to find the resource [" + decodedFilePath + "].");
            }

            inStream = cl.getResourceAsStream(decodedFilePath);

            Properties properties = new Properties();
            properties.load(inStream);
            return properties;
        } finally {
            if (inStream != null) {
                try {
                    inStream.close();
                } catch (Exception ex) {
                    logger.warn("Error closing InputStream [filePath=" + filePath + "]: " + ex);
                }
            }
        }
    }

    public static void writePropertiesToFile(String filePath, Properties properties, String comments) throws IOException {
        String decodedFilePath = URLDecoder.decode(filePath, "UTF-8");
        try (OutputStream out = new FileOutputStream(decodedFilePath)) {
            properties.store(out, comments);
        }
    }

    public static String getFileExtension(String filePath) {
        int index = filePath.lastIndexOf(".");
        if (index > -1) {
            return filePath.substring(index + 1);
        } else {
            return "";
        }
    }

    public static String getFileName(String filePath) {
        File f = new File(filePath);
        if (f.exists()) {
            return f.getName();
        } else {
            return null;
        }
    }

    public static long getLastModifiedTime(String filePath) {
        File f = new File(filePath);
        if (f.exists()) {
            return f.lastModified();
        } else {
            return 0;
        }
    }

    public static void copyFile(String sourceFilePath, String destinationFilePath) throws IOException {
        InputStream in = null;
        OutputStream out = null;
        try {
            in = new FileInputStream(new File(sourceFilePath));
            out = new FileOutputStream(new File(destinationFilePath));

            // Transfer bytes from in to out
            byte[] buf = new byte[1024];
            int len;
            while ((len = in.read(buf)) > 0) {
                out.write(buf, 0, len);
            }
        } finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (IOException ex) {
            }
            try {
                if (out != null) {
                    out.close();
                }
            } catch (IOException ex) {
            }
        }
    }

    public static File[] getFilesInDirectory(String path) {
        File dir = new File(path);
        FileFilter fileFilter = new FileFilter() {
            @Override
            public boolean accept(File file) {
                return !file.isDirectory();
            }
        };

        return dir.listFiles(fileFilter);
    }

    public static boolean fileExistByURL(String urlName) {
        try {
            HttpURLConnection.setFollowRedirects(false);
            // note : you may also need
            //        HttpURLConnection.setInstanceFollowRedirects(false)
            HttpURLConnection con =
                    (HttpURLConnection) new URL(urlName).openConnection();
            con.setRequestMethod("HEAD");
            return (con.getResponseCode() == HttpURLConnection.HTTP_OK);
        } catch (Exception e) {
            logger.warn("Error checking if a file exists by URL [URLName=" + urlName + "]: " + e, e);
            return false;
        }
    }

    public static List<File> getAllEntriesInDir(File dir, Predicate<File> predicate) throws IOException {
        return getAllEntriesInDir(dir.toPath(), predicate);
    }

    public static List<File> getAllEntriesInDir(File dir) throws IOException {
        return getAllEntriesInDir(dir.toPath(), p -> true);
    }

    public static List<File> getAllEntriesInDir(Path dir) throws IOException {
        return getAllEntriesInDir(dir, p -> true);
    }

    /**
     * This will give all the entries (files and directories) found in the given directory
     * path and based on the predicate given.
     * To return just files add file.isFile() to the predicate passed in
     * ex. f -> f.getName().toLowerCase().contains("myfile") && f.isFile()
     * If a file is given then just that file is return in the list.
     * @param dir
     * @param predicate
     * @return List<File> - list of entries (files/dir) found in the given directory
     * @throws IOException
     */
    public static List<File> getAllEntriesInDir(Path dir, Predicate<File> predicate) throws IOException {
        if (dir == null) {
            throw new IllegalArgumentException("The Path arugment needs to be set.");
        }

        if (predicate == null) {
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

    /**
     * Gets a shortened version of the a file String.
     * <pre>
     * If the fileIn is < the maxSize, you will get fileIn back
     * Otherwise we will try to get just the file name by taking all characters
     * to the right of either a '\' or a '/'
     * If the fileName alone is > maxSize then we will take the last maxSize characters
     * Otherwise we will try split the String into parts as follows...
     * If the fileIn contains a drive letter C:\, D:\, etc
     *   we will return C:\...\file.ext or if the file name exceeds maxSize
     *   something like this C:\...aLongFileNameThatExceedsTheMax.ext
     * If the fileIn does not contain a drive letter
     * <pre>
     * @param fileIn the file in
     * @param maxSize the max size
     * @return the shortened file
     */
    public static String getShortenedFile(String fileIn, int maxSize) {
        if (fileIn.length() <= maxSize) {
            return fileIn;
        }
        // Let's limit the minimum shrink size, any smaller than this isn't worth doing
        if (maxSize < 10) {
            throw new IllegalArgumentException(maxSize + " is too small, maxSize must be greater than or equal to 10.");
        }

        // Try to get just the file name
        String fileName = null;
        for (int i = fileIn.length() - 1; i > -1; i--) {
            char c = fileIn.charAt(i);
            if (c == '\\' || c == '/') {
                fileName = fileIn.substring(i);
                break;
            }
        }
        // Not sure what this string is, but we will use it as is
        if (fileName == null) {
            fileName = fileIn;
        }

        String elipses = "...";
        StringBuffer sb = new StringBuffer();

        // First of all file names with Drive letters are treated differently,
        // They will always start with the drive letter and then ...
        // C:\\...\file.txt
        if (fileIn.charAt(1) == ':') {
            // Add the drive
            sb.append(fileIn.substring(0, 3));           // C:\
            sb.append(elipses);                          // C:\...
            int total = sb.length() + fileName.length();
            if (total > maxSize) {
                sb.append(fileName.substring(total - maxSize));
            } else {
                sb.append(fileName);
            }
        }
        // Otherwise it is something else, a network drive perhaps
        else {
            // Big filename, just prepend '...' to the what ever we can of the file name
            if (fileName.length() + elipses.length() > maxSize) {
                sb.append(elipses);
                sb.append(fileName.substring(fileName.length() - (maxSize - sb.length())));
            }
            // Otherwise the file name can fit so we lets prepend the path
            else {
                sb.append(fileIn.substring(0, (maxSize - fileName.length() - elipses.length())));
                sb.append(elipses);
                sb.append(fileName);
            }
        }
        return sb.toString();

    }

    /**
     * Close input stream if not null
     * @param inputStream is given input stream
     */
    public static void closeInputStream(InputStream inputStream) {
        if (inputStream != null) {
            try {
                inputStream.close();
            } catch (IOException e) {
                logger.warn("Unable to close the input stream: " + e, e);
            }
        }
    }

    /**
     * Close input stream reader if not null
     * @param inputStreamReader is given input stream reader
     */
    public static void closeInputStreamReader(InputStreamReader inputStreamReader) {
        if (inputStreamReader != null) {
            try {
                inputStreamReader.close();
            } catch (IOException e) {
                logger.warn("Unable to close the input stream reader: " + e, e);
            }
        }
    }

    /**
     * Close output stream if not null
     * @param outputStream is given output stream
     */
    public static void closeOutputStream(OutputStream outputStream) {
        if (outputStream != null) {
            try {
                outputStream.close();
            } catch (IOException e) {
                logger.warn("Unable to close the output stream: " + e, e);
            }
        }
    }

    /**
     * Close socket if not null
     * @param socket is given socket
     */
    public static void closeSocket(Socket socket) {
        if (socket != null) {
            try {
                socket.close();
            } catch (IOException e) {
                logger.warn("Unable to close the socket: " + e, e);
            }
        }
    }
}
