package leti.practice.commands;

import leti.practice.Controller;

public class CheckSourceAndDestinationCommand implements Command {
    private final Controller controller;

    public CheckSourceAndDestinationCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public boolean execute() {
        if (controller != null) {
            return controller.isSourceAndDestinationCorrect();
        } else {
            return false;
        }
    }
}
