package leti.practice.gui;

import javafx.fxml.FXML;
import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextArea;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.StrokeLineCap;
import javafx.scene.shape.StrokeLineJoin;
import javafx.scene.text.TextAlignment;
import leti.practice.view.LineType;
import leti.practice.view.ResidualNetworkViewPainter;

import java.util.concurrent.atomic.AtomicReference;


public class MainWindowController {
    private MainWindow mainWindow;
    private ViewType viewType = ViewType.ORIGINAL_GRAPH;
    private boolean intermediateMessagesEnabled = true;

    @FXML
    private TextArea console;

    @FXML
    private Canvas canvas;

    @FXML
    private ScrollPane canvasScrollPane;

    // TEST
    private ResidualNetworkViewPainter painter;

    @FXML
    private void initialize() {
        // TEST
        painter = new ResidualNetworkViewPainter();
        painter.setCanvas(canvas);

        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);

        painter.paintEdge(250, 100, 100, 250, "0", LineType.STRAIGHT);
        painter.paintEdge(100, 250, 250, 100, "0", LineType.ARC);
        painter.paintEdge(250, 100, 100, 250, "0", LineType.ARC);
        painter.paintEdge(100, 250, 250, 100, "0", LineType.STRAIGHT);
        painter.paintEdge(100, 250, 250, 400, "0", LineType.STRAIGHT);
        painter.paintEdge(250, 400, 400, 250, "0", LineType.STRAIGHT);
        painter.paintEdge(400, 250, 250, 100, "0", LineType.STRAIGHT);

        painter.paintNode(250, 100, "A");
        painter.paintNode(100, 250, "B");
        painter.paintNode(400, 250, "C");
        painter.paintNode(250, 400, "D");
    }

    @FXML
    private void buttonOpenPressed() {
        printMessageToConsole("Open graph button pressed!");
    }

    @FXML
    private void buttonSavePressed() {
        printMessageToConsole("Save graph button pressed!");
    }

    @FXML
    private void buttonExitPressed() {
        printMessageToConsole("Closing application...");
        mainWindow.endApplication();
    }

    @FXML
    private void radiobuttonOriginalGraphSelected() {
        if (viewType != ViewType.ORIGINAL_GRAPH) {
            viewType = ViewType.ORIGINAL_GRAPH;
            printMessageToConsole("Original graph view select!");
        }
    }

    @FXML
    private void radiobuttonResidualNetworkSelected() {
        if (viewType != ViewType.RESIDUAL_NETWORK) {
            viewType = ViewType.RESIDUAL_NETWORK;
            printMessageToConsole("Residual network view select!");
        }
    }

    @FXML
    private void radiobuttonHeightFunctionSelected() {
        if (viewType != ViewType.HEIGHT_FUNCTION){
            viewType = ViewType.HEIGHT_FUNCTION;
            printMessageToConsole("Height function view select!");
        }
    }

    @FXML
    private void checkboxIntermediateMessagesChecked() {
        intermediateMessagesEnabled = !intermediateMessagesEnabled;

        if (intermediateMessagesEnabled) {
            printMessageToConsole("Intermediate messages enabled!");
        } else {
            printMessageToConsole("Intermediate messages disabled!");
        }
    }

    @FXML
    private void buttonHelpPressed() {
        printMessageToConsole("Help button pressed!");
        mainWindow.showDialog(Alert.AlertType.INFORMATION, "Help", "Help", "Help description");
    }

    @FXML
    private void buttonAboutPressed() {
        printMessageToConsole("About button pressed!");
        String about = """
                The program provides the user with functionality for using the Golberg algorithm for various networks with the possibility of a detailed study of the algorithm's operation.

                Developers: Nikita Shakhin, Rodion Kolovanov, Irina Andrukh""";
        mainWindow.showDialog(Alert.AlertType.INFORMATION, "About", "About", about);
    }

    @FXML
    private void buttonStepBackwardPressed() {
        printMessageToConsole("Step backward button pressed!");
    }

    @FXML
    private void buttonStepForwardPressed() {
        printMessageToConsole("Step forward button pressed!");
    }

    @FXML
    private void buttonRunAlgorithmPressed() {
        printMessageToConsole("Run algorithm button pressed!");
    }

    @FXML
    private void buttonAddEdgePressed() {
        printMessageToConsole("Add edge button pressed!");
    }

    @FXML
    private void buttonRemoveEdgePressed() {
        printMessageToConsole("Remove edge button pressed!");
    }

    @FXML
    private void buttonClearGraphPressed() {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
        printMessageToConsole("Clear graph button pressed!");
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void printMessageToConsole(String text) {
        console.appendText(text + "\n");
    }
}
