package com.example.daycareapp.network.response;

import com.example.daycareapp.models.Caregiver;

public class AddCaregiverResponse {
    String status;
    String message;
    Caregiver caregiver;

    public AddCaregiverResponse() {
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

    public Caregiver getCaregiver() {
        return caregiver;
    }

    public void setCaregiver(Caregiver caregiver) {
        this.caregiver = caregiver;
    }

    public AddCaregiverResponse(String status, String message, Caregiver caregiver) {
        this.status = status;
        this.message = message;
        this.caregiver = caregiver;
    }
}
