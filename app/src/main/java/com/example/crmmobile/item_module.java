package com.example.crmmobile;

public class item_module {
    private String name;
    private int iconimg;

    public item_module(String name, int iconimg){
        this.iconimg = iconimg;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getIconimg() {
        return iconimg;
    }

    public void setIconimg(int iconimg) {
        this.iconimg = iconimg;
    }
}
