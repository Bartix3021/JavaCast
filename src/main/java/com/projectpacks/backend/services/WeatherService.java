package com.projectpacks.backend.services;

import com.projectpacks.backend.forecast.method.ForecastMethod;
import com.projectpacks.backend.input.method.InputStrategy;
import com.projectpacks.backend.models.WeatherData;

public class WeatherService {
    private ForecastMethod weatherStrategy;
    private InputStrategy inputStrategy;

    public void setWeatherStrategy(ForecastMethod weatherStrategy) {
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
