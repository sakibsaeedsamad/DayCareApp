package com.example.daycareapp.models;

import java.sql.Timestamp;

public class Review {
    private Long id;

    private User user;

    private Long caregiverId;

    private String title;

    private String body;

    private Timestamp time;

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

    public Long getCaregiverId() {
        return caregiverId;
    }

    public void setCaregiverId(Long caregiverId) {
        this.caregiverId = caregiverId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Timestamp getTime() {
        return time;
    }

    public void setTime(Timestamp time) {
        this.time = time;
    }

    public Review(Long id, User user, Long caregiverId,  String title,  String body,
                  Timestamp time) {
        super();
        this.id = id;
        this.user = user;
        this.caregiverId = caregiverId;
        this.title = title;
        this.body = body;
        this.time = time;
    }

    public Review(){}
}