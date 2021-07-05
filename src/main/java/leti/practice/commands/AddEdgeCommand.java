package leti.practice.commands;

import leti.practice.Controller;

public class AddEdgeCommand implements Command {
    private final Controller controller;

    AddEdgeCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.addEdge();
    }
}
