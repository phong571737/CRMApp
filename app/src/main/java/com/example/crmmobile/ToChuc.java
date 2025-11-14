package com.example.crmmobile;

import java.util.List;

public class ToChuc {

    public enum TrangThai {
        NONE,
        KHONG_QUAN_TAM,
        CO_CO_HOI,
        CAN_QUAN_TAM
    }

    private String companyName;
    private String industry;
    private String date;
    private boolean isStarred;
    private TrangThai trangThai;
    private String phoneCount;
    private String messageCount;
    private boolean showTraoDoi;
    private List<Integer> avatarDrawables;

    public ToChuc(String companyName, String industry, String date, boolean isStarred,
                  TrangThai trangThai, String phoneCount, String messageCount,
                  boolean showTraoDoi, List<Integer> avatarDrawables) {
        this.companyName = companyName;
        this.industry = industry;
        this.date = date;
        this.isStarred = isStarred;
        this.trangThai = trangThai;
        this.phoneCount = phoneCount;
        this.messageCount = messageCount;
        this.showTraoDoi = showTraoDoi;
        this.avatarDrawables = avatarDrawables;
    }

    // Getters
    public String getCompanyName() { return companyName; }
    public String getIndustry() { return industry; }
    public String getDate() { return date; }
    public boolean isStarred() { return isStarred; }
    public TrangThai getTrangThai() { return trangThai; }
    public String getPhoneCount() { return phoneCount; }
    public String getMessageCount() { return messageCount; }
    public boolean isShowTraoDoi() { return showTraoDoi; }
    public List<Integer> getAvatarDrawables() { return avatarDrawables; }
}
