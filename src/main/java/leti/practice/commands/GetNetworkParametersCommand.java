package leti.practice.commands;

import leti.practice.Controller;

public class GetNetworkParametersCommand implements Command {
    private final Controller controller;
    private String networkParameters;

    public GetNetworkParametersCommand(Controller controller) {
        this.controller = controller;
    }

    public String getNetworkParameters() {
        return networkParameters;
    }

    @Override
    public void execute() {
        networkParameters = controller.getNetworkParameters();
    }
}
