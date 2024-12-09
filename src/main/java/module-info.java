module com.projectpacks.javaproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.web;
    requires java.desktop;

    opens com.projectpacks.backend.objectStructure to com.google.gson;
    opens com.projectpacks.backend.inputStrategy to com.google.gson;
    opens com.projectpacks.javaproject to javafx.fxml;
    exports com.projectpacks.javaproject;
}