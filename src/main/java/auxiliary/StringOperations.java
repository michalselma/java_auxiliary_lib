package auxiliary;


public class StringOperations {

    // *** Checks if String contains any of words. Returns true or false.
    public static boolean checkIfContainWords(String inputString, String[] items) {
        boolean found = false;
        for (String item : items) {
            if (inputString.contains(item)) {
                found = true;
                break;
            }
        }
        return found;
    }

    // *** This will return ONLY first word that is found. If none is found will return empty string.
    public static String returnIfWordIsFound(String inputString, String[] items) {
        String wordFound = "";
        for (String item : items) {
            if (inputString.contains(item)) {
                wordFound = item;
                break;
            }
        }
        return wordFound;
    }

}
