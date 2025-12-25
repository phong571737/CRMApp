package com.example.crmmobile.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.text.TextUtils;
import android.util.Log;

import com.example.crmmobile.IndividualDirectory.CaNhan;
import com.example.crmmobile.LeadDirectory.Lead;
import com.example.crmmobile.LeadDirectory.Nhanvien;

import java.util.ArrayList;
import java.util.List;

public class NhanVienRepository {
    private DBCRMHandler dbHelper;
    private SQLiteDatabase db;

    public NhanVienRepository(Context context) {
        dbHelper = new DBCRMHandler(context);
    }

    public void AddNhanVien() {
        db = dbHelper.getWritableDatabase();

        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM NHANVIEN", null);
        cursor.moveToFirst();
        int count = cursor.getInt(0);
        cursor.close();
        if (count == 0) {
            insert(new Nhanvien("Phan Thị Tường Vy"));
            insert(new Nhanvien("Huỳnh Văn Tuấn Phong"));
            insert(new Nhanvien("Lê Thị Ánh Xuân"));
            insert(new Nhanvien("Nguyễn Đức Thành"));
            insert(new Nhanvien("Nguyễn Hữu Thiện"));
        }
    }

    private void insert(Nhanvien nv) {
        ContentValues values = new ContentValues();
        values.put("HOTEN", nv.getHoten());

        db.insert("NHANVIEN", null, values);
    }

    public List<Nhanvien> getAllNhanVien() {
        List<Nhanvien> list = new ArrayList<>();
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM NHANVIEN", null);

        if(cursor.moveToFirst()) {
            do {
                Nhanvien nv = new Nhanvien();
                nv.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
                nv.setHoten(cursor.getString(cursor.getColumnIndexOrThrow("HOTEN")));
                list.add(nv);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }

    public String getNameByID(int id){
        db = dbHelper.getReadableDatabase();
        Log.e("REPO_DEBUG", "Truy vấn ID: " + id);
        String name = "";
        Cursor cursor = db.rawQuery("SELECT HOTEN FROM NHANVIEN WHERE ID = ?", new String[]{String.valueOf(id)});
        if (cursor.moveToFirst()){
            name = cursor.getString(0);
        }
        Log.e("REPO_DEBUG", "Tên tìm thấy: " + name);
        cursor.close();
//        db.close();
        return name;
    }

    // Xuan them vao de lay du lieu id + name cua employee cho dropdown trong form
    public List<Nhanvien> getAllIdName() {
        List<Nhanvien> list = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT ID, HOTEN FROM NHANVIEN",
                null
        );

        Log.d("DEBUG_NhanvienRepo", "cursor count = " + cursor.getCount());

        while (cursor.moveToNext()) {
            Nhanvien cn = new Nhanvien();
            cn.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
            cn.setHoten(cursor.getString(cursor.getColumnIndexOrThrow("HOTEN")));

            list.add(cn);

            Log.d("DEBUG_NhanvienRepo",
                    "Loaded: id=" + cn.getId() + ", name=" + cn.getHoten());
        }

        cursor.close();
        db.close();

        return list;
    }

}
