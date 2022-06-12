package auxiliary.system;

import java.io.IOException;
import java.nio.file.*;
import java.util.ArrayList;
import java.util.List;

import auxiliary.Log;

public class SystemFileManager {

    // The List is an interface, and the ArrayList is a class of Java Collection framework.
    // The List creates a static array, and the ArrayList creates a dynamic array for storing the objects.
    // So the List can not be expanded once it is created but using the ArrayList, we can expand the array when needed.
    // https://www.javatpoint.com/list-vs-arraylist-in-java

    // *** List files in directory, where directory is Path type and returns as List<Path> array. ***
    public static List<Path> listFilesAsListPath(Path dir, String mask) throws IOException {
        List<Path> result = new ArrayList<Path>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, mask)) {
            for (Path entry: stream) {
                result.add(entry.getFileName());
            }
        }
        catch (DirectoryIteratorException e) {
            // I/O error catch during the iteration, the cause is an IOException
            Log.error("[SystemFileManager.listFilesAsPathArray] DirectoryIteratorException: " +e);
            //e.printStackTrace();
        }
        return result;
    }

    // *** List files in directory, where directory is String type and returns as List<String> array. ***
    public static List<String> listFilesAsListString(String dir, String mask) {
        List<String> result = new ArrayList<String>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Path.of(dir), mask)) {
            for (Path entry: stream) {
                result.add(entry.getFileName().toString());
            }
        }
        catch (DirectoryIteratorException e) {
            // I/O error catch during the iteration, the cause is an IOException
            Log.error("[SystemFileManager.listFilesAsArrayListString] DirectoryIteratorException: " +e);
            //e.printStackTrace();
        }
        catch (IOException e) {
            Log.error("[SystemFileManager.listFilesAsArrayListString] IOException: " +e);
            //e.printStackTrace();
        }
        return result;
    }

    // *** List files in directory, where directory name is Path type and returns as ArrayList<Path> array.
    public static ArrayList<Path> listFilesAsArrayListPath(Path dir, String mask) throws IOException {
        ArrayList<Path> result = new ArrayList<Path>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(dir, mask)) {
            for (Path entry: stream) {
                result.add(entry.getFileName());
            }
        }
        catch (DirectoryIteratorException e) {
            // I/O error catch during the iteration, the cause is an IOException
            Log.error("[SystemFileManager.listFilesAsArrayListPath] DirectoryIteratorException: " +e);
            //e.printStackTrace();
        }
        return result;
    }

    // *** List files in directory, where directory name is String type and returns as ArrayList<String> array.
    public static ArrayList<String> listFilesAsArrayListString(String dir, String mask) {
        ArrayList<String> result = new ArrayList<String>();
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(Path.of(dir), mask)) {
            for (Path entry: stream) {
                result.add(entry.getFileName().toString());
            }
        }
        catch (DirectoryIteratorException e) {
            // I/O error catch during the iteration, the cause is an IOException
            Log.error("[SystemFileManager.listFilesAsArrayListString] DirectoryIteratorException: " +e);
            //e.printStackTrace();
        }
        catch (IOException e) {
            Log.error("[SystemFileManager.listFilesAsArrayListString] IOException: " +e);
            //e.printStackTrace();
        }
        return result;
    }

}
