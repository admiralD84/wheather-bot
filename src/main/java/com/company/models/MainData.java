package com.company.models;

public class MainData {

    private double temp;
    private double feels_like;
    private double temp_min;
    private double temp_max;
    private int pressure;
    private int humidity;

    public MainData(double temp, double feels_like, double temp_min, double temp_max, int pressure, int humidity) {
        this.temp = temp;
        this.feels_like = feels_like;
        this.temp_min = temp_min;
        this.temp_max = temp_max;
        this.pressure = pressure;
        this.humidity = humidity;
    }

    public double getTemp() {
        return temp;
    }

    public void setTemp(double temp) {
        this.temp = temp;
    }

    public double getFeels_like() {
        return feels_like;
    }

    public void setFeels_like(double feels_like) {
        this.feels_like = feels_like;
    }

    public String getTemp_min() {
        return "\nBu kundagi *eng past harorat*: " + temp_min + " C";
    }

    public void setTemp_min(double temp_min) {
        this.temp_min = temp_min;
    }

    public String getTemp_max() {
        return "\nBu kundagi *eng yuqori harorat*: " + temp_max + " C";
    }

    public void setTemp_max(double temp_max) {
        this.temp_max = temp_max;
    }

    public int getPressure() {
        return pressure;
    }

    public void setPressure(int pressure) {
        this.pressure = pressure;
    }

    public int getHumidity() {
        return humidity;
    }

    public void setHumidity(int humidity) {
        this.humidity = humidity;
    }

    @Override
    public String toString() {
        return "Bugun harorat:   *" + temp + " C*\n" +
                feels_like + " C dek tuyuladi" +
                ",\nBosim: " + pressure +
                ",\nNamlik: " + humidity;
    }
}
