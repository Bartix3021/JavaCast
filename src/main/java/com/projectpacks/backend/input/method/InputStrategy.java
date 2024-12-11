package com.projectpacks.backend.input.method;

import com.projectpacks.backend.models.WeatherData;

public interface InputStrategy {

    WeatherData[] inputData(String input);
}
