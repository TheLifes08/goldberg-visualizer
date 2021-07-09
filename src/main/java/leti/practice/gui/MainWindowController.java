package leti.practice.gui;

import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import leti.practice.Controller;
import leti.practice.commands.*;

import java.io.File;
import java.util.HashMap;
import java.util.Optional;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainWindowController {
    private static final Logger logger = Logger.getLogger(MainWindowController.class.getName());
    private MainWindow mainWindow;
    private HashMap<CommandType, Command> commands;
    private ViewType viewType = ViewType.RESIDUAL_NETWORK;
    private boolean intermediateMessagesEnabled = true;

    @FXML
    private TextArea console;
    @FXML
    private TextArea parametersTextArea;
    @FXML
    private Canvas canvas;

    @FXML
    private void initialize() {
        commands = new HashMap<CommandType, Command>();
    }

    public void setMainWindow(MainWindow mainWindow) {
        this.mainWindow = mainWindow;
    }

    public void printMessageToConsole(String text) {
        console.appendText(text + "\n");
    }

    public void setParametersText(String text) {
        parametersTextArea.setText(text);
    }

    public void initializeCommands(Controller controller) {
        commands.put(CommandType.ADD_EDGE, new AddEdgeCommand(controller));
        commands.put(CommandType.REMOVE_EDGE, new RemoveEdgeCommand(controller));
        commands.put(CommandType.CLEAR_NETWORK, new ClearNetworkCommand(controller));
        commands.put(CommandType.LOAD_NETWORK, new LoadNetworkCommand(controller));
        commands.put(CommandType.SAVE_NETWORK, new SaveNetworkCommand(controller));
        commands.put(CommandType.PAINT_VIEW, new PaintViewCommand(controller, canvas));
        commands.put(CommandType.GET_NETWORK_PARAMETERS, new GetNetworkParametersCommand(controller));
        commands.put(CommandType.SET_VIEW_ORIGINAL_NETWORK, new SetViewCommand(controller, ViewType.ORIGINAL_NETWORK));
        commands.put(CommandType.SET_VIEW_RESIDUAL_NETWORK, new SetViewCommand(controller, ViewType.RESIDUAL_NETWORK));
        commands.put(CommandType.SET_VIEW_HEIGHT_FUNCTION, new SetViewCommand(controller, ViewType.HEIGHT_FUNCTION));
        commands.put(CommandType.STEP_FORWARD, new StepForwardCommand(controller));
        commands.put(CommandType.STEP_BACKWARD, new StepBackwardCommand(controller));
        updateNetworkViewAndParameters();
    }

    void updateNetworkViewAndParameters() {
        GetNetworkParametersCommand getParametersCommand = (GetNetworkParametersCommand) commands.get(
                CommandType.GET_NETWORK_PARAMETERS);
        commands.get(CommandType.PAINT_VIEW).execute();
        getParametersCommand.execute();
        setParametersText(getParametersCommand.getNetworkParameters());
    }

    @FXML
    private void buttonLoadPressed() {
        File file = mainWindow.showFileDialog(FileDialogType.LOAD);

        if (file != null) {
            logger.log(Level.INFO, "Chosen file: " + file.getAbsolutePath());
            LoadNetworkCommand loadCommand = (LoadNetworkCommand) commands.get(CommandType.LOAD_NETWORK);
            loadCommand.setFile(file);
            if (!loadCommand.execute()) {
                mainWindow.showError("Error loading the network from a file!");
            }
            updateNetworkViewAndParameters();
        }
    }

    @FXML
    private void buttonSavePressed() {
        File file = mainWindow.showFileDialog(FileDialogType.SAVE);

        if (file != null) {
            logger.log(Level.INFO, "Chosen file: " + file.getAbsolutePath());
            SaveNetworkCommand saveCommand = (SaveNetworkCommand) commands.get(CommandType.SAVE_NETWORK);
            saveCommand.setFile(file);
            if (!saveCommand.execute()) {
                mainWindow.showError("Error saving the network to a file!");
            }
        }
    }

    @FXML
    private void buttonExitPressed() {
        logger.log(Level.INFO, "Closing application...");
        mainWindow.endApplication();
    }

    @FXML
    private void radiobuttonOriginalNetworkSelected() {
        if (viewType != ViewType.ORIGINAL_NETWORK) {
            viewType = ViewType.ORIGINAL_NETWORK;
            logger.log(Level.INFO, "Original network view selected.");
            commands.get(CommandType.SET_VIEW_ORIGINAL_NETWORK).execute();
            commands.get(CommandType.PAINT_VIEW).execute();
        }
    }

    @FXML
    private void radiobuttonResidualNetworkSelected() {
        if (viewType != ViewType.RESIDUAL_NETWORK) {
            viewType = ViewType.RESIDUAL_NETWORK;
            logger.log(Level.INFO, "Residual network view selected.");
            commands.get(CommandType.SET_VIEW_RESIDUAL_NETWORK).execute();
            commands.get(CommandType.PAINT_VIEW).execute();
        }
    }

    @FXML
    private void radiobuttonHeightFunctionSelected() {
        if (viewType != ViewType.HEIGHT_FUNCTION){
            viewType = ViewType.HEIGHT_FUNCTION;
            logger.log(Level.INFO, "Height function view selected.");
            commands.get(CommandType.SET_VIEW_HEIGHT_FUNCTION).execute();
            commands.get(CommandType.PAINT_VIEW).execute();
        }
    }

    @FXML
    private void checkboxIntermediateMessagesChecked() {
        intermediateMessagesEnabled = !intermediateMessagesEnabled;

        if (intermediateMessagesEnabled) {
            logger.log(Level.INFO,"Intermediate messages enabled.");
            Logger.getLogger("leti.practice").setLevel(Level.ALL);
        } else {
            logger.log(Level.INFO,"Intermediate messages disabled.");
            Logger.getLogger("leti.practice").setLevel(Level.INFO);
        }
    }

    @FXML
    private void buttonHelpPressed() {
        String help = """
                In the file tab, you can load or save the network.
                In the view tab, you can switch the type of network display.
                The "Intermediate messages" parameter allows you to enable or disable the intermediate data of the algorithm.
                Button '<-': Go back to the previous step of the algorithm.
                Button '->': Perform the next step of the algorithm.
                Button 'Steps to the finish': Run the algorithm until it is completed.
                Button 'Add edge': Add an edge to the network.
                Button 'Remove edge': Remove an edge from the network.
                Button 'Clear network': Clears the network.
                """;
        mainWindow.showDialog(Alert.AlertType.INFORMATION, "Help", null, help);
    }

    @FXML
    private void buttonAboutPressed() {
        String about = """
                This program is designed to visualize the Goldberg algorithm with the possibility of a detailed study of the algorithm.

                Developers: Nikita Shakhin, Rodion Kolovanov, Irina Andrukh""";
        mainWindow.showDialog(Alert.AlertType.INFORMATION, "About", null, about);
    }

    @FXML
    private void buttonStepBackwardPressed() {
        commands.get(CommandType.STEP_BACKWARD).execute();
        updateNetworkViewAndParameters();
    }

    @FXML
    private void buttonStepForwardPressed() {
        commands.get(CommandType.STEP_FORWARD).execute();
        updateNetworkViewAndParameters();
    }

    @FXML
    private void buttonRunAlgorithmPressed() {
        StepForwardCommand stepForwardCommand = (StepForwardCommand) commands.get(CommandType.STEP_FORWARD);
        boolean stepResult;

        do {
            stepResult = stepForwardCommand.execute();
            updateNetworkViewAndParameters();
        } while (stepResult);
    }

    @FXML
    private void buttonReset() {

    }

    @FXML
    private void buttonAddEdgePressed() {
        Optional<String> answer = mainWindow.showTextInputDialog("Input Dialog", null,
                "Enter edge (<source> <destination> <capacity>):");

        if (answer.isPresent()) {
            String input = answer.get();

            updateNetworkViewAndParameters();
        }
    }

    @FXML
    private void buttonRemoveEdgePressed() {
        Optional<String> answer = mainWindow.showTextInputDialog("Input Dialog", null,
                "Enter edge (<source> <destination>):");

        if (answer.isPresent()) {
            String input = answer.get();

            updateNetworkViewAndParameters();
        }
    }

    @FXML
    private void buttonClearNetworkPressed() {
        commands.get(CommandType.CLEAR_NETWORK).execute();
        updateNetworkViewAndParameters();
    }
}
