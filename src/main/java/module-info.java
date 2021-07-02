module leti.practice {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens leti.practice to javafx.fxml;
    exports leti.practice;
}
