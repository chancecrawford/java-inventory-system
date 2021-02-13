package Main;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import Classes.*;

public class InventorySystem extends Application {
    Inventory inventory = new Inventory();

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        createInventory(inventory);

        Parent root = FXMLLoader.load(getClass().getResource("/ViewControllers/MainForm.fxml"));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    void createInventory(Inventory inventory) {
        Inventory.addPart(new InHouse(01, "IH Part 1", 3.50, 5, 1, 10, 55));
        Inventory.addPart(new Outsourced(03, "OS Part 1", 4.50, 3, 1, 20, "Streisshand"));

        Inventory.addProduct(new Product(15, "Prod 01", 5.25, 6, 1, 8));
        Inventory.addProduct(new Product(18, "Prod 02", 2.25, 9, 1, 12));
    }
}
