package leti.practice.algorithm;

import leti.practice.structures.graph.*;

import java.util.Collections;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.ArrayList;

public class AlgorithmExecutor {
    private static final Logger logger = Logger.getLogger(AlgorithmExecutor.class.getName());
    private ResidualNetwork<Double> network;
    private ArrayList<ResidualNetwork<Double>> networkStates;
    private HashSet<Node> amountOfNodes;
    private double maxFlow;
    private boolean isNetworkCorrect;
    private boolean isAlgorithmEnd;
    private int stepOfAlgorithm;

    public AlgorithmExecutor(){
        network = null;
        networkStates = null;
        amountOfNodes = null;
        maxFlow = 0.0;
        isNetworkCorrect = false;
        isAlgorithmEnd = false;
        stepOfAlgorithm = 0;
    }

    public boolean setNetwork(ResidualNetwork<Double> network){
        this.network = null;
        networkStates = null;
        amountOfNodes = null;
        maxFlow = 0.0;
        isNetworkCorrect = false;
        isAlgorithmEnd = false;
        stepOfAlgorithm = 0;


        if(network==null) {
            logger.log(Level.FINEST, "Error network is empty\n");
            throw new NullPointerException();
        }

        if(network.getSource()== null && network.getDestination()==null){
            throw new NullPointerException();
        }

        this.network = network;
        networkStates = new ArrayList<>();
        isNetworkCorrect = checkNetwork();
        return isNetworkCorrect;
    }

    public ResidualNetwork<Double> getNetwork(){
        return network;
    }

    public double getMaxFlow(){
        if(isAlgorithmEnd){
            maxFlow = 0.0;
            for(Node dest : network.getReverseNetworkEdges(network.getDestination()).keySet()){
                maxFlow += network.getReverseNetworkEdges(network.getDestination()).get(dest).getFlow();
            }
            if (Double.compare(maxFlow, 0.0) != 0) {
                maxFlow *= -1;
            }
        }
        return maxFlow;
    }

    private boolean checkNetwork(){
        logger.log(Level.FINEST, "Checking correction of the network\n");
        for(Node from : network.getNetworkNodes()){
            for (Node to : network.getNetworkEdges(from).keySet()){
                if(network.getNetworkEdges(from).get(to).getCapacity() < 0.0
                        || network.getNetworkEdges(from).get(to).getFlow() < 0.0
                        || network.getNetworkEdges(from).get(to).getFlow() >
                        network.getNetworkEdges(from).get(to).getCapacity()){
                    logger.log(Level.FINEST, "Find error edge "+ from.getName()+" "+to.getName());
                    return false;
                }
            }
        }
        return true;
    }

    private boolean initializeNetwork(){
        if(isNetworkCorrect){
            logger.log(Level.FINEST, "|------------------------------|\nStart Initialization of network");
            amountOfNodes = new HashSet<>(network.getNetworkNodes());
            amountOfNodes.addAll(network.getReverseNetworkNodes());
            /*Initialization of heights and surplus function*/
            for (Node node:amountOfNodes){
                network.getHeights().put(node, 0);
                network.getSurpluses().put(node, 0.0);
            }
            network.getHeights().put(network.getSource(), amountOfNodes.size());
            //add to the states
            networkStates.add(network.copy());

            network.printNetwork();
            /*First algorithm step*/
            for(Node to : network.getNetworkEdges(network.getSource()).keySet()){
                //add flow to edge
                network.getNetworkEdges(network.getSource()).get(to).setFlow(network.getNetworkEdges(network.getSource()).get(to).getCapacity());
                logger.log(Level.FINEST, "Push flow though edge "+network.getSource()+" "+to.getName()+ " flow is "+ network.getNetworkEdges(network.getSource()).get(to).getCapacity());
                //add surplus to the node
                network.getSurpluses().put(to, network.getNetworkEdges(network.getSource()).get(to).getCapacity());
                logger.log(Level.FINEST, "Surplus of node "+to.getName()+" is "+network.getSurpluses().get(to));
                //add flow to the reverse edge
                network.getReverseNetworkEdges(to).get(network.getSource()).setFlow(-1*network.getNetworkEdges(network.getSource()).get(to).getCapacity());
            }
            amountOfNodes.remove(network.getDestination());
            amountOfNodes.remove(network.getSource());
            logger.log(Level.FINEST, "End Initialization of network\n|------------------------------|");
            return true;
        }
        return false;
    }

