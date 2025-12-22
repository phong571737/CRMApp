package com.example.crmmobile.OrganizationDirectory;

import java.io.Serializable;
import java.util.List;

public class ToChuc implements Serializable {

    // Enum trạng thái
    public enum TrangThai {
        KHONG_QUAN_TAM, CO_CO_HOI, CAN_QUAN_TAM, NONE
    }

    private int id; // ID Database

    // Tab 1: Thông tin công ty
    private String companyName;
    private TrangThai trangThai;
    private String website;
    private String phone;
    private String email;
    private String industry;

    // Tab 2: Thông tin khác
    private String address;
    private String district;
    private String city;
    private String country;

    private String buyingStatus; // Tình trạng mua hàng
    private int orderCount;
    private String firstPurchaseDate;
    private String lastPurchaseDate;
    private double totalRevenue;
    private String assignedTo; // Giao cho

    // UI Helper (giữ lại để không lỗi adapter cũ)
    private boolean isStarred;
    private boolean showTraoDoi;
    private List<Integer> avatarDrawables;

    public ToChuc() { }

    // Constructor rút gọn cho dữ liệu mẫu (để Adapter không lỗi)
    public ToChuc(String companyName, String industry, String date, boolean isStarred,
                  TrangThai trangThai, String phoneCount, String messageCount,
                  boolean showTraoDoi, List<Integer> avatarDrawables) {
        this.companyName = companyName;
        this.industry = industry;
        this.lastPurchaseDate = date;
        this.isStarred = isStarred;
        this.trangThai = trangThai;
        this.showTraoDoi = showTraoDoi;
        this.avatarDrawables = avatarDrawables;
    }

    // Getters & Setters đầy đủ
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public TrangThai getTrangThai() { return trangThai; }
    public void setTrangThai(TrangThai trangThai) { this.trangThai = trangThai; }
    public String getWebsite() { return website; }
    public void setWebsite(String website) { this.website = website; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getIndustry() { return industry; }
    public void setIndustry(String industry) { this.industry = industry; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getDistrict() { return district; }
    public void setDistrict(String district) { this.district = district; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getBuyingStatus() { return buyingStatus; }
    public void setBuyingStatus(String buyingStatus) { this.buyingStatus = buyingStatus; }
    public int getOrderCount() { return orderCount; }
    public void setOrderCount(int orderCount) { this.orderCount = orderCount; }
    public String getFirstPurchaseDate() { return firstPurchaseDate; }
    public void setFirstPurchaseDate(String firstPurchaseDate) { this.firstPurchaseDate = firstPurchaseDate; }
    public String getLastPurchaseDate() { return lastPurchaseDate; }
    public void setLastPurchaseDate(String lastPurchaseDate) { this.lastPurchaseDate = lastPurchaseDate; }
    public double getTotalRevenue() { return totalRevenue; }
    public void setTotalRevenue(double totalRevenue) { this.totalRevenue = totalRevenue; }
    public String getAssignedTo() { return assignedTo; }
    public void setAssignedTo(String assignedTo) { this.assignedTo = assignedTo; }
    public boolean isStarred() { return isStarred; }
    public void setStarred(boolean starred) { isStarred = starred; }
    public boolean isShowTraoDoi() { return showTraoDoi; }
    public void setShowTraoDoi(boolean showTraoDoi) { this.showTraoDoi = showTraoDoi; }
    public List<Integer> getAvatarDrawables() { return avatarDrawables; }
    public void setAvatarDrawables(List<Integer> avatarDrawables) { this.avatarDrawables = avatarDrawables; }

    // Support Adapter cũ
    public String getDate() { return lastPurchaseDate != null ? lastPurchaseDate : ""; }
    public String getPhoneCount() { return "0"; }
    public String getMessageCount() { return "0"; }
}
