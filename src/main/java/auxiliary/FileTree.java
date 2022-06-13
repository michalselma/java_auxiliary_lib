package auxiliary;

import java.util.List;

import auxiliary.system.SystemConsole;

import java.util.ArrayList;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.FileVisitResult;
import java.nio.file.Files;
import java.nio.file.SimpleFileVisitor;
import java.nio.file.attribute.BasicFileAttributes;

public class FileTree {

    public List<String> emptyFiles(Path dir) throws IOException {
        List<String> emptyFileList = new ArrayList<String>();

        Files.walkFileTree(dir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                String msg = SystemConsole.cRED() + "ACCESS FAILED: " + SystemConsole.cRESET() + file.toString();
                System.out.println(msg);
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult visitFile(Path file, BasicFileAttributes attrs) throws IOException {
                if (!Files.isDirectory(file)) {
                    if (attrs.size()==0){
                        String filePath = file.toString();
                        emptyFileList.add(filePath);
                        System.out.println(SystemConsole.cGREEN() + "EMPTY FILE: " + SystemConsole.cRESET() + filePath);
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return emptyFileList;
    }

    public List<String> emptyDirs(Path rootdir) throws IOException {
        List<String> emptyDirList = new ArrayList<String>();

        Files.walkFileTree(rootdir, new SimpleFileVisitor<Path>() {
            @Override
            public FileVisitResult visitFileFailed(Path file, IOException exc) throws IOException {
                String msg = SystemConsole.cRED() + "ACCESS FAILED: " + SystemConsole.cRESET() + file.toString();
                System.out.println(msg);
                return FileVisitResult.CONTINUE;
            }
            @Override
            public FileVisitResult postVisitDirectory(Path dir, IOException exc) throws IOException {
                if (exc != null)
                {
                    String err = SystemConsole.cRED() + "ERROR: " + SystemConsole.cRESET() + exc;
                    System.err.println(err);
                    String msg = SystemConsole.cRED() + "DIR FILESCAN FAILED: " + SystemConsole.cRESET() + dir.toString();
                    System.out.println(msg);
                }
                else {
                    String dirPath = dir.toString();

                    File f = new File(dirPath);
                    String[] files = f.list();
                    if (files.length == 0) {
                        emptyDirList.add(dirPath);
                        System.out.println(SystemConsole.cMAGENTA() + "EMPTY DIR: " + SystemConsole.cRESET() + dirPath);
                    }
                }
                return FileVisitResult.CONTINUE;
            }
        });
        return emptyDirList;
    }
}
