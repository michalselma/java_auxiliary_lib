package auxiliary;


import java.time.Instant;

public class DefinedTimeStamp {
	
	public static String currentUtcTimeDate() {
		Instant instant = Instant.now(); // time stamp from the Java epoch
		String timestamp = instant.toString();
		timestamp = timestamp.replaceAll("T"," "); // replace T with space
		timestamp = timestamp.replaceAll("Z",""); // replace Z with empty
		if (timestamp.length()>23) {
			timestamp = timestamp.substring(0, 23); // remove all after first 23 chars
		}
		if (timestamp.length()==19) {
			timestamp = timestamp+".000";
		}
		if (timestamp.length()==20) {
			timestamp = timestamp+"000";
		}
		if (timestamp.length()==21) {
			timestamp = timestamp+"00";
		}
		if (timestamp.length()==22) {
			timestamp = timestamp+"0";
		}
		return timestamp;
	}
}
