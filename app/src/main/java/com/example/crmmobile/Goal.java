package com.example.crmmobile;

public class Goal {
    private String title;
    private String progress;

    public Goal(String title, String progress) {
        this.title = title;
        this.progress = progress;
    }

    public String getTitle() {
        return title;
    }

    public String getProgress() {
        return progress;
    }
}
