package leti.practice.commands;

import leti.practice.Controller;

public class StepForwardCommand implements Command {
    private final Controller controller;

    public StepForwardCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public boolean execute() {
        if (controller != null) {
            return controller.stepForward();
        } else {
            return false;
        }
    }
}
