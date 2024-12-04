package com.projectpacks.javaproject;

import com.projectpacks.backend.forecastStrategies.CurrentStrategy;
import com.projectpacks.backend.forecastStrategies.TwoDayStrategy;
import com.projectpacks.backend.forecastStrategies.WeatherStrategy;
import com.projectpacks.backend.inputStrategy.CityNameStrategy;
import com.projectpacks.backend.inputStrategy.IpStrategy;
import com.projectpacks.backend.objectStructure.Weather;
import com.projectpacks.backend.objectStructure.WeatherData;
import com.projectpacks.backend.services.WeatherService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

public class NewScene {
    @FXML
    public Label info;
    public ImageView icon;
    public Label temp;
    public Label feelsLike;
    public Label weatherName;
    public Label wind;

    private WeatherService wS;

    public NewScene() {
        this.wS = new WeatherService();
    }
    public void setLabelText(String textValue) {
        wS.setInputStrategy(new CityNameStrategy());
        WeatherData[] d = wS.getDataByInput(textValue);
        WeatherData element = d[0];
        Weather ic = element.getWeather().get(0);
        info.setText("Current weather in: " + element.getName());
        temp.setText(element.getMain().getTemp() + "°C");
        feelsLike.setText("Feels like: " + element.getMain().getFeelsLike() + "°C");
        weatherName.setText(ic.getMain() + ": " + ic.getDescription());
        wind.setText(element.getWind().getSpeed() + "km/h");
        Image i  = new Image("https://openweathermap.org/img/wn/"+ ic.getIcon() +".png", true);
        icon.setImage(i);
    }

    public void SetLabelToCity() {
        wS.setInputStrategy(new IpStrategy());
        WeatherData[] d = wS.getDataByInput("");
        WeatherData element = d[0];
        Weather ic = element.getWeather().get(0);
        info.setText("Current weather in: " + element.getName());
        temp.setText(element.getMain().getTemp() + "°C");
        feelsLike.setText("Feels like: " + element.getMain().getFeelsLike() + "°C");
        weatherName.setText(ic.getMain() + ": " + ic.getDescription());
        wind.setText(element.getWind().getSpeed() + "km/h");
        Image i  = new Image("https://openweathermap.org/img/wn/"+ ic.getIcon() +".png", true);
        icon.setImage(i);
    }
}
