package com.projectpacks.backend.forecast.method;

import com.projectpacks.backend.models.WeatherData;
import com.projectpacks.backend.parsing.ParseForecastMethod;
import com.projectpacks.backend.parsing.ParsingMethod;
import com.projectpacks.backend.services.OpenWeatherAPI;

public class TwoDayForecastMethod implements ForecastMethod {

    public TwoDayForecastMethod() {
    }

    @Override
    public WeatherData[] getWeather(String place) {
        ParsingMethod pStrategy = new ParseForecastMethod();
        place = place.replaceAll(" ", "+");
        String urlString = OpenWeatherAPI.urlBuilder("forecast", place, "&exclude=current,minutely,daily,alerts");
        String result = OpenWeatherAPI.fetchFromApi(urlString);
        return pStrategy.parse(result);
    }
}
