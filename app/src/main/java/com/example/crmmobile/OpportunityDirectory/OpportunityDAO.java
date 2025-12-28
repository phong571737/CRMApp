package com.example.crmmobile.OpportunityDirectory;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crmmobile.DataBase.DBCRMHandler;

import java.util.ArrayList;
import java.util.List;

public class OpportunityDAO {
    private DBCRMHandler DBCRMHandler;

    public OpportunityDAO(Context context) {
        DBCRMHandler = new DBCRMHandler(context);
    }

    public long add(Opportunity opportunity) {
        long id = -1;
        SQLiteDatabase db = DBCRMHandler.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("TENCOHOI", opportunity.getTitle());
            values.put("CONGTY", opportunity.getCompany());
            values.put("NGUOILIENHE", opportunity.getContact());
            values.put("GIATRI", opportunity.getPrice());
            values.put("BUOCBANHANG", opportunity.getStatus());
            values.put("NGAYTAO", opportunity.getDate());
            values.put("NGAYCHOT", opportunity.getExpectedDate2());
            values.put("MOTA", opportunity.getDescription());
            values.put("GIAOCHO", opportunity.getManagement());
            id = db.insert("COHOI", null, values);
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
        return id;
    }

    public void update(Opportunity opportunity) {
        SQLiteDatabase db = DBCRMHandler.getWritableDatabase();
        try {
            ContentValues values = new ContentValues();
            values.put("TENCOHOI", opportunity.getTitle());
            values.put("CONGTY", opportunity.getCompany());
            values.put("NGUOILIENHE", opportunity.getContact());
            values.put("GIATRI", opportunity.getPrice());
            values.put("BUOCBANHANG", opportunity.getStatus());
            values.put("NGAYTAO", opportunity.getDate());
            values.put("NGAYCHOT", opportunity.getExpectedDate2());
            values.put("MOTA", opportunity.getDescription());
            values.put("GIAOCHO", opportunity.getManagement());

            db.update("COHOI", values, "ID=?", new String[]{String.valueOf(opportunity.getId())});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public void delete(int id) {
        SQLiteDatabase db = DBCRMHandler.getWritableDatabase();
        try {
            db.delete("COHOI", "ID=?", new String[]{String.valueOf(id)});
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            db.close();
        }
    }

    public Opportunity getById(int id) {
        Opportunity opp = null;
        SQLiteDatabase db = DBCRMHandler.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM COHOI WHERE ID=?", new String[]{String.valueOf(id)});
            if (cursor.moveToFirst()) {
                opp = cursorToOpportunity(cursor);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return opp;
    }
    public List<Opportunity> getAll() {
        List<Opportunity> list = new ArrayList<>();
        SQLiteDatabase db = DBCRMHandler.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM COHOI", null);
            while (cursor.moveToNext()) {
                list.add(cursorToOpportunity(cursor));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return list;
    }

    public List<Opportunity> searchByKeyword(String keyword) {
        List<Opportunity> list = new ArrayList<>();
        SQLiteDatabase db = DBCRMHandler.getReadableDatabase();
        Cursor cursor = null;

        try {
            String sql = "SELECT * FROM COHOI " +
                    "WHERE TENCOHOI LIKE ? " +
                    "OR MOTA LIKE ?";

            String key = "%" + keyword + "%";

            cursor = db.rawQuery(sql, new String[]{key, key});

            while (cursor.moveToNext()) {
                list.add(cursorToOpportunity(cursor));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }

        return list;
    }

    public List<Opportunity> getByContactId(int contactId) {
        List<Opportunity> list = new ArrayList<>();
        SQLiteDatabase db = DBCRMHandler.getReadableDatabase();
        Cursor cursor = null;
        try {
            cursor = db.rawQuery("SELECT * FROM COHOI WHERE NGUOILIENHE = ? ORDER BY ID DESC", new String[]{String.valueOf(contactId)});
            while (cursor.moveToNext()) {
                list.add(cursorToOpportunity(cursor));
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (cursor != null) cursor.close();
            db.close();
        }
        return list;
    }


    private Opportunity cursorToOpportunity(Cursor cursor) {
        return new Opportunity(
                cursor.getInt(cursor.getColumnIndexOrThrow("ID")),
                cursor.getString(cursor.getColumnIndexOrThrow("TENCOHOI")),
                cursor.getInt(cursor.getColumnIndexOrThrow("CONGTY")),
                cursor.getInt(cursor.getColumnIndexOrThrow("NGUOILIENHE")),
                cursor.getDouble(cursor.getColumnIndexOrThrow("GIATRI")),
                cursor.getString(cursor.getColumnIndexOrThrow("BUOCBANHANG")),
                cursor.getString(cursor.getColumnIndexOrThrow("NGAYTAO")),
                cursor.getString(cursor.getColumnIndexOrThrow("NGAYCHOT")),
                cursor.getString(cursor.getColumnIndexOrThrow("MOTA")),
                cursor.getInt(cursor.getColumnIndexOrThrow("GIAOCHO")),
                0, 0 // callCount, messageCount tạm thời
        );
    }

}
