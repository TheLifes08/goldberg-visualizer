package leti.practice.structures.graph;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
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
        this.source = source;
        this.destination = destination;
        this.network = new HashMap<>();
        this.reverseNetwork = new HashMap<>();
        this.heights = new HashMap<>();
        this.surpluses = new HashMap<>();
        for(Node from : network.keySet()){
            this.network.put(from.copy(), new HashMap<>());
            for (Node to : network.get(from).keySet()){
                this.network.get(from).put(to.copy(), network.get(from).get(to).copy());
            }
        }
        for(Node from : reverseNetwork.keySet()){
            this.reverseNetwork.put(from.copy(), new HashMap<>());
            for (Node to : reverseNetwork.get(from).keySet()){
                this.reverseNetwork.get(from).put(to.copy(), reverseNetwork.get(from).get(to).copy());
            }
        }
        for (Map.Entry<Node, Integer> entry : heights.entrySet()){
            this.heights.put(entry.getKey().copy(), Integer.valueOf(entry.getValue().intValue()));
        }
        for (Map.Entry<Node, T> entry : surpluses.entrySet()){
            this.surpluses.put(entry.getKey().copy(), entry.getValue());
        }
    }

    public ResidualNetwork(){
        this.network = new HashMap<>();
        reverseNetwork = new HashMap<>();
        surpluses = new HashMap<>();
        heights = new HashMap<>();
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
    public HashMap<Node, Integer> getHeights(){
        return heights;
    }
    public HashMap<Node, T> getSurpluses(){
        return surpluses;
    }

    public void addEdge(Node from, Node to, EdgeProperties<T> edgeProperties){
        /*add Edge*/
        if(from!=null && to!=null && edgeProperties!=null) {
            Byte zero = 0;
            if (to.equals(source)) {
                source = from;
            }
            if (from.equals(destination)) {
                destination = to;
            }
            if (network.containsKey(from)) {
                if (!network.get(from).containsKey(to)) {
                    network.get(from).put(to, edgeProperties);
                    logger.log(Level.INFO, String.format("Edge {%s} {%s} with capacity {} is added", from.getName(), to.getName(), edgeProperties.toString()));
                }
            } else {
                network.put(from, new HashMap<Node, EdgeProperties<T>>());
                network.get(from).put(to, edgeProperties);
                logger.log(Level.INFO, String.format("Edge {%s} {%s} with capacity {} is added", from.getName(), to.getName(), edgeProperties.toString()));
            }
            /*add reverse Edge*/
            if (reverseNetwork.containsKey(to)) {
                if (!reverseNetwork.get(to).containsKey(from)) {
                    reverseNetwork.get(to).put(from, /*edgeProperties.copy()*/ new EdgeProperties<T>((T)zero, (T)zero));
                }
            } else {
                reverseNetwork.put(to, new HashMap<Node, EdgeProperties<T>>());
                reverseNetwork.get(to).put(from, /*edgeProperties.copy()*/new EdgeProperties<T>((T)zero, (T)zero));
            }
        }
    }

    public void deleteEdge(Node from, Node to){
        /*delete edge*/
        if(network.containsKey(from)){
            network.get(from).remove(to);
            if(network.get(from).size() == 0){ //если других рёбер больше нет
                network.remove(from);
            }
        }
        else {logger.log(Level.INFO, String.format("No Edge {%s} {%s}", from.getName(), to.getName()));}
        if(reverseNetwork.containsKey(to)){
            reverseNetwork.get(to).remove(from);
            if(reverseNetwork.get(to).size() == 0){
                reverseNetwork.remove(to);
            }
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

    public Node getSource(){
        return source;
    }

    public Node getDestination(){
        return destination;
    }

    public ResidualNetwork<T> copy(){
        return  new ResidualNetwork<T>(source, destination, network, reverseNetwork, heights, surpluses);
    }

    public void printNetwork(){
        for (Node from : network.keySet()) {
            for (Node to : network.get(from).keySet()) {
                System.out.println(from.getName()+" "+to.getName()+" "+network.get(from).get(to).getCapacity()+" "+network.get(from).get(to).getFlow());
            }
        }
        System.out.println("Reverse Edges:");
        for (Node from : reverseNetwork.keySet()) {
            for (Node to : reverseNetwork.get(from).keySet()) {
                System.out.println(from.getName()+" "+to.getName()+" "+reverseNetwork.get(from).get(to).getCapacity()+" "+reverseNetwork.get(from).get(to).getFlow());
            }
        }
        System.out.println();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ResidualNetwork<?> that = (ResidualNetwork<?>) o;
        return Objects.equals(network, that.network) && Objects.equals(reverseNetwork, that.reverseNetwork) && Objects.equals(surpluses, that.surpluses) && Objects.equals(heights, that.heights) && Objects.equals(source, that.source) && Objects.equals(destination, that.destination);
    }

    @Override
    public int hashCode() {
        return Objects.hash(network, reverseNetwork, surpluses, heights, source, destination);
    }
}
