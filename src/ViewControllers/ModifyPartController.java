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

    @FXML
    private void initialize() {
        // populate fields with selected part info
        ModifyPartID.setText(String.valueOf(selectedPart.getId()));
        ModifyPartName.setText(selectedPart.getName());
        ModifyPartInventory.setText(String.valueOf(selectedPart.getStock()));
        ModifyPartPriceCost.setText(String.valueOf(selectedPart.getPrice()));
        ModifyPartMax.setText(String.valueOf(selectedPart.getMax()));
        ModifyPartMin.setText(String.valueOf(selectedPart.getMin()));

        if (selectedPart instanceof InHouse) {
            InHouseRadio.setSelected(true);
            ModifyPartUniqueLabel.setText(Text.addPartMachineIDLabel);
            ModifyPartUniqueAttribute.setText(String.valueOf(((InHouse) selectedPart).getMachineId()));
        }
        if (selectedPart instanceof Outsourced) {
            OutsourcedRadio.setSelected(true);
            ModifyPartUniqueLabel.setText(Text.addPartCompanyNameLabel);
            ModifyPartUniqueAttribute.setText(((Outsourced) selectedPart).getCompanyName());
        }
        // set button events
        setModifyPartButtonEvents();
        // set listener for InHouse/Outsourced selection
        setPartTypeListener();
    }

    @FXML
    private void setModifyPartButtonEvents() {
        ModifyPartSave.setOnAction(actionEvent -> {
            // set part input values
            int tempPartId = Integer.parseInt(ModifyPartID.getText());

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
                    ModifyPartName.getText(),
                    ModifyPartInventory.getText(),
                    ModifyPartPriceCost.getText(),
                    ModifyPartMax.getText(),
                    ModifyPartMin.getText(),
                    ModifyPartUniqueAttribute.getText(),
                    tempPartType
            )) {
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
                        Alerts.GenerateAlert("INFORMATION", "Part Updated", "InHouse Part Updated Successfully", "", "Show");
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
                        Alerts.GenerateAlert("INFORMATION", "Part Updated", "Outsourced Part Updated Successfully", "", "Show");
                        closeWindow();
                        break;
                }
            }

        });
        ModifyPartCancel.setOnAction(actionEvent -> closeWindow());
    }

    @FXML
    private void setPartTypeListener() {
        InHouseRadio.setOnAction(actionEvent -> ModifyPartUniqueLabel.setText(Text.addPartMachineIDLabel));
        OutsourcedRadio.setOnAction(actionEvent -> ModifyPartUniqueLabel.setText(Text.addPartCompanyNameLabel));
    }

    private void closeWindow() {
        // need to grab instantiated item on parent window to close
        stage = (Stage) ModifyPartCancel.getScene().getWindow();
        // firing event for main parts table to update with new parts added
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
    }
}
