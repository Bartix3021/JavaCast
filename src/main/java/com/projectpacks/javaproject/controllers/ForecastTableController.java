package com.projectpacks.javaproject.controllers;

import com.projectpacks.backend.util.UnixToDate;
import com.projectpacks.backend.services.WeatherService;
import com.projectpacks.backend.models.WeatherData;
import com.projectpacks.javaproject.App;
import com.projectpacks.javaproject.VisualisationSetup;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.chart.CategoryAxis;
import javafx.scene.chart.LineChart;
import javafx.scene.chart.NumberAxis;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class ForecastTableController {
    @FXML
    private GridPane gridPane; // Injected from FXML

    @FXML
    private AnchorPane primaryStage; // Injected from FXML

    private Stage stage; // JavaFX stage
    private WeatherData[] graphData;
    private List<String> datesLabels = new ArrayList<>();
    private List<Double> tempValues = new ArrayList<>();
    private String city;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void presentData(WeatherService weatherService, String place, Integer limit, String dataFormat) {
        WeatherData[] data = weatherService.getWeatherForecast(place);
        city = place;
        System.out.println("Forecast for " + place + " " + Arrays.toString(data));
        setGrid(data, limit, dataFormat); // Set grid dynamically based on fetched data
    }

    public void setGrid(WeatherData[] forecast, Integer step, String dateformat) {
        graphData = forecast;
        gridPane.getChildren().clear(); // Clear existing content
        gridPane.getColumnConstraints().clear(); // Clear column constraints

        // Add 6 VBox columns dynamically
        int x = 0;
        for (int i = 0; i < forecast.length; i += step) {
            if (x < 8) {
                WeatherData d = forecast[i];
                datesLabels.add(UnixToDate.convertUnixTimestampToDate(d.getDt(), dateformat));
                tempValues.add(d.getMain().getTemp());
                VBox vbox = VisualisationSetup.createWeatherVBox(d, dateformat);
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
        }


        if (stage != null) {
            Scene scene = new Scene(primaryStage, 400, 400); // Dynamic stage update
            stage.setScene(scene);
            stage.setTitle("GridPane with Evenly Spaced VBoxes");
            stage.show();
        }
    }

    public void createGraph(ActionEvent actionEvent) throws IOException {
        List<String> dats = datesLabels;
        List<Double> vals = tempValues;

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");

        // Create the Y-axis (NumberAxis for numeric values)
        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Temperature [°C]");

        // Create the LineChart
        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Weather graph of " + city);

        // Create a series to hold data
        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Data Points");

        // Add data points to the series
        for (int i = 0; i < datesLabels.size(); i++) {
            series.getData().add(new XYChart.Data<>(datesLabels.get(i), tempValues.get(i)));
        }

        // Add series to the chart
        lineChart.getData().add(series);

        // Load FXML (optional, if graph-view.fxml is part of your layout)
        try {
            Stage graphStage = new Stage();
            FXMLLoader loader = new FXMLLoader(App.class.getResource("graph-view.fxml"));
            Parent root = loader.load();

            // If "graph-view.fxml" includes a placeholder, replace it with the chart dynamically
            ((javafx.scene.layout.Pane) root).getChildren().add(lineChart);

            graphStage.setScene(new Scene(root));
            graphStage.setTitle("Graph: " + city);
            graphStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
