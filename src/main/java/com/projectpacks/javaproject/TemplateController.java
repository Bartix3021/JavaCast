package com.projectpacks.javaproject;

import com.projectpacks.backend.forecastStrategies.CurrentStrategy;
import com.projectpacks.backend.forecastStrategies.WeatherStrategy;
import com.projectpacks.backend.objectStructure.WeatherData;
import com.projectpacks.backend.services.WeatherService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundImage;
import javafx.scene.layout.BackgroundRepeat;
import javafx.scene.layout.BackgroundSize;
import javafx.scene.layout.BackgroundPosition;
import javafx.scene.image.Image;
public class TemplateController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField inp;

    @FXML
    public void initialize() {
        // Load the GIF and set it as the background
        Image backgroundImage = new Image("atmo.gif");
        BackgroundImage bgImage = new BackgroundImage(
                backgroundImage,
                BackgroundRepeat.NO_REPEAT,
                BackgroundRepeat.NO_REPEAT,
                BackgroundPosition.DEFAULT,
                new BackgroundSize(100, 100, true, true, false, true)
        );

        rootPane.setBackground(new Background(bgImage));
    }

    public void send() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("new_scene.fxml"));
        Parent root = loader.load();

        // Get controller of Scene2
        NewScene scene2Controller = loader.getController();

        // Pass data to Scene2
        String textValue = inp.getText();
        scene2Controller.setScene(scene2Controller);
        scene2Controller.setLabelText(textValue);
        // Switch scenes
        Stage stage = (Stage) inp.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void ipSend() throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getResource("new_scene.fxml"));
        Parent root = loader.load();

        // Get controller of Scene2
        NewScene scene2Controller = loader.getController();
        // Pass data to Scene2
        String textValue = inp.getText();
        scene2Controller.setScene(scene2Controller);
        scene2Controller.SetLabelToCity();
        // Switch scenes
        Stage stage = (Stage) inp.getScene().getWindow();
        stage.setScene(new Scene(root));
    }

    public void resetInput() {
        inp.setText("");
    }
}