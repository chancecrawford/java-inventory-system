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

    @FXML
    private void setAddProductButtonEvents() {
        AddPartSearchButton.setOnAction(actionEvent -> {
            String searchQuery = AddProductSearch.getText().trim();

            if (InputValidation.isInteger(searchQuery)) {
                int searchId = Integer.parseInt(searchQuery);
                ObservableList<Part> searchedPartIdList = FXCollections.observableArrayList(Inventory.lookupPart(searchId));
                AddProductPartsTable.setItems(searchedPartIdList);
            } else {
                ObservableList<Part> searchedPartList = FXCollections.observableArrayList(Inventory.lookupPart(searchQuery));
                if (!searchedPartList.isEmpty()) {
                    AddProductPartsTable.setItems(Inventory.lookupPart(searchQuery));
                } else {
                    Alerts.GenerateAlert("WARNING", "Part Search Error", "Unable to locate part with that name", "", "Show");
                }
            }
        });
        AddPendingPart.setOnAction(actionEvent -> tempPartsToAdd.add(AddProductPartsTable.getSelectionModel().getSelectedItem()));
        AddProductDelete.setOnAction(actionEvent -> {
            if (AddPendingPartTable.getSelectionModel().getSelectedItem() != null) {
                tempPartsToAdd.remove(AddPendingPartTable.getSelectionModel().getSelectedItem());
            }
        });
        AddProductSave.setOnAction(actionEvent -> {
            int tempProductId = (int) (Math.random() * (9999 - 1 + 1) + 1);

            if (InputValidation.validateProductInputs(
                    AddProductName.getText(),
                    AddProductInventory.getText().trim(),
                    AddProductPriceCost.getText(),
                    AddProductMax.getText().trim(),
                    AddProductMin.getText().trim(),
                    AddPendingPartTable.getItems()
            )) {
                Product tempProduct = new Product(
                        tempProductId,
                        AddProductName.getText(),
                        Double.parseDouble(AddProductPriceCost.getText()),
                        Integer.parseInt(AddProductInventory.getText()),
                        Integer.parseInt(AddProductMin.getText()),
                        Integer.parseInt(AddProductMax.getText())
                );
                tempPartsToAdd.forEach(tempProduct::addAssociatedPart);
                Inventory.addProduct(tempProduct);

                Alerts.GenerateAlert("INFORMATION", "Product Added", "Product Added Successfully", "", "Show");
                closeWindow();
            }
        });
        AddProductCancel.setOnAction(actionEvent -> closeWindow());
    }

    private void closeWindow() {
        // need to grab instantiated item on parent window to close
        stage = (Stage) AddProductCancel.getScene().getWindow();
        // firing event for main parts table to update with new parts added
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
    }
}
