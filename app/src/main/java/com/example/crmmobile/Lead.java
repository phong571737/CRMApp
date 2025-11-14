package com.example.crmmobile;

public class Lead {
    private String name;
    private String company;
    private String daycontact;

    public Lead(String name, String company, String daycontact){
        this.name = name;
        this.company = company;
        this.daycontact = daycontact;
    }

    public String getName(){return name;}
    public String getCompany(){return company;}
    public String getDaycontact(){return daycontact;}
}
