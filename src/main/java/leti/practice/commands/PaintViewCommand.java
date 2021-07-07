package leti.practice.commands;

import javafx.scene.canvas.Canvas;
import leti.practice.Controller;

public class PaintViewCommand implements Command {
    private final Controller controller;
    private Canvas canvas;

    public PaintViewCommand(Controller controller, Canvas canvas) {
        this.controller = controller;
        this.canvas = canvas;
    }

    @Override
    public void execute() {
        controller.paintView(canvas);
    }
}
