package ViewControllers;

import Data.*;
import Classes.Inventory;
import Classes.Part;
import Classes.Product;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFormController {

    // initializing FXML elements for:
    // Parts table
    @FXML
    private Button PartSearchButton;
    @FXML
    private TextField MainPartSearch;
    @FXML
    private TableView<Part> MainPartTable;
    @FXML
    private TableColumn<Part, Integer> partIdColumn;
    @FXML
    private TableColumn<Part, String> partNameColumn;
    @FXML
    private TableColumn<Part, Integer> partInventoryColumn;
    @FXML
    private TableColumn<Part, Double> partPriceCostColumn;
    @FXML
    private Button MainPartAdd;
    @FXML
    private Button MainPartModify;
    @FXML
    private Button MainPartDelete;
    // Products table
    @FXML
    private Button ProductSearchButton;
    @FXML
    private TextField MainProductSearch;
    @FXML
    private TableView<Product> MainProductTable;
    @FXML
    private TableColumn<Product, Integer> productIdColumn;
    @FXML
    private TableColumn<Product, String> productNameColumn;
    @FXML
    private TableColumn<Product, Integer> productInventoryColumn;
    @FXML
    private TableColumn<Product, Double> productPriceCostColumn;
    @FXML
    private Button MainProductAdd;
    @FXML
    private Button MainProductModify;
    @FXML
    private Button MainProductDelete;
    @FXML
    private Button MainExitButton;

    @FXML
    private void initialize() {
        // populate parts table with data from Inventory System
        MainPartTable.getItems().addAll(Inventory.getAllParts());
        partIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        partNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        partInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        partPriceCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // populate product table with data from Inventory System
        MainProductTable.getItems().addAll(Inventory.getAllProducts());
        productIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        productNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        productInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        productPriceCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // assigning events for scenes to relevant buttons
        setMainButtonEvents();
    }

    private void setMainButtonEvents() {
        PartSearchButton.setOnAction(actionEvent -> {

            // maybe need an if/else for checking if search input is an int or string (id or name)
            // isInteger --> search by part ID
            // else search by name

            ObservableList<Part> filteredList = FXCollections.observableArrayList();
            filteredList = Inventory.lookupPart(MainPartSearch.getText());

            MainPartTable.setItems(filteredList);
        });
        MainPartAdd.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage addPartStage = new Stage();
                addPartStage.setTitle(Text.addPartTitle);
                try {
                    addPartStage.setScene(new Scene(FXMLLoader.load(getClass().getResource(Paths.addPartScenePath))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                addPartStage.show();
                // need to set event for part table to update after child window closes
                addPartStage.setOnHidden(windowEvent -> MainPartTable.setItems(Inventory.getAllParts()));
            }
        });
        MainPartModify.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage modifyPartStage = new Stage();
                modifyPartStage.setTitle(Text.modifyPartTitle);
                try {
                    modifyPartStage.setScene(new Scene(FXMLLoader.load(getClass().getResource(Paths.modifyPartScenePath))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                modifyPartStage.show();
            }
        });
        MainPartDelete.setOnAction(actionEvent -> {
            Part selectedPart = MainPartTable.getSelectionModel().getSelectedItem();
            if (selectedPart != null) {
                Inventory.deletePart(selectedPart);
                // not sure why the table isn't refreshing automatically after a .remove
                // is performed on the obs arr list
                MainPartTable.setItems(Inventory.getAllParts());
            }
        });
        MainProductAdd.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage productAddStage = new Stage();
                productAddStage.setTitle(Text.addProductTitle);
                try {
                    productAddStage.setScene(new Scene(FXMLLoader.load(getClass().getResource(Paths.addProductScenePath))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                productAddStage.show();
            }
        });
        MainProductModify.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage modifyProductStage = new Stage();
                modifyProductStage.setTitle(Text.modifyProductTitle);
                try {
                    modifyProductStage.setScene(new Scene(FXMLLoader.load(getClass().getResource(Paths.modifyProductScenePath))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                modifyProductStage.show();
            }
        });
        MainProductDelete.setOnAction(actionEvent -> {
            Product selectedProduct = MainProductTable.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                Inventory.deleteProduct(selectedProduct);
                // table isn't refreshing automatically after a .remove here as well
                MainProductTable.setItems(Inventory.getAllProducts());
            }
        });
        MainExitButton.setOnAction(event -> System.exit(0));
    }

    // TODO: Figure out how to use these two stupid part/product search functions in an easy/intuitive way
    @FXML
    public void partSearchFilter() {

    }
}
