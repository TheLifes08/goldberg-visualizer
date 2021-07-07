package leti.practice.view;

import javafx.geometry.VPos;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.text.TextAlignment;
import leti.practice.structures.graph.ResidualNetwork;

public abstract class ViewPainter {
    private Canvas canvas;
    private final double nodeSize = 25;

    public abstract void paint(ResidualNetwork<? extends Number> network);

    public void setCanvas(Canvas canvas) {
        this.canvas = canvas;
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(2);
        gc.setTextAlign(TextAlignment.CENTER);
        gc.setTextBaseline(VPos.CENTER);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void paintNode(double x, double y, String name) {
        expandCanvas(x, y, nodeSize);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        gc.strokeOval(x - nodeSize / 2, y - nodeSize / 2, nodeSize, nodeSize);
        gc.setFill(Color.WHITE);
        gc.fillOval(x - nodeSize / 2 + 1, y - nodeSize / 2 + 1, nodeSize - 2, nodeSize - 2);
        gc.setFill(Color.BLACK);
        gc.fillText(name, x, y);
    }

    public void paintEdge(double sx, double sy, double dx, double dy, String info, LineType lineType) {
        expandCanvas(Math.max(sx, dx), Math.max(sy, dy), 0);

        GraphicsContext gc = canvas.getGraphicsContext2D();

        double h = nodeSize / 4;
        double D_X = Math.abs(dx - sx);
        double D_Y = Math.abs(dy - sy);
        double D = Math.sqrt(D_X * D_X + D_Y * D_Y);
        double z1 = (dy > sy)? -1 : 1;
        double z2 = (sx > dx)? -1 : 1;
        double d_x = z1 * D_Y * h / D;
        double d_y = z2 * D_X * h / D;

        double new_sx = sx + d_x;
        double new_dx = dx + d_x;
        double new_sy = sy + d_y;
        double new_dy = dy + d_y;

        double center_x = (Math.max(new_dx, new_sx) + Math.min(new_dx, new_sx)) / 2;
        double center_y = (Math.max(new_dy, new_sy) + Math.min(new_dy, new_sy)) / 2;

        double arcCurvature = 4;

        if (lineType == LineType.STRAIGHT) {
            gc.strokeLine(new_sx, new_sy, new_dx, new_dy);
            gc.strokeLine(center_x, center_y, center_x + d_x - d_y, center_y + d_y + d_x);
            gc.strokeLine(center_x, center_y, center_x - d_x - d_y, center_y - d_y + d_x);
            gc.setFill(Color.BLUE);
            gc.fillText(info, center_x + d_x, center_y + d_y);
            gc.setFill(Color.BLACK);
        } else if (lineType == LineType.ARC) {
            gc.setLineDashes(5);
            gc.beginPath();
            gc.moveTo(new_sx, new_sy);
            gc.quadraticCurveTo(center_x + d_x * arcCurvature, center_y + d_y * arcCurvature, new_dx, new_dy);
            gc.stroke();
            gc.setLineDashes(0);
            paintText(center_x + d_x * (arcCurvature - 1), center_y + d_y * (arcCurvature - 1), info, Color.BLUE);
        }
    }

    public void paintText(double x, double y, String text, Paint paint) {
        GraphicsContext gc = canvas.getGraphicsContext2D();
        Paint originalPaint = gc.getFill();
        gc.setFill(paint);
        gc.fillText(text, x, y);
        gc.setFill(originalPaint);
    }

    public void expandCanvas(double x, double y, double size) {
        double additionalMargin = 20;

        if (canvas.getWidth() - (x + size) < 0) {
            canvas.setWidth(x + size + additionalMargin);
        }

        if (canvas.getHeight() - (y + size) < 0) {
            canvas.setHeight(y + size + additionalMargin);
        }
    }
}
