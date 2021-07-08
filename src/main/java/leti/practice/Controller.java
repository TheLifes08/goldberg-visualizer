package leti.practice;

import javafx.scene.canvas.Canvas;
import leti.practice.algorithm.AlgorithmExecutor;
import leti.practice.files.NetworkLoader;
import leti.practice.files.NetworkSaver;
import leti.practice.gui.MainWindow;
import leti.practice.gui.ViewType;
import leti.practice.structures.graph.EdgeProperties;
import leti.practice.structures.graph.Node;
import leti.practice.structures.graph.ResidualNetwork;
import leti.practice.view.*;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    private static final Logger logger = Logger.getLogger(MainWindow.class.getName());
    private AlgorithmExecutor algorithmExecutor;
    private ResidualNetwork<Double> network;
    private NetworkViewPainter networkViewPainter;
    private ResidualNetworkViewPainter residualNetworkViewPainter;
    private HeightFunctionViewPainter heightFunctionViewPainter;
    private ViewPainter viewPainter;

    public Controller() {
        network = new ResidualNetwork<Double>();
        algorithmExecutor = new AlgorithmExecutor();
        networkViewPainter = new OriginalNetworkViewPainter();
        residualNetworkViewPainter = new ResidualNetworkViewPainter();
        heightFunctionViewPainter = new HeightFunctionViewPainter();
        viewPainter = residualNetworkViewPainter;

        // NETWORK TEST INPUT
        addEdge("S", "A", 10.0);
        addEdge("S", "B", 4.0);
        addEdge("S", "C", 6.0);
        addEdge("A", "D", 4.0);
        addEdge("C", "E", 5.0);
        addEdge("B", "E", 7.0);
        addEdge("E", "T", 3.0);
        addEdge("D", "T", 5.0);
        network.setSource(new Node("S"));
        network.setDestination(new Node("T"));

        algorithmExecutor.setNetwork(network);
    }

    public boolean loadNetwork(File file) {
        logger.log(Level.INFO, "Load Network Command executed.");
        ResidualNetwork<Double> network = NetworkLoader.loadNetwork(file);

        if (network != null) {
            this.network = network;
            algorithmExecutor = new AlgorithmExecutor();
            algorithmExecutor.setNetwork(network);

            if (viewPainter != null) {
                setNeedRecalculateNodesParameters(true);
            }

            return true;
        }

        return false;
    }

    public boolean saveNetwork(File file) {
        logger.log(Level.INFO, "Save Network Command executed.");
        return NetworkSaver.saveNetwork(file, network);
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

    public boolean stepForward() {
        logger.log(Level.INFO, "Step Forward Command executed.");

        if (algorithmExecutor != null) {
            boolean result = algorithmExecutor.nextStep();
            network = algorithmExecutor.getNetwork();
            return result;
        }

        return false;
    }

    public boolean stepBackward() {
        logger.log(Level.INFO, "Step Backward Command executed.");

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
            network.addEdge(new Node(source), new Node(destination), new EdgeProperties<Double>(capacity, 0.0));

            if (viewPainter != null) {
                setNeedRecalculateNodesParameters(true);
            }

            return true;
        }

        return false;
    }

    public boolean removeEdge(String source, String destination) {
        logger.log(Level.INFO, "Remove Edge Command executed.");

        if (source != null && destination != null) {
            network.deleteEdge(new Node(source), new Node(destination));

            if (viewPainter != null) {
                setNeedRecalculateNodesParameters(true);
            }

            return true;
        }

        return false;
    }

    public void clearNetwork() {
        logger.log(Level.INFO, "Clear Network Command executed.");

        if (viewPainter != null && viewPainter.isCanvasSet()) {
            viewPainter.clearCanvas();
            setNeedRecalculateNodesParameters(true);
        }

        network = new ResidualNetwork<Double>();
        algorithmExecutor = new AlgorithmExecutor();
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

    private void setNeedRecalculateNodesParameters(boolean value) {
        networkViewPainter.setNeedRecalculateNodesParameters(value);
        heightFunctionViewPainter.setNeedRecalculateNodesParameters(value);
        residualNetworkViewPainter.setNeedRecalculateNodesParameters(value);
    }
}
