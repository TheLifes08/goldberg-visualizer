package leti.practice.view;

import leti.practice.structures.graph.ResidualNetwork;

public class NetworkViewPainter extends ResidualNetworkViewPainter {
    @Override
    public void paint(ResidualNetwork<Double> network) {
        clearCanvas();

        this.network = network;

        if (needRecalculateNodesParameters) {
            layersNodeCount.ensureCapacity(network.getNetworkNodes().size());
            getNodesParameters(network.getSource());
            layersNodeCount.clear();
        }

        paintEdges(false);
        paintNodes();
    }
}
