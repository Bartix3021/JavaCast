package com.projectpacks.javaproject.controllers;

import com.projectpacks.backend.services.FileService;
import com.projectpacks.backend.forecast.method.CurrentForecastMethod;
import com.projectpacks.backend.forecast.method.FiveDayForecastMethod;
import com.projectpacks.backend.forecast.method.TwoDayForecastMethod;
import com.projectpacks.backend.input.method.CityNameStrategy;
import com.projectpacks.backend.input.method.IpStrategy;
import com.projectpacks.backend.models.Weather;
import com.projectpacks.backend.models.WeatherData;
import com.projectpacks.backend.observers.PeriodicalWeatherDataObserver;
import com.projectpacks.backend.services.PeriodicalWeatherService;
import com.projectpacks.backend.services.WeatherService;
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
import java.util.ArrayList;
import java.util.Collections;
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
    public CityViewController n;

    private WeatherService wS;
    private String city;
    private PeriodicalWeatherService weatherController;

    public CityViewController() {
        info = new Label();
        temp = new Label();
        icon = new ImageView();
        feelsLike = new Label();
        weatherName = new Label();
        wind = new Label();
        clouds = new Label();
        rain = new Label();
        humidity = new Label();
        pressure = new Label();
        visibility = new Label();


        this.wS = new WeatherService();
        wS.setWeatherStrategy(new CurrentForecastMethod());
        weatherController = new PeriodicalWeatherService(wS);
        System.out.println(System.identityHashCode(info));
        weatherController.addObserver(this);

        // Rozpoczęcie aktualizacji co 15 minut
    }

    public void setLabelText(String textValue) {
        wS.setInputStrategy(new CityNameStrategy());
        city = textValue;
        WeatherData[] d = wS.getDataByInput(textValue);
        WeatherData element = d[0];
        //setSceneElements(element);
        info.setText("Current weather in: " + textValue);
        weatherController.startUpdatingWeather(textValue);
    }

    public void setScene(CityViewController n) {
        this.n = n;
    }

    public void SetLabelToCity() {
        wS.setInputStrategy(new IpStrategy());
        WeatherData[] d = wS.getDataByInput("");
        WeatherData element = d[0];
        city = element.getName();
        //setSceneElements(element);
        info.setText("Current weather in: " + element.getName());
        weatherController.startUpdatingWeather(element.getName());
        System.out.println(info.getText());
    }

    public void setSceneElements(WeatherData element) {
        if (element == null) return; // Ochrona przed NPE
        Weather ic = element.getWeather().get(0);
        info.setText("Current weather in: " + element.getName());
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
    }

    public void renderForecast(ActionEvent actionEvent) throws IOException {
        int step = 0;
        String format = "";
        if (actionEvent.getSource() == hourly) {
            wS.setWeatherStrategy(new TwoDayForecastMethod());
            step = 1;
            format = "dd.MM.yyyy HH:mm";
        } else {
            wS.setWeatherStrategy(new FiveDayForecastMethod());
            step = 5;
            format = "dd.MM.yyyy HH:mm";
        }

        FXMLLoader loader = new FXMLLoader(App.class.getResource("forecast-table-view.fxml"));
        Parent root = loader.load();

        // Get controller of Scene2
        ForecastTableController scene2Controller = loader.getController();
        scene2Controller.presentData(wS, city, step, format);

        // Switch scenes
        Stage stage = new Stage();
        scene2Controller.setStage(stage);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void goBack(ActionEvent actionEvent) {
        try {
            weatherController.setEndFlag();
            weatherController.removeObserver(this);
            // Load main-view.fxml
            FXMLLoader loader = new FXMLLoader(App.class.getResource("main-view.fxml"));
            Parent root = loader.load();
            MainViewController controller = loader.getController();
            controller.resetInput();
            // Get the current stage (window) by using the button's scene
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();

            // Set the scene to main-view.fxml
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void update(WeatherData[] forecast) {
        WeatherData element = forecast[0];
        if (element == null) return; // Ochrona przed NPE
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
