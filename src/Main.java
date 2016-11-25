import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


public class Main {
    private static ArrayList<String> fragment = new ArrayList<String>();


    public static void main(String[] args) throws IOException {
        readTextFile();
        System.out.println(fragment.size());
        deleteContains();
        System.out.println(fragment.size());
        overlap();
        System.out.println(fragment);
        for (String a : fragment) {
            System.out.println(a);
        }
        System.out.println(fragment.size());


    }

    private static void overlap() {
        int i = 0;
        while (i < fragment.size()) {
            int j = 0;
            int tempOverlapping = 0;
            int tempOverlappingLength = 0;
            while (j < fragment.size() && i != j) {
                int positionSecond = 0;
                int containtmentLength = 1;
                int containtmentLength2 = 1;
                while (positionSecond < fragment.get(j).length() && positionSecond + containtmentLength < fragment.get(j).length()) {
                    String subFragment = fragment.get(i).substring(0, containtmentLength);
                    String subFragment2 = fragment.get(j).substring(0, containtmentLength2);
                    if (subFragment2.equals(subFragment)) {
                        containtmentLength++;
                        containtmentLength2++;
                        if (containtmentLength == fragment.get(i).length() && positionSecond == 0) {
                            j = 0;
                            fragment.remove(i);
                            positionSecond = fragment.get(j).length();
                        }
                    } else {
                        positionSecond++;
                        containtmentLength = 0;
                        containtmentLength2 = 0;
                    }
                }
                if (positionSecond + containtmentLength >= fragment.get(j).length()) {
                    if (containtmentLength > tempOverlappingLength) {
                        tempOverlappingLength = containtmentLength;
                        tempOverlapping = j;
                    }
                }
                j++;


            }
            if (tempOverlappingLength > 0) {
                String subFragment = fragment.get(tempOverlapping).concat(fragment.get(i).substring(tempOverlappingLength, fragment.get(i).length()));
                fragment.add(tempOverlapping, subFragment);
                fragment.remove(tempOverlapping + 1);
                fragment.remove(i);
            } else {
                i++;
            }


        }


    }


    private static void deleteContains() {
        ArrayList<String> listToDelete = new ArrayList<String>();
        listToDelete.addAll(fragment);
        for (String element : fragment) {
            for (String underElement : fragment) {
                if (element.contains(underElement) && element != underElement) {
                    listToDelete.remove(underElement);
                }
            }
        }
        fragment.clear();
        fragment.addAll(listToDelete);
    }


    private static void readTextFile() throws IOException {
        BufferedReader in = new BufferedReader(new FileReader("C:/Users/chris/Google Drive/Hochschule/Semester 9/BIM/text-fragmente.txt"));
        String line;
        while ((line = in.readLine()) != null) {

            fragment.add(line);
        }
        in.close();
    }


}
