package auxiliary.system;


import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.TimeUnit;

import auxiliary.Log;

public class SystemExecute {

    // *** Calls ProcessBuilder to execute command but requires List of Strings as tokenized parameters.
    // *** Do the same as 'Runtime.getRuntime().exec()', but runtime do tokenization itself
    public String SystemCommandExecuteProcessBuilder(List<String> command) {
        Log.trace("Class:[SystemExecute] Method:[SystemCommandExecuteProcessBuilder] START");
        String result = "";
        try {
            ProcessBuilder process = new ProcessBuilder(command);
            List<String> proccmd = process.command();
            Log.info("System ProcessBuilder execute '" +proccmd +"'");
            Process execute = process.start();
            execute.destroy();
        }
        catch (Exception ex) {
            Log.error("Operating System ProcessBuilder Exception: " +ex);
            //ex.printStackTrace();
        }
        Log.trace("Class:[SystemExecute] Method:[SystemCommandExecuteProcessBuilder] END");
        return result;
    }

    // *** This is preferred instead of calling ProcessBuilder directly with List of Strings as tokenized parameters.
    public static String SystemCommandExecuteRuntimeExec(String command) {
        Log.trace("Class:[SystemExecute] Method:[SystemCommandExecuteRuntimeExec] START");
        String result = "";
        try {
            // *** Build and call command
            // *** Runtime.getRuntime().exec() splits command String into single tokens.
            // *** Then calls exec(String[] cmdarray, ......) which construct and calls ProcessBuilder.
            Log.info("System Runtime execute: '" +command +"'");
            Process execute = Runtime.getRuntime().exec(command);
            int seconds = 30;
            // *** Until execute.waitfor() not finished wait up to 30 seconds
            if(!execute.waitFor(seconds, TimeUnit.SECONDS)) {
                // *** Timeout - kill the process
                execute.destroy();
                if (execute.isAlive()) {
                    // *** If process still active force kill
                    execute.destroyForcibly();
                }
                Log.error("Operating System Runtime Process Timeout after " +seconds +" seconds.");
            }
            // *** Then when execute finished (or destroyed) go with InputStreamReader (if destroyed it will catch exception)
            else {
                // *** Get command result (System executed command is Output & os result/response is Input for JVM)
                // *** First read ErrorStream
                InputStreamReader inputerrorstream = new InputStreamReader(execute.getErrorStream());
                // If 'execute.getErrorStream()' will return '.ready()' value true then there is error and we can read it from inputstream.
                if (inputerrorstream.ready()){ // to consider to use bufferederrorstream.ready() instead, but both works
                    Log.warn("Operating System Runtime ErrorStream is not empty, InputStream processing will be ignored.");
                    String error = "";
                    String errorline = "";
                    BufferedReader bufferederrorstream = new BufferedReader(inputerrorstream);
                    while((errorline = bufferederrorstream.readLine()) != null){
                        error = error + errorline;
                        // If BufferedReader has another line add next sline to String
                        if (bufferederrorstream.ready()){
                            error = error + System.lineSeparator();
                        }
                    }
                    bufferederrorstream.close();
                    inputerrorstream.close();
                    Log.warn("Operating System Runtime ErrorStream:");
                    Log.warn(error);
                    result = error;
                }
                // else 'execute.getErrorStream()' returned '.ready()' value false, so we can try to read from 'execute.getInputStream()'
                else {
                    Log.debug("Operating System Runtime ErrorStream is empty, Processing InputStream...");
                    InputStreamReader inputstream = new InputStreamReader(execute.getInputStream());
                    BufferedReader bufferedinputstream = new BufferedReader(inputstream);
                    String response = "";
                    // *** Method to read input stream data without buffering (not suggested by Oracle)
                    // int inputrawdata = inputstream.read();
                    // Log.debug("Operating System Runtime InputStream - first segment of raw data:");
                    // Log.debug(inputrawdata);
                    // while(inputrawdata != -1){
                    //      theChar = (char) inputrawdata;
                    //      response = response + theChar;
                    //      inputrawdata = inputstream.read();
                    // }
                    // Log.debug("Operating System Runtime response (InputStream raw data): " +response);
                    // inputstream.close();
                    // *** END
                    String responseline = "";
                    while((responseline = bufferedinputstream.readLine())  != null ){
                        response = response + responseline;
                        // If BufferedReader has another line add next sline to String
                        if (bufferedinputstream.ready()){
                            response = response + System.lineSeparator();
                        }
                    }
                    bufferedinputstream.close();
                    inputstream.close();
                    Log.debug("Operating System Runtime response (BufferedReader Data): " +response);
                    result = response;
                }
            }
        }
        catch (Exception ex) {
            Log.error("Operating System Runtime Exception: " +ex);
            //ex.printStackTrace();
        }
        Log.trace("Class:[SystemExecute] Method:[SystemCommandExecuteRuntimeExec] END");
        return result;
    }
}
