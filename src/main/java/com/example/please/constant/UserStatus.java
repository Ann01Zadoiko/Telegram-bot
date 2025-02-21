package com.example.please.constant;

public enum UserStatus {
    WORK ("Працюю"),
    SICK ("На лікарняному"),
    VACATION ("У відпустці"),
    REMOTE ("Дистанційно працюю"),
    BUSINESS_TRIP ("У відрядженні"),
    BACK ("Назад");

    private String name;

    UserStatus(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

}
