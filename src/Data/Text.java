package Data;

// this class if purely for storing and exporting static text that
// may be used in multiple locations throughout the applciation
public class Text {
    // scene titles
    public static String addPartTitle = "Add Part";
    public static String addProductTitle = "Add Product";
    public static String modifyPartTitle = "Modify Part";
    public static String modifyProductTitle = "Modify Product";

    // error messages
    public static String addPartNameError = "Entry for part name is missing or invalid";
    public static String addPartInventoryAmountError = "Entry for inventory cannot be more than Max or less than Min";
    public static String addPartInventoryError = "Entry for part inventory is missing or invalid";
    public static String addPartPriceCostError = "Entry for part price/cost is missing or invalid";
    public static String addPartMaxError = "Entry for part maximum is missing or invalid";
    public static String addPartMinError = "Entry for part minimum is missing or invalid";
    public static String addPartMinAmountError = "Entry for part minimum cannot be less than 0";
    public static String addPartMachineIdError = "Entry for part machine ID is missing or invalid";
    public static String addPartCompanyNameError = "Entry for part company name is missing or invalid";
    public static String addPartPartTypeSelectionError = "No part type selection has been made";

    // inhouse/outsource unique labels
    public static String addPartMachineIDLabel = "Machine ID";
    public static String addPartCompanyNameLabel = "Company Name";
}
