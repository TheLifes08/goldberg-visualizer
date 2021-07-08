package leti.practice.commands;

import leti.practice.Controller;

public class RemoveEdgeCommand implements Command {
    private final Controller controller;
    private String source, destination;

    public RemoveEdgeCommand(Controller controller) {
        this.controller = controller;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setDestination(String destination) {
        this.destination = destination;
    }

    @Override
    public boolean execute() {
        if (controller != null) {
            controller.removeEdge(source, destination);
            return true;
        } else {
            return false;
        }
    }
}
