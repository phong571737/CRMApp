package com.example.crmmobile.OrderDirectory;

public class ProductLine {
    private String name;
    private String note;
    private int qty;
    private long price;

    // ✅ Giảm giá theo dòng (tiền tuyệt đối, đơn vị: đ)
    private long discountAmount = 0L;

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

    public void setNote(String note) { this.note = note; }
    public void setQty(int qty) { this.qty = qty; }
    public void setPrice(long price) { this.price = price; }

    // ✅ discount
    public long getDiscountAmount() { return discountAmount; }
    public void setDiscountAmount(long discountAmount) {
        this.discountAmount = Math.max(0L, discountAmount);
    }

    public long getBaseAmount() {
        return (long) qty * price;
    }

    // ✅ Thành tiền sau giảm giá (fragment tổng kết sẽ dùng cái này)
    public long getThanhTien() {
        long base = getBaseAmount();
        long d = Math.min(Math.max(0L, discountAmount), base);
        return Math.max(0L, base - d);
    }
}
