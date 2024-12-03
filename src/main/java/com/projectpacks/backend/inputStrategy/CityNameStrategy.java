package com.projectpacks.backend.inputStrategy;

import com.projectpacks.backend.forecastStrategies.CurrentStrategy;
import com.projectpacks.backend.forecastStrategies.OpenWeatherAPI;
import com.projectpacks.backend.forecastStrategies.WeatherStrategy;
import com.projectpacks.backend.objectStructure.WeatherData;

public class CityNameStrategy implements InputStrategy {

    @Override
    public WeatherData[] inputData(String input) {
        WeatherStrategy strategy = new CurrentStrategy();
        return strategy.getWeather(input);
    }
}
