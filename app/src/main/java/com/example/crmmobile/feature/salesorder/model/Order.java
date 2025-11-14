package com.example.crmmobile.feature.salesorder.model;

public class Order {
    private String orderCode;
    private String company;
    private String price;
    private String date;
    private String paymentStatus;
    private String orderType;

    public Order(String orderCode, String company, String price, String date, String paymentStatus, String orderType) {
        this.orderCode = orderCode;
        this.company = company;
        this.price = price;
        this.date = date;
        this.paymentStatus = paymentStatus;
        this.orderType = orderType;
    }

    public String getOrderCode() {
        return orderCode;
    }

    public String getCompany() {
        return company;
    }

    public String getPrice() {
        return price;
    }

    public String getDate() {
        return date;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public String getOrderType() {
        return orderType;
    }
}

