package com.projectpacks.javaproject;

import com.projectpacks.backend.filesHandling.FileHandler;
import com.projectpacks.backend.forecastStrategies.CurrentStrategy;
import com.projectpacks.backend.forecastStrategies.WeatherStrategy;
import com.projectpacks.backend.objectStructure.WeatherData;
import com.projectpacks.backend.services.WeatherService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.image.Image;
public class TemplateController {
    @FXML
    private AnchorPane rootPane;
    @FXML
    private TextField inp;

    @FXML
    private GridPane gridPane;

    private String[] cities;

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
        cities = FileHandler.ReadFile();
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

    public void generateGrid() {

        // Initialize the GridPane
        gridPane.setHgap(10); // Horizontal gap between columns
        gridPane.setVgap(10); // Vertical gap between rows
        gridPane.maxHeight(150);
        gridPane.minHeight(100);
        // Add header row

        String[] headings = {"City Name", "Search","Delete"};
        for (int i = 0; i < headings.length; i++) {
            Label l = new Label(headings[i]);
            l.setStyle("-fx-text-fill: white; fx-font-weight: bold;");
            gridPane.add(l, i, 0);
        }

        // Populate the grid with city rows
        populateGrid(cities);
    }

    private void populateGrid(String[] cities) {
        for (int i = 0; i < cities.length; i++) {
            addCityRow(cities[i], i + 1);
        }
    }

    private void addCityRow(String cityName, int rowIndex) {
        // Label for the city name
        Label cityLabel = new Label(cityName);
        cityLabel.setStyle("-fx-text-fill: white; fx-font-weight: bold;");

        // "Search" button
        Button searchButton = new Button("Search");
        searchButton.setOnAction(e -> {
            inp.setText(cityName);
            try {
                send();
            } catch (IOException ex) {
                throw new RuntimeException(ex);
            }
        });

        // "Delete" button
        Button deleteButton = new Button("Delete");
        deleteButton.setOnAction(e -> {
            // Remove the row from the grid
            gridPane.getChildren().removeIf(node -> GridPane.getRowIndex(node) == rowIndex);

            // Re-index the rows below the deleted row
            for (int i = rowIndex + 1; i <= gridPane.getRowCount(); i++) {
                for (var node : gridPane.getChildren()) {
                    Integer currentRow = GridPane.getRowIndex(node);
                    if (currentRow != null && currentRow == i) {
                        GridPane.setRowIndex(node, i - 1);
                    }
                }
            }
        });

        // Add components to the grid
        gridPane.add(cityLabel, 0, rowIndex);
        gridPane.add(searchButton, 1, rowIndex);
        gridPane.add(deleteButton, 2, rowIndex);
    }
}