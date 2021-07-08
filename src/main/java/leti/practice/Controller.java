package leti.practice;

import javafx.scene.canvas.Canvas;
import leti.practice.algorithm.AlgorithmExecutor;
import leti.practice.gui.MainWindow;
import leti.practice.gui.ViewType;
import leti.practice.structures.graph.EdgeProperties;
import leti.practice.structures.graph.Node;
import leti.practice.structures.graph.ResidualNetwork;
import leti.practice.view.HeightFunctionViewPainter;
import leti.practice.view.NetworkViewPainter;
import leti.practice.view.ResidualNetworkViewPainter;
import leti.practice.view.ViewPainter;

import java.io.File;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Controller {
    private static final Logger logger = Logger.getLogger(MainWindow.class.getName());
    private AlgorithmExecutor algorithmExecutor = null;
    private ResidualNetwork<Double> network;
    private NetworkViewPainter networkViewPainter;
    private ResidualNetworkViewPainter residualNetworkViewPainter;
    private HeightFunctionViewPainter heightFunctionViewPainter;
    private ViewPainter viewPainter;

    public Controller() {
        algorithmExecutor = new AlgorithmExecutor();
        networkViewPainter = new NetworkViewPainter();
        residualNetworkViewPainter = new ResidualNetworkViewPainter();
        heightFunctionViewPainter = new HeightFunctionViewPainter();
        network = new ResidualNetwork<Double>();
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
        logger.log(Level.INFO, "Set View Command executed.");

        if (viewType == ViewType.ORIGINAL_NETWORK) {
            viewPainter = networkViewPainter;
        } else if (viewType == ViewType.RESIDUAL_NETWORK) {
            viewPainter = residualNetworkViewPainter;
        } else {
            viewPainter = heightFunctionViewPainter;
        }
    }

    public boolean stepForward() {
        logger.log(Level.INFO, "Step Forward Command executed.");
        logger.log(Level.FINEST, "Intermediate message!");
        network.printNetwork();
        return algorithmExecutor.nextStep();
        //return false;
    }

    public void stepBackward() {
        logger.log(Level.INFO, "Step Backward Command executed.");
        logger.log(Level.FINEST, "Intermediate message!");
        //algorithmExecutor.previousSrep();
    }

    public void addEdge(String source, String destination, double capacity) {
        logger.log(Level.INFO, "Add Edge Command executed.");
        network.addEdge(new Node(source), new Node(destination), new EdgeProperties<Double>(capacity, 0.0));
    }

    public void removeEdge(String source, String destination) {
        logger.log(Level.INFO, "Remove Edge Command executed.");
        network.deleteEdge(new Node(source), new Node(destination));
    }

    public void clearNetwork() {
        logger.log(Level.INFO, "Clear Network Command executed.");
        if (viewPainter.isCanvasSet()) {
            viewPainter.clearCanvas();
        }
    }

    public void paintView(Canvas canvas) {
        logger.log(Level.INFO, "Paint View Command executed.");
        viewPainter.setCanvas(canvas);
        viewPainter.paint(network);
    }
}
