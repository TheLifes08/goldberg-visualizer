module goldberg.visualizer.main {
    requires javafx.controls;
    requires javafx.fxml;
    requires java.logging;
    requires junit;
    requires org.junit.jupiter.api;
    opens leti.practice to javafx.fxml, java.logging, junit;
    opens leti.practice.structures.graph to junit;
    opens leti.practice.algorithm to junit;
    opens leti.practice.gui to javafx.fxml;

    exports leti.practice;
    exports  leti.practice.gui;
}