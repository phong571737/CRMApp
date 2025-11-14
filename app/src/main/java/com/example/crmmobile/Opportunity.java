package com.example.crmmobile;

import java.io.Serializable;

public class Opportunity implements Serializable {
    private static final long serialVersionUID = 1L;
    private int callCount;
    private int messageCount;

    private String title;          // Tên cơ hội
    private String company;        // Công ty
    private String contact;        // Liên hệ
    private String price;          // Giá trị (Value)
    private String status;         // Sales stage
    private String successRate;    // Success rate
    private String date;           // Expected close date
    private String expectedDate2;  // Expected close date 2
    private String exchangeText;   // Description
    private String management;     // Management

    public Opportunity(String title, String price, String date, String status,
                       int callCount, int messageCount, String exchangeText) {
        this.title = title;
        this.price = price;
        this.date = date;
        this.status = status;
        this.callCount = callCount;
        this.messageCount = messageCount;
        this.exchangeText = exchangeText;
    }
    public Opportunity(String title, String company, String contact, String price,
                       String status, String successRate, String date,
                       String expectedDate2, String exchangeText, String management) {
        this.title = title;
        this.company = company;
        this.contact = contact;
        this.price = price;
        this.status = status;
        this.successRate = successRate;
        this.date = date;
        this.expectedDate2 = expectedDate2;
        this.exchangeText = exchangeText;
        this.management = management;
    }

    // --- GETTER ---

    public int getCallCount() { return callCount; }
    public int getMessageCount() { return messageCount; }


    public String getTitle() {
        return title;
    }

    public String getCompany() {
        return company;
    }

    public String getContact() {
        return contact;
    }

    public String getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }

    public String getSuccessRate() {
        return successRate;
    }

    public String getDate() {
        return date;
    }

    public String getExpectedDate2() {
        return expectedDate2;
    }

    public String getExchangeText() {
        return exchangeText;
    }

    public String getManagement() {
        return management;
    }

    // --- SETTER (nếu bạn cần cập nhật sau này) ---
    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setSuccessRate(String successRate) {
        this.successRate = successRate;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setExpectedDate2(String expectedDate2) {
        this.expectedDate2 = expectedDate2;
    }

    public void setExchangeText(String exchangeText) {
        this.exchangeText = exchangeText;
    }

    public void setManagement(String management) {
        this.management = management;
    }
}
