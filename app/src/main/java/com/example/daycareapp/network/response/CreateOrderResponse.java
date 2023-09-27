package com.example.daycareapp.network.response;

import com.example.daycareapp.models.Order;

public class CreateOrderResponse {
    private Order order;

    public CreateOrderResponse(Order order, String status) {
        this.order = order;
        this.status = status;
    }

    public Order getOrder() {
        return order;
    }

    public CreateOrderResponse() {
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    private String status;

}
