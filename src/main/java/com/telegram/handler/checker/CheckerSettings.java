package com.telegram.handler.checker;

import com.telegram.constant.Settings;

public class CheckerSettings {

    private CheckerSettings(){}

    public static boolean isEnumValueSetting(String value) {
        for (Settings e : Settings.values()) {
            if (e.name().equals(value)) {
                return true;
            }
        }
        return false;
    }
}
