package com.example.crmmobile.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crmmobile.LeadDirectory.Lead;
import com.example.crmmobile.LeadDirectory.Nhanvien;
import com.example.crmmobile.SanPhamDirectory.SanPham;

public class SanPhamRepository {
    DBCRMHandler dbHelper;

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

//    public void AddNhanVien() {
//        db = dbHelper.getWritableDatabase();
//
//        Cursor cursor = db.rawQuery("SELECT COUNT(*) FROM NHANVIEN", null);
//        cursor.moveToFirst();
//        int count = cursor.getInt(0);
//        cursor.close();
//        if (count == 0) {
//            insert(new Nhanvien("Phan Thị Tường Vy"));
//            insert(new Nhanvien("Huỳnh Văn Tuấn Phong"));
//            insert(new Nhanvien("Lê Thị Ánh Xuân"));
//            insert(new Nhanvien("Nguyễn Đức Thành"));
//            insert(new Nhanvien("Nguyễn Hữu Thiện"));
//        }
//    }
}
