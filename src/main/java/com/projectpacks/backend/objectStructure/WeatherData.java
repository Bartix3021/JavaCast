package com.projectpacks.backend.objectStructure;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class WeatherData {
    @SerializedName("coord")
    private Coord coord;

    @SerializedName("weather")
    private List<Weather> weather;

    @SerializedName("base")
    private String base;

    @SerializedName("main")
    private Main main;

    @SerializedName("visibility")
    private int visibility;

    @SerializedName("wind")
    private Wind wind;

    @SerializedName("clouds")
    private Clouds clouds;

    @SerializedName("dt")
    private long dt;

//    @SerializedName("sys")
//    private Sys sys;

//    @SerializedName("timezone")
//    private int timezone;
//
//    @SerializedName("id")
//    private int id;
//
    @SerializedName("name")
    private String name;

//    @SerializedName("cod")
//    private int cod;

    @SerializedName("rain")
    private Rain rain;


    public Rain getRain() {
        return rain;
    }

    public void setRain(Rain rain) {
        this.rain = rain;
    }

    public Coord getCoord() {
        return coord;
    }

    public void setCoord(Coord coord) {
        this.coord = coord;
    }

    public List<Weather> getWeather() {
        return weather;
    }

    public void setWeather(List<Weather> weather) {
        this.weather = weather;
    }

    public String getBase() {
        return base;
    }

    public void setBase(String base) {
        this.base = base;
    }

    public Main getMain() {
        return main;
    }

    public void setMain(Main main) {
        this.main = main;
    }

    public int getVisibility() {
        return visibility;
    }

    public void setVisibility(int visibility) {
        this.visibility = visibility;
    }

    public Wind getWind() {
        return wind;
    }

    public void setWind(Wind wind) {
        this.wind = wind;
    }

    public Clouds getClouds() {
        return clouds;
    }

    public void setClouds(Clouds clouds) {
        this.clouds = clouds;
    }

    public long getDt() {
        return dt;
    }

    public void setDt(long dt) {
        this.dt = dt;
    }

//    public Sys getSys() {
//        return sys;
//    }
//
//    public void setSys(Sys sys) {
//        this.sys = sys;
//    }

//    public int getTimezone() {
//        return timezone;
//    }

//    public void setTimezone(int timezone) {
//        this.timezone = timezone;
//    }
//
//    public int getId() {
//        return id;
//    }
//
//    public void setId(int id) {
//        this.id = id;
//    }
//
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

///    public int getCod() {
//        return cod;
//    }
//
//    public void setCod(int cod) {
//        this.cod = cod;
//    }

    @Override
    public String toString() {
        return "WeatherData{" +
                "coord=" + coord +
                ", base='" + base + '\'' +
                ", main=" + main +
                ", visibility=" + visibility +
                ", wind=" + wind +
                ", clouds=" + clouds +
                ", dt=" + dt +
                '}';
    }
}
