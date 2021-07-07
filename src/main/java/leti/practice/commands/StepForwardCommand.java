package leti.practice.commands;

import leti.practice.Controller;

public class StepForwardCommand implements Command {
    private final Controller controller;
    private boolean result;

    public StepForwardCommand(Controller controller) {
        this.controller = controller;
    }

    public boolean getResult() {
        return result;
    }

    @Override
    public void execute() {
        result = controller.stepForward();
    }
}
