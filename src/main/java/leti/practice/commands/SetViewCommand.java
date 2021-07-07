package leti.practice.commands;

import leti.practice.Controller;
import leti.practice.gui.ViewType;

public class SetViewCommand implements Command {
    private final Controller controller;
    private final ViewType viewType;

    public SetViewCommand(Controller controller, ViewType viewType) {
        this.controller = controller;
        this.viewType = viewType;
    }

    @Override
    public void execute() {
        controller.setView(viewType);
    }
}
