package com.projectpacks.backend.forecastStrategies;


import com.projectpacks.backend.objectStructure.WeatherData;

public interface WeatherStrategy {
    WeatherData[] getWeather(String place);
}
