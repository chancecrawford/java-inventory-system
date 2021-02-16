package ViewControllers;

import Classes.InHouse;
import Classes.Inventory;
import Classes.Outsourced;
import Data.*;
import Validations.InputValidation;

import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.fxml.FXML;
import javafx.scene.Parent;
import javafx.scene.control.*;
import javafx.stage.Stage;

public class AddPartController {
    Stage stage;
    Parent scene;

    @FXML
    private RadioButton InHouseRadio;
    @FXML
    private RadioButton OutsourcedRadio;
    @FXML
    private TextField AddPartID;
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

    @FXML
    private void initialize() {
        // set button events
        setAddPartButtonEvents();
        // set listener for InHouse/Outsourced selection

    }

    private void setAddPartButtonEvents() {
        AddPartSave.setOnAction(actionEvent -> {
            if (validateInput()) {
                // declare/set part input values
                int tempPartId = (int) (Math.random() * (9999 - 1 + 1) + 1);
                String tempPartName = AddPartName.getText();
                int tempPartInventory = Integer.parseInt(AddPartInventory.getText().trim());
                double tempPartPrice = Double.parseDouble(AddPartPriceCost.getText());
                int tempPartMax = Integer.parseInt(AddPartMax.getText().trim());
                int tempPartMin = Integer.parseInt(AddPartMin.getText().trim());

                // assign correct variables after check on selected radio button
                // then add part type to inventory
                if (InHouseRadio.isSelected()) {
                    int tempMachineId = Integer.parseInt(AddPartUniqueAttribute.getText().trim());

                    Inventory.addPart(new InHouse(tempPartId, tempPartName, tempPartPrice, tempPartInventory, tempPartMax, tempPartMin, tempMachineId));

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Part Added");
                    alert.setHeaderText("InHouse Part Added Successfully");
                    alert.showAndWait();
                }
                if (OutsourcedRadio.isSelected()) {
                    String tempCompanyName = AddPartUniqueAttribute.getText();

                    Inventory.addPart(new Outsourced(tempPartId, tempPartName, tempPartPrice, tempPartInventory, tempPartMax, tempPartMin, tempCompanyName));

                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Part Added");
                    alert.setHeaderText("Outsourced Part Added Successfully");
                    alert.showAndWait();
                }
            }

        });
    }

    private boolean validateInput() {
        StringBuilder inputErrors = new StringBuilder();

        if (!InputValidation.checkInputField(AddPartName.getText().trim())) {
            inputErrors.append(Text.addPartNameError).append("\n");
        }
        if (!InputValidation.checkInputField(AddPartInventory.getText().trim()) || !InputValidation.isInteger(AddPartInventory.getText().trim()) ) {
            inputErrors.append(Text.addPartInventoryError).append("\n");
        }
        if (!InputValidation.checkInputField(AddPartPriceCost.getText()) || !InputValidation.isDouble(AddPartPriceCost.getText())) {
            inputErrors.append(Text.addPartPriceCostError).append("\n");
        }
        if (!InputValidation.checkInputField(AddPartMax.getText().trim()) || !InputValidation.isInteger(AddPartMax.getText().trim())) {
            inputErrors.append(Text.addPartMaxError).append("\n");
        }
        if (!InputValidation.checkInputField(AddPartMin.getText().trim()) || !InputValidation.isInteger(AddPartMin.getText().trim())) {
            inputErrors.append(Text.addPartMinError).append("\n");
        }
        if (InHouseRadio.isSelected()) {
            if (!InputValidation.checkInputField(AddPartUniqueAttribute.getText().trim()) || !InputValidation.isInteger(AddPartUniqueAttribute.getText().trim())) {
                inputErrors.append(Text.addPartMachineIdError);
            }
        }
        if (OutsourcedRadio.isSelected()) {
            if (!InputValidation.checkInputField(AddPartUniqueAttribute.getText().trim())) {
                inputErrors.append(Text.addPartCompanyNameError);
            }
        }

        if (inputErrors.length() > 0 || !inputErrors.toString().equals("")) {
            Alert alert = new Alert(Alert.AlertType.WARNING);
            alert.setTitle("Warning");
            alert.setHeaderText("Required Fields Empty or Invalid");
            alert.setContentText(inputErrors.toString());

            alert.showAndWait();
            return false;
        }
        return true;
    }
}
