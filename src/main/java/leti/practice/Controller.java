package leti.practice;

import javafx.scene.canvas.Canvas;
import leti.practice.algorithm.AlgorithmExecutor;
import leti.practice.commands.StepForwardResult;
import leti.practice.files.NetworkLoader;
import leti.practice.files.NetworkSaver;
import leti.practice.gui.MainWindow;
import leti.practice.gui.ViewType;
import leti.practice.structures.graph.EdgeProperties;
import leti.practice.structures.graph.Node;
import leti.practice.structures.graph.ResidualNetwork;
import leti.practice.view.*;

import java.io.File;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    private static final Logger logger = Logger.getLogger(MainWindow.class.getName());
    private AlgorithmExecutor algorithmExecutor;
    private ResidualNetwork<Double> network;
    private ResidualNetwork<Double> initialNetwork;
    private NetworkViewPainter networkViewPainter;
    private ResidualNetworkViewPainter residualNetworkViewPainter;
    private HeightFunctionViewPainter heightFunctionViewPainter;
    private ViewPainter viewPainter;
    private boolean isAlgorithmRan;

    public Controller() {
        initialNetwork = new ResidualNetwork<Double>();
        networkViewPainter = new OriginalNetworkViewPainter();
        residualNetworkViewPainter = new ResidualNetworkViewPainter();
        heightFunctionViewPainter = new HeightFunctionViewPainter();
        viewPainter = residualNetworkViewPainter;
        network = initialNetwork;
        algorithmExecutor = null;
        isAlgorithmRan = false;
    }

    public boolean loadNetwork(File file) {
        logger.log(Level.INFO, "Load Network Command executed.");
        ResidualNetwork<Double> network = NetworkLoader.loadNetwork(file);

        if (network != null) {
            initialNetwork = network;
            resetAlgorithm();
            setNeedRecalculateNodesParameters(true);
            return true;
        }

        return false;
    }

    public boolean saveNetwork(File file) {
        logger.log(Level.INFO, "Save Network Command executed.");
        return NetworkSaver.saveNetwork(file, initialNetwork);
    }

    public boolean setView(ViewType viewType) {
        if (viewType == ViewType.ORIGINAL_NETWORK) {
            viewPainter = networkViewPainter;
        } else if (viewType == ViewType.RESIDUAL_NETWORK) {
            viewPainter = residualNetworkViewPainter;
        } else if (viewType == ViewType.HEIGHT_FUNCTION){
            viewPainter = heightFunctionViewPainter;
        } else {
            return false;
        }

        return true;
    }

    public StepForwardResult stepForward() {
        logger.log(Level.INFO, "Step Forward Command executed.");

        if (!isAlgorithmRan) {
            initialNetwork = network.copy();
            algorithmExecutor = new AlgorithmExecutor();

            try {
                algorithmExecutor.setNetwork(network);
            } catch (NullPointerException e) {
                algorithmExecutor = null;
                return StepForwardResult.INCORRECT_NETWORK;
            }

            isAlgorithmRan = true;
        }

        if (algorithmExecutor != null) {
            boolean result = algorithmExecutor.nextStep();
            network = algorithmExecutor.getNetwork();

            if (result) {
                return StepForwardResult.SUCCESS;
            } else {
                return StepForwardResult.END_ALGORITHM;
            }
        }

        return StepForwardResult.ERROR;
    }

    public void setSourceAndDestination(String source, String destination) {
        if (source != null && destination != null) {
            initialNetwork.setSource(new Node(source));
            initialNetwork.setDestination(new Node(destination));
            resetAlgorithm();
            setNeedRecalculateNodesParameters(true);
        }
    }

    public boolean isSourceAndDestinationCorrect() {
        if (initialNetwork.getSource() == null || initialNetwork.getDestination() == null) {
            return false;
        }

        HashSet<Node> nodes = new HashSet<>(initialNetwork.getNetworkNodes());
        nodes.addAll(initialNetwork.getReverseNetworkNodes());

        return nodes.contains(network.getSource()) && nodes.contains(network.getDestination());
    }

    public boolean stepBackward() {
        logger.log(Level.INFO, "Step Backward Command executed.");

        if (!isAlgorithmRan) {
            return false;
        }

        if (algorithmExecutor != null) {
            boolean result = algorithmExecutor.previousStep();
            network = algorithmExecutor.getNetwork();
            return result;
        }

        return false;
    }

    public boolean addEdge(String source, String destination, Double capacity) {
        logger.log(Level.INFO, "Add Edge Command executed.");

        if (source != null && destination != null && capacity != null) {
            resetAlgorithm();
            initialNetwork.addEdge(new Node(source), new Node(destination), new EdgeProperties<Double>(capacity, 0.0));
            setNeedRecalculateNodesParameters(true);
            return true;
        }

        return false;
    }

    public boolean removeEdge(String source, String destination) {
        logger.log(Level.INFO, "Remove Edge Command executed.");

        if (source != null && destination != null) {
            resetAlgorithm();
            initialNetwork.deleteEdge(new Node(source), new Node(destination));
            setNeedRecalculateNodesParameters(true);
            return true;
        }

        return false;
    }

    public void resetAlgorithm() {
        logger.log(Level.INFO, "Reset Command executed.");
        network = initialNetwork;
        algorithmExecutor = null;
        isAlgorithmRan = false;
    }

    public void clearNetwork() {
        logger.log(Level.INFO, "Clear Network Command executed.");

        if (viewPainter.isCanvasSet()) {
            viewPainter.clearCanvas();
            setNeedRecalculateNodesParameters(true);
        }

        initialNetwork = new ResidualNetwork<Double>();
        network = initialNetwork;
        algorithmExecutor = null;
        isAlgorithmRan = false;
    }

    public void paintView(Canvas canvas) {
        if (viewPainter != null && canvas != null) {
            viewPainter.setCanvas(canvas);
            viewPainter.paint(network);
        }
    }

    public String getNetworkParameters() {
        return NetworkParametersFormatter.getNetworkParameters(network);
    }

    public double getNetworkMaxFlow() {
        if (algorithmExecutor != null) {
            return algorithmExecutor.getMaxFlow();
        }

        return 0.0;
    }

    private void setNeedRecalculateNodesParameters(boolean value) {
        networkViewPainter.setNeedRecalculateNodesParameters(value);
        residualNetworkViewPainter.setNeedRecalculateNodesParameters(value);
    }
}
