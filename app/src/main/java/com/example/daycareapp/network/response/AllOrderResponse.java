package com.example.daycareapp.network.response;

import com.example.daycareapp.models.Order;

import java.util.ArrayList;

public class AllOrderResponse {
    String status;
    ArrayList<Order> orders;

    public AllOrderResponse(String status, ArrayList<Order> orders) {
        this.status = status;
        this.orders = orders;
    }

    public AllOrderResponse() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public ArrayList<Order> getOrders() {
        return orders;
    }

    public void setOrders(ArrayList<Order> orders) {
        this.orders = orders;
    }
}
