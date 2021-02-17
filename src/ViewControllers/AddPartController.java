package ViewControllers;

import Classes.InHouse;
import Classes.Inventory;
import Classes.Outsourced;
import Data.*;
import Utilities.*;

import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.*;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

import java.util.ArrayList;
import java.util.List;

public class AddPartController {
    Stage stage;

    @FXML
    private AnchorPane AddPartAnchorPane;
    @FXML
    private RadioButton InHouseRadio;
    @FXML
    private RadioButton OutsourcedRadio;
    @FXML
    private TextField AddPartName;
    @FXML
    private TextField AddPartInventory;
    @FXML
    private TextField AddPartPriceCost;
    @FXML
    private TextField AddPartMax;
    @FXML
    private TextField AddPartMin;
    @FXML
    private Label AddPartUniqueLabel;
    @FXML
    private TextField AddPartUniqueAttribute;
    @FXML
    private Button AddPartSave;
    @FXML
    private Button AddPartCancel;

    // declaring part type to change based on radio button selection
    private String tempPartType;

    @FXML
    private void initialize() {
        // set button events
        setAddPartButtonEvents();
        // set listener for InHouse/Outsourced selection
        setPartTypeListener();
    }

    private void setAddPartButtonEvents() {
        AddPartSave.setOnAction(actionEvent -> {
            // set part input values
            int tempPartId = (int) (Math.random() * (9999 - 1 + 1) + 1);
            String tempPartName = AddPartName.getText();
            int tempPartInventory = Integer.parseInt(AddPartInventory.getText().trim());
            double tempPartPrice = Double.parseDouble(AddPartPriceCost.getText());
            int tempPartMax = Integer.parseInt(AddPartMax.getText().trim());
            int tempPartMin = Integer.parseInt(AddPartMin.getText().trim());
            String tempUniqueAttribute = AddPartUniqueAttribute.getText().trim();

            if (InHouseRadio.isSelected()) {
                tempPartType = "InHouse";
            }
            if (OutsourcedRadio.isSelected()) {
                tempPartType = "Outsourced";
            }
            if (!InHouseRadio.isSelected() && !OutsourcedRadio.isSelected()) {
                tempPartType = "";
            }
            // validate inputs before saving part
            if (InputValidation.validatePartInputs(tempPartName, tempPartInventory, tempPartPrice, tempPartMax, tempPartMin, tempUniqueAttribute, tempPartType)) {
                switch (tempPartType) {
                    case "InHouse":
                        Inventory.addPart(new InHouse(tempPartId, tempPartName, tempPartPrice, tempPartInventory, tempPartMax, tempPartMin, Integer.parseInt(tempUniqueAttribute)));
                        Alerts.GenerateAlert("INFORMATION", "Part Added", "InHouse Part Added Successfully", "", "Show");
                        break;
                    case "Outsourced":
                        Inventory.addPart(new Outsourced(tempPartId, tempPartName, tempPartPrice, tempPartInventory, tempPartMax, tempPartMin, tempUniqueAttribute));
                        Alerts.GenerateAlert("INFORMATION", "Part Added", "Outsourced Part Added Successfully", "", "Show");
                        break;
                }
            }
            clearAllFields();
        });
        AddPartCancel.setOnAction(actionEvent -> {
            stage = (Stage) AddPartCancel.getScene().getWindow();
            // firing event for main parts table to update with new parts added
            stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
            stage.close();
        });
    }

    @FXML
    private void setPartTypeListener() {
        InHouseRadio.setOnAction(actionEvent -> AddPartUniqueLabel.setText(Text.addPartMachineIDLabel));
        OutsourcedRadio.setOnAction(actionEvent -> AddPartUniqueLabel.setText(Text.addPartCompanyNameLabel));
    }

    private void clearAllFields() {
        AddPartName.clear();
        AddPartInventory.clear();
        AddPartPriceCost.clear();
        AddPartMax.clear();
        AddPartMin.clear();
        AddPartUniqueAttribute.clear();
    }
}
