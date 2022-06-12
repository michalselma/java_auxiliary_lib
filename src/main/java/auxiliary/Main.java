package auxiliary;

import auxiliary.system.SystemConsole;
import auxiliary.system.SystemExecute;

public class Main {

    public static void main(String[] args) {

        Log.trace("Class:[Main] Method:[main] START");

        // TO DO: Add functionality to get startup values of Log.setLoggingLevel from application cmd args
        // TO DO: if not take Main class defaults and then if needed adjust them by calling setLoggingLevel
        System.out.println("**** Setting Console & File Log Levels ****");
        Log.setLoggingLevel(6,6);

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
    }
}
