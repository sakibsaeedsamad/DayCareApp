package com.example.daycareapp.network.response;

import com.example.daycareapp.models.Caregiver;

import java.util.ArrayList;

public class AllCaregiverResponse {
    ArrayList<Caregiver> caregivers;

    public String getStatus() {
        return status;
    }

    public AllCaregiverResponse(ArrayList<Caregiver> caregiverArrayList, String status) {
        this.caregivers = caregiverArrayList;
        this.status = status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

    public ArrayList<Caregiver> getCaregivers() {
        return caregivers;
    }

    public void setCaregivers(ArrayList<Caregiver> caregivers) {
        this.caregivers = caregivers;
    }

    public AllCaregiverResponse(ArrayList<Caregiver> caregiverArrayList) {
        this.caregivers = caregiverArrayList;
    }

    public AllCaregiverResponse() {
    }
}
