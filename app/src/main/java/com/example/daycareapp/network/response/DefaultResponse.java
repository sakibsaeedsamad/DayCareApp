package com.example.daycareapp.network.response;

public class DefaultResponse {
    String status;
    String message;

    public DefaultResponse(String status, String message) {
        this.status = status;
        this.message = message;
    }

    public DefaultResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
