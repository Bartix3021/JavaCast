package com.projectpacks.backend.observers;
import com.projectpacks.backend.objectStructure.Weather;
import com.projectpacks.backend.objectStructure.WeatherData;
import com.projectpacks.javaproject.NewScene;
import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.control.Label;
import javafx.scene.image.Image;

public class WeatherDisplay implements Observer {
    public Label info;
    public ImageView icon;
    public Label temp;
    private Label feelsLike;
    private Label weatherName;
    private Label wind;
    private Label clouds;
    private Label rain;
    private Label humidity;
    private Label pressure;
    private Label visibility;
    private NewScene n;

    public WeatherDisplay(NewScene n, Label info, ImageView icon, Label temp, Label feelsLike, Label weatherName, Label wind, Label clouds, Label rain, Label humidity, Label pressure, Label visibility) {
        this.info = info;
        this.n = n;
        System.out.println(System.identityHashCode(info));
        this.icon = icon;
        this.temp = temp;
        this.feelsLike = feelsLike;
        this.weatherName = weatherName;
        this.wind = wind;
        this.clouds = clouds;
        this.rain = rain;
        this.humidity = humidity;
        this.pressure = pressure;
        this.visibility = visibility;
    }



    private void setSceneElements(WeatherData element) {
        System.out.println(element);
        Weather ic = element.getWeather().get(0);
        n.info.setText("Current weather in: " + element.getName());
        System.out.println(info.getText());
        System.out.println(System.identityHashCode(info));
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
        visibility.setText(element.getVisibility() / 1000 + " km");

        Image i = new Image("https://openweathermap.org/img/wn/" + ic.getIcon() + ".png", true);
        icon.setImage(i);
    }

    @Override
    public void update(WeatherData[] newWeatherData) {
        if (newWeatherData != null && newWeatherData.length > 0) {
            WeatherData element = newWeatherData[0];

            Platform.runLater(() -> setSceneElements(element));
        }
    }
}

