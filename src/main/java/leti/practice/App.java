package leti.practice;

import javafx.application.Application;
import javafx.stage.Stage;
import leti.practice.gui.MainWindow;

import java.util.logging.ConsoleHandler;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.logging.SimpleFormatter;


public class App extends Application {
    private static final Logger logger = Logger.getLogger("leti.practice");

    public static void main(String[] args) {
        ConsoleHandler consoleHandler = new ConsoleHandler();
        SimpleFormatter formatter = new SimpleFormatter();

        consoleHandler.setLevel(Level.ALL);
        consoleHandler.setFormatter(formatter);
        logger.setLevel(Level.ALL);
        logger.addHandler(consoleHandler);
        logger.setUseParentHandlers(false);

        launch(args);
    }

    @Override
    public void start(Stage stage) {
        MainWindow mainWindow = new MainWindow(stage);
    }
}
