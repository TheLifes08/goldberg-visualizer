package leti.practice.view;

import leti.practice.structures.graph.Node;

public class NodeViewParameters {
    public Node node;
    public double x, y;
    public int layer;

    NodeViewParameters(Node node, double x, double y, int layer) {
        this.node = node;
        this.x = x;
        this.y = y;
        this.layer = layer;
    }
}
