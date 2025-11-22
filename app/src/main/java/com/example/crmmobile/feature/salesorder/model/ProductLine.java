package com.example.crmmobile.feature.salesorder.model;

public class ProductLine {
    private String name;
    private String note;
    private int qty;
    private long price;

    public ProductLine(String name, String note, int qty, long price) {
        this.name = name;
        this.note = note;
        this.qty = qty;
        this.price = price;
    }

    public String getName() { return name; }
    public String getNote() { return note; }
    public int getQty() { return qty; }
    public long getPrice() { return price; }

    // Thành tiền = đơn giá * số lượng
    public long getThanhTien() {
        return price * qty;
    }
}
