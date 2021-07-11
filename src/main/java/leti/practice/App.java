package leti.practice;

import javafx.application.Application;
import javafx.stage.Stage;
import leti.practice.gui.MainWindow;
import leti.practice.logging.MessageFormatter;
import leti.practice.logging.MessageHandler;

import java.util.logging.Level;
import java.util.logging.Logger;


public class App extends Application {
    private static final Logger logger = Logger.getLogger("leti.practice");
    private static MessageHandler messageHandler;
    private MainWindow mainWindow;
    private Controller controller;

    public static void main(String[] args) {
        messageHandler = new MessageHandler();
        MessageFormatter formatter = new MessageFormatter();

        messageHandler.setLevel(Level.ALL);
        messageHandler.setFormatter(formatter);
        logger.setLevel(Level.ALL);
        logger.addHandler(messageHandler);
        logger.setUseParentHandlers(false);

        launch(args);
    }

    @Override
    public void start(Stage stage) {
        controller = new Controller();
        mainWindow = new MainWindow(stage, controller);
        messageHandler.setMainWindowController(mainWindow.getController());
    }
}
