package com.projectpacks.backend.inputStrategy;

import com.projectpacks.backend.forecastStrategies.CurrentStrategy;
import com.projectpacks.backend.forecastStrategies.WeatherStrategy;
import com.projectpacks.backend.objectStructure.WeatherData;

public class IpStrategy implements InputStrategy {

    @Override
    public WeatherData[] inputData(String input) {
        String ip = IpFetcher.fetch();
        WeatherStrategy strategy = new CurrentStrategy();
        String c = IpToCity.getCity(ip).getCity();
        System.out.println(c);
        return strategy.getWeather(c);
    }
}
