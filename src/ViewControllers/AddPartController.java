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

/**
 * Class for adding new parts to inventory
 */
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

    // declaring part type to change based on radio button selection
    private String tempPartType;

    /**
     * Initializes button events and listeners
     */
    @FXML
    private void initialize() {
        // set button events
        setAddPartButtonEvents();
        // set listener for InHouse/Outsourced selection
        setPartTypeListener();
    }

    /**
     * Sets actions for add part buttons
     *
     * Part save button
     *  validates inputs before saving the new part into inventory
     *
     * Part Cancel button
     *  closes window
     */
    private void setAddPartButtonEvents() {
        AddPartSave.setOnAction(actionEvent -> {
            // generate new part id
            int tempPartId = (int) (Math.random() * (9999 - 1 + 1) + 1);

            // checks radio button selection for part type
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
            if (InputValidation.validatePartInputs(
                    AddPartName.getText(),
                    AddPartInventory.getText(),
                    AddPartPriceCost.getText(),
                    AddPartMax.getText(),
                    AddPartMin.getText(),
                    AddPartUniqueAttribute.getText(),
                    tempPartType
            )) {
                // switch statement to create correct part type (InHouse or Outsourced)
                switch (tempPartType) {
                    case "InHouse":
                        Inventory.addPart(new InHouse(
                                tempPartId,
                                AddPartName.getText(),
                                Double.parseDouble(AddPartPriceCost.getText()),
                                Integer.parseInt(AddPartInventory.getText()),
                                Integer.parseInt(AddPartMax.getText()),
                                Integer.parseInt(AddPartMin.getText()),
                                Integer.parseInt(AddPartUniqueAttribute.getText())
                        ));
                        Alerts.GenerateAlert("INFORMATION", "Part Added", Text.inhousePartSaveMessage, "", "Show");
                        closeWindow();
                        break;
                    case "Outsourced":
                        Inventory.addPart(new Outsourced(
                                tempPartId,
                                AddPartName.getText(),
                                Double.parseDouble(AddPartPriceCost.getText()),
                                Integer.parseInt(AddPartInventory.getText()),
                                Integer.parseInt(AddPartMax.getText()),
                                Integer.parseInt(AddPartMin.getText()),
                                AddPartUniqueAttribute.getText()
                        ));
                        Alerts.GenerateAlert("INFORMATION", "Part Added", Text.outsourcedPartSaveMessage, "", "Show");
                        closeWindow();
                        break;
                }
            }
        });
        AddPartCancel.setOnAction(actionEvent -> closeWindow());
    }

    /**
     * Listener to change the label for a parts unique attribute (Machine ID or Company Name)
     */
    @FXML
    private void setPartTypeListener() {
        InHouseRadio.setOnAction(actionEvent -> AddPartUniqueLabel.setText(Text.partMachineIDLabel));
        OutsourcedRadio.setOnAction(actionEvent -> AddPartUniqueLabel.setText(Text.partCompanyNameLabel));
    }

    /**
     * Fires event on main form to refresh parts table with the updated part inventory then closes the window
     */
    private void closeWindow() {
        // need to grab instantiated item on parent window to close
        stage = (Stage) AddPartCancel.getScene().getWindow();
        // firing event for main parts table to update with new parts added
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
    }
}
