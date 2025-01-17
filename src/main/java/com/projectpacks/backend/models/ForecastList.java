package com.projectpacks.backend.models;

import com.google.gson.annotations.SerializedName;

public class ForecastList {

    @SerializedName("list")
    private WeatherData[] list;

    public WeatherData[] getList() {
        return list;
    }

    public void setList(WeatherData[] list) {
        this.list = list;
    }
}
