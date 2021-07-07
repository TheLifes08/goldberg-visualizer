package leti.practice.commands;

import leti.practice.Controller;

public class ClearNetworkCommand implements Command {
    private final Controller controller;

    public ClearNetworkCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.clearNetwork();
    }
}
