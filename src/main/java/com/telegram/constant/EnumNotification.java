package com.telegram.constant;

import lombok.Getter;

@Getter
public enum EnumNotification {
    EIGHT ("8:00"),
    EIGHT_FORTY ("8:40"),
    EIGHT_FIFTY ("8:50"),
    EIGHT_FORTY_FIVE ("8:45"),
    EIGHT_FIFTY_FIVE ("8:55"),
    TURN_OFF ("Вимкнути"),
    BACK ("Назад");

    private final String time;

    EnumNotification(String time){
        this.time = time;
    }

}
