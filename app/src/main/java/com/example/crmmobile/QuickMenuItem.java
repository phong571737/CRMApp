package com.example.crmmobile;
public class QuickMenuItem {
    private int iconResId;
    private String title;

    public QuickMenuItem(int iconResId, String title) {
        this.iconResId = iconResId;
        this.title = title;
    }

    public int getIconResId() {
        return iconResId;
    }

    public String getTitle() {
        return title;
    }
}
