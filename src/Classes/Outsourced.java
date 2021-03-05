package Classes;

/**
 * Class for creating Outsourced parts that is an extension of the abstract class, Part.
 */
public class Outsourced extends Part {
    private String companyName;

    /**
     * Constructor for creating an Outsourced part
     * @param id part id
     * @param name part name
     * @param price part price/cost
     * @param stock part inventory level
     * @param min part inventory minimum
     * @param max part inventory maximum
     * @param companyName part company name
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName) {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * Returns company name of Outsourced part
     * @return company name
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * Sets company name for an Outsourced part
     * @param companyName new company name
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }
}
