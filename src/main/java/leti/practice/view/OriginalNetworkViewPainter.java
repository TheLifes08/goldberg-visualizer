package leti.practice.view;

import leti.practice.structures.graph.ResidualNetwork;

import java.util.ArrayList;
import java.util.HashMap;

public class OriginalNetworkViewPainter extends NetworkViewPainter {
    public OriginalNetworkViewPainter() {
        nodeViewParameters = new HashMap<>();
        layersNodeCount = new ArrayList<Integer>();
    }

    @Override
    public void paint(ResidualNetwork<Double> network) {
        clearCanvas();

        if (network == null) {
            return;
        }

        if (needRecalculateNodesParameters) {
            layersNodeCount.ensureCapacity(network.getNetworkNodes().size());
            calculateNodesParameters(network);
            layersNodeCount.clear();
        }

        paintEdges(network, false);
        paintNodes(network);
    }
}
