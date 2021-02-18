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
                        Alerts.GenerateAlert("INFORMATION", "Part Added", "InHouse Part Added Successfully", "", "Show");
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
                        Alerts.GenerateAlert("INFORMATION", "Part Added", "Outsourced Part Added Successfully", "", "Show");
                        closeWindow();
                        break;
                }
            }
        });
        AddPartCancel.setOnAction(actionEvent -> closeWindow());
    }

    @FXML
    private void setPartTypeListener() {
        InHouseRadio.setOnAction(actionEvent -> AddPartUniqueLabel.setText(Text.addPartMachineIDLabel));
        OutsourcedRadio.setOnAction(actionEvent -> AddPartUniqueLabel.setText(Text.addPartCompanyNameLabel));
    }

    private void closeWindow() {
        // need to grab instantiated item on parent window to close
        stage = (Stage) AddPartCancel.getScene().getWindow();
        // firing event for main parts table to update with new parts added
        stage.fireEvent(new WindowEvent(stage, WindowEvent.WINDOW_CLOSE_REQUEST));
        stage.close();
    }
}
