package com.projectpacks.backend.parsing;

import com.google.gson.Gson;
import com.projectpacks.backend.models.ForecastList;
import com.projectpacks.backend.models.WeatherData;

public class ParseForecastMethod implements ParsingMethod {

    @Override
    public WeatherData[] parse(String response) {
        Gson gson = new Gson();
        return gson.fromJson(response, ForecastList.class).getList();
    }
}
