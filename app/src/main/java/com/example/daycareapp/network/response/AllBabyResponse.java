package com.example.daycareapp.network.response;

import com.example.daycareapp.models.Baby;

import java.util.ArrayList;

public class AllBabyResponse {
    private String status;
    private ArrayList<Baby> babies;

    public AllBabyResponse() {
    }

    public AllBabyResponse(String status, ArrayList<Baby> babies) {
        this.status = status;
        this.babies = babies;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Baby> getBabies() {
        return babies;
    }

    public void setBabies(ArrayList<Baby> babies) {
        this.babies = babies;
    }
}
