package leti.practice.commands;

import leti.practice.Controller;

public class StepForwardCommand implements Command {
    private final Controller controller;
    private StepForwardResult result;
    private double algorithmResult;

    public StepForwardCommand(Controller controller) {
        this.controller = controller;
    }

    public StepForwardResult getResult() {
        return result;
    }

    public double getAlgorithmResult() {
        return algorithmResult;
    }

    @Override
    public boolean execute() {
        if (controller != null) {
            result = controller.stepForward();

            if (result == StepForwardResult.END_ALGORITHM) {
                algorithmResult = controller.getNetworkMaxFlow();
            }

            return result == StepForwardResult.SUCCESS;
        }

        return false;
    }
}
