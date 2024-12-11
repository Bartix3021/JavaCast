package com.projectpacks.javaproject.controllers;

import com.projectpacks.backend.services.FileService;
import com.projectpacks.backend.forecast.method.CurrentForecastMethod;
import com.projectpacks.backend.forecast.method.FiveDayForecastMethod;
import com.projectpacks.backend.forecast.method.TwoDayForecastMethod;
import com.projectpacks.backend.input.method.CityNameMethod;
import com.projectpacks.backend.input.method.IPMethod;
import com.projectpacks.backend.models.Weather;
import com.projectpacks.backend.models.WeatherData;
import com.projectpacks.backend.observers.PeriodicalWeatherDataObserver;
import com.projectpacks.backend.services.PeriodicalWeatherService;
import com.projectpacks.backend.services.WeatherService;
import com.projectpacks.backend.util.UnixToDate;
import com.projectpacks.javaproject.App;
import javafx.application.Platform;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import java.io.IOException;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.List;

public class CityViewController implements PeriodicalWeatherDataObserver {
    @FXML
    public Label info;
    public ImageView icon;
    public Label temp;
    public Label feelsLike;
    public Label weatherName;
    public Label wind;
    public Label clouds;
    public Label rain;
    public Label humidity;
    public Label pressure;
    public Label visibility;
    public Button hourly;
    public Button daily;
    public Label updated;

    private final WeatherService weatherService;
    private String city;
    private final PeriodicalWeatherService periodicalService;

    public CityViewController() {


        this.weatherService = new WeatherService();
        weatherService.setWeatherStrategy(new CurrentForecastMethod());
        periodicalService = new PeriodicalWeatherService(weatherService);
        periodicalService.addObserver(this);

    }

    public void setLabelText(String textValue) {
        weatherService.setInputStrategy(new CityNameMethod());
        city = textValue;
        WeatherData[] d = weatherService.getDataByInput(textValue);
        WeatherData element = d[0];
        info.setText("Current weather in: " + textValue);
        periodicalService.startUpdatingWeather(textValue);
    }



    public void SetLabelToCity() {
        weatherService.setInputStrategy(new IPMethod());
        WeatherData[] d = weatherService.getDataByInput("");
        WeatherData element = d[0];
        city = element.getName();
        info.setText("Current weather in: " + element.getName());
        periodicalService.startUpdatingWeather(element.getName());
    }


    public void renderForecast(ActionEvent actionEvent) throws IOException {
        int step = 0;
        String format = "";

        if (actionEvent.getSource() == hourly) {
            weatherService.setWeatherStrategy(new TwoDayForecastMethod());
            step = 1;
            format = "dd.MM.yyyy HH:mm";
        } else {
            weatherService.setWeatherStrategy(new FiveDayForecastMethod());
            step = 5;
            format = "dd.MM.yyyy HH:mm";
        }

        FXMLLoader loader = new FXMLLoader(App.class.getResource("forecast-table-view.fxml"));
        Parent root = loader.load();

        ForecastTableController scene2Controller = loader.getController();
        scene2Controller.presentData(weatherService, city, step, format);

        Stage stage = new Stage();
        scene2Controller.setStage(stage);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void goBack(ActionEvent actionEvent) {
        try {
            periodicalService.setEndFlag();
            periodicalService.removeObserver(this);
            FXMLLoader loader = new FXMLLoader(App.class.getResource("main-view.fxml"));
            Parent root = loader.load();
            MainViewController controller = loader.getController();
            controller.resetInput();
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(WeatherData[] forecast) {
        WeatherData element = forecast[0];
        if (element == null) return;
        Weather ic = element.getWeather().get(0);
        Platform.runLater(() -> {
            temp.setText(element.getMain().getTemp() + "°C");
            feelsLike.setText("Feels like: " + element.getMain().getFeelsLike() + "°C");
            weatherName.setText(ic.getMain() + ": " + ic.getDescription());
            wind.setText(element.getWind().getSpeed() + " km/h");
            clouds.setText(element.getClouds().getAll() + "% of the sky");
            if (element.getRain() == null) {
                rain.setText("no rain");
            } else {
                rain.setText(element.getRain().getAmount() + " mm/h");
            }
            pressure.setText(element.getMain().getPressure() + "hPa");
            humidity.setText(element.getMain().getHumidity() + "%");
            visibility.setText(element.getVisibility() / 1000 + "km");
            Image i  = new Image("https://openweathermap.org/img/wn/"+ ic.getIcon() +".png", true);
            icon.setImage(i);
            LocalTime currentTime = LocalTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("HH:mm");
            updated.setText("Updated: " + currentTime.format(formatter));
        });

    }

    public void bookmark(ActionEvent actionEvent) {
        String[] cities = FileService.ReadFile();

        List<String> output = new ArrayList<>();
        Collections.addAll(output, cities);

        String message = "";
        if (output.contains(city)) {
            output.remove(city);
            message = city + " has been removed from pinned locations";
        } else {
            output.add(city);
            message = city + " has been added to the pinned locations";
        }
        FileService.UpdateFile(output.toString());
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle("Bookmarks");
        alert.setHeaderText(message);
        alert.showAndWait();
    }
}
