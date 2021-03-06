package ViewControllers;

import Classes.InHouse;
import Classes.Inventory;
import Classes.Outsourced;
import Classes.Part;
import Data.Text;
import Utilities.Alerts;
import Utilities.InputValidation;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

/**
 * Class for modifying existing parts in the inventory
 */
public class ModifyPartController {
    Stage stage;

    @FXML
    private RadioButton InHouseRadio;
    @FXML
    private RadioButton OutsourcedRadio;
    @FXML
    private TextField ModifyPartID;
    @FXML
    private TextField ModifyPartName;
    @FXML
    private TextField ModifyPartInventory;
    @FXML
    private TextField ModifyPartPriceCost;
    @FXML
    private TextField ModifyPartMax;
    @FXML
    private TextField ModifyPartMin;
    @FXML
    private Label ModifyPartUniqueLabel;
    @FXML
    private TextField ModifyPartUniqueAttribute;
    @FXML
    private Button ModifyPartSave;
    @FXML
    private Button ModifyPartCancel;

    // assign selected part and index in inventory to local variables
    private final Part selectedPart = MainFormController.selectedPart;
    private final int selectedPartIndex = MainFormController.selectedPartIndex;
    // declaring part type to change based on radio button selection
    private String tempPartType;

    /**
     * Populates input fields with part information to be modified. Selects needed part type radio button and sets correct unique attribute label.
     * Also initializes button events and listener for part type.
     */
    @FXML
    private void initialize() {
        // populate fields with selected part info
        ModifyPartID.setText(String.valueOf(selectedPart.getId()));
        ModifyPartName.setText(selectedPart.getName());
        ModifyPartInventory.setText(String.valueOf(selectedPart.getStock()));
        ModifyPartPriceCost.setText(String.valueOf(selectedPart.getPrice()));
        ModifyPartMax.setText(String.valueOf(selectedPart.getMax()));
        ModifyPartMin.setText(String.valueOf(selectedPart.getMin()));

        // verifies part type, selects relevant radio button, and sets unique attribute label to needed text
        if (selectedPart instanceof InHouse) {
            InHouseRadio.setSelected(true);
            ModifyPartUniqueLabel.setText(Text.partMachineIDLabel);
            ModifyPartUniqueAttribute.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }
        if (selectedPart instanceof Outsourced) {
            OutsourcedRadio.setSelected(true);
            ModifyPartUniqueLabel.setText(Text.partCompanyNameLabel);
            ModifyPartUniqueAttribute.setText(((Outsourced) selectedPart).getCompanyName());
        }
        // set button events
        setModifyPartButtonEvents();
        // set listener for InHouse/Outsourced selection
        setPartTypeListener();
    }

    /**
     * Sets actions for modify part buttons
     *
     * Part save button
     *  validates inputs before updating the existing part into inventory
     *
     * Part cancel button
     *  closes window
     */
    @FXML
    private void setModifyPartButtonEvents() {
        ModifyPartSave.setOnAction(actionEvent -> {
            // grab part id now since it won't ever change
            int tempPartId = Integer.parseInt(ModifyPartID.getText());

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
            // validate inputs before updating part
            if (InputValidation.validatePartInputs(
                    ModifyPartName.getText(),
                    ModifyPartInventory.getText(),
                    ModifyPartPriceCost.getText(),
                    ModifyPartMax.getText(),
                    ModifyPartMin.getText(),
                    ModifyPartUniqueAttribute.getText(),
                    tempPartType
            )) {
                // switch statement to update with correct part type (InHouse or Outsourced)
                switch (tempPartType) {
                    case "InHouse":
                        InHouse updatedInHousePart = new InHouse(
                                tempPartId,
                                ModifyPartName.getText(),
                                Double.parseDouble(ModifyPartPriceCost.getText()),
                                Integer.parseInt(ModifyPartInventory.getText()),
                                Integer.parseInt(ModifyPartMin.getText()),
                                Integer.parseInt(ModifyPartMax.getText()),
                                Integer.parseInt(ModifyPartUniqueAttribute.getText())
                        );
                        Inventory.updatePart(selectedPartIndex, updatedInHousePart);
                        Alerts.GenerateAlert("INFORMATION", "Part Updated", Text.inhousePartUpdatedMessage, "", "Show");
                        closeWindow();
                        break;
                    case "Outsourced":
                        Outsourced updatedOutsourcePart = new Outsourced(
                                tempPartId,
                                ModifyPartName.getText(),
                                Double.parseDouble(ModifyPartPriceCost.getText()),
                                Integer.parseInt(ModifyPartInventory.getText()),
                                Integer.parseInt(ModifyPartMin.getText()),
                                Integer.parseInt(ModifyPartMax.getText()),
                                ModifyPartUniqueAttribute.getText()
                        );
                        Inventory.updatePart(selectedPartIndex, updatedOutsourcePart);
                        Alerts.GenerateAlert("INFORMATION", "Part Updated", Text.outsourcedPartUpdatedMessage, "", "Show");
                        closeWindow();
                        break;
                }
            }

        });
        ModifyPartCancel.setOnAction(actionEvent -> closeWindow());
    }

    /**
     * Listener to change the label for a parts unique attribute (Machine ID or Company Name)
     */
    @FXML
    private void setPartTypeListener() {
        InHouseRadio.setOnAction(actionEvent -> ModifyPartUniqueLabel.setText(Text.partMachineIDLabel));
        OutsourcedRadio.setOnAction(actionEvent -> ModifyPartUniqueLabel.setText(Text.partCompanyNameLabel));
    }

    /**
     * Fires event on main form to refresh parts table with the updated part inventory then closes the window
     */
    private void closeWindow() {
        // need to grab instantiated item on parent window to close
        stage = (Stage) ModifyPartCancel.getScene().getWindow();
        // firing event for main parts table to update with new parts added
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
    }
}
