package leti.practice;

import leti.practice.algorithm.AlgorithmExecutor;
import leti.practice.commands.*;
import leti.practice.files.NetworkLoader;
import leti.practice.structures.graph.Node;
import leti.practice.structures.graph.ResidualNetwork;
import leti.practice.view.ViewPainter;

import java.io.File;
import java.util.HashSet;
import java.util.logging.Level;

public class CLIController {
    private String[] args;
    private AlgorithmExecutor algorithmExecutor;
    private ResidualNetwork<Double> network;
    private ResidualNetwork<Double> initialNetwork;
    private ViewPainter viewPainter;
    private boolean isAlgorithmRan;
    File file = null;
    public CLIController(String[] args){
        this.args = args;
        initialNetwork = new ResidualNetwork<Double>();
        network = initialNetwork;
        algorithmExecutor = null;
        isAlgorithmRan = false;
    }

    public boolean startCli(){
        if(args.length == 1){
            System.out.println("Empty args");
            helpFunction();
            return false;
        }
        for(int i = 0; i < args.length; i++){
            if(args[i].equals("-f") && i < args.length-1){
                file = new File(args[i+1]);
            }
            if(args[i].equals("-h")){
                helpFunction();
            }if(args[i].equals("-o")){
                // enable logging
            }
        }
        if(file==null){
            System.out.println("Error please choose file");
        }
        else {
            if(cliLoadNetwork(file)) {
                System.out.println(cliRunAlgorithm());
            }

        }
        return false;
    }
    private void helpFunction(){
        System.out.println("|----------------------------------|\n"+
                "This is a help information: \n"+
                "try to use this flags: \n"+
                "-h for help information\n"+
                "-f <file> to choose file with graph\n"+
                "-o to enable intermediate outputs"+
                "|----------------------------------|");
    }

    private void resetAlgorithm() {
        network = initialNetwork;
        algorithmExecutor = null;
        isAlgorithmRan = false;
    }

    private boolean isSourceAndDestinationCorrect() {
        if (initialNetwork.getSource() == null || initialNetwork.getDestination() == null) {
            return false;
        }

        HashSet<Node> nodes = new HashSet<>(initialNetwork.getNetworkNodes());
        nodes.addAll(initialNetwork.getReverseNetworkNodes());

        return nodes.contains(network.getSource()) && nodes.contains(network.getDestination());
    }

    private boolean cliLoadNetwork(File file){
        ResidualNetwork<Double> network = NetworkLoader.loadNetwork(file);

        if (network != null) {
            initialNetwork = network;
            resetAlgorithm();
            return isSourceAndDestinationCorrect();
        }

        return false;
    }

    private double cliRunAlgorithm(){
        algorithmExecutor = new AlgorithmExecutor();
        double result = -1.0;
        try {
            result = algorithmExecutor.runAlgorithm(network);
        } catch (NullPointerException e) {
            algorithmExecutor = null;
        }
        return result;
    }

}
