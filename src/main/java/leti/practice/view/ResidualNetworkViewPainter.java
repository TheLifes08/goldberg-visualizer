package leti.practice.view;

import leti.practice.structures.graph.ResidualNetwork;

import java.util.ArrayList;
import java.util.HashMap;

public class ResidualNetworkViewPainter extends NetworkViewPainter {
    public ResidualNetworkViewPainter() {
        nodeViewParameters = new HashMap<>();
        layersNodeCount = new ArrayList<Integer>();
    }

    @Override
    public void paint(ResidualNetwork<Double> network) {
        clearCanvas();

        if (needRecalculateNodesParameters) {
            layersNodeCount.ensureCapacity(network.getNetworkNodes().size());
            getNodesParameters(network);
            layersNodeCount.clear();
            needRecalculateNodesParameters = false;
        }

        paintEdges(network, true);
        paintNodes(network);
    }
}
