package com.projectpacks.backend.observers;

import com.projectpacks.backend.objectStructure.WeatherData;

public interface Observer {
    void update(WeatherData[] newWeatherData);
}
