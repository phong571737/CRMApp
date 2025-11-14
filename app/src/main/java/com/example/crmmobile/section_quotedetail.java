package com.example.crmmobile;

import java.util.List;

public class section_quotedetail {
    private String title;
    private List<item_quote> items;

    public section_quotedetail(String title, List<item_quote> items){
        this.title = title;
        this.items = items;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<item_quote> getItems() {
        return items;
    }

    public void setItems(List<item_quote> items) {
        this.items = items;
    }
}
