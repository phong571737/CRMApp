package com.example.crmmobile.LeadDirectory;

import androidx.annotation.NonNull;

public class Nhanvien {
    private int id;
    private String hoten;
    private String email;
    private String password;
    private String role;

    public Nhanvien(int id, String hoten) {
        this.id = id;
        this.hoten = hoten;
    }
    public Nhanvien(String hoten){
        this.hoten = hoten;
    }

    public Nhanvien(){}

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getHoten() {
        return hoten;
    }

    @NonNull
    @Override
    public String toString(){
        return hoten;
    }

    public void setHoten(String hoten) {
        this.hoten = hoten;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }
}
