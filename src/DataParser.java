import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class DataParser {
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
