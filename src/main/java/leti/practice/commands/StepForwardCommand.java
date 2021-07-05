package leti.practice.commands;

import leti.practice.Controller;

public class StepForwardCommand implements Command {
    private final Controller controller;

    StepForwardCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.stepForward();
    }
}
