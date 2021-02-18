package Utilities;

import Classes.Part;
import Data.Text;
import javafx.collections.ObservableList;

import java.util.ArrayList;

public class InputValidation {

    public static boolean checkInputField(String userInput) {
        return userInput == null || userInput.trim().isEmpty();
    }

    public static boolean isInteger(String userInput) {
        try {
            Integer.parseInt(userInput);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean isDouble(String userInput) {
        try {
            Double.parseDouble(userInput);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean validatePartInputs(String name, String inventory, String priceCost, String max, String min, String uniqueAttribute, String partType) {
        StringBuilder inputErrors = new StringBuilder();

        if (checkInputField(name)) {
            inputErrors.append(Text.addPartNameError).append("\n");
        }
        if (checkInputField(String.valueOf(inventory)) || !isInteger(inventory)) {
            inputErrors.append(Text.addPartInventoryError).append("\n");
        }
        if (!inventory.isEmpty() && !max.isEmpty() && !min.isEmpty() && isInteger(inventory) && isInteger(max) && isInteger(min)) {
            if (Integer.parseInt(inventory) > Integer.parseInt(max) || Integer.parseInt(inventory) < Integer.parseInt(min)) {
                inputErrors.append(Text.addPartInventoryAmountError).append("\n");
            }
        }
        if (checkInputField(priceCost) || !isDouble(priceCost)) {
            inputErrors.append(Text.addPartPriceCostError).append("\n");
        }

        if (checkInputField(max) || !isInteger(max)) {
            inputErrors.append(Text.addPartMaxError).append("\n");
        }
        if (checkInputField(min) || !isInteger(min)) {
            inputErrors.append(Text.addPartMinError).append("\n");
        }
        if (!min.isEmpty() && isInteger(min)) {
            if (Integer.parseInt(min) < 0) {
                inputErrors.append(Text.addPartMinAmountError).append("\n");
            }
        }

        switch (partType) {
            case "InHouse":
                if (checkInputField(uniqueAttribute) || !isInteger(uniqueAttribute)) {
                    inputErrors.append(Text.addPartMachineIdError);
                }
                break;
            case "Outsourced":
                if (checkInputField(uniqueAttribute)) {
                    inputErrors.append(Text.addPartCompanyNameError);
                }
                break;
            case "":
                inputErrors.delete(0, inputErrors.length());
                inputErrors.append(Text.addPartPartTypeSelectionError);
                break;
        }

        if (inputErrors.length() > 0 || !inputErrors.toString().equals("")) {
            Alerts.GenerateAlert("WARNING", "Part Entry Warning", "Required Fields Empty or Invalid", inputErrors.toString(), "ShowAndWait");
            return false;
        }
        return true;
    }

    public static boolean validateProductInputs(String name, String inventory, String priceCost, String max, String min, ObservableList<Part> productParts) {
        StringBuilder inputErrors = new StringBuilder();

        if (checkInputField(name)) {
            inputErrors.append(Text.addProductNameError).append("\n");
        }
        if (checkInputField(String.valueOf(inventory)) || !isInteger(inventory)) {
            inputErrors.append(Text.addProductInventoryError).append("\n");
        }
        if (!inventory.isEmpty() && !max.isEmpty() && !min.isEmpty()) {
            if (Integer.parseInt(inventory) > Integer.parseInt(max) || Integer.parseInt(inventory) < Integer.parseInt(min)) {
                inputErrors.append(Text.addProductInventoryAmountError).append("\n");
            }
        }
        if (checkInputField(priceCost) || !isDouble(priceCost)) {
            inputErrors.append(Text.addProductPriceCostError).append("\n");
        }

        if (checkInputField(max) || !isInteger(max)) {
            inputErrors.append(Text.addProductMaxError).append("\n");
        }
        if (checkInputField(min) || !isInteger(min)) {
            inputErrors.append(Text.addProductMinError).append("\n");
        }
        if (!min.isEmpty()) {
            if (Integer.parseInt(min) < 0) {
                inputErrors.append(Text.addProductMinAmountError).append("\n");
            }
        }
        if (productParts.isEmpty()) {
            inputErrors.append(Text.addProductAssociatedPartsError).append("\n");
        }

        if (inputErrors.length() > 0 || !inputErrors.toString().equals("")) {
            Alerts.GenerateAlert("WARNING", "Product Entry Warning", "Required Fields Empty or Invalid", inputErrors.toString(), "ShowAndWait");
            return false;
        }
        return true;
    }
}
