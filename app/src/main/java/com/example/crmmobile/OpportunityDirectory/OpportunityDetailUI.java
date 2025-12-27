package com.example.crmmobile.OpportunityDirectory;

public class OpportunityDetailUI {
    public final int id;
    public final String title;

    public final int companyId;
    public final String companyName;

    public final int contactId;
    public final String contactName;

    public final int managementId;
    public final String managementName;

    public final double price;
    public final String status;
    public final String date;
    public final String expectedDate2;
    public final String description;

    public final String createdAt;
    public final String updatedAt;

    public OpportunityDetailUI(
            int id,
            String title,
            int companyId,
            String companyName,
            int contactId,
            String contactName,
            int managementId,
            String managementName,
            double price,
            String status,
            String date,
            String expectedDate2,
            String description,
            String createdAt,
            String updatedAt
    ) {
        this.id = id;
        this.title = title;
        this.companyId = companyId;
        this.companyName = companyName;
        this.contactId = contactId;
        this.contactName = contactName;
        this.managementId = managementId;
        this.managementName = managementName;
        this.price = price;
        this.status = status;
        this.date = date;
        this.expectedDate2 = expectedDate2;
        this.description = description;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
    }
}
