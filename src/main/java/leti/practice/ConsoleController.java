package leti.practice;

import leti.practice.algorithm.AlgorithmExecutor;
import leti.practice.files.NetworkLoader;
import leti.practice.logging.MessageFormatter;
import leti.practice.logging.MessageHandler;
import leti.practice.structures.graph.Node;
import leti.practice.structures.graph.ResidualNetwork;

import java.io.File;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ConsoleController {
    private static final Logger logger = Logger.getLogger("leti.practice");
    private static MessageHandler messageHandler;
    private AlgorithmExecutor algorithmExecutor;
    private ResidualNetwork<Double> network;
    private boolean isNetworkCorrect;


    public ConsoleController(){
        algorithmExecutor = new AlgorithmExecutor();;
        network = null;
        isNetworkCorrect = false;
        messageHandler = new MessageHandler();
        MessageFormatter formatter = new MessageFormatter();

        messageHandler.setLevel(Level.INFO);
        messageHandler.setFormatter(formatter);
        logger.setLevel(Level.ALL);
        logger.addHandler(messageHandler);
        logger.setUseParentHandlers(false);
    }

    public void run(String[] args){
        if (args.length == 1){
            printHelp();
            return;
        }

        for (int i = 0; i < args.length; ++i){
            if (args[i].equals("-f")) {
                if (i + 1 >= args.length) {
                    logger.log(Level.INFO, "The path to the file is not specified.");
                    return;
                }
                File file = new File(args[i + 1]);
                isNetworkCorrect = loadNetworkFromFile(file);
                i++;
            } else if (args[i].equals("-h")) {
                printHelp();
            } else if (args[i].equals("-o")) {
                logger.log(Level.INFO, "The output of intermediate messages is activated.");
                messageHandler.setLevel(Level.ALL);
            } else {
                if (!args[i].equals("-cli")) {
                    printHelp();
                    return;
                }
            }
        }

        if (isNetworkCorrect) {
            double result = runAlgorithm();
            logger.log(Level.INFO, "The maximum flow in the network is equal to " + result);
        } else {
            logger.log(Level.INFO, "The network is not loaded.");
        }
    }

    private void printHelp(){
        System.out.println("""
                Available flags:
                  -h          Print help
                  -f <file>   Choose file with network
                  -o          to enable intermediate outputs
                """);
    }

    private boolean isSourceAndDestinationCorrect() {
        if (network.getSource() == null || network.getDestination() == null) {
            return false;
        }

        HashSet<Node> nodes = new HashSet<>(network.getNetworkNodes());
        nodes.addAll(network.getReverseNetworkNodes());

        return nodes.contains(network.getSource()) && nodes.contains(network.getDestination());
    }

    private boolean loadNetworkFromFile(File file){
        ResidualNetwork<Double> network = NetworkLoader.loadNetwork(file);

        if (network != null) {
            this.network = network;
            return isSourceAndDestinationCorrect();
        } else {
            logger.log(Level.INFO, "Error loading the network from a file.");
        }

        return false;
    }

    private double runAlgorithm(){
        double result = -1.0;

        try {
            result = algorithmExecutor.runAlgorithm(network);
        } catch (NullPointerException e) {
            logger.log(Level.INFO, "The network is incorrect.");
        }

        return result;
    }

}
