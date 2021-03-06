package Utilities;

import Classes.Part;
import Data.Text;
import javafx.collections.ObservableList;

/**
 * This class handles various validations that may be used in multiple locations throughout the application
 */
public class InputValidation {

    /**
     * Verifies if input field is null or empty
     * @param userInput input from user
     * @return boolean
     */
    public static boolean checkInputField(String userInput) {
        return userInput != null && !userInput.trim().isEmpty();
    }

    /**
     * Checks if input is an integer
     * @param userInput input from user
     * @return boolean
     */
    public static boolean isInteger(String userInput) {
        try {
            Integer.parseInt(userInput);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * Checks if input is a double
     * @param userInput input from user
     * @return boolean
     */
    public static boolean isDouble(String userInput) {
        try {
            Double.parseDouble(userInput);
            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    /**
     * This validation method is used to verify user inputs to add or modify a part
     * @param name part name
     * @param inventory part inventory
     * @param priceCost part price/cost
     * @param max part maximum inventory
     * @param min part minimum inventory
     * @param uniqueAttribute part machine id (InHouse) or company name (Outsourced)
     * @param partType designates whether part is made InHouse or is Outsourced
     * @return boolean
     */
    public static boolean validatePartInputs(String name, String inventory, String priceCost, String max, String min, String uniqueAttribute, String partType) {
        // use string builder to add all validation errors to push into one alert
        StringBuilder inputErrors = new StringBuilder();

        // validates name
        if (!checkInputField(name)) {
            inputErrors.append(Text.partNameError).append("\n");
        }
        // validates inventory level
        if (!checkInputField(String.valueOf(inventory)) || !isInteger(inventory)) {
            inputErrors.append(Text.partInventoryError).append("\n");
        }
        // verifies inventory level is within min/max range
        if (!inventory.isEmpty() && !max.isEmpty() && !min.isEmpty() && isInteger(inventory) && isInteger(max) && isInteger(min)) {
            if (Integer.parseInt(inventory) > Integer.parseInt(max) || Integer.parseInt(inventory) < Integer.parseInt(min)) {
                inputErrors.append(Text.partInventoryAmountError).append("\n");
            }
        }
        // validates part price/cost
        if (!checkInputField(priceCost) || !isDouble(priceCost)) {
            inputErrors.append(Text.partPriceCostError).append("\n");
        }
        // validates part maximum inventory level
        if (!checkInputField(max) || !isInteger(max)) {
            inputErrors.append(Text.partMaxError).append("\n");
        }
        // validates part minimum inventory level
        if (!checkInputField(min) || !isInteger(min)) {
            inputErrors.append(Text.partMinError).append("\n");
        }
        // ensures part minimum isn't less than zero
        if (!min.isEmpty() && isInteger(min)) {
            if (Integer.parseInt(min) < 0) {
                inputErrors.append(Text.partMinAmountError).append("\n");
            }
        }
        // switch statement to check part type and verify type is correct attribute type
        switch (partType) {
            case "InHouse":
                if (!checkInputField(uniqueAttribute) || !isInteger(uniqueAttribute)) {
                    inputErrors.append(Text.partMachineIdError);
                }
                break;
            case "Outsourced":
                if (!checkInputField(uniqueAttribute)) {
                    inputErrors.append(Text.partCompanyNameError);
                }
                break;
            case "":
                inputErrors.delete(0, inputErrors.length());
                inputErrors.append(Text.partTypeSelectionError);
                break;
        }
        // generates alert and populates it with error messages if inputErrors string builder isn't empty
        if (inputErrors.length() > 0 || !inputErrors.toString().equals("")) {
            Alerts.GenerateAlert("WARNING", "Part Entry Warning", "Required Fields Empty or Invalid", inputErrors.toString(), "ShowAndWait");
            return false;
        }
        return true;
    }

    /**
     * This validation method is used to verify user inputs to add or modify a product
     * @param name product name
     * @param inventory product inventory
     * @param priceCost product price/cost
     * @param max product maximum inventory level
     * @param min product minimum inventory level
     * @param productParts the products associated parts
     * @param currentStage current stage to determine if associated parts can be empty or not
     * @return boolean
     */
    public static boolean validateProductInputs(String name, String inventory, String priceCost, String max, String min, ObservableList<Part> productParts, javafx.stage.Stage currentStage) {
        // use string builder to add all validation errors to push into one alert
        StringBuilder inputErrors = new StringBuilder();

        // validates name
        if (!checkInputField(name)) {
            inputErrors.append(Text.productNameError).append("\n");
        }
        // validates inventory level
        if (!checkInputField(String.valueOf(inventory)) || !isInteger(inventory)) {
            inputErrors.append(Text.productInventoryError).append("\n");
        }
        // verifies inventory level is within min/max range
        if (!inventory.isEmpty() && !max.isEmpty() && !min.isEmpty() && isInteger(inventory) && isInteger(max) && isInteger(min)) {
            if (Integer.parseInt(inventory) > Integer.parseInt(max) || Integer.parseInt(inventory) < Integer.parseInt(min)) {
                inputErrors.append(Text.productInventoryAmountError).append("\n");
            }
        }
        // validates price/cost
        if (!checkInputField(priceCost) || !isDouble(priceCost)) {
            inputErrors.append(Text.productPriceCostError).append("\n");
        }
        // validates inventory maximum
        if (!checkInputField(max) || !isInteger(max)) {
            inputErrors.append(Text.productMaxError).append("\n");
        }
        // validates inventory minimum
        if (!checkInputField(min) || !isInteger(min)) {
            inputErrors.append(Text.productMinError).append("\n");
        }
        // ensures minimum is not less than zero
        if (!min.isEmpty() && isInteger(min)) {
            if (Integer.parseInt(min) < 0) {
                inputErrors.append(Text.productMinAmountError).append("\n");
            }
        }
        // ensures associated parts isn't empty if adding a new product
        if (productParts.isEmpty() && currentStage.getTitle().equals(Text.addProductTitle)) {
            inputErrors.append(Text.productAssociatedPartsError).append("\n");
        }
        // generates alert and populates it with error messages if inputErrors string builder isn't empty
        if (inputErrors.length() > 0 || !inputErrors.toString().equals("")) {
            Alerts.GenerateAlert("WARNING", "Product Entry Warning", "Required Fields Empty or Invalid", inputErrors.toString(), "ShowAndWait");
            return false;
        }
        return true;
    }
}
