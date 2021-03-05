package Data;

/**
* This class if purely for exporting static text that may be used in multiple locations throughout the application
*/
public class Text {
    // scene titles
    public static String addPartTitle = "Add Part";
    public static String addProductTitle = "Add Product";
    public static String modifyPartTitle = "Modify Part";
    public static String modifyProductTitle = "Modify Product";

    // part error messages
    public static String partSearchError = "Unable to locate part with that ID or name";
    public static String partModifySelectionError = "You Must Select A Part To Modify";

    public static String partNameError = "Entry for part name is missing or invalid";
    public static String partInventoryAmountError = "Entry for part inventory cannot be more than Max or less than Min";
    public static String partInventoryError = "Entry for part inventory is missing or invalid";
    public static String partPriceCostError = "Entry for part price/cost is missing or invalid";
    public static String partMaxError = "Entry for part maximum is missing or invalid";
    public static String partMinError = "Entry for part minimum is missing or invalid";
    public static String partMinAmountError = "Entry for part minimum cannot be less than 0";
    public static String partMachineIdError = "Entry for part machine ID is missing or invalid";
    public static String partCompanyNameError = "Entry for part company name is missing or invalid";
    public static String partTypeSelectionError = "No part type selection has been made";
    // product error messages
    public static String productSearchError = "Unable to locate product with that ID or name";
    public static String productModifySelectionError = "You Must Select A Part To Modify";
    public static String productDeleteError = "You Cannot Delete A Product With Associated Parts";

    public static String productNameError = "Entry for product name is missing or invalid";
    public static String productInventoryAmountError = "Entry for product inventory cannot be more than Max or less than Min";
    public static String productInventoryError = "Entry for product inventory is missing or invalid";
    public static String productPriceCostError = "Entry for product price/cost is missing or invalid";
    public static String productMaxError = "Entry for product maximum is missing or invalid";
    public static String productMinError = "Entry for product minimum is missing or invalid";
    public static String productMinAmountError = "Entry for product minimum cannot be less than 0";
    public static String productAssociatedPartsError = "You must add associated parts to the product";

    // inhouse/outsource unique labels
    public static String partMachineIDLabel = "Machine ID";
    public static String partCompanyNameLabel = "Company Name";
}
