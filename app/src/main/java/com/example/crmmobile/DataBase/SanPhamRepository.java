package com.example.crmmobile.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crmmobile.LeadDirectory.Lead;
import com.example.crmmobile.LeadDirectory.Nhanvien;
import com.example.crmmobile.SanPhamDirectory.SanPham;

import java.util.ArrayList;
import java.util.List;

public class SanPhamRepository {
    DBCRMHandler dbHelper;
    private SQLiteDatabase db;

    public SanPhamRepository(Context context){
        dbHelper = new DBCRMHandler(context);
    }

    //Add SP
    public long addSanPham(SanPham sp){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("TEN", sp.getName());
        values.put("MOTA", sp.getMota());
        values.put("DONGIA", sp.getDonggia());
        values.put("TRANGTHAI", sp.getTrangthai());
        values.put("NGAYTAO", sp.getNgaytao());
        values.put("NGUOITAO", sp.getNguoitao());
        values.put("MOTA_THEM", sp.getMotaThem());

        long newId = db.insert("SANPHAM", null, values);
        db.close();
        return  newId;
    }

    public void addProduct() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM SANPHAM", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        db.close();

        if (count == 0) {
            addSanPham(new SanPham(
                    "Cam AI HA800",
                    "",
                    4090000L,
                    "Đang bán",
                    "27/12/2025",
                    "Tuấn Phong",
                    ""
            ));

            addSanPham(new SanPham(
                    "CloudWORK - Giải pháp quản lý dự án chuyên nghiệp",
                    "",
                    1290000L,
                    "Đang bán",
                    "27/12/2025",
                    "Tuấn Phong",
                    ""
            ));

            addSanPham(new SanPham(
                    "CloudCheckin",
                    "",
                    1290000L,
                    "Đang bán",
                    "27/12/2025",
                    "Tuấn Phong",
                    ""
            ));

            addSanPham(new SanPham(
                    "CloudLead",
                    "",
                    1290000L,
                    "Đang bán",
                    "27/12/2025",
                    "Tuấn Phong",
                    ""
            ));
        }
    }


    public List<SanPham> getAllSanPham() {
        List<SanPham> list = new ArrayList<>();
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM SANPHAM", null);

        if(cursor.moveToFirst()) {
            do {
                SanPham sp = new SanPham();
                sp.setID(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
                sp.setName(cursor.getString(cursor.getColumnIndexOrThrow("TEN")));
                list.add(sp);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
