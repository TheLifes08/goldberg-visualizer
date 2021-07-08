package leti.practice.view;

import javafx.scene.paint.Color;
import leti.practice.structures.graph.EdgeProperties;
import leti.practice.structures.graph.Node;
import leti.practice.structures.graph.ResidualNetwork;

import java.util.*;

public abstract class NetworkViewPainter extends ViewPainter {
    protected ArrayList<Integer> layersNodeCount;
    private final double margin = 200;

    @Override
    protected void getNodesParameters(ResidualNetwork<Double> network) {
        nodeViewParameters.clear();

        if (network == null || network.getSource() == null) {
            return;
        }

        Node startNode = network.getSource();
        HashSet<Node> undrawnNodes = new HashSet<>();
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

    protected void paintEdges(ResidualNetwork<Double> network, boolean paintReverseEdges) {
        for (Node source : network.getNetworkNodes()) {
            HashMap<Node, EdgeProperties<Double>> edges = network.getNetworkEdges(source);

            if (edges != null) {
                for (Node destination : edges.keySet()) {
                    NodeViewParameters sourceParams = nodeViewParameters.get(source);
                    NodeViewParameters destParams = nodeViewParameters.get(destination);
                    EdgeProperties<Double> edgeParams = edges.get(destination);
                    String info = edgeParams.getFlow() + "/" + edgeParams.getCapacity();
                    if (edgeParams.getFlow() > 0) {
                        paintEdge(sourceParams.x, sourceParams.y, destParams.x, destParams.y, info,
                                LineType.STRAIGHT, Color.BLUE);
                    } else {
                        paintEdge(sourceParams.x, sourceParams.y, destParams.x, destParams.y, info, LineType.STRAIGHT);
                    }
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

    protected void paintNodes(ResidualNetwork<Double> network) {
        for (NodeViewParameters parameters : nodeViewParameters.values()) {
            if (network.getSurpluses().get(parameters.node) > 0) {
                paintNode(parameters.x, parameters.y, parameters.node.getName(), Color.BLUE);
            } else {
                paintNode(parameters.x, parameters.y, parameters.node.getName());
            }
        }
    }
}
