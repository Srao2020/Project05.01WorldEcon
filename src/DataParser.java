import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

/**
 * The DataParser class is used to parse data from a file.
 * It contains a method to read the first line of a tab-separated file and print its contents to the console.
 * @author Srao2020
 * @version 05/04/2023
 */
public class DataParser {
    /**
     * Reads the first line of a tab-separated file and prints its contents to the console.
     */
    public static void giveMeAClue() {
        String[] line;
        String filename = "IEF_2023_data.txt";
        try {
            Scanner in = new Scanner(new File(filename));
            line = in.nextLine().split("\t");
            in.close();
            for (String item : line) {
                System.out.println(item);
            }
        }
        catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }
    }
}
