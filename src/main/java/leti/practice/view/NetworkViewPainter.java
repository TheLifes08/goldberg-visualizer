package leti.practice.view;

import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import leti.practice.structures.graph.Node;
import leti.practice.structures.graph.ResidualNetwork;

public class NetworkViewPainter extends ViewPainter {
    double margin = 100;

    @Override
    public void paint(ResidualNetwork<? extends Number> network) {
        Canvas canvas = getCanvas();
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

        double y = 100;

        for (Node node : network.getNetworkNodes()) {
            paintNode(100, y, node.getName());
            y += margin;
        }
    }
}
