package leti.practice;

public class Launcher {
    public static void main(String[] args) {
        boolean isConsoleLineInterface = false;

        for (String arg : args) {
            if (arg.equals("-cli")) {
                isConsoleLineInterface = true;
                break;
            }
        }

        if (isConsoleLineInterface) {
            CLIController cliController = new CLIController(args);
            cliController.startCli();
        } else {
            App.main(args);
        }
    }

}
