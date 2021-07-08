package leti.practice;

import javafx.scene.canvas.Canvas;
import leti.practice.algorithm.AlgorithmExecutor;
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
        addEdge("S", "A", 10);
        addEdge("S", "B", 4);
        addEdge("S", "C", 6);
        addEdge("A", "D", 4);
        addEdge("C", "E", 5);
        addEdge("B", "E", 7);
        addEdge("E", "T", 3);
        addEdge("D", "T", 5);
        network.setSource(new Node("S"));
        network.setDestination(new Node("T"));

        algorithmExecutor.setNetwork(network);
    }

    public void loadNetwork(File file) {
        logger.log(Level.INFO, "Load Network Command executed.");
    }

    public void saveNetwork(File file) {
        logger.log(Level.INFO, "Save Network Command executed.");
    }

    public void setView(ViewType viewType) {
        if (viewType == ViewType.ORIGINAL_NETWORK) {
            viewPainter = networkViewPainter;
        } else if (viewType == ViewType.RESIDUAL_NETWORK) {
            viewPainter = residualNetworkViewPainter;
        } else {
            viewPainter = heightFunctionViewPainter;
        }
    }

    public boolean stepForward() {
        if (algorithmExecutor != null) {
            logger.log(Level.INFO, "Step Forward Command executed.");
            boolean result = algorithmExecutor.nextStep();
            network = algorithmExecutor.getNetwork();
            return result;
        }
        return false;
    }

    public void stepBackward() {
        if (algorithmExecutor != null) {
            logger.log(Level.INFO, "Step Backward Command executed.");
            algorithmExecutor.previousStep();
            network = algorithmExecutor.getNetwork();
        }
    }

    public void addEdge(String source, String destination, double capacity) {
        logger.log(Level.INFO, "Add Edge Command executed.");
        network.addEdge(new Node(source), new Node(destination), new EdgeProperties<Double>(capacity, 0.0));
        viewPainter.setNeedRecalculateNodesParameters(true);
    }

    public void removeEdge(String source, String destination) {
        logger.log(Level.INFO, "Remove Edge Command executed.");
        network.deleteEdge(new Node(source), new Node(destination));
        viewPainter.setNeedRecalculateNodesParameters(true);
    }

    public void clearNetwork() {
        logger.log(Level.INFO, "Clear Network Command executed.");
        if (viewPainter.isCanvasSet()) {
            viewPainter.clearCanvas();
        }
        network = new ResidualNetwork<Double>();
        algorithmExecutor = null;
        viewPainter.setNeedRecalculateNodesParameters(true);
    }

    public void paintView(Canvas canvas) {
        viewPainter.setCanvas(canvas);
        viewPainter.paint(network);
    }

    public String getNetworkParameters() {
        return NetworkParametersFormatter.getNetworkParameters(network);
    }
}
