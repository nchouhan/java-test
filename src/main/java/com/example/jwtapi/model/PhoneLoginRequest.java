package com.example.jwtapi.model;

public class PhoneLoginRequest {
    private String phoneNumber;

    public PhoneLoginRequest() {}

    public PhoneLoginRequest(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}