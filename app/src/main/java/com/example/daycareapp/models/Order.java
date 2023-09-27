package com.example.daycareapp.models;

import java.sql.Timestamp;

public class Order {
    private Long id;

    private User user;
    private Long babyId;
    private Long caregiverId;
    private Timestamp startTime;

    public Order(String speciality, Long babyId, Long caregiverId, Long amount, boolean isPaymentDone, boolean isServiceDone) {
        this.speciality = speciality;
        this.babyId = babyId;
        this.caregiverId = caregiverId;
        this.amount = amount;
        this.isPaymentDone = isPaymentDone;
        this.isServiceDone = isServiceDone;
    }

    private Timestamp endTime;

    public Order(Long id, User user, Long babyId, Long caregiverId, Timestamp startTime, Timestamp endTime, Long amount, boolean isPaymentDone, boolean isServiceDone, String speciality) {
        this.id = id;
        this.user = user;
        this.babyId = babyId;
        this.caregiverId = caregiverId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.amount = amount;
        this.isPaymentDone = isPaymentDone;
        this.isServiceDone = isServiceDone;
        this.speciality = speciality;
    }

    public void setBabyId(Long babyId) {
        this.babyId = babyId;
    }

    public Long getAmount() {
        return amount;
    }

    public void setAmount(Long amount) {
        this.amount = amount;
    }

    private Long amount;

    private boolean isPaymentDone = false;
    private boolean isServiceDone = false;
    private String speciality;
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
    public Long getBabyId() {
        return babyId;
    }
    public void setBaby(Long babyId) {
        this.babyId = babyId;
    }
    public Long getCaregiverId() {
        return caregiverId;
    }
    public void setCaregiverId(Long caregiverId) {
        this.caregiverId = caregiverId;
    }
    public Timestamp getStartTime() {
        return startTime;
    }
    public void setStartTime(Timestamp startTime) {
        this.startTime = startTime;
    }
    public Timestamp getEndTime() {
        return endTime;
    }
    public void setEndTime(Timestamp endTime) {
        this.endTime = endTime;
    }
    public boolean isPaymentDone() {
        return isPaymentDone;
    }
    public void setPaymentDone(boolean isPaymentDone) {
        this.isPaymentDone = isPaymentDone;
    }
    public boolean isServiceDone() {
        return isServiceDone;
    }
    public void setServiceDone(boolean isServiceDone) {
        this.isServiceDone = isServiceDone;
    }
    public String getSpeciality() {
        return speciality;
    }
    public void setSpeciality(String speciality) {
        this.speciality = speciality;
    }
    public Order(Long id, User user, Long babyId,Long caregiverId, Timestamp startTime, Timestamp endTime,
                 boolean isPaymentDone, boolean isServiceDone, String speciality) {
        this.id = id;
        this.user = user;
        this.babyId = babyId;
        this.caregiverId = caregiverId;
        this.startTime = startTime;
        this.endTime = endTime;
        this.isPaymentDone = isPaymentDone;
        this.isServiceDone = isServiceDone;
        this.speciality = speciality;
    }
    public Order() {
    }

}