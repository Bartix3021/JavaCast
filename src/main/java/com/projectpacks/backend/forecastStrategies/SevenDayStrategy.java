package com.projectpacks.backend.forecastStrategies;

import com.projectpacks.backend.objectStructure.WeatherData;

public class SevenDayStrategy implements WeatherStrategy{
    private OpenWeatherAPI openWeatherAPI;

    public SevenDayStrategy() {
        this.openWeatherAPI = new OpenWeatherAPI();
    }
    @Override
    public WeatherData[] getWeather(String place) {
        return openWeatherAPI.getWeeklyForecast(place);
    }
}
