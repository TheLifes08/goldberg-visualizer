package leti.practice.commands;

import leti.practice.Controller;

public class SetSourceAndDestinationCommand implements Command {
    private final Controller controller;
    private String source;
    private String destination;

    public SetSourceAndDestinationCommand(Controller controller) {
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
            controller.setSourceAndDestination(source, destination);
            return true;
        } else {
            return false;
        }
    }
}
