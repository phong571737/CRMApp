package com.example.crmmobile.OrderDirectory;

public class ProductLine {

    public static final int DISCOUNT_NONE    = 0;
    public static final int DISCOUNT_PERCENT = 1;
    public static final int DISCOUNT_DIRECT  = 2;

    private String name;
    private String note;
    private int qty;
    private long price;

    // ===== Discount state (để mở lại bottomsheet đúng) =====
    private int discountType = DISCOUNT_NONE;
    private int discountPercent = 0;   // 0..100
    private long discountDirect = 0L;  // tiền nhập trực tiếp

    // tiền giảm thực tế dùng tính toán
    private long discountAmount = 0L;

    // ===== Tax per line (VAT) =====
    private int vatPercent = 0;   // 0 = không áp dụng, 10 = VAT 10%
    private long taxAmount = 0L;  // tiền thuế dòng

    public ProductLine(String name, String note, int qty, long price) {
        this.name = name;
        this.note = note;
        this.qty = qty;
        this.price = price;
        recalc();
    }

    public String getName() { return name; }
    public String getNote() { return note; }
    public int getQty() { return qty; }
    public long getPrice() { return price; }

    public void setNote(String note) { this.note = note; }
    public void setQty(int qty) { this.qty = Math.max(1, qty); recalc(); }
    public void setPrice(long price) { this.price = Math.max(0L, price); recalc(); }

    // ===== Discount getters =====
    public int getDiscountType() { return discountType; }
    public int getDiscountPercent() { return discountPercent; }
    public long getDiscountDirect() { return discountDirect; }
    public long getDiscountAmount() { return discountAmount; } // tiền giảm thực tế

    // ===== Tax getters =====
    public int getVatPercent() { return vatPercent; }
    public long getTaxAmount() { return taxAmount; }

    public long getBaseAmount() {
        return (long) Math.max(1, qty) * Math.max(0L, price);
    }

    /** Sau giảm giá (chưa thuế) */
    public long getAfterDiscountAmount() {
        long base = getBaseAmount();
        long d = Math.min(Math.max(0L, discountAmount), base);
        return Math.max(0L, base - d);
    }

    /** Thành tiền cuối của dòng (sau giảm + thuế dòng) */
    public long getFinalAmount() {
        return getAfterDiscountAmount() + Math.max(0L, taxAmount);
    }

    // ===== Set discount (giữ state để reopen đúng) =====
    public void setNoDiscount() {
        this.discountType = DISCOUNT_NONE;
        this.discountPercent = 0;
        this.discountDirect = 0L;
        recalc();
    }

    public void setDiscountPercent(int percent) {
        this.discountType = DISCOUNT_PERCENT;
        this.discountPercent = clamp(percent, 0, 100);
        this.discountDirect = 0L;
        recalc();
    }

    public void setDiscountDirect(long amount) {
        this.discountType = DISCOUNT_DIRECT;
        this.discountDirect = Math.max(0L, amount);
        this.discountPercent = 0;
        recalc();
    }

    /** giữ tương thích code cũ (nếu chỗ nào còn gọi setDiscountAmount) */
    public void setDiscountAmount(long discountAmount) {
        // nếu gọi setDiscountAmount thì xem như "direct"
        setDiscountDirect(discountAmount);
    }

    // ===== Set VAT per line =====
    public void setVatPercent(int percent) {
        this.vatPercent = clamp(percent, 0, 100);
        recalc();
    }

    private void recalc() {
        long base = getBaseAmount();

        // discountAmount
        long d = 0L;
        if (discountType == DISCOUNT_PERCENT) {
            d = Math.round(base * (discountPercent / 100.0));
        } else if (discountType == DISCOUNT_DIRECT) {
            d = discountDirect;
        }
        if (d < 0) d = 0;
        if (d > base) d = base;
        this.discountAmount = d;

        // taxAmount tính trên sau giảm
        long afterDiscount = Math.max(0L, base - d);
        if (vatPercent > 0) {
            this.taxAmount = Math.round(afterDiscount * (vatPercent / 100.0));
        } else {
            this.taxAmount = 0L;
        }
        if (this.taxAmount < 0) this.taxAmount = 0L;
    }

    private int clamp(int v, int min, int max) {
        if (v < min) return min;
        if (v > max) return max;
        return v;
    }
}
