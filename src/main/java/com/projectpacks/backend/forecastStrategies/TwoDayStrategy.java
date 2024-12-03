package com.projectpacks.backend.forecastStrategies;

import com.projectpacks.backend.objectStructure.WeatherData;

public class TwoDayStrategy implements WeatherStrategy {
    private OpenWeatherAPI openWeatherAPI;

    public TwoDayStrategy() {
        openWeatherAPI = new OpenWeatherAPI();
    }
    @Override
    public WeatherData[] getWeather(String place) {
        return openWeatherAPI.getTwoDayForecast(place);
    }
}
