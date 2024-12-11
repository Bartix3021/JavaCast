module com.projectpacks.javaproject {
    requires javafx.controls;
    requires javafx.fxml;
    requires com.google.gson;
    requires javafx.web;
    requires org.jxmapviewer.jxmapviewer2;
    requires javafx.swing;
    requires com.sothawo.mapjfx;

    opens com.projectpacks.backend.models to com.google.gson;
    opens com.projectpacks.backend.input.method to com.google.gson;
    opens com.projectpacks.javaproject to javafx.fxml;
    exports com.projectpacks.javaproject;
    exports com.projectpacks.javaproject.controllers;
    opens com.projectpacks.javaproject.controllers to javafx.fxml;
}