package com.projectpacks.backend.parsingStrategy;

import com.google.gson.Gson;
import com.projectpacks.backend.objectStructure.ForecastList;
import com.projectpacks.backend.objectStructure.WeatherData;

public class ParseForecastStrategy implements  ParsingStrategy {

    @Override
    public WeatherData[] parse(String response) {
        Gson gson = new Gson();
        return gson.fromJson(response, ForecastList.class).getList();
    }
}
