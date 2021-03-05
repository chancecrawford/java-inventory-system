package Classes;

/**
 * Class for creating InHouse parts that is an extension of the abstract class, Part.
 */
public class InHouse extends Part {
    private int machineId;

    /**
     * Constructor for creating an InHouse part
     * @param id part id
     * @param name part name
     * @param price part price/cost
     * @param stock part inventory level
     * @param min part inventory minimum
     * @param max part inventory maximum
     * @param machineId part machine id
     */
    public InHouse(int id, String name, double price, int stock, int min, int max, int machineId) {
        super(id, name, price, stock, min, max);
        this.machineId = machineId;
    }

    /**
     * Returns InHouse part machine id
     * @return machine id
     */
    public int getMachineId() {
        return machineId;
    }

    /**
     * Sets the machine id for an InHouse part
     * @param machineId new machine id
     */
    public void setMachineId(int machineId) {
        this.machineId = machineId;
    }
}
