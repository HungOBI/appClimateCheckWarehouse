package com.example.appclimatecheckwarehouse;

public class Room {
    private String roomName;
    private double temperature;
    private double humidity;
    private int fan;

    public Room(int fan) {
        this.fan = fan;
    }

    public int getFan() {
        return fan;
    }


    public Room(String roomName, double temperature, double humidity,int fan) {
        this.roomName = roomName;
        this.temperature = temperature;
        this.humidity = humidity;
        this.fan = fan;
    }
    public Room(){

    }
    public String getRoomName() {
        return roomName;
    }

    public void setRoomName(String roomName) {
        this.roomName = roomName;
    }

    public double getTemperature() {
        return temperature;
    }

    public void setTemperature(double temperature) {
        this.temperature = temperature;
    }

    public double getHumidity() {
        return humidity;
    }

    public void setHumidity(double humidity) {
        this.humidity = humidity;
    }
}
