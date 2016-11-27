import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;

public class GreedySuperstring {


    public static String buildSuperstring(String... strings) {

        return buildSuperstring(Arrays.asList(strings));
    }

    /**
     * Erzeugt einen superstring aus einer Menge von Strings. Es wird
     * der Greedy Algorithmus verwendet.
     * @param strings Menge von Strings, welche verwendet werden soll
     * @return den fertigen Superstring
     */
    public static String buildSuperstring(List<String> strings) {
        // Komplexität O(n²)
        List<String> substringFree = getSubstringfreeList(strings);

        // Solange die Menge noch mehr als ein Element hat
        while(substringFree.size() > 1) {

            // Komplexität O(n²)
            // Hole die 2 Strings, welche sich am meisten überlappen (nicht identisch)
            String[] twoStringsWithMaxOverlap = getStringsWithTheMostOverlappingChars(substringFree);

            // Vereine beide Strings an den überlappenden Stellen ;
            String mergedString = merge(twoStringsWithMaxOverlap[0], twoStringsWithMaxOverlap[1]);

            // Entferne die beiden gefundenen Strings aus der Liste
            substringFree.remove(twoStringsWithMaxOverlap[0]);
            substringFree.remove(twoStringsWithMaxOverlap[1]);

            // Füge den zusammengesetzten hinzu
            substringFree.add(mergedString);
        }

        return substringFree.get(0);
    }

    /**
     * Sucht aus der mitgegebenen Menge von Strings die 2 Strings heraus, welche sich am meisten
     * überlappen
     * @param strings Menge von Strings, welche abgesucht werden soll
     * @return ein Array mit den beiden gefundenen Strings
     */
    private static String[] getStringsWithTheMostOverlappingChars(List<String> strings) {
        String[] stringsWithMaxOverlap = new String[2];
        // Starte mit -1, denn wenn es keine überlappungen gibt so sollte der string einfach zusammengefügt werden
        int overlappingSize = -1;

        // Überprüfe jeden String mit jedem
        for (int i = 0 ; i < strings.size() ; i++) {
            for (int j = 0 ; j < strings.size() ; j++) {

                // nicht der selbe String
                if (i != j) {
                    int currentOverlappedSize = overlappedStringLength(strings.get(i), strings.get(j));

                    if(currentOverlappedSize > overlappingSize) {
                        stringsWithMaxOverlap[0] = strings.get(i);
                        stringsWithMaxOverlap[1] = strings.get(j);

                        overlappingSize = currentOverlappedSize;
                    }

                }
            }
        }

        if (overlappingSize == 0 ) {
            System.err.println("Warnung! Keine Ueberlappung gefunden ab jetzt kann der SuperString nicht genau bestimmt werden!");
        }
        return stringsWithMaxOverlap;
    }

    /**
     * Überprüft bei mitgegebenen Strings, wieviele Zeichen sich überlappen
     * @return Anzahl von Zeichen, welche üperlappen. Beispiel: "abcde" "defge" => 2
     */
    private static int overlappedStringLength(String str1, String str2) {
        if (str1 == null || str2 == null) {
            return 0;
        }

        // Trim den 1. String das er nicht länger ist als der 2.
        if (str1.length() > str2.length()) {
            str1 = str1.substring(str1.length() - str2.length());
        }

        // Komplexität O(n)
        int[] T = computeBackTrackTable(str2);

        int str1Counter = 0;
        int countOverlap = 0;
        while (str1Counter + countOverlap < str1.length()) {
            if (str2.charAt(countOverlap) == str1.charAt(str1Counter + countOverlap)) {
                countOverlap += 1;
            } else {
                str1Counter += countOverlap - T[countOverlap];
                if (countOverlap > 0) {
                    countOverlap = T[countOverlap];
                }
            }
        }

        return countOverlap; //<-- changed the return here to return characters matched
    }

    private static int[] computeBackTrackTable(String s) {
        int[] t = new int[s.length()];
        int cnd = 0;
        t[0] = -1;
        t[1] = 0;
        int pos = 2;
        while (pos < s.length()) {
            if (s.charAt(pos - 1) == s.charAt(cnd)) {
                t[pos] = cnd + 1;
                pos += 1;
                cnd += 1;
            } else if (cnd > 0) {
                cnd = t[cnd];
            } else {
                t[pos] = 0;
                pos += 1;
            }
        }

        return t;
    }

    /**
     * Konkatiniert beide Strings miteinander an überlappende Chars werden
     * nur einmal übernommen
     * Beispiel: "abdde" "defgh" => "abcdefgh"
     * @return Konkatinierter String
     */
    public static String merge(String str1, String str2) {
        if (str1 == null) {
            return str2;
        }

        if (str2 == null) {
            return str1;
        }

        for (int i = 0; i < str1.length(); i++) {
            if (str2.startsWith(str1.substring(i))) {
                return str1.substring(0, i) + str2;
            }
        }
        return str1 + str2;
    }

    /**
     * Erzeugt eine Teilstringfreie Menge aus der mitgegebenen
     * @param strings Mitgegebene Menge von Strings
     * @return Die Teilstringfreie Menge
     */
    private static List<String> getSubstringfreeList(List<String> strings) {
        List<String> result = new LinkedList<>();

        for (String string : strings) {
            LinkedList<String> elementsOfSubstringsInBulk = getAllElementsWhichAreSubstrings(strings, string);

            // Ist der String nur 1 mal vorhanden, so ist es ein relevanter String
            // und kann in die Teilstringfreie Menge hinzugefügt werden
            if (elementsOfSubstringsInBulk.size() == 1) {
                result.add(elementsOfSubstringsInBulk.getFirst());
            }
        }

        return result;
    }

    /**
     * Liefert die Menge von Strings zurück, welche den SearchString als Teilstring enthalten
     * @param strings Menge von Strings, welche überprüft werden soll.
     * @param searchString String welcher in der Menge gesucht werden soll.
     * @return Menge aller Strings, welche den searchString als Teilstring enthalten
     */
    private static LinkedList<String> getAllElementsWhichAreSubstrings(List<String> strings, String searchString) {
        LinkedList<String> result = new LinkedList<>();

        for (String string : strings) {
            if (normalizeString(string).contains(normalizeString(searchString))) {
                result.add(string);
            }
        }

        return result;
    }

    private static String normalizeString(String str) {
        return str.toUpperCase();
    }
}
