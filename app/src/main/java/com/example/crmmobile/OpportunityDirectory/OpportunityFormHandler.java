
package com.example.crmmobile.OpportunityDirectory;

import android.text.TextUtils;

import com.example.crmmobile.IndividualDirectory.CaNhan;
import com.example.crmmobile.LeadDirectory.Nhanvien;
import com.example.crmmobile.OrganizationDirectory.ToChuc;

import org.jspecify.annotations.Nullable;

import java.text.ParseException;
import java.util.List;

public class OpportunityFormHandler {

    public Opportunity createFromForm(
            String mode,
            Opportunity existing,
            String title,
            String priceStr,
            String stage,
            String date1,
            String date2,
            String desc,
            int companyId,
            int contactId,
            int managementId
    ) throws ParseException {

        // ================= UPDATE =================
        if (MODE_UPDATE.equals(mode) && existing != null) {


            if (!TextUtils.isEmpty(title)) {
                existing.setTitle(title);
            }

            if (!TextUtils.isEmpty(priceStr)) {
                existing.setPrice(parsePrice(priceStr));
            }

            if (!TextUtils.isEmpty(stage)) {
                existing.setStatus(stage);
            }

            if (!TextUtils.isEmpty(date1)) {
                existing.setDate(date1);
            }

            if (!TextUtils.isEmpty(date2)) {
                existing.setExpectedDate2(date2);
            }

            if (!TextUtils.isEmpty(desc)) {
                existing.setDescription(desc);
            }

            if (companyId > 0) {
                existing.setCompany(companyId);
            }

            if (contactId > 0) {
                existing.setContact(contactId);
            }

            if (managementId > 0) {
                existing.setManagement(managementId);
            }

            return existing;
        }

        // ================= CREATE =================
        double price = parsePrice(priceStr);

        return new Opportunity(
                0,
                title,
                companyId,
                contactId,
                price,
                stage,
                date1,
                date2,
                desc,
                managementId,
                0,
                0
        );
    }


    public boolean validateTitle(String title) {
        return !TextUtils.isEmpty(title);
    }

    public double parsePrice(String raw) {
        try {
            String cleaned = raw.replaceAll("[^\\d.]", "");
            if (cleaned.isEmpty()) return 0;
            return Double.parseDouble(cleaned);
        } catch (Exception e) {
            return 0;
        }
    }

    /** Giúp Fragment tìm text theo ID khi populate form */
    public String findCompanyName(int id, @Nullable List<ToChuc> list) {
        for (ToChuc c : list) if (c.getId() == id) return c.getCompanyName();
        return "";
    }

    public String findContactName(int id, @Nullable List<CaNhan> list) {
        for (CaNhan c : list) if (c.getId() == id) return c.getHoVaTen();
        return "";
    }

    public String findEmployeeName(int id, @Nullable List<Nhanvien> list) {
        for (Nhanvien e : list) if (e.getId() == id) return e.getHoten();
        return "";
    }

    public static final String MODE_CREATE = "create";
    public static final String MODE_UPDATE = "update";

    public String formatSelectedDate(int day, int month, int year) {
        return day + "/" + (month + 1) + "/" + year;
    }

}
