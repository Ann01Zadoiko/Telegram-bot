package com.telegram.handler.registration;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegistration {

    private String fullName;
    private String phoneNumber;
    private int step = 1;
    private RegistrationType type;

    public UserRegistration(RegistrationType type, int step) {
        this.type = type;
        this.step = step;
    }

    public void nextStep() {
        this.step++;
    }

}
