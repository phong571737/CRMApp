package com.example.crmmobile;

public class item_quote {
    private String label;
    private String content;

    public item_quote(String label, String content){
        this.label = label;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }
}
