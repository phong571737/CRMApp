package com.example.crmmobile;

import java.util.Objects;

public class Achievement {
    private String title;
    private String value;
    private String rank;

    public Achievement() { }

    public Achievement(String title, String value, String rank) {
        this.title = title;
        this.value = value;
        this.rank = rank;
    }

    // Getters
    public String getTitle() {
        return title;
    }

    public String getValue() {
        return value;
    }

    public String getRank() {
        return rank;
    }

    // Setters
    public void setTitle(String title) {
        this.title = title;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    // Convenience factory
    public static Achievement of(String title, String value, String rank) {
        return new Achievement(title, value, rank);
    }

    @Override
    public String toString() {
        return "Achievement{" +
                "title='" + title + '\'' +
                ", value='" + value + '\'' +
                ", rank='" + rank + '\'' +
                '}';
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Achievement)) return false;
        Achievement that = (Achievement) o;
        return Objects.equals(title, that.title) &&
                Objects.equals(value, that.value) &&
                Objects.equals(rank, that.rank);
    }

    @Override
    public int hashCode() {
        return Objects.hash(title, value, rank);
    }

}
