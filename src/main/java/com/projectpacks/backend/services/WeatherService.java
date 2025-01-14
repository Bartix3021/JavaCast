package com.projectpacks.backend.services;

import com.projectpacks.backend.forecast.method.ForecastMethod;
import com.projectpacks.backend.input.method.InputMethod;
import com.projectpacks.backend.models.WeatherData;

public class WeatherService {
    private ForecastMethod weatherMethod;
    private InputMethod inputMethod;

    public void setWeatherMethod(ForecastMethod weatherMethod) {
        this.weatherMethod = weatherMethod;
    }

    public void setInputMethod(InputMethod inputMethod) {
        this.inputMethod = inputMethod;
    }

    public WeatherData[] getWeatherForecast(String place) {
        return weatherMethod.getWeather(place);
    }

    public WeatherData[] getDataByInput(String input) {
        return inputMethod.inputData(input);
    }
}
