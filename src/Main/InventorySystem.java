package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Classes.*;

import java.io.IOException;

public class InventorySystem extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws IOException {
        createTestInventory();

        Parent root = FXMLLoader.load(getClass().getResource("/ViewControllers/MainForm.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    void createTestInventory() {
        Inventory.addPart(new InHouse(1234, "InHouse Part", 3.50, 5, 1, 10, 5532));
        Inventory.addPart(new Outsourced(8541, "Outsourced Part", 4.50, 3, 1, 20, "Stray"));

        Inventory.addProduct(new Product(1511, "Product", 5.25, 6, 1, 8));
        Inventory.addProduct(new Product(1823, "Bicycle", 2.25, 9, 1, 12));
    }
}
