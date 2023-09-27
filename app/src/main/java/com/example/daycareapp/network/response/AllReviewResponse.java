package com.example.daycareapp.network.response;

import com.example.daycareapp.models.Review;

import java.util.List;

public class AllReviewResponse {
    String message;
    String status;
    List<Review> reviews;

    public AllReviewResponse() {
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public List<Review> getReviews() {
        return reviews;
    }

    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }

    public AllReviewResponse(String message, String status, List<Review> reviews) {
        this.message = message;
        this.status = status;
        this.reviews = reviews;
    }
}
