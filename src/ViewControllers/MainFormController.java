package ViewControllers;

import Data.*;
import Classes.Inventory;
import Classes.Part;
import Classes.Product;

import Utilities.Alerts;
import Utilities.InputValidation;
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

    // selections and there indexes for use in modifying them later
    public static Part selectedPart;
    public static int selectedPartIndex;
    public static Product selectedProduct;
    public static int selectedProductIndex;

    // TODO: Correct tab order for elements in all views
    // add confirmations for remove/delete actions
    // figure out what "Javadoc" comments are

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
            String searchQuery = MainPartSearch.getText().trim();

            if (InputValidation.isInteger(searchQuery)) {
                int searchId = Integer.parseInt(searchQuery);
                ObservableList<Part> searchedPartIdList = FXCollections.observableArrayList(Inventory.lookupPart(searchId));
                MainPartTable.setItems(searchedPartIdList);
            } else {
                ObservableList<Part> searchedPartList = FXCollections.observableArrayList(Inventory.lookupPart(searchQuery));
                if (!searchedPartList.isEmpty()) {
                    MainPartTable.setItems(Inventory.lookupPart(searchQuery));
                } else {
                    Alerts.GenerateAlert("WARNING", "Part Search Error", "Unable to locate part with that name", "", "ShowAndWait");
                }
            }
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
                if (MainPartTable.getSelectionModel().getSelectedItem() != null) {
                    selectedPart = MainPartTable.getSelectionModel().getSelectedItem();
                    selectedPartIndex = Inventory.getAllParts().indexOf(selectedPart);

                    Stage modifyPartStage = new Stage();
                    modifyPartStage.setTitle(Text.modifyPartTitle);
                    try {
                        modifyPartStage.setScene(new Scene(FXMLLoader.load(getClass().getResource(Paths.modifyPartScenePath))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    modifyPartStage.show();
                    modifyPartStage.setOnHidden(windowEvent -> MainPartTable.setItems(Inventory.getAllParts()));
                } else {
                    Alerts.GenerateAlert("WARNING", "Modify Part Error", "You Must Select A Part To Modify", "", "Show");
                }
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
        ProductSearchButton.setOnAction(actionEvent -> {
            String searchQuery = MainProductSearch.getText().trim();

            if (InputValidation.isInteger(searchQuery)) {
                int searchId = Integer.parseInt(searchQuery);
                ObservableList<Product> searchedProductIdList = FXCollections.observableArrayList(Inventory.lookupProduct(searchId));
                MainProductTable.setItems(searchedProductIdList);
            } else {
                ObservableList<Product> searchedProductList = FXCollections.observableArrayList(Inventory.lookupProduct(searchQuery));
                if (!searchedProductList.isEmpty()) {
                    MainProductTable.setItems(Inventory.lookupProduct(searchQuery));
                } else {
                    Alerts.GenerateAlert("WARNING", "Product Search Error", "Unable to locate product with that name", "", "ShowAndWait");
                }
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
                productAddStage.setOnHidden(windowEvent -> MainProductTable.setItems(Inventory.getAllProducts()));
            }
        });
        MainProductModify.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (MainProductTable.getSelectionModel().getSelectedItem() != null) {
                    selectedProduct = MainProductTable.getSelectionModel().getSelectedItem();
                    selectedProductIndex = Inventory.getAllProducts().indexOf(selectedProduct);

                    Stage modifyProductStage = new Stage();
                    modifyProductStage.setTitle(Text.modifyProductTitle);
                    try {
                        modifyProductStage.setScene(new Scene(FXMLLoader.load(getClass().getResource(Paths.modifyProductScenePath))));
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                    modifyProductStage.show();
                    modifyProductStage.setOnHidden(windowEvent -> MainProductTable.setItems(Inventory.getAllProducts()));
                } else {
                    Alerts.GenerateAlert("WARNING", "Modify Product Error", "You Must Select A Product To Modify", "", "Show");
                }
            }
        });
        MainProductDelete.setOnAction(actionEvent -> {
            Product selectedProduct = MainProductTable.getSelectionModel().getSelectedItem();
            if (selectedProduct != null) {
                if (selectedProduct.getAllAssociatedParts().isEmpty()) {
                    Inventory.deleteProduct(selectedProduct);
                } else {
                    Alerts.GenerateAlert("WARNING", "Delete Product Error", "You Cannot Delete A Product With Associated Parts", "", "Show");
                }
                // table isn't refreshing automatically after a .remove here as well
                MainProductTable.setItems(Inventory.getAllProducts());
            }
        });
        MainExitButton.setOnAction(event -> System.exit(0));
    }
}
