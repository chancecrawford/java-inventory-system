package Utilities;

import javafx.scene.control.Alert;

public class Alerts {
    public static void GenerateAlert(String type, String title, String header, String content, String showStatus) {
        Alert alert = new Alert(Alert.AlertType.valueOf(type));
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        switch (showStatus) {
            case "Show":
                alert.show();
                break;
            case "ShowAndWait":
                alert.showAndWait();
                break;
        }
    }
}
