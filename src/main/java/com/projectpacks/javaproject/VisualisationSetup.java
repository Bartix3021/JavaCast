package com.projectpacks.javaproject;

import com.projectpacks.backend.util.UnixToDate;
import com.projectpacks.backend.models.Weather;
import com.projectpacks.backend.models.WeatherData;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;

public class VisualisationSetup {
    public static VBox createWeatherVBox(WeatherData forecast, String dataformat) {

        VBox root = new VBox(10);
        root.setAlignment(Pos.CENTER);

        Label infoLabel = new Label(AppController.getInstance().convertUnixTimestampToDate(forecast.getDt(), dataformat));
        infoLabel.setStyle("-fx-font-size: 20px;");


        VBox hBox = new VBox(20);
        hBox.setAlignment(Pos.CENTER);

        ImageView icon = new ImageView();
        icon.setFitHeight(100);
        icon.setFitWidth(100);
        Weather ic = forecast.getWeather().get(0);
        icon.setImage(new Image("https://openweathermap.org/img/wn/"+ ic.getIcon() +".png", true));

        VBox tempVBox = new VBox(5);
        Label tempLabel = new Label(forecast.getMain().getTemp() + "°C");
        tempLabel.setStyle("-fx-font-size: 40px; fx-padding: 5px");
        tempLabel.setAlignment(Pos.CENTER);

        Label feelsLikeLabel = new Label("Feels like:" + forecast.getMain().getFeelsLike() + "°C");
        feelsLikeLabel.setStyle("-fx-font-size: 14px; fx-padding: 5px");
        feelsLikeLabel.setAlignment(Pos.CENTER);

        tempVBox.getChildren().addAll(tempLabel, feelsLikeLabel);
        hBox.getChildren().addAll(icon, tempVBox);


        Label weatherNameLabel = new Label(forecast.getWeather().get(0).getMain() + " : " + forecast.getWeather().get(0).getDescription());
        weatherNameLabel.setStyle("-fx-font-size: 16px;");


        GridPane gridPane = new GridPane();
        gridPane.setHgap(10);
        gridPane.setVgap(5);
        gridPane.setAlignment(Pos.CENTER);

        ColumnConstraints col1 = new ColumnConstraints();
        col1.setHalignment(HPos.RIGHT);

        ColumnConstraints col2 = new ColumnConstraints();
        col2.setHalignment(HPos.LEFT);

        gridPane.getColumnConstraints().addAll(col1, col2);

        RowConstraints rowConstraints = new RowConstraints();
        rowConstraints.setValignment(Pos.CENTER.getVpos());
        for (int i = 0; i < 6; i++) {
            gridPane.getRowConstraints().add(rowConstraints);
        }

        String[] labels = { "Wind:", "Clouds:", "Rain:", "Pressure:", "Humidity:", "Visibility:" };
        String [] values = {forecast.getWind().getSpeed() + " km/h", forecast.getClouds().getAll() + "% of the sky", forecast.getRain() != null ? forecast.getRain().getAmount() + "mm/h" : "no rain", forecast.getMain().getPressure() + " hPa", forecast.getMain().getHumidity() + "%", forecast.getVisibility() / 1000 + " km" };

        for (int i = 0; i < labels.length; i++) {
            Label label = new Label(labels[i]);
            gridPane.add(label, 0, i);

            Label valueLabel = new Label(values[i]);
            gridPane.add(valueLabel, 1, i);
        }


        root.getChildren().addAll(infoLabel, hBox, weatherNameLabel, gridPane);

        return root;
    }
}
