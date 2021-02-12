package Classes;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

public class Inventory {
    // pulling array lists of parts/products
    private static ObservableList<Part> allParts = FXCollections.observableArrayList();
    private static ObservableList<Product> allProducts = FXCollections.observableArrayList();

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
            for(int i = 0; i < allParts.size(); i++) {
                if(allParts.get(i).getId() == partId) {
                    return allParts.get(i);
                }
            }
        }
//        throw new Error("Unable to locate part with that Part ID");
        System.out.println("Unable to locate part with that Part ID");
        return null;
    }

    public static Product lookupProduct(int productId) {
        if(!allProducts.isEmpty()) {
            for(int i = 0; i < allProducts.size(); i++) {
                if(allProducts.get(i).getId() == productId) {
                    return allProducts.get(i);
                }
            }
        }
//        throw new Error("Unable to locate product with that Product ID");
        System.out.println("Unable to locate product with that Product ID");
        return null;
    }

    public static ObservableList<Part> lookupPart(String partName) {
        ObservableList<Part> searchResult = FXCollections.observableArrayList();

        if (!allParts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getName().contains(partName)) {
                    searchResult.add(allParts.get(i));
                }
            }
        return searchResult;
        }
//        throw new Error("Unable to locate part with that name");
        System.out.println("Unable to locate part with that name");
        return null;
    }

    public static ObservableList<Product> lookupProduct(String productName) {
        ObservableList<Product> searchResult = FXCollections.observableArrayList();

        if (!allProducts.isEmpty()) {
            for (int i = 0; i < allParts.size(); i++) {
                if (allParts.get(i).getName().contains(productName)) {
                    searchResult.add(allProducts.get(i));
                }
            }
            return searchResult;
        }
//        throw new Error("Unable to locate part with that name");
        System.out.println("Unable to locate product with that name");
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
