package com.telegram.handler.checker;

import com.telegram.constant.EnumNotification;

public class CheckerEnumNotification {

    private CheckerEnumNotification(){}

    public static boolean isEnumValueNotification(String value) {
        for (EnumNotification e : EnumNotification.values()) {
            if (e.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