    private boolean push(){
        if(isNetworkCorrect){
            for(Node node : amountOfNodes){
                //Если переполнение
                if(network.getSurpluses().get(node)>0){
                    if(network.getReverseNetworkNodes().contains(node)){
                        for (Node to : network.getReverseNetworkEdges(node).keySet()){
                            //если можно пропустить поток через обратное ребро
                            if(network.getReverseNetworkEdges(node).get(to).getFlow()< 0.0){
                                if(network.getHeights().get(node) == network.getHeights().get(to) + 1){
                                    logger.log(Level.FINEST, "|-----------------------------------|");
                                    logger.log(Level.FINEST, "Make push with node " + node.getName());
                                    double availableAmountOfFlow = Math.min(network.getSurpluses().get(node), network.getReverseNetworkEdges(node).get(to).getCapacity() - network.getReverseNetworkEdges(node).get(to).getFlow());
                                    logger.log(Level.FINEST, "availableAmountOfFlow " + availableAmountOfFlow);
                                    network.getReverseNetworkEdges(node).get(to).setFlow(network.getReverseNetworkEdges(node).get(to).getFlow() + availableAmountOfFlow);
                                    logger.log(Level.FINEST, "Add flow " + availableAmountOfFlow + " to the Edge "+node.getName()+" "+to.getName());
                                    network.getNetworkEdges(to).get(node).setFlow(network.getNetworkEdges(to).get(node).getFlow() - availableAmountOfFlow);
                                    logger.log(Level.FINEST, "Flow of edge "+to.getName()+" "+ node.getName()+" is "+network.getNetworkEdges(to).get(node).getFlow());
                                    network.getSurpluses().put(node, network.getSurpluses().get(node) - availableAmountOfFlow);
                                    logger.log(Level.FINEST, "Surplus of node "+node.getName()+" is "+network.getSurpluses().get(node));
                                    network.getSurpluses().put(to, network.getSurpluses().get(to) + availableAmountOfFlow);
                                    logger.log(Level.FINEST, "Surplus of node "+to.getName()+" is "+network.getSurpluses().get(to));
                                    logger.log(Level.FINEST, "|-----------------------------------|");
                                    return true;
                                }
                            }
                        }
                    }
                    if(network.getNetworkNodes().contains(node)){
                        for(Node to :network.getNetworkEdges(node).keySet()){
                            //если можно пропустить поток через ребро
                            if(network.getNetworkEdges(node).get(to).getCapacity() - network.getNetworkEdges(node).get(to).getFlow() > 0.0){
                                if(network.getHeights().get(node) == network.getHeights().get(to) + 1){
                                    logger.log(Level.FINEST, "|-----------------------------------|");
                                    logger.log(Level.FINEST, "Make push with node " + node.getName());
                                    double availableAmountOfFlow = Math.min(network.getSurpluses().get(node), network.getNetworkEdges(node).get(to).getCapacity() - network.getNetworkEdges(node).get(to).getFlow());
                                    logger.log(Level.FINEST, "availableAmountOfFlow " + availableAmountOfFlow);
                                    network.getNetworkEdges(node).get(to).setFlow(network.getNetworkEdges(node).get(to).getFlow() + availableAmountOfFlow);
                                    logger.log(Level.FINEST, "Add flow " + availableAmountOfFlow + " to the Edge "+node.getName()+" "+to.getName());
                                    network.getReverseNetworkEdges(to).get(node).setFlow(network.getReverseNetworkEdges(to).get(node).getFlow() - availableAmountOfFlow);
                                    logger.log(Level.FINEST, "Flow of reverse edge "+to.getName()+" "+ node.getName()+" is "+network.getReverseNetworkEdges(to).get(node).getFlow());
                                    network.getSurpluses().put(node, network.getSurpluses().get(node) - availableAmountOfFlow);
                                    logger.log(Level.FINEST, "Surplus of node "+node.getName()+" is "+network.getSurpluses().get(node));
                                    network.getSurpluses().put(to, network.getSurpluses().get(to) + availableAmountOfFlow);
                                    logger.log(Level.FINEST, "Surplus of node "+to.getName()+" is "+network.getSurpluses().get(to));
                                    logger.log(Level.FINEST, "|-----------------------------------|");
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
        }
        return false;
    }

    private boolean relabel(){
        if(isNetworkCorrect){
            for(Node node : amountOfNodes) {
                //Если переполнение
                boolean flag = false;
                if (network.getSurpluses().get(node) > 0) {
                    if(network.getNetworkNodes().contains(node)){
                        ArrayList<Integer> heights = new ArrayList<>();
                        for(Node to :network.getNetworkEdges(node).keySet()){
                            if(!network.getNetworkEdges(node).get(to).getCapacity().equals(network.getNetworkEdges(node).get(to).getFlow())) {
                                if (network.getHeights().get(node) > network.getHeights().get(to)) {
                                    flag = true;
                                    break;
                                }
                                heights.add(network.getHeights().get(to));
                            }
                        }
                        if(network.getReverseNetworkNodes().contains(node) && !flag){
                            for (Node to : network.getReverseNetworkEdges(node).keySet()){
                                if(!network.getReverseNetworkEdges(node).get(to).getFlow().equals(network.getReverseNetworkEdges(node).get(to).getCapacity())) {
                                    if (network.getHeights().get(node) > network.getHeights().get(to)) {
                                        flag = true;
                                        break;
                                    }
                                    heights.add(network.getHeights().get(to));
                                }

                            }
                        }
                        if(!flag){
                            logger.log(Level.FINEST, "|-----------------------------------|");
                            logger.log(Level.FINEST, "Make relabel with node " + node.getName());
                            logger.log(Level.FINEST, "Height of node " + node.getName()+" was "+network.getHeights().get(node));
                            network.getHeights().put(node, 1+Math.min(network.getHeights().get(node), Collections.min(heights)));
                            logger.log(Level.FINEST, "Height of node " + node.getName()+" is "+network.getHeights().get(node));
                            logger.log(Level.FINEST, "|-----------------------------------|");
                            return true;
                        }
                    }
                }
            }
        }
        return false;
    }

    public boolean nextStep(){
        if(isNetworkCorrect && !isAlgorithmEnd) {
            if(stepOfAlgorithm == 0){
                stepOfAlgorithm++;
                return initializeNetwork();
            }
            networkStates.add(network.copy());
            if (relabel()) {
                stepOfAlgorithm++;
                return true;
            }
            if (push()) {
                stepOfAlgorithm++;
                return true;
            }
            isAlgorithmEnd = true;
            logger.log(Level.FINEST, "END ALGORITHM");
        }

        if (!isNetworkCorrect) {
            logger.log(Level.FINEST, "NETWORK ISNT CORRECT");
        }

        return false;
    }

    public boolean previousStep(){
        if(isNetworkCorrect) {
            if (networkStates.size() != 0) {
                if (isAlgorithmEnd) {
                    isAlgorithmEnd = false;
                }
                network = null;
                network = networkStates.get(networkStates.size() - 1);
                networkStates.remove(networkStates.size() - 1);
                return true;
            }
        }
        return false;
    }

    public double runAlgorithm(ResidualNetwork<Double> network){
        if (setNetwork(network)){
            while (nextStep()){}
            return getMaxFlow();
        }
        return -1.0;
    }
}
