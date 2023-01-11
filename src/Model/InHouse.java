package Model;

/**
 * @author Brian Nason, Student Number xxxxxxxxx
 */

/**
 * This class extends the abstract Part class to include a new data point for a Part-type product.
 * This data point is a machineId used only for InHouse created products/
 *
 */
public class InHouse extends Part
{

    private int machineId;

    /**
     *
     * @param id The part's ID
     * @param name The part's Name
     * @param price The Part's Price
     * @param stock The Part's Inventory Level
     * @param min The Part's Minimum Stock Level
     * @param max The Part's Maximum Stock Level
     * @param machineId The ID of the Machine the part comes from
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId)
    {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * @return returns the machineId of the part
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     *
     * @param machineId sets the machine ID of the part
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}