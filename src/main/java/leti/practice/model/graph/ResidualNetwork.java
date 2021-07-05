package leti.practice.model.graph;

import java.util.HashMap;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResidualNetwork<T extends Number> {
    private static final Logger logger = Logger.getLogger(ResidualNetwork.class.getName());
    private HashMap<Node, HashMap<Node, EdgeProperties<T>>> network = null;
    private HashMap<Node, HashMap<Node, EdgeProperties<T>>> reverseNetwork = null;
    private HashMap<Node, T> surpluses = null;
    private HashMap<Node, Integer> heights = null;
    private Node source = null;
    private Node destination = null;

    private ResidualNetwork(Node source, Node destination, HashMap<Node, HashMap<Node, EdgeProperties<T>>> network, HashMap<Node, HashMap<Node, EdgeProperties<T>>> reverseNetwork, HashMap<Node, Integer> heights, HashMap<Node, T> surpluses){
        this.source = source.copy();
        this.destination = destination.copy();
    }
    public ResidualNetwork(HashMap<Node, HashMap<Node, EdgeProperties<T>>> graph, Node source, Node destination){
        this.network = graph;
        this.source = source;
        this.destination = destination;
        reverseNetwork = new HashMap<>();
        surpluses = new HashMap<>();
        heights = new HashMap<>();
        for (Node from : network.keySet()){
            for(Node to : network.get(from).keySet()){
                if(!reverseNetwork.containsKey(to)){
                    reverseNetwork.put(to, new HashMap<Node, EdgeProperties<T>>()); // create reverse edges;
                }
                reverseNetwork.get(to).put(from, network.get(from).get(to));
            }
        }
    }
    public Set<Node> getNetworkNodes(){
        return network.keySet();
    }
    public HashMap<Node, EdgeProperties<T>> getNetworkEdges(Node key){
        return network.get(key);
    }
    public Set<Node> getReverseNetworkNodes(){
        return reverseNetwork.keySet();
    }
    public HashMap<Node, EdgeProperties<T>> getReverseNetworkEdges(Node key){
        return reverseNetwork.get(key);
    }

    public void addEdge(Node from, Node to, EdgeProperties<T> edgeProperties){
        /*add Edge*/
        if(network.containsKey(from)){
            if(!network.get(from).containsKey(to)) {
                network.get(from).put(to, edgeProperties);
                logger.log(Level.INFO, String.format("Edge {} {} with capacity {} is added", from.getName(), to.getName(), edgeProperties.toString()));
            }
        }else {
            network.put(from, new HashMap<Node, EdgeProperties<T>>());
            network.get(from).put(to, edgeProperties);
            logger.log(Level.INFO, String.format("Edge {} {} with capacity {} is added", from.getName(), to.getName(), edgeProperties.toString()));
        }
        /*add reverse Edge*/
        if(reverseNetwork.containsKey(to)){
            if(!network.get(to).containsKey(from)) {
                network.get(to).put(from, edgeProperties);
            }
        }else {
            network.put(to, new HashMap<Node, EdgeProperties<T>>());
            network.get(to).put(from, edgeProperties);
        }
    }

    public void deleteEdge(Node from, Node to){
        /*delete edge*/
        if(network.containsKey(from)){
            network.get(from).remove(to);
        }
        else {logger.log(Level.INFO, String.format("No Edge {} {}", from.getName(), to.getName()));}
        if(reverseNetwork.containsKey(to)){
            reverseNetwork.get(to).remove(from);
        }
    }
    public void setSource(Node source){
        /*think about more functional or check correction*/
        this.source = source;
    }
    public void setDestination(Node destination){
        /*think about more functional or check correction*/
        this.destination = destination;
    }
    public ResidualNetwork<T> copy(){
        return  new ResidualNetwork<T>(source, destination, network, reverseNetwork, heights, surpluses);
    }

}
