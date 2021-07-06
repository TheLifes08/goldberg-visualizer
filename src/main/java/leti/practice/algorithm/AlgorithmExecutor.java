package leti.practice.algorithm;

import leti.practice.structures.graph.*;

import java.util.logging.Logger;
import java.util.ArrayList;

public class AlgorithmExecutor {
    private static final Logger logger = Logger.getLogger(AlgorithmExecutor.class.getName());
    private ResidualNetwork<Double> network;
    private ArrayList<ResidualNetwork<Double>> networkStates;
    private double maxFlow;
    public AlgorithmExecutor(){
        network = null;
        networkStates = null;
        maxFlow = 0.0;
    }
    public void setNetwork(ResidualNetwork<Double> network){

    }
}
