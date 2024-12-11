package com.projectpacks.backend.forecast.method;


import com.projectpacks.backend.models.WeatherData;

public interface ForecastMethod {
    WeatherData[] getWeather(String place);
}
