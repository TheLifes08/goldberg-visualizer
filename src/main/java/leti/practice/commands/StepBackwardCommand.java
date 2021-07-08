package leti.practice.commands;

import leti.practice.Controller;

public class StepBackwardCommand implements Command {
    private final Controller controller;

    public StepBackwardCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public boolean execute() {
        if (controller != null) {
            return controller.stepBackward();
        } else {
            return false;
        }
    }
}
