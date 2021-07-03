module goldberg.visualizer.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;

    opens leti.practice to javafx.fxml, java.logging;
    exports leti.practice;
}