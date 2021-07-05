package leti.practice.commands;

import leti.practice.Controller;

public class SaveNetworkCommand implements Command {
    private final Controller controller;
    private String path;

    SaveNetworkCommand(Controller controller) {
        this.controller = controller;
    }

    void setPath(String path) {
        this.path = path;
    }

    @Override
    public void execute() {
        controller.saveNetwork(path);;
    }
}
