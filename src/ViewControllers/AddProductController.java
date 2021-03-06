package ViewControllers;

import Classes.Inventory;
import Classes.Part;
import Classes.Product;
import Data.Text;
import Utilities.Alerts;
import Utilities.InputValidation;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Class for adding new products to inventory
 */
public class AddProductController {
    Stage stage;

    @FXML
    private Button AddPartSearchButton;
    @FXML
    private TextField AddProductSearch;
    @FXML
    private TextField AddProductName;
    @FXML
    private TextField AddProductInventory;
    @FXML
    private TextField AddProductPriceCost;
    @FXML
    private TextField AddProductMax;
    @FXML
    private TextField AddProductMin;

    @FXML
    private TableView<Part> AddProductPartsTable;
    @FXML
    private TableColumn<Part, Integer> addPartIdColumn;
    @FXML
    private TableColumn<Part, String> addPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> addPartInventoryColumn;
    @FXML
    private TableColumn<Part, Double> addPartPriceCostColumn;
    @FXML
    private Button AddPendingPart;

    @FXML
    private TableView<Part> AddPendingPartTable;
    @FXML
    private TableColumn<Part, Integer> pendingPartIdColumn;
    @FXML
    private TableColumn<Part, String> pendingPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> pendingPartInventoryColumn;
    @FXML
    private TableColumn<Part, Double> pendingPartPriceCostColumn;
    @FXML
    private Button AddProductDelete;
    @FXML
    private Button AddProductSave;
    @FXML
    private Button AddProductCancel;

    // list for storing parts pending addition to product
    private final ObservableList<Part> tempPartsToAdd = FXCollections.observableArrayList();

    /**
     * Populates tables for existing parts and parts to associate with a new product.
     * Also sets up events for buttons.
     */
    @FXML
    private void initialize() {
        // populate parts table with data from Inventory System
        AddProductPartsTable.getItems().addAll(Inventory.getAllParts());
        addPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        addPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        addPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        addPartPriceCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // populate pending parts table with data from Inventory System
        AddPendingPartTable.setItems(tempPartsToAdd);
        pendingPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        pendingPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pendingPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pendingPartPriceCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // assigning events for scenes to relevant buttons
        setAddProductButtonEvents();
    }

    /**
     * Sets actions for add product buttons
     *
     * Search Button
     *  searches existing parts and displays matches in the parts table
     *
     * Add pending part button
     *  adds a part to be associated with the product
     *
     * Delete pending part button
     *  deletes a pending part from the current product
     *
     * Save button
     *  saves the product and returns the user to the main form
     *
     * Cancel button
     *  closes the add product window
     */
    @FXML
    private void setAddProductButtonEvents() {
        // searches inventory for matching part
        // operates exactly the same as the parts table search on the main form
        AddPartSearchButton.setOnAction(actionEvent -> {
            // grab user search input
            String searchQuery = AddProductSearch.getText().trim();
            // check if search input is an int
            if (InputValidation.isInteger(searchQuery)) {
                int searchId = Integer.parseInt(searchQuery);
                ObservableList<Part> searchedPartIdList = FXCollections.observableArrayList(Inventory.lookupPart(searchId));
                AddProductPartsTable.setItems(searchedPartIdList);
            } else {
                ObservableList<Part> searchedPartList = FXCollections.observableArrayList(Inventory.lookupPart(searchQuery));
                // verify field isn't empty before searching for a part name
                if (!searchedPartList.isEmpty()) {
                    AddProductPartsTable.setItems(Inventory.lookupPart(searchQuery));
                } else {
                    Alerts.GenerateAlert("WARNING", "Part Search Error", Text.partSearchError, "", "Show");
                }
            }
        });
        // add existing part from top table to bottom pending parts table
        AddPendingPart.setOnAction(actionEvent -> tempPartsToAdd.add(AddProductPartsTable.getSelectionModel().getSelectedItem()));
        // deletes a pending associated part from bottom table
        AddProductDelete.setOnAction(actionEvent -> {
            // ensure a part is selected then remove
            if (AddPendingPartTable.getSelectionModel().getSelectedItem() != null) {
                tempPartsToAdd.remove(AddPendingPartTable.getSelectionModel().getSelectedItem());
            }
        });
        AddProductSave.setOnAction(actionEvent -> {
            // generate product id
            int tempProductId = (int) (Math.random() * (9999 - 1 + 1) + 1);
            // validate inputs and make sure associated parts table isn't empty
            if (InputValidation.validateProductInputs(
                    AddProductName.getText(),
                    AddProductInventory.getText().trim(),
                    AddProductPriceCost.getText(),
                    AddProductMax.getText().trim(),
                    AddProductMin.getText().trim(),
                    AddPendingPartTable.getItems(),
                    // use cancel button to get current window
                    (Stage)AddProductCancel.getScene().getWindow()
            )) {
                // assign user inputs to new product
                Product tempProduct = new Product(
                        tempProductId,
                        AddProductName.getText(),
                        Double.parseDouble(AddProductPriceCost.getText()),
                        Integer.parseInt(AddProductInventory.getText()),
                        Integer.parseInt(AddProductMin.getText()),
                        Integer.parseInt(AddProductMax.getText())
                );
                // populate associate parts for product from temp list
                tempPartsToAdd.forEach(tempProduct::addAssociatedPart);
                Inventory.addProduct(tempProduct);

                Alerts.GenerateAlert("INFORMATION", "Product Added", Text.productSaveMessage, "", "Show");
                closeWindow();
            }
        });
        AddProductCancel.setOnAction(actionEvent -> closeWindow());
    }

    /**
     * Fires event on main form to refresh products table with the updated product inventory then closes the window
     */
    private void closeWindow() {
        // need to grab instantiated item on parent window to close
        stage = (Stage) AddProductCancel.getScene().getWindow();
        // firing event for main parts table to update with new parts added
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
    }
}
