package com.example.crmmobile.OpportunityDirectory;


import androidx.annotation.NonNull;

public class Opportunity {

    private int callCount;
    private int messageCount;

    private long createdAt;
    private long updatedAt;

    private int id;                 // Id cơ hội
    private String title;          // Tên cơ hội
    private int company;        // Công ty
    private int contact;        // Liên hệ
    private double price;          // Giá trị (Value)
    private String status;         // Sales stage
    private String date;           // Expected close date
    private String expectedDate2;  // Expected close date 2
    private String description;   // Description
    private int management;     // Management (user id)

    public Opportunity() {} // empty constructor

    // Constructor dùng cho list (ngắn gọn)
    public Opportunity(int id, String title, double price, String date, String status,
                       int callCount, int messageCount, String description) {
        this.id = id;
        this.title = title;
        this.price = price;
        this.date = date;
        this.status = status;
        this.callCount = callCount;
        this.messageCount = messageCount;
        this.description = description;
    }

    // Constructor đầy đủ (dùng cho detail)
    public Opportunity(int id, String title, int company, int contact, double price,
                       String status, String date,
                       String expectedDate2, String description, int management,int callCount, int messageCount) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.contact = contact;
        this.price = price;
        this.status = status;
        this.date = date;
        this.expectedDate2 = expectedDate2;
        this.description = description;
        this.management = management;
    }

    // Constructor cho tạo mới (không có id)
    public Opportunity(String title, int company, int contact,
                       double price, String status, String date, String expectedDate2,
                       String description, int management) {
        this(0, title, company, contact, price, status, date, expectedDate2,
                description, management, 0, 0);
    }

    // Constructor for detail with timestamp
    public Opportunity(int id, String title, int company, int contact, double price,
                       String status, String date,
                       String expectedDate2, String description, int management, long createdAt, long updatedAt) {
        this.id = id;
        this.title = title;
        this.company = company;
        this.contact = contact;
        this.price = price;
        this.status = status;
        this.date = date;
        this.expectedDate2 = expectedDate2;
        this.description = description;
        this.management = management;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }

    // --- GETTER ---

    public int getCallCount() { return callCount; }
    public int getMessageCount() { return messageCount; }

    public Integer getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public int getCompany() {
        return company;
    }

    public int getContact() {
        return contact;
    }

    public double getPrice() {
        return price;
    }

    public String getStatus() {
        return status;
    }


    public String getDate() {
        return date;
    }

    public String getExpectedDate2() {
        return expectedDate2;
    }

    public String getDescription() {
        return description;
    }

    public int getManagement() {
        return management;
    }

    public long getCreatedAt() { return createdAt; }
    public long getUpdatedAt() { return updatedAt; }


    // --- SETTER (nếu bạn cần cập nhật sau này) ---
    public void setId(int id){
        this.id = id;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public void setCompany(int company) {
        this.company = company;
    }

    public void setContact(int contact) {
        this.contact = contact;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setExpectedDate2(String expectedDate2) {
        this.expectedDate2 = expectedDate2;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setManagement(int management) {
        this.management = management;
    }

    public void setCreatedAt(long createdAt) {
        this.createdAt = createdAt;
    }

    public void setUpdatedAt(long updatedAt) {
        this.updatedAt = updatedAt;
    }

    @NonNull
    @Override
    public String toString() {
        return getTitle();
    }
}
