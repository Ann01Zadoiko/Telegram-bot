package com.example.please.constant;

public enum Settings {

    FULL_NAME ("ПІБ"),

    PHONE_NUMBER ("Номер телефона"),

    STATUS ("Статус");

    private final String name;

    Settings(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }
}
