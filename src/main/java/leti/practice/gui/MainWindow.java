package leti.practice.gui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.image.Image;
import javafx.stage.Stage;
import leti.practice.App;

import java.io.InputStream;
import java.net.URL;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainWindow {
    private static final Logger logger = Logger.getLogger(MainWindow.class.getName());
    private Stage primaryStage;

    public void showDialog(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void showError(String content) {
        logger.log(Level.SEVERE, "Error test has occurred: {0}", content);
        showDialog(Alert.AlertType.ERROR, "Error Dialog", "An error has occurred!", content);
    }

    public void showErrorAndExit(String content) {
        showError(content);
        Platform.exit();
    }

    public MainWindow(Stage primaryStage) {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Goldberg Visualizer");
        primaryStage.setWidth(800);
        primaryStage.setHeight(600);
        primaryStage.setResizable(false);

        InputStream inputStream = App.class.getResourceAsStream("/icon/icon.png");

        if (inputStream != null) {
            Image image = new Image(inputStream);
            primaryStage.getIcons().add(image);
        }

        Parent parent = null;

        try {
            FXMLLoader loader = new FXMLLoader();
            URL url = App.class.getResource("/fxml/MainScene.fxml");
            loader.setLocation(url);
            parent = loader.load();
        } catch (Exception e) {
            String errorMessage = "Cannot open file '/fxml/MainScene.fxml'";
            logger.log(Level.SEVERE, "Exception caught", e);
            showErrorAndExit(errorMessage);
        }

        if (parent == null) {
            showErrorAndExit("Cannot load main scene: scene == null");
        }

        assert parent != null;
        Scene scene = new Scene(parent);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

}
