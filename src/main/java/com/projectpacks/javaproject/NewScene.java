package com.projectpacks.javaproject;

import com.projectpacks.backend.forecastStrategies.CurrentStrategy;
import com.projectpacks.backend.forecastStrategies.TwoDayStrategy;
import com.projectpacks.backend.forecastStrategies.WeatherStrategy;
import com.projectpacks.backend.inputStrategy.CityNameStrategy;
import com.projectpacks.backend.inputStrategy.IpStrategy;
import com.projectpacks.backend.objectStructure.WeatherData;
import com.projectpacks.backend.services.WeatherService;
import javafx.fxml.FXML;
import javafx.scene.control.Label;

public class NewScene {
    @FXML
    public Label info;
    private WeatherService wS;

    public NewScene() {
        this.wS = new WeatherService();
    }
    public void setLabelText(String textValue) {
        wS.setInputStrategy(new CityNameStrategy());
        WeatherData[] d = wS.getDataByInput(textValue);
        info.setText(d[0].toString());
    }

    public void SetLabelToCity() {
        wS.setInputStrategy(new IpStrategy());
        WeatherData[] d = wS.getDataByInput("");
        info.setText(d[0].getName());
    }
}
