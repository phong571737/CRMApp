package com.example.crmmobile.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crmmobile.LeadDirectory.Lead;

import java.util.ArrayList;
import java.util.List;

public class LeadReposity {
    DBCRMHandler dbHelper;

    public LeadReposity(Context context){
        dbHelper = new DBCRMHandler(context);
    }

    //Add Lead
    public void addLead(Lead lead){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("ID", lead.getID());
        values.put("HOTEN", lead.getHoten());
        values.put("DIENTHOAI", lead.getDienThoai());
        values.put("EMAIL", lead.getEmail());
        values.put("NGAYSINH", lead.getNgaysinh());
        values.put("GIOITINH", lead.getGioitinh());
        values.put("DIACHI", lead.getDiachi());
        values.put("CHUCVU", lead.getChucvu());
        values.put("CONGTY", lead.getCongty());
        values.put("TINHTRANG", lead.getTinhTrang());
        values.put("MOTA", lead.getMota());
        values.put("GIAOCHO", lead.getGiaocho());
        values.put("NGAYLIENHE", lead.getNgayLienHe());

        db.insert("LEAD", null, values);
        db.close();
    }

    //Get Lead
    public List<Lead> getAllLead(){
        List<Lead> list = new ArrayList<>();
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM LEAD", null );

        if(cursor.moveToFirst()){
            do {
                Lead lead = new Lead();
                lead.setID(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
                lead.setHoten(cursor.getString(cursor.getColumnIndexOrThrow("HOTEN")));
                lead.setDienThoai(cursor.getString(cursor.getColumnIndexOrThrow("DIENTHOAI")));
                lead.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("EMAIL")));
                lead.setNgaysinh(cursor.getString(cursor.getColumnIndexOrThrow("NGAYSINH")));
                lead.setGioitinh(cursor.getString(cursor.getColumnIndexOrThrow("GIOITINH")));
                lead.setDiachi(cursor.getString(cursor.getColumnIndexOrThrow("DIACHI")));
                lead.setChucvu(cursor.getString(cursor.getColumnIndexOrThrow("CHUCVU")));
                lead.setCongty(cursor.getString(cursor.getColumnIndexOrThrow("CONGTY")));
                lead.setTinhTrang(cursor.getString(cursor.getColumnIndexOrThrow("TINHTRANG")));
                lead.setMota(cursor.getString(cursor.getColumnIndexOrThrow("MOTA")));
                lead.setGiaocho(cursor.getString(cursor.getColumnIndexOrThrow("GIAOCHO")));
                lead.setNgayLienHe(cursor.getString(cursor.getColumnIndexOrThrow("NGAYLIENHE")));
            }while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return list;
    }

    //Update Lead
    public int updateLead(Lead lead){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("HOTEN", lead.getHoten());
        values.put("DIENTHOAI", lead.getDienThoai());
        values.put("EMAIL", lead.getEmail());
        values.put("NGAYSINH", lead.getNgaysinh());
        values.put("GIOITINH", lead.getGioitinh());
        values.put("DIACHI", lead.getDiachi());
        values.put("CHUCVU", lead.getChucvu());
        values.put("CONGTY", lead.getCongty());
        values.put("TINHTRANG", lead.getTinhTrang());
        values.put("MOTA", lead.getMota());
        values.put("GIAOCHO", lead.getGiaocho());
        values.put("NGAYLIENHE", lead.getNgayLienHe());


        int result = db.update("LEAD", values, "ID=?", new String[]{String.valueOf(lead.getID())});
        db.close();
        return result;
    }

    //Delete Lead
    public void DeleteLead(int id){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        db.delete("LEAD", "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }
}
