package Data;

// this class if purely for storing and exporting static text that
// may be used in multiple locations throughout the applciation
public class Text {
    // scene titles
    public static String addPartTitle = "Add Part";
    public static String addProductTitle = "Add Product";
    public static String modifyPartTitle = "Modify Part";
    public static String modifyProductTitle = "Modify Product";

    // part error messages
    public static String addPartNameError = "Entry for part name is missing or invalid";
    public static String addPartInventoryAmountError = "Entry for part inventory cannot be more than Max or less than Min";
    public static String addPartInventoryError = "Entry for part inventory is missing or invalid";
    public static String addPartPriceCostError = "Entry for part price/cost is missing or invalid";
    public static String addPartMaxError = "Entry for part maximum is missing or invalid";
    public static String addPartMinError = "Entry for part minimum is missing or invalid";
    public static String addPartMinAmountError = "Entry for part minimum cannot be less than 0";
    public static String addPartMachineIdError = "Entry for part machine ID is missing or invalid";
    public static String addPartCompanyNameError = "Entry for part company name is missing or invalid";
    public static String addPartPartTypeSelectionError = "No part type selection has been made";
    // product error messages
    public static String addProductNameError = "Entry for product name is missing or invalid";
    public static String addProductInventoryAmountError = "Entry for product inventory cannot be more than Max or less than Min";
    public static String addProductInventoryError = "Entry for product inventory is missing or invalid";
    public static String addProductPriceCostError = "Entry for product price/cost is missing or invalid";
    public static String addProductMaxError = "Entry for product maximum is missing or invalid";
    public static String addProductMinError = "Entry for product minimum is missing or invalid";
    public static String addProductMinAmountError = "Entry for product minimum cannot be less than 0";
    public static String addProductAssociatedPartsError = "You must add associated parts to the product";

    // inhouse/outsource unique labels
    public static String addPartMachineIDLabel = "Machine ID";
    public static String addPartCompanyNameLabel = "Company Name";
}
