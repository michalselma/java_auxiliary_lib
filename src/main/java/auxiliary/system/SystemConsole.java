package auxiliary.system;

import auxiliary.Log;

public class SystemConsole {

    // *** ANSI console text colours & styles
    // *** https://en.wikipedia.org/wiki/ANSI_escape_code
    private static final String ANSI_RED = "\u001B[31m";
    private static final String ANSI_GREEN = "\u001B[32m";
    private static final String ANSI_YELLOW = "\u001B[33m";
    private static final String ANSI_BLUE = "\u001B[34m";
    private static final String ANSI_MAGENTA = "\u001B[35m";
    private static final String ANSI_CYAN = "\u001B[36m";
    private static final String ANSI_DEFAULT = "\u001B[0m";

    private static boolean consolecolours = false; // by default set to false

    public static void setSystemConsoleColoursUsage(boolean value) {
        Log.trace("Class:[SystemConsole] Method:[setSystemConsoleColoursUsage] START");
        consolecolours = value;
        if (value){  // *** The same as (value==true)
            Log.info("Application/System console colours have been turned on");
        }
        else {
            Log.info("Application/System console colours have been turned off");
        }
        Log.trace("Class:[SystemConsole] Method:[setSystemConsoleColoursUsage] END");
    }

    public static String cRED() {
        if (consolecolours) {
            return ANSI_RED;
        }
        return "";
    }

    public static String cGREEN() {
        if (consolecolours) {
            return ANSI_GREEN;
        }
        return "";
    }

    public static String cBLUE() {
        if (consolecolours) {
            return ANSI_BLUE;
        }
        return "";
    }

    public static String cMAGENTA() {
        if (consolecolours) {
            return ANSI_MAGENTA;
        }
        return "";
    }

    public static String cCYAN() {
        if (consolecolours) {
            return ANSI_CYAN;
        }
        return "";
    }

    public static String cYELLOW() {
        if (consolecolours) {
            return ANSI_YELLOW;
        }
        return "";
    }

    public static String cRESET() {
        if (consolecolours) {
            return ANSI_DEFAULT;
        }
        return "";
    }
}