package leti.practice.commands;

import leti.practice.Controller;

public class GetViewCommand implements Command {
    private final Controller controller;

    GetViewCommand(Controller controller) {
        this.controller = controller;
    }

    @Override
    public void execute() {
        controller.getView();
    }
}
