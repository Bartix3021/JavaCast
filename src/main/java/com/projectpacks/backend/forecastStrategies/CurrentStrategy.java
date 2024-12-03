package com.projectpacks.backend.forecastStrategies;

import com.projectpacks.backend.objectStructure.WeatherData;

public class CurrentStrategy implements WeatherStrategy{

    private OpenWeatherAPI apiClient;

    public CurrentStrategy() {
        this.apiClient = new OpenWeatherAPI();
    }

    @Override
    public WeatherData[] getWeather(String place) {
        return apiClient.getCurrentWeather(place);
    }
}
