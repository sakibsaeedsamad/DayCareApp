package com.example.daycareapp.network.response;

import com.example.daycareapp.models.User;

public class AccountVerifyResponse {
    String status;

    public AccountVerifyResponse() {
    }

    public AccountVerifyResponse(String status, User user) {
        this.status = status;
        this.data = user;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public User getData() {
        return data;
    }

    public void setData(User data) {
        this.data = data;
    }

    User data;

}
