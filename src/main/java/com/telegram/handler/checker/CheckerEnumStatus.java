package com.telegram.handler.checker;

import com.telegram.constant.UserStatus;

public class CheckerEnumStatus {

    private CheckerEnumStatus(){}

    public static boolean isEnumValueStatus(String value) {
        for (UserStatus e : UserStatus.values()) {
            if (e.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
