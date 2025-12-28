package com.example.crmmobile.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
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
            // Insert admin account
            insertAdmin();
            // Insert other employees with default password
            insert(new Nhanvien("Phan Thị Tường Vi"), "pttv@gmail.com", "123456");
            insert(new Nhanvien("Huỳnh Văn Tuấn Phong"), "hvtt@gmail.com", "123456");
            insert(new Nhanvien("Lê Thị Ánh Xuân"), "ltax@gmail.com", "123456");
            insert(new Nhanvien("Nguyễn Đức Thành"), "ndt@gmail.com", "123456");
            insert(new Nhanvien("Nguyễn Hữu Thiện"), "nht@gmail.com", "123456");
        } else {
            // Update existing employees with default password if they don't have one
            updateDefaultPasswords();
        }
    }

    private void insertAdmin() {
        ContentValues values = new ContentValues();
        values.put("HOTEN", "Admin");
        values.put("EMAIL", "admin@gmail.com");
        values.put("MATKHAU", "123456");
        values.put("ROLE", "ADMIN");
        values.put("TRANGTHAI", "Đang làm việc");
        
        db.insert("NHANVIEN", null, values);
    }

    private void insert(Nhanvien nv, String email, String password) {
        ContentValues values = new ContentValues();
        values.put("HOTEN", nv.getHoten());
        values.put("EMAIL", email);
        values.put("MATKHAU", password);
        values.put("ROLE", "USER");
        values.put("TRANGTHAI", "Đang làm việc");

        db.insert("NHANVIEN", null, values);
    }

    private void updateDefaultPasswords() {
        // Update all employees without password to default password
        ContentValues values = new ContentValues();
        values.put("MATKHAU", "123456");
        db.update("NHANVIEN", values, "MATKHAU IS NULL OR MATKHAU = ''", null);
        
        // Ensure admin account exists
        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM NHANVIEN WHERE EMAIL = ?", new String[]{"admin@gmail.com"});
        cursor.moveToFirst();
        int adminCount = cursor.getInt(0);
        cursor.close();
        
        if (adminCount == 0) {
            insertAdmin();
        }
    }

    public Nhanvien authenticate(String email, String password) {
        db = dbHelper.getReadableDatabase();
        Nhanvien nv = null;
        
        Cursor cursor = db.rawQuery(
            "SELECT * FROM NHANVIEN WHERE EMAIL = ? AND MATKHAU = ?",
            new String[]{email, password}
        );
        
        if (cursor.moveToFirst()) {
            nv = new Nhanvien();
            nv.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
            nv.setHoten(cursor.getString(cursor.getColumnIndexOrThrow("HOTEN")));
            nv.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("EMAIL")));
            nv.setPassword(cursor.getString(cursor.getColumnIndexOrThrow("MATKHAU")));
            int roleIndex = cursor.getColumnIndex("ROLE");
            if (roleIndex >= 0 && !cursor.isNull(roleIndex)) {
                nv.setRole(cursor.getString(roleIndex));
            }
        }
        
        cursor.close();
        return nv;
    }

    public String getRoleByUserId(int userId) {
        db = dbHelper.getReadableDatabase();
        String role = null;
        
        Cursor cursor = db.rawQuery(
            "SELECT ROLE FROM NHANVIEN WHERE ID = ?",
            new String[]{String.valueOf(userId)}
        );
        
        if (cursor.moveToFirst()) {
            int roleIndex = cursor.getColumnIndex("ROLE");
            if (roleIndex >= 0 && !cursor.isNull(roleIndex)) {
                role = cursor.getString(roleIndex);
            }
        }
        
        cursor.close();
        return role;
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
        Log.e("REPO_DEBUG", "ID: " + id);
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
