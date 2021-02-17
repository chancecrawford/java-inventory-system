package Utilities;

import Data.Text;

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

    public static boolean validatePartInputs(String name, int inventory, double priceCost, int max, int min, String uniqueAttribute, String partType) {
        StringBuilder inputErrors = new StringBuilder();

        if (checkInputField(name)) {
            inputErrors.append(Text.addPartNameError).append("\n");
        }
        if (checkInputField(String.valueOf(inventory))) {
            inputErrors.append(Text.addPartInventoryError).append("\n");
        }
        if (!String.valueOf(inventory).isEmpty() && !String.valueOf(max).isEmpty() && !String.valueOf(min).isEmpty()) {
            if (inventory > max || inventory < min) {
                inputErrors.append(Text.addPartInventoryAmountError).append("\n");
            }
        }
        if (checkInputField(String.valueOf(priceCost))) {
            inputErrors.append(Text.addPartPriceCostError).append("\n");
        }

        if (checkInputField(String.valueOf(max))) {
            inputErrors.append(Text.addPartMaxError).append("\n");
        }
        if (checkInputField(String.valueOf(min))) {
            inputErrors.append(Text.addPartMinError).append("\n");
        }
        if (!String.valueOf(min).isEmpty()) {
            if (min < 0) {
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
}
