package leti.practice;

import javafx.scene.canvas.Canvas;
import leti.practice.gui.MainWindow;
import leti.practice.gui.ViewType;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    private static final Logger logger = Logger.getLogger(MainWindow.class.getName());

    public void loadNetwork(File file) {
        logger.log(Level.INFO, "Command work!");
    }

    public void saveNetwork(File file) {
        logger.log(Level.INFO, "Command work!");
    }

    public void setView(ViewType viewType) {
        logger.log(Level.INFO, "Command work!");
    }

    public boolean stepForward() {
        logger.log(Level.INFO, "Command work!");
        logger.log(Level.FINEST, "Finest data!");
        return false;
    }

    public void stepBackward() {
        logger.log(Level.INFO, "Command work!");
        logger.log(Level.FINEST, "Finest data!");
    }

    public void addEdge(String source, String destination, double capacity) {
        logger.log(Level.INFO, "Command work!");
    }

    public void removeEdge(String source, String destination) {
        logger.log(Level.INFO, "Command work!");
    }

    public void clearNetwork() {
        logger.log(Level.INFO, "Command work!");
    }

    public void paintView(Canvas canvas) {
        logger.log(Level.INFO, "Command work!");
    }
}
