package com.example.daycareapp.models;


public class User {
    private String username;
    private String password;
    private String email;
    private String name;
    private String verificationPin = "";
    private boolean isVerified = false;
    private String userRole;

    public User(String username, String password, String email, String name, String verificationPin, boolean isVerified, String userRole) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
        this.verificationPin = verificationPin;
        this.isVerified = isVerified;
        this.userRole = userRole;
    }

    public String getUserRole() {
        return userRole;
    }

    public void setUserRole(String userRole) {
        this.userRole = userRole;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

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

    public User(String username, String password, String email, String name) {
        this.username = username;
        this.password = password;
        this.email = email;
        this.name = name;
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
    }


}
