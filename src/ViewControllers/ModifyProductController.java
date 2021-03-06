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
 * Class for modifying existing products in the inventory
 */
public class ModifyProductController {
    Stage stage;

    @FXML
    private Button ModifyPartSearchButton;
    @FXML
    private TextField ModifyProductSearch;
    @FXML
    private TextField ModifyProductID;
    @FXML
    private TextField ModifyProductName;
    @FXML
    private TextField ModifyProductInventory;
    @FXML
    private TextField ModifyProductPriceCost;
    @FXML
    private TextField ModifyProductMax;
    @FXML
    private TextField ModifyProductMin;

    @FXML
    private TableView<Part> ModifyProductPartsTable;
    @FXML
    private TableColumn<Part, Integer> modifyPartIdColumn;
    @FXML
    private TableColumn<Part, String> modifyPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> modifyPartInventoryColumn;
    @FXML
    private TableColumn<Part, Double> modifyPartPriceCostColumn;
    @FXML
    private Button ModifyAddPendingPart;

    @FXML
    private TableView<Part> ModifyProductAssociatedPartsTable;
    @FXML
    private TableColumn<Part, Integer> pendingPartIdColumn;
    @FXML
    private TableColumn<Part, String> pendingPartNameColumn;
    @FXML
    private TableColumn<Part, Integer> pendingPartInventoryColumn;
    @FXML
    private TableColumn<Part, Double> pendingPartPriceCostColumn;
    @FXML
    private Button ModifyProductRemovePart;
    @FXML
    private Button ModifyProductSave;
    @FXML
    private Button ModifyProductCancel;

    // assign selected part and index in inventory to local variables
    private final Product selectedProduct = MainFormController.selectedProduct;
    private final int selectedProductIndex = MainFormController.selectedProductIndex;
    // list for storing parts pending addition to product
    private final ObservableList<Part> tempPartsToAdd = selectedProduct.getAllAssociatedParts();

    /**
     * Populates input fields with selected product and both existing parts in inventory
     * and associated parts to the product. Also sets events for buttons in the window.
     */
    @FXML
    private void initialize() {
        // populate fields with selected part info
        ModifyProductID.setText(String.valueOf(selectedProduct.getId()));
        ModifyProductName.setText(selectedProduct.getName());
        ModifyProductInventory.setText(String.valueOf(selectedProduct.getStock()));
        ModifyProductPriceCost.setText(String.valueOf(selectedProduct.getPrice()));
        ModifyProductMax.setText(String.valueOf(selectedProduct.getMax()));
        ModifyProductMin.setText(String.valueOf(selectedProduct.getMin()));

        // populate parts table with data from Inventory System
        ModifyProductPartsTable.getItems().addAll(Inventory.getAllParts());
        modifyPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        modifyPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        modifyPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        modifyPartPriceCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // populate pending parts table with data from Inventory System
        ModifyProductAssociatedPartsTable.setItems(tempPartsToAdd);
        pendingPartIdColumn.setCellValueFactory(new PropertyValueFactory<>("id"));
        pendingPartNameColumn.setCellValueFactory(new PropertyValueFactory<>("name"));
        pendingPartInventoryColumn.setCellValueFactory(new PropertyValueFactory<>("stock"));
        pendingPartPriceCostColumn.setCellValueFactory(new PropertyValueFactory<>("price"));

        // assigning events for scenes to relevant buttons
        setModifyProductButtonEvents();
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
     *  deletes an associated part from the current product
     *
     * Save button
     *  updates the product and returns the user to the main form
     *
     * Cancel button
     *  closes the add product window
     */
    @FXML
    private void setModifyProductButtonEvents() {
        // searches inventory for matching part
        // operates exactly the same as the parts table search on the main form
        ModifyPartSearchButton.setOnAction(actionEvent -> {
            // grab user search input
            String searchQuery = ModifyProductSearch.getText().trim();
            // check if search input is an int
            if (InputValidation.isInteger(searchQuery)) {
                int searchId = Integer.parseInt(searchQuery);
                ObservableList<Part> searchedPartIdList = FXCollections.observableArrayList(Inventory.lookupPart(searchId));
                ModifyProductPartsTable.setItems(searchedPartIdList);
            } else {
                ObservableList<Part> searchedPartList = FXCollections.observableArrayList(Inventory.lookupPart(searchQuery));
                // verify field isn't empty before searching for a part name
                if (!searchedPartList.isEmpty()) {
                    ModifyProductPartsTable.setItems(Inventory.lookupPart(searchQuery));
                } else {
                    Alerts.GenerateAlert("WARNING", "Part Search Error", Text.partSearchError, "", "Show");
                }
            }
        });
        // add existing part from top table to bottom associated parts table
        ModifyAddPendingPart.setOnAction(actionEvent -> tempPartsToAdd.add(ModifyProductPartsTable.getSelectionModel().getSelectedItem()));
        // deletes an associated part of product from bottom table
        ModifyProductRemovePart.setOnAction(actionEvent -> {
            // ensure a part is selected then remove
            if (ModifyProductAssociatedPartsTable.getSelectionModel().getSelectedItem() != null) {
                tempPartsToAdd.remove(ModifyProductAssociatedPartsTable.getSelectionModel().getSelectedItem());
            }
        });
        ModifyProductSave.setOnAction(actionEvent -> {
            // generate product id
            int tempProductId = Integer.parseInt(ModifyProductID.getText());
            // validate inputs before updating product
            if (InputValidation.validateProductInputs(
                    ModifyProductName.getText(),
                    ModifyProductInventory.getText().trim(),
                    ModifyProductPriceCost.getText(),
                    ModifyProductMax.getText().trim(),
                    ModifyProductMin.getText().trim(),
                    ModifyProductAssociatedPartsTable.getItems(),
                    // used cancel button to get current window
                    (Stage)ModifyProductCancel.getScene().getWindow()
            )) {
                // assign user inputs to updated product
                Product updatedProduct = new Product(
                        tempProductId,
                        ModifyProductName.getText(),
                        Double.parseDouble(ModifyProductPriceCost.getText()),
                        Integer.parseInt(ModifyProductInventory.getText()),
                        Integer.parseInt(ModifyProductMin.getText()),
                        Integer.parseInt(ModifyProductMax.getText())
                );
                // update associated parts for product from temp list
                tempPartsToAdd.forEach(updatedProduct::addAssociatedPart);
                Inventory.updateProduct(selectedProductIndex, updatedProduct);

                Alerts.GenerateAlert("INFORMATION", "Product Updated", Text.productUpdatedMessage, "", "Show");
                closeWindow();
            }
        });
        ModifyProductCancel.setOnAction(actionEvent -> closeWindow());
    }

    /**
     * Fires event on main form to refresh products table with the updated product inventory then closes the window
     */
    private void closeWindow() {
        // need to grab instantiated item on parent window to close
        stage = (Stage) ModifyProductCancel.getScene().getWindow();
        // firing event for main parts table to update with new parts added
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
    }
}

