import java.io.IOException;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

public class Main {


    public static void main(String[] args) throws IOException {
        String absolutePath = Paths.get(".").toAbsolutePath().normalize().toString();

        // Aufgabe 3.2 Text-Fragmente
        printTask(absolutePath + "/src/resources/text-fragmente.txt", "Aufgabe 3.2");

        // Aufgabe 3.3 DNA-Fragmente-1
        printTask(absolutePath + "/src/resources/DNA-fragmente-1.txt", "Aufgabe 3.3");

        // Aufgabe 3.4 DNA-Fragmente-2
        printTask(absolutePath + "/src/resources/DNA-fragmente-2.txt", "Aufgabe 3.4");

        // Aufgabe 3.5 DNA-Fragmente-3
        printTask(absolutePath + "/src/resources/DNA-fragmente-3.txt", "Aufgabe 3.5");


    }

    private static void printTask(String path, String taskLabel) throws IOException {
        System.out.println("------ " + taskLabel + " ------");
        List<String> dnaFragmentList = getListFromFile(path);

        String superString = GreedySuperstring.buildSuperstring(dnaFragmentList);
        System.out.println("Gesamte Laenge vor Superstring Bildung alle Fragmente: " + getFullStringLenght(dnaFragmentList));
        System.out.println("Gesamte Laenge nach Superstring Bildung              : " + superString.length());
        System.out.println();
        System.out.println("------ Superstring ------");
        System.out.println(superString);
        System.out.println();
        System.out.println();
    }

    public static long getFullStringLenght(List<String> strings) {
        long result = 0;

        for (String string : strings) {
            if (string == null) {
                continue;
            }

            result += string.length();
        }

        return result;
    }

    public static List<String> getListFromFile(String path) throws IOException {
        return Files.readAllLines(Paths.get(path));
    }


}
