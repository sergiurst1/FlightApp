module template.template {
    requires javafx.controls;
    requires javafx.fxml;

    requires org.controlsfx.controls;
    requires com.dlsc.formsfx;
    requires java.sql;

    opens com.example.project to javafx.graphics, javafx.fxml, javafx.base;
    opens com.example.project.entities to javafx.graphics, javafx.fxml, javafx.base;
    exports com.example.project;
    exports com.example.project.controller;
    opens com.example.project.controller to javafx.graphics, javafx.fxml, javafx.base;
}