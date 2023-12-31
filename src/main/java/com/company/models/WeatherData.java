package com.company.models;

import java.util.List;

public class WeatherData {
    private Coord coord;
    private List<Weather> weather;
    private String base;
    private MainData main;
    private int visibility;
    private Wind wind;
    private Clouds clouds;
    private long dt;
    private Sys sys;
    private int timezone;
    private int id;
    private String name;
    private int cod;

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

    public MainData getMain() {
        return main;
    }

    public void setMain(MainData main) {
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

    public Sys getSys() {
        return sys;
    }

    public void setSys(Sys sys) {
        this.sys = sys;
    }

    public int getTimezone() {
        return timezone;
    }

    public void setTimezone(int timezone) {
        this.timezone = timezone;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getCod() {
        return cod;
    }

    public void setCod(int cod) {
        this.cod = cod;
    }

    public WeatherData(Coord coord, List<Weather> weather, String base,
                       MainData main, int visibility, Wind wind,
                       Clouds clouds, long dt, Sys sys, int timezone,
                       int id, String name, int cod) {
        this.coord = coord;
        this.weather = weather;
        this.base = base;
        this.main = main;
        this.visibility = visibility;
        this.wind = wind;
        this.clouds = clouds;
        this.dt = dt;
        this.sys = sys;
        this.timezone = timezone;
        this.id = id;
        this.name = name;
        this.cod = cod;
    }

    @Override
    public String toString() {
        String maxTemp = main.getTemp_max();
        String minTemp = main.getTemp_min();
        return "\uD83C\uDF21\uD83C\uDF1E* Ob-havo ma'lumoti: \uD83C\uDF1E\uD83C\uDF21*\n" +
//                "coord=" + coord +
                "\n" + main +
                "\n" + weather +
//                ",\nbase : '" + base + '\'' +
//                ", visibility=" + visibility +
                "\n" + wind +
//                ", clouds=" + clouds +
//                ", dt=" + dt +
                ",\nJoy nomi : '" + name + '\'' +
                " " + sys;
                //+maxTemp+minTemp;
//                ", timezone=" + timezone +
//                ", cod=" + cod +;
    }
}
