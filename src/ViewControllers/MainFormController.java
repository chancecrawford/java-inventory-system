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

    // selections and their indexes for use in modifying them later
    public static Part selectedPart;
    public static int selectedPartIndex;
    public static Product selectedProduct;
    public static int selectedProductIndex;

    // TODO: create build for project turn-in

    /**
     * Initializes and sets values for parts and products tables
     */
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

    /**
     * Sets action events for buttons
     *
     * Part/Product Search Buttons
     * Part/Product Add/Modify/Delete Buttons
     * Application Exit Button
     *
     */
    private void setMainButtonEvents() {
        // check for input type (part id or name) to then search with correct method
        PartSearchButton.setOnAction(actionEvent -> {
            String searchQuery = MainPartSearch.getText().trim();
            // check if search input is an int
            if (InputValidation.isInteger(searchQuery)) {
                int searchId = Integer.parseInt(searchQuery);
                ObservableList<Part> searchedPartIdList = FXCollections.observableArrayList(Inventory.lookupPart(searchId));
                MainPartTable.setItems(searchedPartIdList);
            } else {
                ObservableList<Part> searchedPartList = FXCollections.observableArrayList(Inventory.lookupPart(searchQuery));
                // verify field isn't empty before searching for a part name
                if (!searchedPartList.isEmpty()) {
                    MainPartTable.setItems(Inventory.lookupPart(searchQuery));
                } else {
                    Alerts.GenerateAlert("WARNING", "Part Search Error", Text.partSearchError, "", "Show");
                }
            }
        });
        // creates and displays add part form on click
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
        // creates and displays modify part form on click
        MainPartModify.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (MainPartTable.getSelectionModel().getSelectedItem() != null) {
                    // grab selection and index to pass to modify part form
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
                    // set event for part table to update after window closes
                    modifyPartStage.setOnHidden(windowEvent -> MainPartTable.setItems(Inventory.getAllParts()));
                } else {
                    Alerts.GenerateAlert("WARNING", "Modify Part Error", Text.partModifySelectionError, "", "Show");
                }
            }
        });
        // deletes part selection after confirmation
        MainPartDelete.setOnAction(actionEvent -> {
            Part selectedPart = MainPartTable.getSelectionModel().getSelectedItem();
            if (selectedPart != null) {
                // can't use Alerts class here due to needing to verify against user response from alert
                Alert deletePartWarning = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + selectedPart.getName() + "?", ButtonType.OK, ButtonType.CANCEL);
                deletePartWarning.showAndWait();

                if (deletePartWarning.getResult() == ButtonType.OK) {
                    Inventory.deletePart(selectedPart);
                    // not sure why the table isn't refreshing automatically after a .remove
                    // is performed on the obs arr list
                    MainPartTable.setItems(Inventory.getAllParts());
                }
            }
        });
        // check for input type (product id or name) to then search with correct method
        ProductSearchButton.setOnAction(actionEvent -> {
            String searchQuery = MainProductSearch.getText().trim();

            // check if search input is an int
            if (InputValidation.isInteger(searchQuery)) {
                int searchId = Integer.parseInt(searchQuery);
                ObservableList<Product> searchedProductIdList = FXCollections.observableArrayList(Inventory.lookupProduct(searchId));
                MainProductTable.setItems(searchedProductIdList);
            } else {
                ObservableList<Product> searchedProductList = FXCollections.observableArrayList(Inventory.lookupProduct(searchQuery));
                // verify field isn't empty before searching for a part name
                if (!searchedProductList.isEmpty()) {
                    MainProductTable.setItems(Inventory.lookupProduct(searchQuery));
                } else {
                    Alerts.GenerateAlert("WARNING", "Product Search Error", Text.productSearchError, "", "Show");
                }
            }
        });
        // creates and displays add product form on click
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
        // creates and displays modify part form on click
        MainProductModify.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                if (MainProductTable.getSelectionModel().getSelectedItem() != null) {
                    // grab selection and index to pass to modify product form
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
                    // set event for product table to update after window closes
                    modifyProductStage.setOnHidden(windowEvent -> MainProductTable.setItems(Inventory.getAllProducts()));
                } else {
                    Alerts.GenerateAlert("WARNING", "Modify Product Error", Text.productModifySelectionError, "", "Show");
                }
            }
        });
        // deletes product selection after confirmation if product has no associated parts
        MainProductDelete.setOnAction(actionEvent -> {
            Product selectedProduct = MainProductTable.getSelectionModel().getSelectedItem();

            if (selectedProduct != null) {
                // can't use Alerts class here due to needing to verify against user response from alert
                Alert deleteProductWarning = new Alert(Alert.AlertType.CONFIRMATION, "Delete " + selectedProduct.getName() + "?", ButtonType.OK, ButtonType.CANCEL);
                deleteProductWarning.showAndWait();

                if (deleteProductWarning.getResult() == ButtonType.OK && selectedProduct.getAllAssociatedParts().isEmpty()) {
                    Inventory.deleteProduct(selectedProduct);
                    // table isn't refreshing automatically after a .remove here as well
                    MainProductTable.setItems(Inventory.getAllProducts());
                } else {
                    Alerts.GenerateAlert("WARNING", "Delete Product Error", Text.productDeleteError, "", "Show");
                }
            }
        });
        // exits the application entirely
        MainExitButton.setOnAction(event -> System.exit(0));
    }
}
