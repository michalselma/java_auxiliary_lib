package auxiliary;


import java.time.LocalDate;
import java.time.LocalTime;
import java.time.temporal.ChronoUnit;

import auxiliary.system.SystemConsole;

public class Log {

// 6 - TRACE - Cyan
// gives more detailed information than the DEBUG level and sits top of the hierarchy.
// 5 - DEBUG - Blue
// helps developer to debug application. Level of message logged will be focused on providing support to a application developer.
// 4 - INFO - Green
// gives the progress and chosen state information. This level will be generally useful for end user.
// This level is one level higher than DEBUG.
// 3 - WARN - Yellow
// gives a warning about an unexpected event to the user.
// The messages coming out of this level may not halt the progress of the system.
// 2 - ERROR - Magenta
// gives information about a serious error which needs to be addressed and may result in unstable state.
// This level is one level higher than WARN.
// 1 - FATAL - Red
// is straightforward and you don’t get it quite often. Once you get this level and it indicates application death.
// 0 - OFF

    private static int consolelog = 3; // *** by default set console logging level to WARN
    private static int filelog = 4; // *** by default set file logging level to INFO

    // *** If needed modify default logging level by calling setLoggingLevel method
    public static void setLoggingLevel(int consoleloglevel, int fileloglevel){
        Log.trace("Class:[Log] Method:[setLoggingLevel] START");
        if ((consoleloglevel>=0 && consoleloglevel<=6) && (fileloglevel>=0 && fileloglevel<=6)){
            consolelog = consoleloglevel;
            filelog = fileloglevel;
            Log.info("Console log level set to " +consolelog);
            Log.info("File log level set to " +filelog);
        }
        else if ((consoleloglevel<0 || consoleloglevel>6) && (fileloglevel>=0 && fileloglevel<=6)){
            filelog = fileloglevel;
            Log.warn("Requested log level " +consoleloglevel+ " can not be set. Using default value " +consolelog);
            Log.info("File log level set to " +filelog);
        }
        else if ((consoleloglevel>=0 && consoleloglevel<=6) && (fileloglevel<0 || fileloglevel>6)){
            consolelog = consoleloglevel;
            Log.info("Console log level set to " +consolelog);
            Log.warn("Requested log level " +fileloglevel+ " can not be set. Using default value " +filelog);
        }
        else {
            // do nothing
        }
        Log.trace("Class:[Log] Method:[setLoggingLevel] END");
    }

    // TO DO: Build a way to workaround it
    // *** Any pieces of code or other methods that are used in trace, debug, info, warn, error or fatal methods
    // *** can not call those Log.***** methods as will cause StackOverflowError (at some point it will try to call itself and break app)
    // *** eg. Log.trace can call other Log methods (eg. Log.debug()) but not the Log.trace().
    // *** Any other/external method that is called by Log.trace can not use Log.trace() method, but can use eg. Log.debug
    // *** On the other hand Log.debug can call Log.trace() but can not use Log.debug()

    public static void trace(String message){
        if (consolelog==6){
            System.out.println(SystemConsole.cCYAN() +"[TRACE]" +SystemConsole.cRESET() +" " +message);
        }
        else {
            // do nothing
        }
        if (filelog==6){
            String logdatetime = LocalDate.now().toString() +" " +LocalTime.now().truncatedTo(ChronoUnit.MILLIS).toString();
            //System.out.println(logdatetime +" [TRACE] " +message);
        }
        else {
            // do nothing
        }
    }

    public static void debug(String message){
        if (consolelog>=5){
            System.out.println(SystemConsole.cBLUE() +"[DEBUG]" +SystemConsole.cRESET() +" " +message);
        }
        else {
            // do nothing
        }
        if (filelog>=5){
            String logdatetime = LocalDate.now().toString()+" "+LocalTime.now().truncatedTo(ChronoUnit.MILLIS).toString();
            //System.out.println(logdatetime +" [DEBUG] " +message);
        }
        else {
            // do nothing
        }
    }

    public static void info(String message){
        if (consolelog>=4){
            System.out.println(SystemConsole.cGREEN() +"[INFO ]" +SystemConsole.cRESET() +" " +message);
        }
        else {
            // do nothing
        }
        if (filelog>=4){
            String logdatetime = LocalDate.now().toString()+" "+LocalTime.now().truncatedTo(ChronoUnit.MILLIS).toString();
            //System.out.println(logdatetime +" [INFO ] " +message);
        }
        else {
            // do nothing
        }
    }

    public static void warn(String message){
        if (consolelog>=3){
            System.out.println(SystemConsole.cYELLOW() +"[WARN ]" +SystemConsole.cRESET() +" " +message);
        }
        else {
            // do nothing
        }
        if (filelog>=3){
            String logdatetime = LocalDate.now().toString()+" "+LocalTime.now().truncatedTo(ChronoUnit.MILLIS).toString();
            //System.out.println(logdatetime +" [WARN ] " +message);
        }
        else {
            // do nothing
        }
    }

    public static void error(String message){
        if (consolelog>=2){
            System.out.println(SystemConsole.cMAGENTA() +"[ERROR]" +SystemConsole.cRESET() +" " +message);
        }
        else {
            // do nothing
        }
        if (filelog>=2){
            String logdatetime = LocalDate.now().toString()+" "+LocalTime.now().truncatedTo(ChronoUnit.MILLIS).toString();
            //System.out.println(logdatetime +" [ERROR] " +message);
        }
        else {
            // do nothing
        }
    }

    public static void fatal(String message){
        if (consolelog>=1){
            System.out.println(SystemConsole.cRED() +"[FATAL]" +SystemConsole.cRESET() +" " +message);
        }
        else {
            // do nothing
        }
        if (filelog>=1){
            String logdatetime = LocalDate.now().toString()+" "+LocalTime.now().truncatedTo(ChronoUnit.MILLIS).toString();
            //System.out.println(logdatetime +" [FATAL] " +message);
        }
        else {
            // do nothing
        }
    }

}
