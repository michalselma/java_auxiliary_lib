// https://docs.oracle.com/en/java/javase/12/docs/api/java.base/java/nio/file/FileVisitor.html
// https://docs.oracle.com/javase/tutorial/essential/io/walk.html

package auxiliary;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.FileVisitResult;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class SimpleFileVisitorScan extends SimpleFileVisitor<Path> {

    @Override
    public FileVisitResult preVisitDirectory(Path dir, BasicFileAttributes attrs) throws IOException {
        Log.debug("PROCESSING DIRECTORY: "+dir.toString());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
        Log.debug("PROCESSING FILE: "+file.toString());
        if (attrs.isSymbolicLink()) {
            Log.warn("FILE TYPE: Symbolic link");
        } 
        else if (attrs.isRegularFile()) {
            Log.trace("FILE TYPE: Regular file");
        } 
        else if (attrs.isOther()) {
            Log.warn("FILE TYPE: Other");
        }
        else if (attrs.isDirectory()) {
            Log.trace("FILE TYPE: Directory");
        }
        else {
            Log.error("FILE TYPE: Unidentified");
        }
        Long fileSize = attrs.size();
        Log.info("FILE SIZE: "+fileSize+" ["+fileSize+" bytes]");
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
        Log.debug("IOException: "+exc);
        Log.error("ACCESS FAILED: "+file.toString());
        return FileVisitResult.CONTINUE;
    }

    @Override
    public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
        if (exc != null)
        {
            Log.debug("IOException: "+exc);
            Log.error("DIRECTORY SCAN FAILED: "+dir.toString());
        }
        Log.info("DIRECTORY SCAN FINISHED: "+dir.toString());
        return FileVisitResult.CONTINUE;
    }
}