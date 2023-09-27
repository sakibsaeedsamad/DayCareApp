package com.example.daycareapp.network.response;

import com.example.daycareapp.models.Baby;

public class AddBabyResponse {
    private Baby baby;
    private String status;

    public Baby getBaby() {
        return baby;
    }

    public void setBaby(Baby baby) {
        this.baby = baby;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public AddBabyResponse() {
    }

    public AddBabyResponse(Baby baby, String status) {
        this.baby = baby;
        this.status = status;
    }
}
