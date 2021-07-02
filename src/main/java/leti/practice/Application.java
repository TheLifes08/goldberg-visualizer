package leti.practice;

import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;

import java.io.IOException;

public class Application extends javafx.application.Application {
    Stage mainStage = null;

    public static void main(String[] args) {
        launch();
    }

    @Override
    public void start(Stage stage) throws IOException {
        mainStage = stage;

        try {
            FXMLLoader loader = new FXMLLoader();
            loader.setLocation(Application.class.getResource("/fxml/MainWindow.fxml"));
            stage.setScene(new Scene(loader.load()));
            stage.show();
        } catch (IOException e) {
            System.out.println("Exception caught: ");
            e.printStackTrace();
        }
    }
}