package Validations;

public class InputValidation {

    public static boolean checkInputField(String userInput) {
        return userInput != null && !userInput.trim().isEmpty();
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
}
