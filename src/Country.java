import java.text.DecimalFormat;
import java.util.HashMap;

/**
 * This class represents a Country object which contains information about a country's name, region, and economic freedom index.
 * It also contains a hash map of the country's category indices and a secondary category for display.
 * @author Srao2020
 * @version 05/04/2023
 */
public class Country implements Comparable<Country> {
    private int ID;
    private String countryName, webName;
    private String region;
    private HashMap<String, Double> categoryIndices;
    private double economicFreedomIndex;
    private String secondaryCategory;

    /**
     * Constructs a Country object with the specified id, name, web name, and region.
     * Initializes the category indices hash map and sets the default values to 0.0.
     * @param id
     * @param name
     * @param wName
     * @param region
     */
    public Country(int id, String name, String wName, String region)    {
        this.ID = id;
        this.countryName = name;
        this.webName = wName;
        this.region = region;
        this.secondaryCategory = "";    // populate this with a sub-category for display
        categoryIndices = new HashMap<>();
        categoryIndices.put("Property Rights", 0.0);
        categoryIndices.put("Judicial Effectiveness", 0.0);
        categoryIndices.put("Government Integrity", 0.0);
        categoryIndices.put("Tax Burden", 0.0);
        categoryIndices.put("Government Spending", 0.0);
        categoryIndices.put("Fiscal Health", 0.0);
        categoryIndices.put("Business Freedom", 0.0);
        categoryIndices.put("Labor Freedom", 0.0);
        categoryIndices.put("Monetary Freedom", 0.0);
        categoryIndices.put("Trade Freedom", 0.0);
        categoryIndices.put("Investment Freedom", 0.0);
        categoryIndices.put("Financial Freedom", 0.0);
    }

    /**
     * Populates the country's category indices with the specified values array and calculates the economic freedom index.
     * @param values the specified values
     */
    public void populateIndices(double[] values)    {
        int i = 0;
        double sum = 0.;
        for(String key : categoryIndices.keySet())  {
            sum += values[i];
            categoryIndices.put(key, values[i]);
            i++;
        }
        // calculate economic freedom index
        economicFreedomIndex = sum/categoryIndices.size();
    }

    /**
     * Sets the secondary category for display to the specified term.
     * @param sTerm the specified term
     */
    public void setSecondaryCategory(String sTerm)  {
        secondaryCategory = sTerm;
    }

    /**
     * Returns a string representation of the Country object.
     * @return a string representation of the Country object in the specified format
     */
    public String toString()    {
        DecimalFormat df = new DecimalFormat("0.00");
        String asString = ID + ": " + countryName + " | Region: " + region +
                " | Economic Freedom Index: " + df.format(economicFreedomIndex);
        if(!secondaryCategory.equals(""))
            asString += " | " + secondaryCategory + ": " + categoryIndices.get(secondaryCategory);
        return asString;
    }

    /**
     * Compares this Country object to another Country object based on their economic freedom indices.
     * @param other the object to be compared.
     * @return an integer value indicating the result of the comparison
     */
    public int compareTo(Country other) {
        Double myEFI = economicFreedomIndex;
        Double otherEFI = other.economicFreedomIndex;
        return myEFI.compareTo(otherEFI);
    }

    /**
     * This method returns the economic freedom index of a country.
     * @return the economic freedom index of the country
     */
    public double getEconomicFreedomIndex() {
        return economicFreedomIndex;
    }

    /**
     * This method returns the index value of a specific category for a country.
     * @param indexName
     * @return the index value of the specified category for the country
     */
    public double getIndexValue(String indexName)   {
        return categoryIndices.get(indexName);
    }

    /**
     * This method returns the ID of the country.
     * @return the ID of the country
     */
    public int getID() {
        return ID;
    }

    /**
     * This method returns the name of the country.
     * @return the name of the country
     */
    public String getCountryName() {
        return countryName;
    }

    /**
     * This method returns the region of the country.
     * @return the region of the country
     */
    public String getRegion() {
        return region;
    }
}
