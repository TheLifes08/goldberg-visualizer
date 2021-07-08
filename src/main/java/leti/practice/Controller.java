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
        network.addEdge(new Node("a"), new Node("b"), new EdgeProperties<>(5.0,0.0));
        network.addEdge(new Node("a"), new Node("c"), new EdgeProperties<>(7.0,0.0));
        network.addEdge(new Node("b"), new Node("a"), new EdgeProperties<>(5.0,0.0));
        network.addEdge(new Node("b"), new Node("d"), new EdgeProperties<>(0.0,0.0));
        network.addEdge(new Node("c"), new Node("a"), new EdgeProperties<>(7.0,0.0));
        network.addEdge(new Node("c"), new Node("f"), new EdgeProperties<>(12.0,0.0));
        network.addEdge(new Node("d"), new Node("e"), new EdgeProperties<>(9.0,0.0));
        network.addEdge(new Node("d"), new Node("f"), new EdgeProperties<>(6.0,0.0));
        network.addEdge(new Node("e"), new Node("c"), new EdgeProperties<>(6.0,0.0));
        network.setSource(new Node("a"));
        network.setDestination(new Node("f"));

        network.printNetwork();
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
            network = algorithmExecutor.getNetwork();
            return algorithmExecutor.nextStep();
        }
        return false;
    }

    public void stepBackward() {
        if (algorithmExecutor != null) {
            logger.log(Level.INFO, "Step Backward Command executed.");
            network = algorithmExecutor.getNetwork();
            algorithmExecutor.previousStep();
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
}
