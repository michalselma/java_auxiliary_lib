package auxiliary;

import auxiliary.system.SystemExecute;

public class WinRegistry {

    private String RegQuery(String key, String valuename){
        System.out.println("[INFO] Checking Windows Registry: "+key+"\\"+valuename);
        String command = "reg query "+key+" /v "+valuename;
        String regcheckresult = SystemExecute.SystemCommandExecuteRuntimeExec(command);
        return regcheckresult;
    }

    private String CheckValueData(String key, String valuename){
        System.out.println("[INFO] Checking Value Data of Windows Registry: "+key+"\\"+valuename);
        String command = "powershell.exe (Get-ItemProperty Registry::"+key+" -Name "+valuename+")."+valuename+"";
        String regvaluedata = SystemExecute.SystemCommandExecuteRuntimeExec(command);
        return regvaluedata;
    }

    private void Add(String key, String valuename, String valuetype, String valuedata){
        System.out.println("[INFO] Adding Windows Registry: "+key+"\\"+valuename);
        String command = "powershell.exe New-ItemProperty Registry::"+key+" -Name "+valuename+" -PropertyType "+valuetype+" -Value "+valuedata;
        SystemExecute.SystemCommandExecuteRuntimeExec(command);
    }

    private void SetValueData(String key, String valuename, String valuedata){
        System.out.println("[INFO] Setting Value Data: "+valuedata+" of Windows Registry: "+key+"\\"+valuename);
        String command = "powershell.exe Set-ItemProperty Registry::"+key+" -Name "+valuename+" -Value "+valuedata;
        SystemExecute.SystemCommandExecuteRuntimeExec(command);
    }
    
    public void ItemProcessing(String key, String valuename, String valuetype, String valuedata){
        System.out.println("[INFO] Processing Registry: "+key+"\\"+valuename+", ValueType: "+valuetype+", ValueData"+valuedata);
        RegQuery(key, valuename); // Full registry Key view just for information purpose for debbuging
        String regcheckresult = CheckValueData(key,valuename);
        if (regcheckresult.equals(valuedata)){
            if (regcheckresult.equals("1")){
                System.out.println("[INFO] Windows Setting "+valuename+" is already Enabled.");
            }
            else if (regcheckresult.equals("0")){
                System.out.println("[INFO] Windows Setting "+valuename+" is already Disabled.");
            }
            else {
                System.out.println("[INFO] Windows Registry "+valuename+" got desired value data set: "+valuedata);
            } 
        }      
        else {
            if (regcheckresult.equals("1")&valuedata.equals("0")){
                System.out.println("[INFO] Windows Setting "+valuename+" is Enabled. Deactivating...");
                SetValueData(key,valuename,valuedata);
            }
            else if(regcheckresult.equals("0")&valuedata.equals("1")){
                System.out.println("[INFO] Windows Setting "+valuename+" is Disabled. Activating...");
                SetValueData(key,valuename,valuedata);
            }       
            else {
                System.out.println("[INFO] Windows Setting "+valuename+" does not exist or is unidentified. Adding...");
                Add(key,valuename,valuetype,valuedata);
            }
        }
    }

}
