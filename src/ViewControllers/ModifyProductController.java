package ViewControllers;

import Classes.Inventory;
import Classes.Part;
import Classes.Product;
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

    @FXML
    private void setModifyProductButtonEvents() {
        ModifyPartSearchButton.setOnAction(actionEvent -> {
            String searchQuery = ModifyProductSearch.getText().trim();

            if (InputValidation.isInteger(searchQuery)) {
                int searchId = Integer.parseInt(searchQuery);
                ObservableList<Part> searchedPartIdList = FXCollections.observableArrayList(Inventory.lookupPart(searchId));
                ModifyProductPartsTable.setItems(searchedPartIdList);
            } else {
                ObservableList<Part> searchedPartList = FXCollections.observableArrayList(Inventory.lookupPart(searchQuery));
                if (!searchedPartList.isEmpty()) {
                    ModifyProductPartsTable.setItems(Inventory.lookupPart(searchQuery));
                } else {
                    Alerts.GenerateAlert("WARNING", "Part Search Error", "Unable to locate part with that name", "", "Show");
                }
            }
        });
        ModifyAddPendingPart.setOnAction(actionEvent -> tempPartsToAdd.add(ModifyProductPartsTable.getSelectionModel().getSelectedItem()));
        ModifyProductRemovePart.setOnAction(actionEvent -> {
            if (ModifyProductAssociatedPartsTable.getSelectionModel().getSelectedItem() != null) {
                tempPartsToAdd.remove(ModifyProductAssociatedPartsTable.getSelectionModel().getSelectedItem());
            }
        });
        ModifyProductSave.setOnAction(actionEvent -> {
            int tempProductId = Integer.parseInt(ModifyProductID.getText());

            if (InputValidation.validateProductInputs(
                    ModifyProductName.getText(),
                    ModifyProductInventory.getText().trim(),
                    ModifyProductPriceCost.getText(),
                    ModifyProductMax.getText().trim(),
                    ModifyProductMin.getText().trim(),
                    ModifyProductAssociatedPartsTable.getItems(),
                    (Stage)ModifyProductCancel.getScene().getWindow()
            )) {
                Product updatedProduct = new Product(
                        tempProductId,
                        ModifyProductName.getText(),
                        Double.parseDouble(ModifyProductPriceCost.getText()),
                        Integer.parseInt(ModifyProductInventory.getText()),
                        Integer.parseInt(ModifyProductMin.getText()),
                        Integer.parseInt(ModifyProductMax.getText())
                );
                Inventory.updateProduct(selectedProductIndex, updatedProduct);
                Alerts.GenerateAlert("INFORMATION", "Product Updated", "Product Updated Successfully", "", "Show");
                closeWindow();
            }
        });
        ModifyProductCancel.setOnAction(actionEvent -> closeWindow());
    }

    private void closeWindow() {
        // need to grab instantiated item on parent window to close
        stage = (Stage) ModifyProductCancel.getScene().getWindow();
        // firing event for main parts table to update with new parts added
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
    }
}

