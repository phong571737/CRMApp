package com.example.crmmobile.feature.salesorder.model;

public class Product {
    public final String name;
    public final String desc;
    public final String badge;
    public final String price;

    public Product(String name, String desc, String badge, String price) {
        this.name = name;
        this.desc = desc;
        this.badge = badge;
        this.price = price;
    }
}
