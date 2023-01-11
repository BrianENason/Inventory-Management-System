package Model;

/**
 * @author Brian Nason, Student Number xxxxxxxxx
 */

/**
 * This class extends the abstract Part class to include a new data point for a Part-type product.
 * This data point is a machineId used only for InHouse created products/
 *
 */
public class Outsourced extends Part
{

    private String companyName;

    /**
     *
     * @param id The part's ID
     * @param name The Part's Name
     * @param price The Part's Price
     * @param stock The Part's Inventory Level
     * @param min The part's Minimum Stock Level
     * @param max The part's Maximum Stock Level
     * @param companyName The name of the company from which the part is sourced from
     */
    public Outsourced(int id, String name, double price, int stock, int min, int max, String companyName)
    {
        super(id, name, price, stock, min, max);
        this.companyName = companyName;
    }

    /**
     * @return companyName of part
     */
    public String getCompanyName() {
        return companyName;
    }

    /**
     * @param companyName sets the company name of the part
     */
    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

}