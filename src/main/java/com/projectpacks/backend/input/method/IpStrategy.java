package com.projectpacks.backend.input.method;

import com.projectpacks.backend.forecast.method.CurrentForecastMethod;
import com.projectpacks.backend.forecast.method.ForecastMethod;
import com.projectpacks.backend.models.WeatherData;

public class IpStrategy implements InputStrategy {

    @Override
    public WeatherData[] inputData(String input) {
        String ip = IpFetcher.fetch();
        ForecastMethod strategy = new CurrentForecastMethod();
        String c = IpToCity.getCity(ip).getCity();
        System.out.println(c);
        return strategy.getWeather(c);
    }
}
