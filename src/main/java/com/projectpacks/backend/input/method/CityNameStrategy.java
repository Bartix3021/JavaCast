package com.projectpacks.backend.input.method;

import com.projectpacks.backend.forecast.method.CurrentForecastMethod;
import com.projectpacks.backend.forecast.method.ForecastMethod;
import com.projectpacks.backend.models.WeatherData;

public class CityNameStrategy implements InputStrategy {

    @Override
    public WeatherData[] inputData(String input) {
        ForecastMethod strategy = new CurrentForecastMethod();
        return strategy.getWeather(input);
    }
}
