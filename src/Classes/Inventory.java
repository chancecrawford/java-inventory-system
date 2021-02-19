package Classes;

import Utilities.Alerts;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    // pulling array lists of parts/products
    private static final ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static final ObservableList<Product> allProducts = FXCollections.observableArrayList();

    // default constructor
    public Inventory() {
    }

    public static void addPart(Part newPart) {
        allParts.add(newPart);
    }

    public static void addProduct(Product newProduct) {
        allProducts.add(newProduct);
    }

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

    public static void updatePart(int index, Part selectedPart) {
        allParts.set(index, selectedPart);
    }

    public static void updateProduct(int index, Product newProduct) {
        allProducts.set(index, newProduct);
    }

    public static boolean deletePart(Part selectedPart) {
        return allParts.remove(selectedPart);
    }

    public static boolean deleteProduct(Product selectedProduct) {
        return allProducts.remove(selectedProduct);
    }

    public static ObservableList<Part> getAllParts() {
        return allParts;
    }

    public static ObservableList<Product> getAllProducts() {
        return allProducts;
    }
}
