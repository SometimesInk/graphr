module ink.ink {
    requires javafx.controls;
    requires javafx.fxml;


    opens ink.ink to javafx.fxml;
    exports ink.ink;
}