package com.example.crmmobile.OrderDirectory;

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

    // ✅ getters bạn đang gọi trong SOProductsFragment
    public String getName() { return name; }
    public long getPrice() { return price; }
    public int getQty() { return qty; }

    public String getNote() { return note; }
    public void setNote(String note) { this.note = note; }

    public void setQty(int qty) { this.qty = qty; }
    public void setPrice(long price) { this.price = price; }

    public long getThanhTien() { return (long) qty * price; }
}
