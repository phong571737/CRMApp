package com.example.crmmobile;

public class Quote {
    String code;
    String company;
    String date;

    public Quote(String code, String company, String date){
        this.code = code;
        this.company = company;
        this.date = date;
    }
    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }
}
