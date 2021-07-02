module practice.goldberg {
    requires javafx.controls;
    requires javafx.fxml;
    
    opens practice.goldberg to javafx.fxml;
    exports practice.goldberg;
}
