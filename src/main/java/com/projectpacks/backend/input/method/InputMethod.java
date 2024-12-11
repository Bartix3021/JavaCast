package com.projectpacks.backend.input.method;

import com.projectpacks.backend.models.WeatherData;

public interface InputMethod {

    WeatherData[] inputData(String input);
}
