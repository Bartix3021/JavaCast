package com.projectpacks.backend.input.method;

import com.projectpacks.backend.forecast.method.CurrentForecastMethod;
import com.projectpacks.backend.forecast.method.ForecastMethod;
import com.projectpacks.backend.models.WeatherData;
import com.projectpacks.backend.services.IPService;

public class IPMethod implements InputMethod {

    @Override
    public WeatherData[] inputData(String input) {
        String ip = IPService.fetch();
        ForecastMethod strategy = new CurrentForecastMethod();
        String c = IPService.getCity(ip).getCity();
        return strategy.getWeather(c);
    }
}
