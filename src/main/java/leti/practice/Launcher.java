package leti.practice;

public class Launcher {
    public static void main(String[] args) {
        boolean isCLI = false;

        for (String arg : args) {
            if (arg.equals("-cli")) {
                isCLI = true;
            }
        }

        if (isCLI) {
            System.out.println("CLI!");
        } else {
            App.main(args);
        }
    }
}
