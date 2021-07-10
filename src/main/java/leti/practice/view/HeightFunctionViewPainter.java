package leti.practice.view;

import javafx.scene.canvas.GraphicsContext;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.scene.transform.Affine;
import leti.practice.structures.graph.Node;
import leti.practice.structures.graph.ResidualNetwork;

import java.util.HashMap;
import java.util.HashSet;

public class HeightFunctionViewPainter extends ViewPainter {
    private final double heightMargin = 75.0;

    public HeightFunctionViewPainter() {
        nodeViewParameters = new HashMap<>();
    }

    @Override
    public void paint(ResidualNetwork<Double> network) {
        clearCanvas();

        if (network == null || !isCanvasSet()) {
            return;
        }

        calculateNodesParameters(network);

        GraphicsContext gc = getCanvas().getGraphicsContext2D();
        int nodeCount = nodeViewParameters.size();
        double arrowSize = 5;
        double plotX = Math.max((nodeCount + 1) * heightMargin, 5 * heightMargin);
        double plotY = Math.max((2 * nodeCount + 1) * heightMargin, 9 * heightMargin);
        double originalLineWidth = gc.getLineWidth();
        TextAlignment originalTextAlignment = gc.getTextAlign();
        Affine originalAffineTransform = gc.getTransform();
        Font originalFont = gc.getFont();

        expandCanvas(heightMargin + plotX, heightMargin + plotY, heightMargin);

        gc.strokeLine(heightMargin, heightMargin, heightMargin + plotX + arrowSize, heightMargin);
        gc.strokeLine(heightMargin, heightMargin, heightMargin, heightMargin + plotY + arrowSize);
        gc.strokeLine(heightMargin + plotX + arrowSize, heightMargin, heightMargin + plotX, heightMargin - arrowSize);
        gc.strokeLine(heightMargin + plotX + arrowSize, heightMargin, heightMargin + plotX, heightMargin + arrowSize);
        gc.strokeLine(heightMargin, heightMargin + plotY + arrowSize, heightMargin - arrowSize, heightMargin + plotY);
        gc.strokeLine(heightMargin, heightMargin + plotY + arrowSize, heightMargin + arrowSize, heightMargin + plotY);

        gc.setFont(new Font("Arial", 14));
        gc.rotate(270);
        paintText(-150, heightMargin / 2, "NODE HEIGHT");
        gc.setTransform(originalAffineTransform);
        gc.setFont(originalFont);

        gc.setLineWidth(0.5);
        gc.setLineDashes(10);
        gc.setLineDashOffset(10);
        gc.setTextAlign(TextAlignment.LEFT);

        for (int i = 0; i <= Math.max(2 * nodeCount, 8); i++) {
            double y = heightMargin * (1 + i);
            gc.strokeLine(heightMargin, y, heightMargin + plotX, y);
            paintText(heightMargin - 15, y, String.valueOf(i));
        }

        gc.setLineDashes(0);
        gc.setLineWidth(originalLineWidth);
        gc.setTextAlign(originalTextAlignment);

        for (int i = 0; i <= Math.max(2 * nodeCount, 8); i++) {
            double y = heightMargin * (1 + i);
            gc.strokeLine(heightMargin - 3, y, heightMargin + 3, y);
        }

        paintNodes(network);
    }

    @Override
    protected void calculateNodesParameters(ResidualNetwork<Double> network) {
        nodeViewParameters.clear();

        int nodeIndex = 0;
        HashSet<Node> nodes = new HashSet<>(network.getNetworkNodes());
        nodes.addAll(network.getReverseNetworkNodes());

        for (Node node : nodes) {
            Integer height = network.getHeights().get(node);

            if (height == null) {
                height = 0;
            }

            nodeViewParameters.put(node, new NodeViewParameters(node,
                    heightMargin * (nodeIndex + 2),
                    heightMargin * (height + 1), nodeIndex));
            nodeIndex++;
        }
    }
}
