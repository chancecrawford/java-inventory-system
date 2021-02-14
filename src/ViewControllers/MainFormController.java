package ViewControllers;

import Data.*;
import Classes.Inventory;
import Classes.Part;
import Classes.Product;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;

import java.io.IOException;

public class MainFormController {
    Stage stage;
    Parent scene;

    // initializing FXML elements for:
    // Parts table
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
        setButtonEvents();
    }

    private void setButtonEvents() {
        MainPartAdd.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage newStage = new Stage();
                newStage.setTitle(Text.addPartTitle);
                try {
                    newStage.setScene(new Scene(FXMLLoader.load(getClass().getResource(Paths.addPartScenePath))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                newStage.show();
            }
        });
        MainPartModify.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage newStage = new Stage();
                newStage.setTitle(Text.modifyPartTitle);
                try {
                    newStage.setScene(new Scene(FXMLLoader.load(getClass().getResource(Paths.modifyPartScenePath))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                newStage.show();
            }
        });
        MainProductAdd.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage newStage = new Stage();
                newStage.setTitle(Text.addProductTitle);
                try {
                    newStage.setScene(new Scene(FXMLLoader.load(getClass().getResource(Paths.addProductScenePath))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                newStage.show();
            }
        });
        MainProductModify.setOnAction(new EventHandler<>() {
            @Override
            public void handle(ActionEvent actionEvent) {
                Stage newStage = new Stage();
                newStage.setTitle(Text.modifyProductTitle);
                try {
                    newStage.setScene(new Scene(FXMLLoader.load(getClass().getResource(Paths.modifyProductScenePath))));
                } catch (IOException e) {
                    e.printStackTrace();
                }
                newStage.show();
            }
        });
        MainExitButton.setOnAction(event -> System.exit(0));
    }
}
