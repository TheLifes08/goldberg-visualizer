package leti.practice.commands;

import leti.practice.Controller;

import java.io.File;

public class LoadNetworkCommand implements Command {
    private final Controller controller;
    private File file;

    public LoadNetworkCommand(Controller controller) {
        this.controller = controller;
    }

    public void setFile(File file) {
        this.file = file;
    }

    @Override
    public void execute() {
        controller.loadNetwork(file);;
    }
}
