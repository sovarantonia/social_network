module com.example.begin1 {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires org.kordamp.bootstrapfx.core;
    requires java.sql;

    opens com.example.begin1 to javafx.fxml;
    opens domain to javafx.base;
    exports com.example.begin1;
    exports controllers;
    opens controllers to javafx.fxml;
}