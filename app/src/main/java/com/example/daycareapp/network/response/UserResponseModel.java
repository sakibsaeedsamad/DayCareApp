package com.example.daycareapp.network.response;

import com.google.gson.annotations.SerializedName;

public class UserResponseModel {
    @SerializedName(value = "id")
    public int id;

    @SerializedName(value = "name")
    private String name;

    @SerializedName(value = "email")
    private String email;

    private String userRole;

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public UserResponseModel(int id, String name, String email, String userRole, String verificationPin, boolean isVerified) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.userRole = userRole;
        this.verificationPin = verificationPin;
        this.isVerified = isVerified;
    }

    private String verificationPin = "";
    private boolean isVerified = false;

    public String getVerificationPin() {
        return verificationPin;
    }

    public void setVerificationPin(String verificationPin) {
        this.verificationPin = verificationPin;
    }

    public boolean isVerified() {
        return isVerified;
    }

    public void setVerified(boolean verified) {
        isVerified = verified;
    }

    public UserResponseModel(int id, String name, String email) {
        this.id = id;
        this.name = name;
        this.email = email;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
