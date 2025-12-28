package com.example.crmmobile.QuoteDirectory;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

public class Quote {
    private Integer ID;
    private String OrderCode;
    private String Title;
    private String company;
    private String date;
    private String ContactPersonName;
    private Integer ContactPersonID;
    private String OpportunityName;
    private String State;
    private String Product;
    private Integer Quantity;
    private Integer Price;
    private Long TotalAmount;

    public Quote() {}

    public Quote(String OrderCode, String company, String date,  Integer Price){
        this.OrderCode = OrderCode;
        this.company = company;
        this.date = date;
        this.Price = Price;
    }
    public Quote(String OrderCode, String company,  Integer Price){
        this.OrderCode = OrderCode;
        this.company = company;
        this.Price = Price;
    }
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getOrderCode() {
        return OrderCode;
    }

    public void setOrderCode(String orderCode) {
        OrderCode = orderCode;
    }

    public String getContactPersonName() {
        return ContactPersonName;
    }

    public void setContactPersonName(String contactPersonName) {
        ContactPersonName = contactPersonName;
    }

    public Integer getContactPersonID() {
        return ContactPersonID;
    }

    public void setContactPersonID(Integer contactPersonID) {
        ContactPersonID = contactPersonID;
    }

    public String getOpportunityName() {
        return OpportunityName;
    }

    public void setOpportunityName(String opportunityName) {
        OpportunityName = opportunityName;
    }

    public String getState() {
        return State;
    }

    public void setState(String state) {
        State = state;
    }

    public String getProduct() {
        return Product;
    }

    public void setProduct(String product) {
        Product = product;
    }

    public Integer getQuantity() {
        return Quantity;
    }

    public void setQuantity(Integer quantity) {
        Quantity = quantity;
    }

    public Integer getPrice() {
        return Price;
    }

    public void setPrice(Integer price) {
        Price = price;
    }

    public Long getTotalAmount() {
        return TotalAmount;
    }

    public void setTotalAmount(Long totalAmount) {
        TotalAmount = totalAmount;
    }
}
