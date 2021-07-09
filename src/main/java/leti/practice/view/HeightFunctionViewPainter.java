package leti.practice.view;

import leti.practice.structures.graph.ResidualNetwork;

public class HeightFunctionViewPainter extends ViewPainter {
    public HeightFunctionViewPainter() {}

    @Override
    public void paint(ResidualNetwork<Double> network) {
        clearCanvas();

        if (network == null) {
            return;
        }
    }

    @Override
    protected void calculateNodesParameters(ResidualNetwork<Double> network) {
        nodeViewParameters.clear();
    }
}
