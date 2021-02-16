package ViewControllers;

import Classes.InHouse;
import Classes.Inventory;
import Classes.Outsourced;
import Data.*;
import Utilities.*;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

public class AddPartController {
    Stage stage;

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

    @FXML
    private void initialize() {
        // set button events
        setAddPartButtonEvents();
        // set listener for InHouse/Outsourced selection
        setPartTypeListener();
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

                    Alerts.GenerateAlert("INFORMATION", "Part Added", "InHouse Part Added Successfully", "", "Show");

                }
                if (OutsourcedRadio.isSelected()) {
                    String tempCompanyName = AddPartUniqueAttribute.getText();

                    Inventory.addPart(new Outsourced(tempPartId, tempPartName, tempPartPrice, tempPartInventory, tempPartMax, tempPartMin, tempCompanyName));

                    Alerts.GenerateAlert("INFORMATION", "Part Added", "Outsourced Part Added Successfully", "", "Show");
                }
                clearAllFields();
            }
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

    private boolean validateInput() {
        StringBuilder inputErrors = new StringBuilder();

        if (InputValidation.checkInputField(AddPartName.getText().trim())) {
            inputErrors.append(Text.addPartNameError).append("\n");
        }
        if (InputValidation.checkInputField(AddPartInventory.getText().trim()) || !InputValidation.isInteger(AddPartInventory.getText().trim()) ) {
                inputErrors.append(Text.addPartInventoryError).append("\n");
        }
        if (!AddPartInventory.getText().isEmpty() && !AddPartMax.getText().isEmpty() && !AddPartMin.getText().isEmpty()) {
            if (Integer.parseInt(AddPartInventory.getText().trim()) > Integer.parseInt(AddPartMax.getText().trim())
                    || Integer.parseInt(AddPartInventory.getText().trim()) < Integer.parseInt(AddPartMin.getText().trim())) {
                inputErrors.append(Text.addPartInventoryAmountError).append("\n");
            }
        }
        if (InputValidation.checkInputField(AddPartPriceCost.getText()) || !InputValidation.isDouble(AddPartPriceCost.getText())) {
                inputErrors.append(Text.addPartPriceCostError).append("\n");
        }

        if (InputValidation.checkInputField(AddPartMax.getText().trim()) || !InputValidation.isInteger(AddPartMax.getText().trim())) {
            inputErrors.append(Text.addPartMaxError).append("\n");
        }
        if (InputValidation.checkInputField(AddPartMin.getText().trim()) || !InputValidation.isInteger(AddPartMin.getText().trim())) {
            inputErrors.append(Text.addPartMinError).append("\n");
        }
        if (!AddPartMin.getText().isEmpty()) {
            if (Integer.parseInt(AddPartMin.getText()) < 0) {
                inputErrors.append(Text.addPartMinAmountError).append("\n");
            }
        }
        if (InHouseRadio.isSelected()) {
            if (InputValidation.checkInputField(AddPartUniqueAttribute.getText().trim()) || !InputValidation.isInteger(AddPartUniqueAttribute.getText().trim())) {
                inputErrors.append(Text.addPartMachineIdError);
            }
        }
        if (OutsourcedRadio.isSelected()) {
            if (InputValidation.checkInputField(AddPartUniqueAttribute.getText().trim())) {
                inputErrors.append(Text.addPartCompanyNameError);
            }
        }
        if (!InHouseRadio.isSelected() && !OutsourcedRadio.isSelected()) {
            inputErrors.delete(0, inputErrors.length());
            inputErrors.append(Text.addPartPartTypeSelectionError);
        }

        if (inputErrors.length() > 0 || !inputErrors.toString().equals("")) {
            Alerts.GenerateAlert("WARNING", "Part Entry Warning", "Required Fields Empty or Invalid", inputErrors.toString(), "ShowAndWait");
            return false;
        }
        return true;
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
