package com.telegram.constant;

import lombok.Getter;

@Getter
public enum ListOfStatus {

    WORK ("На даний момент Ви працюєте"),
    SICK ("На даний момент Ви на лікарняному"),
    VACATION ("На даний момент Ви у відпустці"),
    REMOTE ("На даний момент Ви на дистанційній роботі"),
    BUSINESS_TRIP ("На даний момент Ви у відрядженні");

    private final String name;

    ListOfStatus(String name){
        this.name = name;
    }

}
