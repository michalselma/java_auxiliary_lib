package auxiliary;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.Properties;
import java.util.Set;

public class Config {

    public ArrayList<ArrayList<String>> readConfigFile(String configname) {
        System.out.println("[TRACE] Class:[Config] Method:[readConfig] START");

        String configfilelocation = "config/" +configname +".conf";
        ArrayList<ArrayList<String>> array2d = new ArrayList<ArrayList<String>>();
        try {
            InputStream input = new FileInputStream(configfilelocation);
            Properties property = new Properties();
            // Reads a property list (key and element pairs) from the input byte stream
            property.load(input);
            System.out.println("[INFO ] Configuration file loaded: '" +configfilelocation +"'");
            Set<String> propertiesnames = property.stringPropertyNames();
            // Iterating properties using For-Each
            String value;
            int index = 0;
            for (String key : propertiesnames) {
                // Add an new empty element (row) to each array dimension. If not .get(index) will always throw 'out of bounds' error.
                array2d.add(new ArrayList<String>());
                value = property.getProperty(key);
                System.out.println("[TRACE] Identified configuration pair of Key-Value: '" +key +" : " +value +"'");
                array2d.get(index).add(key);
                array2d.get(index).add(value);
                index = index + 1;
            }
        }
        catch (Exception ex) {
            System.out.println("[ERROR] Class:[Config] Method:[readConfig] Exception");
            System.out.println("[ERROR] Exception Stack Trace:");
            ex.printStackTrace();
        }
        System.out.println("[TRACE] Array to be returned from method: " +array2d);
        System.out.println("[TRACE] Class:[Config] Method:[readConfig] END");
        return array2d;
    }

}
