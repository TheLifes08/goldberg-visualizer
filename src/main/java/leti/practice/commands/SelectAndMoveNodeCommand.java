package leti.practice.commands;

import leti.practice.Controller;

public class SelectAndMoveNodeCommand implements Command {
    private final Controller controller;
    private double x, y;

    public SelectAndMoveNodeCommand(Controller controller) {
        this.controller = controller;
    }

    public void setX(double x) {
        this.x = x;
    }

    public void setY(double y) {
        this.y = y;
    }

    @Override
    public boolean execute() {
        if (controller != null) {
            controller.selectAndMoveNetworkNode(x, y);
            return true;
        } else {
            return false;
        }
    }
}
