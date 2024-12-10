package com.projectpacks.javaproject;

import com.projectpacks.backend.filesHandling.FileHandler;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.Arrays;

public class HelloApplication extends Application {
    @Override
    public void start(Stage stage) throws IOException {
        System.out.println(FileHandler.ReadFile()[0]);
        FXMLLoader fxmlLoader = new FXMLLoader(HelloApplication.class.getResource("temp.fxml"));
        Scene scene = new Scene(fxmlLoader.load());
        stage.setTitle("JavaCast - Your weather in Java");
        stage.setScene(scene);
        stage.show();
    }



    public static void main(String[] args) {
        launch();
    }
}