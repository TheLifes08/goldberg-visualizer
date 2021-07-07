package leti.practice.view;

import leti.practice.structures.graph.EdgeProperties;
import leti.practice.structures.graph.Node;
import leti.practice.structures.graph.ResidualNetwork;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.logging.Level;
import java.util.logging.Logger;

public class ResidualNetworkViewPainter extends ViewPainter {
    private static final Logger logger = Logger.getLogger(NetworkViewPainter.class.getName());
    protected ArrayList<Integer> layersNodeCount;
    protected ResidualNetwork<Double> network;
    private final double margin = 200;

    public ResidualNetworkViewPainter() {
        nodeViewParameters = new HashMap<>();
        layersNodeCount = new ArrayList<Integer>();
    }

    @Override
    public void paint(ResidualNetwork<Double> network) {
        clearCanvas();

        this.network = network;

        if (needRecalculateNodesParameters) {
            layersNodeCount.ensureCapacity(network.getNetworkNodes().size());
            getNodesParameters(network.getSource());
            layersNodeCount.clear();
            needRecalculateNodesParameters = false;
        }

        paintEdges(true);
        paintNodes();
    }

    protected void getNodesParameters(Node startNode) {
        HashSet<Node> undrawnNodes = new HashSet<Node>();
        undrawnNodes.addAll(network.getNetworkNodes());
        undrawnNodes.addAll(network.getReverseNetworkNodes());

        while (!undrawnNodes.isEmpty()) {
            int layer = 0;
            NodeViewParameters currentNode = new NodeViewParameters(startNode, margin, margin, layer);
            ArrayDeque<NodeViewParameters> frontier = new ArrayDeque<>(100);

            frontier.offer(currentNode);
            nodeViewParameters.put(currentNode.node, currentNode);
            undrawnNodes.remove(currentNode.node);

            while (!frontier.isEmpty()) {
                currentNode = frontier.poll();
                layer = currentNode.layer;

                if (layersNodeCount.size() <= layer) {
                    layersNodeCount.add(0);
                }

                currentNode.x = margin * (layer + 1);
                currentNode.y = margin * (layersNodeCount.get(layer) + 1);
                layersNodeCount.set(layer, layersNodeCount.get(layer) + 1);

                HashMap<Node, EdgeProperties<Double>> edges = network.getNetworkEdges(currentNode.node);

                if (edges != null) {
                    for (Node node : edges.keySet()) {
                        if (undrawnNodes.contains(node)) {
                            NodeViewParameters params = new NodeViewParameters(node, 0, 0, layer + 1);
                            frontier.offer(params);
                            nodeViewParameters.put(node, params);
                            undrawnNodes.remove(node);
                        }
                    }
                }
            }

            boolean fitNodeFound = false;

            for (Node node : undrawnNodes) {
                HashMap<Node, EdgeProperties<Double>> edges = network.getNetworkEdges(node);

                if (edges != null && edges.size() > 0) {
                    startNode = node;
                    fitNodeFound = true;
                    break;
                }
            }

            if (!fitNodeFound) {
                for (Node node : undrawnNodes) {
                    startNode = node;
                    break;
                }
            }
        }
    }

    protected void paintEdges(boolean paintReverseEdges) {
        for (Node source : network.getNetworkNodes()) {
            HashMap<Node, EdgeProperties<Double>> edges = network.getNetworkEdges(source);

            if (edges != null) {
                for (Node destination : edges.keySet()) {
                    NodeViewParameters sourceParams = nodeViewParameters.get(source);
                    NodeViewParameters destParams = nodeViewParameters.get(destination);
                    EdgeProperties<Double> edgeParams = edges.get(destination);
                    String info = edgeParams.getFlow() + "/" + edgeParams.getCapacity();
                    paintEdge(sourceParams.x, sourceParams.y, destParams.x, destParams.y, info, LineType.STRAIGHT);
                }
            }
        }

        if (!paintReverseEdges) {
            return;
        }

        for (Node source : network.getReverseNetworkNodes()) {
            HashMap<Node, EdgeProperties<Double>> edges = network.getReverseNetworkEdges(source);

            if (edges != null) {
                for (Node destination : edges.keySet()) {
                    NodeViewParameters sourceParams = nodeViewParameters.get(source);
                    NodeViewParameters destParams = nodeViewParameters.get(destination);
                    EdgeProperties<Double> edgeParams = edges.get(destination);
                    String info = edgeParams.getFlow() + "/" + edgeParams.getCapacity();
                    paintEdge(sourceParams.x, sourceParams.y, destParams.x, destParams.y, info, LineType.ARC);
                }
            }
        }
    }

    protected void paintNodes() {
        for (NodeViewParameters parameters : nodeViewParameters.values()) {
            paintNode(parameters.x, parameters.y, parameters.node.getName());
        }
    }
}
