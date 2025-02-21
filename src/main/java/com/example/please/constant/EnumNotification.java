package com.example.please.constant;

public enum EnumNotification {
    EIGHT ("8:00"),
    EIGHT_FORTY ("8:40"),
    EIGHT_FIFTY ("8:50"),
    EIGHT_FORTY_FIVE ("8:45"),
    EIGHT_FIFTY_FIVE ("8:55"),
    TURN_OFF ("Вимкнути"),
    BACK ("Назад");

    private String time;

    EnumNotification(String time){
        this.time = time;
    }

    public String getTime(){
        return time;
    }

}
