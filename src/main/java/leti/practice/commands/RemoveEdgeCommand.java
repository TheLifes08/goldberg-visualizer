package leti.practice.commands;

import leti.practice.Controller;

public class RemoveEdgeCommand implements Command {
    private final Controller controller;

    RemoveEdgeCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.removeEdge();
    }
}
