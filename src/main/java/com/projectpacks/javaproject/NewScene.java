package com.projectpacks.javaproject;

import com.projectpacks.backend.forecastStrategies.CurrentStrategy;
import com.projectpacks.backend.forecastStrategies.SevenDayStrategy;
import com.projectpacks.backend.forecastStrategies.TwoDayStrategy;
import com.projectpacks.backend.inputStrategy.CityNameStrategy;
import com.projectpacks.backend.inputStrategy.IpStrategy;
import com.projectpacks.backend.objectStructure.Weather;
import com.projectpacks.backend.objectStructure.WeatherData;
import com.projectpacks.backend.observers.WeatherController;
import com.projectpacks.backend.observers.WeatherDisplay;
import com.projectpacks.backend.services.WeatherService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;

import javax.swing.text.LabelView;
import java.io.IOException;

public class NewScene {
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
    public NewScene n;

    private WeatherService wS;
    private String city;
    private WeatherController weatherController;
    private WeatherDisplay weatherDisplay;

    public NewScene() {
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
        wS.setWeatherStrategy(new CurrentStrategy());
        weatherController = new WeatherController(wS);
        System.out.println(System.identityHashCode(info));
        weatherDisplay = new WeatherDisplay(n, info, icon, temp, feelsLike, weatherName, wind, clouds, rain, humidity, pressure, visibility);
        weatherController.addObserver(weatherDisplay);

        // Rozpoczęcie aktualizacji co 15 minut
    }

    public void setLabelText(String textValue) {
        wS.setInputStrategy(new CityNameStrategy());
        city = textValue;
        WeatherData[] d = wS.getDataByInput(textValue);
        WeatherData element = d[0];
        setSceneElements(element);
        //weatherController.startUpdatingWeather(textValue);
    }

    public void setScene(NewScene n) {
        this.n = n;
    }

    public void SetLabelToCity() {
        wS.setInputStrategy(new IpStrategy());
        WeatherData[] d = wS.getDataByInput("");
        WeatherData element = d[0];
        city = element.getName();
        setSceneElements(element);
        //weatherController.startUpdatingWeather(element.getName());
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
        Integer step = 0;
        String format = "";
        if (actionEvent.getSource() == hourly) {
            wS.setWeatherStrategy(new TwoDayStrategy());
            step = 1;
            format = "dd.MM.yyyy HH:mm";
        } else {
            wS.setWeatherStrategy(new SevenDayStrategy());
            step = 5;
            format = "dd.MM.yyyy HH:mm";
        }

        FXMLLoader loader = new FXMLLoader(getClass().getResource("forecast_display.fxml"));
        Parent root = loader.load();

        // Get controller of Scene2
        ForecastDisplay scene2Controller = loader.getController();
        scene2Controller.presentData(wS, city, step, format);

        // Switch scenes
        Stage stage = new Stage();
        scene2Controller.setStage(stage);
        stage.setScene(new Scene(root));
        stage.show();
    }

    public void goBack(ActionEvent actionEvent) {
        try {
            // Load temp.fxml
            FXMLLoader loader = new FXMLLoader(getClass().getResource("temp.fxml"));
            Parent root = loader.load();
            TemplateController controller = loader.getController();
            controller.resetInput();
            // Get the current stage (window) by using the button's scene
            Stage stage = (Stage) ((Button) actionEvent.getSource()).getScene().getWindow();

            // Set the scene to temp.fxml
            stage.setScene(new Scene(root));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
