package com.telegram.constant;

public enum UserStatus {
    WORK ("Працюю"),
    SICK ("На лікарняному"),
    VACATION ("У відпустці"),
    REMOTE ("Дистанційно працюю"),
    BUSINESS_TRIP ("У відрядженні"),
    BACK ("Назад");

    private final String name;

    UserStatus(String name){
        this.name = name;
    }

    public String getName(){
        return name;
    }

}
