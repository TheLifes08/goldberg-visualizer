package leti.practice.gui;

import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.Alert;
import javafx.scene.control.TextArea;
import javafx.util.Duration;
import leti.practice.Controller;
import leti.practice.commands.*;

import java.io.File;
import java.util.HashMap;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.logging.Level;
import java.util.logging.Logger;

public class MainWindowController {
    private static final Logger logger = Logger.getLogger(MainWindowController.class.getName());
    private MainWindow mainWindow;
    private HashMap<CommandType, Command> commands;
    private ViewType viewType = ViewType.RESIDUAL_NETWORK;
    private boolean intermediateMessagesEnabled = true;
    private AtomicBoolean isAnimationExecuting;

    @FXML
    private TextArea console;
    @FXML
    private TextArea parametersTextArea;
    @FXML
    private Canvas canvas;

    @FXML
    private void initialize() {
        commands = new HashMap<CommandType, Command>();
        isAnimationExecuting = new AtomicBoolean(false);
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
        commands.put(CommandType.RESET, new ResetCommand(controller));
        commands.put(CommandType.SET_SOURCE_AND_DESTINATION, new SetSourceAndDestinationCommand(controller));
        commands.put(CommandType.CHECK_SOURCE_AND_DESTINATION, new CheckSourceAndDestinationCommand(controller));
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
        if (isAnimationExecuting.get()) {
            return;
        }

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
                Button 'Reset': Reset the algorithm to its original state.
                Button 'Set the source and destination': Set the source and destination for the network.
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
    private void setSourceAndDestinationButtonPressed() {
        if (isAnimationExecuting.get()) {
            return;
        }

        Optional<String> answer = mainWindow.showTextInputDialog("Input Dialog", null,
                "Enter source and destination (<source> <destination>):");

        if (answer.isPresent()) {
            SetSourceAndDestinationCommand setSourceAndDestinationCommand =
                    (SetSourceAndDestinationCommand) commands.get(CommandType.SET_SOURCE_AND_DESTINATION);
            String input = answer.get();

            boolean success = true;
            int firstSpaceIndex = input.indexOf(' ');

            try {
                if (firstSpaceIndex != -1) {
                    String source = input.substring(0, firstSpaceIndex);
                    String destination = input.substring(firstSpaceIndex + 1);
                    setSourceAndDestinationCommand.setSource(source);
                    setSourceAndDestinationCommand.setDestination(destination);
                    success = setSourceAndDestinationCommand.execute();
                } else {
                    success = false;
                }
            } catch (Exception e) {
                success = false;
            }

            if (!success) {
                mainWindow.showError("Error setting source and destination.");
                return;
            }

            updateNetworkViewAndParameters();
        }
    }

    @FXML
    private void buttonStepBackwardPressed() {
        if (isAnimationExecuting.get()) {
            return;
        }

        commands.get(CommandType.STEP_BACKWARD).execute();
        updateNetworkViewAndParameters();
    }

    @FXML
    private void buttonStepForwardPressed() {
        if (isAnimationExecuting.get()) {
            return;
        }

        boolean isSourceAndDestinationSet = commands.get(CommandType.CHECK_SOURCE_AND_DESTINATION).execute();

        if (!isSourceAndDestinationSet) {
            mainWindow.showError("The source or destination is set incorrectly.");
            return;
        }

        StepForwardCommand stepForwardCommand = (StepForwardCommand) commands.get(CommandType.STEP_FORWARD);

        stepForwardCommand.execute();
        updateNetworkViewAndParameters();

        switch (stepForwardCommand.getResult()) {
            case END_ALGORITHM -> {
                mainWindow.showDialog(Alert.AlertType.INFORMATION,
                        "Information Dialog", "The work of the algorithm is completed.",
                        "Maximum network flow: " + stepForwardCommand.getAlgorithmResult());
            }
            case INCORRECT_NETWORK -> {
                mainWindow.showError("The source or destination is set incorrectly.");
            }
            case ERROR -> mainWindow.showError("Unknown error occurred.");
        }
    }

    @FXML
    private void buttonRunAlgorithmPressed() {
        boolean isSourceAndDestinationSet = commands.get(CommandType.CHECK_SOURCE_AND_DESTINATION).execute();

        if (!isSourceAndDestinationSet) {
            mainWindow.showError("The source or destination is set incorrectly.");
            return;
        }

        isAnimationExecuting.set(true);

        StepForwardCommand stepForwardCommand = (StepForwardCommand) commands.get(CommandType.STEP_FORWARD);
        PauseTransition pause = new PauseTransition(Duration.seconds(1));
        pause.setOnFinished(event -> {
            boolean stepResult = stepForwardCommand.execute();
            updateNetworkViewAndParameters();

            if (stepResult) {
                pause.play();
            } else {
                isAnimationExecuting.set(false);
            }
        });
        pause.play();
    }

    @FXML
    private void buttonResetPressed() {
        if (isAnimationExecuting.get()) {
            return;
        }

        Command command = commands.get(CommandType.RESET);
        command.execute();
        updateNetworkViewAndParameters();
    }

    @FXML
    private void buttonAddEdgePressed() {
        if (isAnimationExecuting.get()) {
            return;
        }

        Optional<String> answer = mainWindow.showTextInputDialog("Input Dialog", null,
                "Enter edge (<source> <destination> <capacity>):");

        if (answer.isPresent()) {
            AddEdgeCommand addEdgeCommand = (AddEdgeCommand) commands.get(CommandType.ADD_EDGE);
            String input = answer.get();

            boolean success = true;
            int firstSpaceIndex = input.indexOf(' ');
            int secondSpaceIndex = input.lastIndexOf(' ');

            try {
                if (firstSpaceIndex != -1 && secondSpaceIndex != -1) {
                    String source = input.substring(0, firstSpaceIndex);
                    String destination = input.substring(firstSpaceIndex + 1, secondSpaceIndex);
                    Double capacity = Double.valueOf(input.substring(secondSpaceIndex + 1));
                    addEdgeCommand.setSource(source);
                    addEdgeCommand.setDestination(destination);
                    addEdgeCommand.setCapacity(capacity);
                    addEdgeCommand.execute();
                } else {
                    success = false;
                }
            } catch (Exception e) {
                success = false;
            }

            if (!success) {
                mainWindow.showError("Error adding an edge: Invalid format.");
                return;
            }

            updateNetworkViewAndParameters();
        }
    }

    @FXML
    private void buttonRemoveEdgePressed() {
        if (isAnimationExecuting.get()) {
            return;
        }

        Optional<String> answer = mainWindow.showTextInputDialog("Input Dialog", null,
                "Enter edge (<source> <destination>):");

        if (answer.isPresent()) {
            RemoveEdgeCommand removeEdgeCommand = (RemoveEdgeCommand) commands.get(CommandType.REMOVE_EDGE);
            String input = answer.get();

            boolean success = true;
            int spaceIndex = input.indexOf(' ');

            try {
                if (spaceIndex != -1) {
                    String source = input.substring(0, spaceIndex);
                    String destination = input.substring(spaceIndex + 1);
                    removeEdgeCommand.setSource(source);
                    removeEdgeCommand.setDestination(destination);
                    removeEdgeCommand.execute();
                } else {
                    success = false;
                }
            } catch (Exception e) {
                success = false;
            }

            if (!success) {
                mainWindow.showError("Error removing an edge: Invalid format.");
                return;
            }

            updateNetworkViewAndParameters();
        }
    }

    @FXML
    private void buttonClearNetworkPressed() {
        if (isAnimationExecuting.get()) {
            return;
        }

        commands.get(CommandType.CLEAR_NETWORK).execute();
        updateNetworkViewAndParameters();
    }
}
