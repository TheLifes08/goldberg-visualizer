package leti.practice.gui;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import leti.practice.App;
import leti.practice.Controller;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainWindow {
    private static final Logger logger = Logger.getLogger(MainWindow.class.getName());
    private Stage primaryStage;
    private MainWindowController mainWindowController;

    public MainWindow(Stage primaryStage, Controller controller) {
        this.primaryStage = primaryStage;

        primaryStage.setTitle("Goldberg Visualizer");
        primaryStage.setWidth(1000);
        primaryStage.setHeight(800);

        // Setting icon
        try (InputStream inputStream = App.class.getResourceAsStream("/icons/app.png")) {
            if (inputStream != null) {
                Image image = new Image(inputStream);
                primaryStage.getIcons().add(image);
            } else {
                logger.log(Level.SEVERE, "Cannot open file '/icons/app.png'.");
            }
        } catch (IOException e) {
            logger.log(Level.SEVERE, "Cannot open file '/icons/app.png'. Exception caught.", e);
        }

        AnchorPane parent = null;

        // Setting MainWindowController
        try {
            FXMLLoader loader = new FXMLLoader();
            URL url = App.class.getResource("/fxml/MainScene.fxml");
            loader.setLocation(url);
            parent = loader.load();
            mainWindowController = loader.getController();
            mainWindowController.setMainWindow(this);
            mainWindowController.initializeCommands(controller);
        } catch (Exception e) {
            String errorMessage = "Cannot open file '/fxml/MainScene.fxml'";
            logger.log(Level.SEVERE, "Exception caught", e);
            showError(errorMessage);
            System.exit(0);
        }

        if (parent == null) {
            showError("Cannot load MainWindow scene: scene == null");
            System.exit(0);
        }

        Scene scene = new Scene(parent);

        primaryStage.setScene(scene);
        primaryStage.show();
    }

    public MainWindowController getController() {
        return mainWindowController;
    }

    public File showFileDialog(FileDialogType fileDialogType) {
        File file;
        FileChooser dialog = new FileChooser();

        dialog.getExtensionFilters().add(new FileChooser.ExtensionFilter("Text Files", "*.txt"));

        try {
            if (fileDialogType == FileDialogType.LOAD) {
                dialog.setTitle("Load Network");
                file = dialog.showOpenDialog(primaryStage);
            } else {
                dialog.setTitle("Save Network");
                file = dialog.showSaveDialog(primaryStage);
            }
        } catch (Exception e) {
            file = null;
        }

        return file;
    }

    public Optional<String> showTextInputDialog(String title, String header, String content) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.initOwner(primaryStage);
        dialog.setTitle(title);
        dialog.setHeaderText(header);
        dialog.setContentText(content);
        return dialog.showAndWait();
    }

    public void showDialog(Alert.AlertType alertType, String title, String header, String content) {
        Alert alert = new Alert(alertType);
        alert.initOwner(primaryStage);
        alert.setTitle(title);
        alert.setHeaderText(header);
        alert.setContentText(content);
        alert.showAndWait();
    }

    public void showError(String content) {
        logger.log(Level.SEVERE, "Error has occurred. {0}", content);
        showDialog(Alert.AlertType.ERROR, "Error Dialog", "An error has occurred!", content);
    }

    public void showErrorAndExit(String content) {
        showError(content);
        endApplication();
    }

    public void endApplication() {
        Platform.exit();
    }
}
