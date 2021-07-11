package leti.practice.commands;

import leti.practice.Controller;

public class ResetCommand implements Command {
    private final Controller controller;

    public ResetCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public boolean execute() {
        if (controller != null) {
            controller.resetAlgorithm();
            return true;
        } else {
            return false;
        }
    }
}
