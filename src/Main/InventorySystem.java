package Main;

import Data.Paths;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;

/**
 * Creates initial scene/stage and loads main form
 */
public class InventorySystem extends Application {

    /**
     * launches application
     * @param args starting arguments for applicaiton
     */
    public static void main(String[] args) {
        launch(args);
    }

    /**
     * Builds initial scene/stage for application
     * @param primaryStage main form
     * @throws IOException IOExceptions needed for several validations or attempts to access data
     */
    @Override
    public void start(Stage primaryStage) throws IOException {

        Parent root = FXMLLoader.load(getClass().getResource(Paths.mainFormScenePath));
        Scene scene = new Scene(root);
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
