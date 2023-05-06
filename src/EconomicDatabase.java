import java.io.File;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * TODO: Class header comment
 */
public class EconomicDatabase {
    private static ArrayList<Country> database;
    private String[] categories;
    private double[] indices;
    private CountryComparator myComparator;
    private String primarySort, secondarySort;
    private boolean asc;
    private String[] primaryTerms = {"ID: Country ID", "NA: Country Name", "RE: Region", "EF: Economic Freedom Index", "IN: Specific Index"};
    private String[] secondaryTerms = {"JE: Judicial Effectiveness", "GI: Government Integrity", "TB: Tax Burden", "GS: Government Spending",
            "FH: Fiscal Health", "BF: Business Freedom", "LF: Labor Freedom", "MF: Monetary Freedom", "TF: Trade Freedom", "IF: Investment Freedom", "FF: Financial Freedom"};

    /**
     * TODO: Constructor comment
     */
    public EconomicDatabase() {
        database = new ArrayList<>();
        categories = new String[16];
        indices = new double[12];
        primarySort = "";
        secondarySort = "";
    }

    /**
     * TODO: Method comment
     */
    public void populateDatabase() {
        String filename = "IEF_2023_data.txt";
        try {
            boolean firstLine = true;
            Scanner in = new Scanner(new File(filename));
            String[] line;
            while (in.hasNext()) {
                if (firstLine) {
                    categories = in.nextLine().split("\t");
                    firstLine = false;
                } else {
                    line = in.nextLine().split("\t");
                    database.add(new Country(Integer.parseInt(line[0]), line[1], line[2], line[3]));
                    indices[0] = Double.parseDouble(line[4]);
                    indices[1] = Double.parseDouble(line[5]);
                    indices[2] = Double.parseDouble(line[6]);
                    indices[3] = Double.parseDouble(line[7]);
                    indices[4] = Double.parseDouble(line[8]);
                    indices[5] = Double.parseDouble(line[9]);
                    indices[6] = Double.parseDouble(line[10]);
                    indices[7] = Double.parseDouble(line[11]);
                    indices[8] = Double.parseDouble(line[12]);
                    indices[9] = Double.parseDouble(line[13]);
                    indices[10] = Double.parseDouble(line[14]);
                    indices[11] = Double.parseDouble(line[15]);
                    database.get(database.size() - 1).populateIndices(indices);
                }
            }
            in.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * TODO: Method comment
     *
     * @return
     */
    public boolean getSearchCriteria() {
        String userIn;
        Scanner in = new Scanner(System.in);
        while (true) {
            System.out.println("+ Menu of search terms to use for sorting countries +");
            for (String term : primaryTerms)
                System.out.println("\t" + term);
            System.out.print("Enter your preferred sort by term or 'Q' to quit: ");
            userIn = in.nextLine();
            if (userIn.equalsIgnoreCase("q")) {
                in.close();
                return false;
            }
            primarySort = userIn.toUpperCase();
            if (!primarySort.equals("IN")) {
                // prompt for asc/dsc
                System.out.print("Enter 'A' to sort in ascending order or 'D' to sort in descending order: ");
                userIn = in.nextLine();
                asc = (userIn.equalsIgnoreCase("a")) ? true : false;
                for (String term : primaryTerms) {
                    if (term.indexOf(primarySort) != -1) {
                        primarySort = term.substring(4);    // for map retrieval
                        return true;
                    }
                }
                System.out.println("Invalid search term entered, try again.");
            } else {
                System.out.println("Search term selected specific index, please select the index value to display:");
                for (String term : secondaryTerms)
                    System.out.println("\t" + term);
                System.out.print("Enter your preferred sort by index or 'Q' to quit: ");
                userIn = in.nextLine();
                if (userIn.equalsIgnoreCase("q")) {
                    return false;
                }
                secondarySort = userIn.toUpperCase();
                System.out.print("Enter 'A' to sort in ascending order or 'D' to sort in descending order: ");
                userIn = in.nextLine();
                asc = (userIn.equalsIgnoreCase("a")) ? true : false;
                for (String term : secondaryTerms) {
                    if (term.indexOf(secondarySort) != -1) {
                        primarySort = "Specific Index";
                        secondarySort = term.substring(4); // for map retrieval
                        return true;
                    }
                }
                System.out.println("Invalid search term entered, try again.");
            }
        }
    }

    /**
     * TODO: Overwrite this call to Collections.sort with a user-defined sort
     * TODO: Selection, Insertion, or Mergesort
     */
    public void sortDB() {
        CountryComparator comp = new CountryComparator(asc, primarySort, secondarySort);
        s(database, 0, database.size() - 1, comp);
    }

    public static void merge(ArrayList<Country> arr, int left, int middle, int right, CountryComparator comp) {
        int half1 = middle - left + 1;
        int half2 = right - middle;

        Country L[] = new Country[half1];
        Country R[] = new Country[half2];

        for (int i = 0; i < half1; ++i)
            L[i] = arr.get(left + i);
        for (int j = 0; j < half2; ++j)
            R[j] = arr.get(middle + 1 + j);

        int i = 0;
        int j = 0;
        int k = left;
        while (i < half1 && j < half2) {
            if (j >= R.length || (i < L.length && comp.compare(L[i], R[j]) <= 0)) {
                //System.out.println(i);
                arr.set(k, L[i]);
                    i++;
            }
            else {
                arr.set(k, R[j]);
                j++;
            }
            k++;
        }

        while (i < half1) {
            arr.set(k, L[i]);
            i++;
            k++;
        }

        while (j < half2) {
            arr.set(k, R[j]);
            j++;
            k++;
        }
    }

    public void s(ArrayList<Country> arr, int left, int right, CountryComparator comp)    {
        if (left < right) {
            int middle = left + (right - left) / 2;

            s(arr, left, middle, comp);
            s(arr, middle + 1, right, comp);

            merge(arr, left, middle, right, comp);

        }

    }


    /**
     * TODO: Method comment
     */
    public void printDatabase() {
        for(int i = 0; i < database.size(); i++) {
            database.get(i).setSecondaryCategory(secondarySort);
            System.out.println(database.get(i));
        }
    }

    /**
     * TODO: Method comment
     * @param args
     */
    public static void main(String[] args) {
        EconomicDatabase database = new EconomicDatabase();
        database.populateDatabase();
        System.out.println("*** Welcome to the World Economic Freedom Database ***");
        while (database.getSearchCriteria()) {
            database.sortDB();
            database.printDatabase();
        }
        System.out.println("Exiting the program");
    }
}