module goldberg.visualizer.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;

    opens leti.practice to javafx.fxml, java.logging;
    opens leti.practice.gui to javafx.fxml;

    exports leti.practice;
    exports  leti.practice.gui;
}