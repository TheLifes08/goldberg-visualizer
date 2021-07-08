module goldberg.visualizer.main {
    requires org.junit.jupiter.api;
    requires javafx.controls;
    requires java.logging;
    requires javafx.fxml;
    requires junit;

    opens leti.practice to javafx.fxml, java.logging, junit;
    opens leti.practice.gui to javafx.fxml;
    opens leti.practice.algorithm to junit;
    opens leti.practice.structures.graph to junit;

    exports leti.practice;
    exports leti.practice.gui;
    exports leti.practice.algorithm;
    exports leti.practice.structures.graph;
}