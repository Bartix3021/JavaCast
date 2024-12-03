package com.projectpacks.javaproject;

import com.projectpacks.backend.forecastStrategies.CurrentStrategy;
import com.projectpacks.backend.forecastStrategies.WeatherStrategy;
import com.projectpacks.backend.objectStructure.WeatherData;
import com.projectpacks.backend.services.WeatherService;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

public class HelloController {
    @FXML
    private Label welcomeText;
    public TextField inp;

    @FXML
    protected void onHelloButtonClick() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("new_scene.fxml"));
        Parent root = loader.load();

        // Get controller of Scene2
        NewScene scene2Controller = loader.getController();

        // Pass data to Scene2
        String textValue = inp.getText();
        scene2Controller.setLabelText(textValue);

        // Switch scenes
        Stage stage = (Stage) inp.getScene().getWindow();
        stage.setScene(new Scene(root));


    }
}