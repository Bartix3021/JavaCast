package com.projectpacks.javaproject;

import com.projectpacks.backend.UnixToDate;
import com.projectpacks.backend.services.WeatherService;
import com.projectpacks.backend.objectStructure.WeatherData;
import javafx.fxml.FXML;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Arrays;

public class ForecastDisplay {
    @FXML
    private GridPane gridPane; // Injected from FXML

    @FXML
    private AnchorPane primaryStage; // Injected from FXML

    private Stage stage; // JavaFX stage

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void presentData(WeatherService weatherService, String place) {
        WeatherData[] data = weatherService.getWeatherForecast(place);
        System.out.println("Forecast for " + place + " " + Arrays.toString(data));
        setGrid(data); // Set grid dynamically based on fetched data
    }

    public void setGrid(WeatherData[] forecast) {
        gridPane.getChildren().clear(); // Clear existing content
        gridPane.getColumnConstraints().clear(); // Clear column constraints

        // Add 6 VBox columns dynamically
        int x = 0;
        for (int i = 0; i < forecast.length; i += 5) {
            WeatherData d = forecast[i];
            VBox vbox = VisualisationSetup.createWeatherVBox(d);
            if (x %2 == 0) {
                vbox.setStyle("-fx-background-color: #FBE2D5; -fx-padding: 10; -fx-border-color: black;");
            } else {
                vbox.setStyle("-fx-background-color: #D3D3D3; -fx-padding: 10; -fx-border-color: black;");
            }
            x++;

            // Add VBox to the GridPane
            gridPane.add(vbox, i, 0);

            // Ensure VBox expands to fill its column
            GridPane.setHgrow(vbox, Priority.ALWAYS);
        }


        if (stage != null) {
            Scene scene = new Scene(primaryStage, 400, 400); // Dynamic stage update
            stage.setScene(scene);
            stage.setTitle("GridPane with Evenly Spaced VBoxes");
            stage.show();
        }
    }
}
