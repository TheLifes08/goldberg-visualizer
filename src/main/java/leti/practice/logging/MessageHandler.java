package leti.practice.logging;

import leti.practice.gui.MainWindowController;

import java.util.logging.ConsoleHandler;
import java.util.logging.LogRecord;

public class MessageHandler extends ConsoleHandler {
    MainWindowController mainWindowController;

    public void setMainWindowController(MainWindowController mainWindowController) {
        this.mainWindowController = mainWindowController;
    }

    @Override
    public void publish(LogRecord record) {
        super.publish(record);
        if (mainWindowController != null) {
            mainWindowController.printMessageToConsole(record.getMessage());
        }
    }
}
