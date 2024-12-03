package com.projectpacks.backend.services;

import com.projectpacks.backend.forecastStrategies.WeatherStrategy;
import com.projectpacks.backend.inputStrategy.InputStrategy;
import com.projectpacks.backend.objectStructure.WeatherData;

public class WeatherService {
    private WeatherStrategy weatherStrategy;
    private InputStrategy inputStrategy;

    public void setWeatherStrategy(WeatherStrategy weatherStrategy) {
        this.weatherStrategy = weatherStrategy;
    }

    public void setInputStrategy(InputStrategy inputStrategy) {
        this.inputStrategy = inputStrategy;
    }

    public WeatherData[] getWeatherForecast(String place) {
        return weatherStrategy.getWeather(place);
    }

    public WeatherData[] getDataByInput(String input) {
        return inputStrategy.inputData(input);
    }
}
