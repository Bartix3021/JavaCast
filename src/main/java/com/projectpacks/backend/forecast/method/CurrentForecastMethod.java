package com.projectpacks.backend.forecast.method;

import com.projectpacks.backend.models.WeatherData;
import com.projectpacks.backend.parsing.ParseWeatherMethod;
import com.projectpacks.backend.parsing.ParsingMethod;
import com.projectpacks.backend.services.OpenWeatherAPI;

public class CurrentForecastMethod implements ForecastMethod {


    public CurrentForecastMethod() {
    }

    @Override
    public WeatherData[] getWeather(String place) {
        ParsingMethod pStrategy = new ParseWeatherMethod();
        place = place.replaceAll(" ", "+");
        String urlString = OpenWeatherAPI.urlBuilder("weather", place);
        String result = OpenWeatherAPI.fetchFromApi(urlString);
        return pStrategy.parse(result);
    }
}
