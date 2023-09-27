package com.example.daycareapp.models;

public class Caregiver {
    private Long id;
    private String speciality;
    private Boolean isAvailable = true;
    private String adminFeedBack;
    private User user;
    private String caregiverMotherName;
    private String address;

    public Caregiver(Long id, String speciality, Boolean isAvailable, String adminFeedBack, User user, String caregiverMotherName, String address) {
        this.id = id;
        this.speciality = speciality;
        this.isAvailable = isAvailable;
        this.adminFeedBack = adminFeedBack;
        this.user = user;
        this.caregiverMotherName = caregiverMotherName;
        this.address = address;
    }

    public Boolean getAvailable() {
        return isAvailable;
    }

    public void setAvailable(Boolean available) {
        isAvailable = available;
    }

    public String getAdminFeedBack() {
        return adminFeedBack;
    }

    public void setAdminFeedBack(String adminFeedBack) {
        this.adminFeedBack = adminFeedBack;
    }


    public String getSpeciality() {
        return speciality;
    }

    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }

    public Caregiver() {
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public String getCaregiverMotherName() {
        return caregiverMotherName;
    }

    public void setCaregiverMotherName(String caregiverMotherName) {
        this.caregiverMotherName = caregiverMotherName;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
//    private String imagefileText;
}
