package Classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class for creating/modifying products and adding them to the inventory
 */
public class Product {
    private final ObservableList<Part> associatedParts = FXCollections.observableArrayList();

    private int id;
    private String name;
    private double price;
    private int stock;
    private int min;
    private int max;

    /**
     * Constructor for a Product object. Contains all the same properties as a part except for
     * a list of associated parts tied to the product
     * @param id product id
     * @param name product name
     * @param price product price/cost
     * @param stock product inventory level
     * @param min product minimum inventory
     * @param max product maximum inventory
     */
    public Product(int id, String name, double price, int stock, int min, int max) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.stock = stock;
        this.min = min;
        this.max = max;
    }

    /**
     * Adds an associated part to a product
     * @param part associated part
     */
    public void addAssociatedPart(Part part) {
        associatedParts.add(part);
    }

    /**
     * Deletes an associated part for a product. Not currently used since replacing an existing product
     * with changes in inventory is more efficient.
     * @param selectedAssociatedPart associated part to be deleted
     * @return boolean
     */
    public boolean deleteAssociatedPart(Part selectedAssociatedPart) {
        if(associatedParts.contains(selectedAssociatedPart)) {
            associatedParts.remove(selectedAssociatedPart);
            return true;
        } else {
            return false;
        }
    }

    /**
     * Retrieves all associated parts for a product
     * @return list of all associated parts
     */
    public ObservableList<Part> getAllAssociatedParts() {
        return this.associatedParts;
    }

    /**
     * Retrieves a products id
     * @return product id
     */
    public int getId() {
        return id;
    }

    /**
     * Retrieves a products name
     * @return product name
     */
    public String getName() {
        return name;
    }

    /**
     * Retrieves a products price/cost
     * @return product price/cost
     */
    public double getPrice() {
        return price;
    }

    /**
     * Retrieves a products minimum inventory level
     * @return product inventory minimum
     */
    public int getMin() {
        return min;
    }

    /**
     * Retrieves a products maximum inventory level
     * @return product inventory maximum
     */
    public int getMax() {
        return max;
    }

    /**
     * Retrieves a products inventory level
     * @return product inventory
     */
    public int getStock() {
        return stock;
    }

    /**
     * Sets id for a product
     * @param id product id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * Sets name for a product
     * @param name product name
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * Sets price/cost for a product
     * @param price product price/cost
     */
    public void setPrice(double price) {
        this.price = price;
    }

    /**
     * Sets inventory level for a product
     * @param stock product inventory
     */
    public void setStock(int stock) {
        this.stock = stock;
    }

    /**
     * Sets inventory minimum for a product
     * @param min product inventory minimum
     */
    public void setMin(int min) {
        this.min = min;
    }

    /**
     * Sets inventory maximum for a product
     * @param max product inventory maximum
     */
    public void setMax(int max) {
        this.max = max;
    }
}
