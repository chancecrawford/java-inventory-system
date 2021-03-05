package Classes;

import Utilities.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 * Class that tracks inventory for all parts and products within application.
 * Contains functions for adding, modifying, and searching for parts/products.
 */
public class Inventory {
    // pulling array lists of parts/products
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    /**
     * Default inventory constructor
     */
    public Inventory() {
    }

    /**
     * Adds a part to inventory
     * @param newPart new part to be added
     */
    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    /**
     * Adds a product to inventory
     * @param newProduct new product to be added
     */
    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

    /**
     * Searches for a part in inventory via its part id
     * This will still return a part if the id is not exactly as entered. It would be ideal that this function returns a list of parts that contain
     * the user search input in the future but, for now, it will return the closest match if one exists
     * @param partId id to search parts list with
     * @return part, if found, or null if one is not
     */
    public static Part lookupPart(int partId) {
        if(!allParts.isEmpty()) {
            for (Part part : allParts) {
                if (String.valueOf(part.getId()).contains(String.valueOf(partId))) {
                    return part;
                }
            }
        }
        Alerts.GenerateAlert("WARNING", "Part Search Error", "Unable to locate part with that Part ID", "", "ShowAndWait");
        return null;
    }

    /**
     * Searches for a product in inventory via its product id
     * This will still return a product if the id is not exactly as entered. It would be ideal that this function returns a list of products that contain
     * the user search input in the future but, for now, it will return the closest match if one exists
     * @param productId id to search products list with
     * @return product, if found, or null if one is not
     */
    public static Product lookupProduct(int productId) {
        if(!allProducts.isEmpty()) {
            for (Product product : allProducts) {
                if (String.valueOf(product.getId()).contains(String.valueOf(productId))) {
                    return product;
                }
            }
        }
        Alerts.GenerateAlert("WARNING", "Product Search Error", "Unable to locate product with that Product ID", "", "ShowAndWait");
        return null;
    }

    /**
     * Searches for a part in inventory via its part name
     * This will still return a part if the name is not complete but still contains part of the search input. It would be ideal that this function returns a list of parts
     * that contain the user search input in the future but, for now, it will return the closest match if one exists
     * @param partName name to search parts list with
     * @return part, if found, or null if one is not
     */
    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> searchResult = FXCollections.observableArrayList();

        if (!allParts.isEmpty()) {
            for (Part part : allParts) {
                if (part.getName().toLowerCase().contains(partName.toLowerCase().trim())) {
                    searchResult.add(part);
                }
            }
            return searchResult;
        }
        return null;
    }

    /**
     * Searches for a product in inventory via its product name
     * This will still return a product if the name is not complete but still contains part of the search input. It would be ideal that this function returns a list of products
     * that contain the user search input in the future but, for now, it will return the closest match if one exists
     * @param productName name to search products list with
     * @return product, if found, or null if one is not
     */
    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> searchResult = FXCollections.observableArrayList();

        if (!allProducts.isEmpty()) {
            for (Product product : allProducts) {
                if (product.getName().toLowerCase().contains(productName.toLowerCase().trim())) {
                    searchResult.add(product);
                }
            }
            return searchResult;
        }
        return null;
    }

    /**
     * Updates part at given index in inventory
     * @param index part index
     * @param selectedPart part selected to update
     */
    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    /**
     * Updates product at given index in inventory
     * @param index product index
     * @param newProduct product selected to update
     */
    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    /**
     * Deletes selected part in main parts table
     * In the future, this function could be void since the boolean is not needed
     * @param selectedPart part selected to delete
     * @return  boolean
     */
    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    /**
     * Deletes selected product in main products table
     * In the future, this function could be void since the boolean is not needed
     * @param selectedProduct product selected to delete
     * @return boolean
     */
    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    /**
     * @return list of all parts in inventory
     */
    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    /**
     * @return list of all products in inventory
     */
    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
