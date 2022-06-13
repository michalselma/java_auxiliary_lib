package auxiliary;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

import auxiliary.system.SystemConsole;
import auxiliary.system.SystemExecute;

public class Main {

    public static void main(String[] args) throws IOException {

        Log.trace("Class:[Main] Method:[main] START");

        // TO DO: Add functionality to get startup values of Log.setLoggingLevel from application cmd args
        // TO DO: if not take Main class defaults and then if needed adjust them by calling setLoggingLevel
        System.out.println("**** Setting Console & File Log Levels ****");
        Log.setLoggingLevel(6,6);

        // VirtualTerminalLevel Win Regisry needs to be active to use ANSI windows command line terminal colours
        System.out.println("**** Windows VirtualTerminalLevel Activation ****");  
	    WinRegistry registry = new WinRegistry();

        String regkey = "HKEY_CURRENT_USER\\Console";
        String regname = "VirtualTerminalLevel";
        String regvaluetype = "DWORD";
        String regvaluedata = "1";
        registry.ItemProcessing(regkey,regname,regvaluetype,regvaluedata);

        System.out.println("**** Setting Console Colours Usage ****");
        SystemConsole.setSystemConsoleColoursUsage(true);

        System.out.println("**** Application Log Sample ****");
        Log.trace("Trace Sample");
        Log.debug("Debug Sample");
        Log.info("Info Sample");
        Log.warn("Warn Sample");
        Log.error("Error Sample");
        Log.fatal("Fatal Sample");
        Log.trace("Class:[Main] Method:[main] END");

        System.out.println("**** Operating System Runtime Execute Sample ****");
        String command = "powercfg /LIST";
        SystemExecute.SystemCommandExecuteRuntimeExec(command);

        System.out.println("**** walkFileTree using SimpleFileVisitorScan to get details of each file ****");
        Path path = Paths.get("c:\\$Recycle.Bin\\");
        SimpleFileVisitorScan visitor = new SimpleFileVisitorScan();
        Files.walkFileTree(path, visitor);
        System.out.println("**** FileTree class to find zero lengh files and empty directories ****");
        FileTree customvisitor = new FileTree();
        customvisitor.emptyFiles(path);
        customvisitor.emptyDirs(path);

    }
}
