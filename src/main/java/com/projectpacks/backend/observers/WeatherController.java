package com.projectpacks.backend.observers;
import com.projectpacks.backend.objectStructure.WeatherData;
import com.projectpacks.backend.services.WeatherService;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class WeatherController {
    private List<Observer> observers = new ArrayList<>();
    private WeatherService apiService;
    private String place;

    public WeatherController(WeatherService apiService) {
        this.apiService = apiService;
    }

    public void addObserver(Observer observer) {
        observers.add(observer);
    }

    public void removeObserver(Observer observer) {
        observers.remove(observer);
    }

    public void notifyObservers() throws IOException {
        WeatherData[] weatherData = apiService.getWeatherForecast(place);
        for (Observer observer : observers) {
            observer.update(weatherData);
        }
    }

    public void startUpdatingWeather(String place) {
        this.place = place;
        Timer timer = new Timer();
        timer.scheduleAtFixedRate(new TimerTask() {
            @Override
            public void run() {
                try {
                    notifyObservers();
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        }, 0, 15 * 1000); // Co 15 minut
    }
}
