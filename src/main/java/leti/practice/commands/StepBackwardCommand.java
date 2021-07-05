package leti.practice.commands;

import leti.practice.Controller;

public class StepBackwardCommand implements Command {
    private final Controller controller;

    StepBackwardCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.stepBackward();
    }
}
