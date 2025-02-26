package com.telegram.handler.registration;

public class UserRegistration {

    private String fullName;
    private String phoneNumber;
    private int step = 1;
    private RegistrationType type;

    public UserRegistration(RegistrationType type, int step) {
        this.type = type;
        this.step = step;
    }

    public RegistrationType getType() {
        return type;
    }

    public void setType(RegistrationType type) {
        this.type = type;
    }

    public void nextStep() {
        this.step++;
    }

    public int getStep() {
        return step;
    }

    public String getFullName() { return fullName; }

    public void setFullName(String fullName) { this.fullName = fullName; }

    public String getPhoneNumber() { return phoneNumber; }

    public void setPhoneNumber(String phoneNumber) { this.phoneNumber = phoneNumber; }


}
