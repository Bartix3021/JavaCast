package com.projectpacks.javaproject.controllers;

import com.projectpacks.backend.util.UnixToDate;
import com.projectpacks.backend.services.WeatherService;
import com.projectpacks.backend.models.WeatherData;
import com.projectpacks.javaproject.App;
import com.projectpacks.javaproject.AppController;
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
    private GridPane gridPane;

    @FXML
    private AnchorPane primaryStage;

    private Stage stage;
    private WeatherData[] graphData;
    private List<String> datesLabels = new ArrayList<>();
    private List<Double> tempValues = new ArrayList<>();
    private String city;

    public void setStage(Stage stage) {
        this.stage = stage;
    }

    public void presentData(String place, Integer limit, String dataFormat) {
        WeatherData[] data = AppController.getInstance().getWeatherForecast(place);
        city = place;
        setGrid(data, limit, dataFormat);
    }

    public void setGrid(WeatherData[] forecast, Integer step, String dateformat) {
        graphData = forecast;
        gridPane.getChildren().clear();
        gridPane.getColumnConstraints().clear();

        int x = 0;
        for (int i = 0; i < forecast.length; i += step) {
            if (x < 8) {
                WeatherData d = forecast[i];
                datesLabels.add(AppController.getInstance().convertUnixTimestampToDate(d.getDt(), dateformat));
                tempValues.add(d.getMain().getTemp());
                VBox vbox = VisualisationSetup.createWeatherVBox(d, dateformat);
                if (x %2 == 0) {
                    vbox.setStyle("-fx-background-color: #FBE2D5; -fx-padding: 10; -fx-border-color: black;");
                } else {
                    vbox.setStyle("-fx-background-color: #D3D3D3; -fx-padding: 10; -fx-border-color: black;");
                }
                x++;


                gridPane.add(vbox, i, 0);

                GridPane.setHgrow(vbox, Priority.ALWAYS);
            }
        }


        if (stage != null) {
            Scene scene = new Scene(primaryStage, 400, 400);
            stage.setScene(scene);
            stage.setTitle("GridPane with Evenly Spaced VBoxes");
            stage.show();
        }
    }

    public void createGraph(ActionEvent actionEvent) throws IOException {

        CategoryAxis xAxis = new CategoryAxis();
        xAxis.setLabel("Date");


        NumberAxis yAxis = new NumberAxis();
        yAxis.setLabel("Temperature [°C]");


        LineChart<String, Number> lineChart = new LineChart<>(xAxis, yAxis);
        lineChart.setTitle("Weather graph of " + city);


        XYChart.Series<String, Number> series = new XYChart.Series<>();
        series.setName("Data Points");


        for (int i = 0; i < datesLabels.size(); i++) {
            series.getData().add(new XYChart.Data<>(datesLabels.get(i), tempValues.get(i)));
        }


        lineChart.getData().add(series);


        try {
            Stage graphStage = new Stage();
            FXMLLoader loader = new FXMLLoader(App.class.getResource("graph-view.fxml"));
            Parent root = loader.load();


            ((javafx.scene.layout.Pane) root).getChildren().add(lineChart);

            graphStage.setScene(new Scene(root));
            graphStage.setTitle("Graph: " + city);
            graphStage.show();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
