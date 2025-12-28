package com.example.crmmobile.DataBase;

import static android.content.ContentValues.TAG;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.crmmobile.HoatDongDirectory.HoatDong;

import java.util.ArrayList;

public class HoatDongRepository {
    private static final String TAG = "HoatDongRepository";


    private DBCRMHandler dbHandler;

    public HoatDongRepository(Context context) {
        dbHandler = new DBCRMHandler(context);
    }

    public long add(HoatDong hoatDong) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();

        ContentValues values = new ContentValues();
        values.put("TENHOATDONG", hoatDong.getTenHoatDong());
        values.put("THOIGIANBATDAU", hoatDong.getThoiGianBatDau());
        values.put("THOIGIANKETTHUC", hoatDong.getThoiGianKetThuc());
        values.put("NGAYBATDAU", hoatDong.getNgayBatDau());
        values.put("TINHTRANG", hoatDong.getTinhTrang());

        // Xử lý NHANVIEN: nếu = 0 thì set NULL để tránh lỗi foreign key
        if (hoatDong.getNhanVien() > 0) {
            values.put("NHANVIEN", hoatDong.getNhanVien());
        } else {
            values.putNull("NHANVIEN");
        }

        // Xử lý TOCHUC: nếu = 0 thì set NULL để tránh lỗi foreign key
        if (hoatDong.getToChuc() > 0) {
            values.put("TOCHUC", hoatDong.getToChuc());
        } else {
            values.putNull("TOCHUC");
        }

        // Xử lý NGUOILIENHE: nếu = 0 thì set NULL để tránh lỗi foreign key
        if (hoatDong.getNguoiLienHe() > 0) {
            values.put("NGUOILIENHE", hoatDong.getNguoiLienHe());
        } else {
            values.putNull("NGUOILIENHE");
        }

        // Xử lý COHOI: nếu = 0 thì set NULL để tránh lỗi foreign key
        if (hoatDong.getCoHoi() > 0) {
            values.put("COHOI", hoatDong.getCoHoi());
        } else {
            values.putNull("COHOI");
        }

        values.put("MOTA", hoatDong.getMoTa());

        // Xử lý GIAOCHO: nếu = 0 thì set NULL để tránh lỗi foreign key
        if (hoatDong.getGiaoCho() > 0) {
            values.put("GIAOCHO", hoatDong.getGiaoCho());
        } else {
            values.putNull("GIAOCHO");
        }

        if (hoatDong.getType() != null) {
            values.put("TYPE", hoatDong.getType());
        } else {
            values.putNull("TYPE");
        }

        long result = -1;
        try {
            result = db.insert("HOATDONG", null, values);
            if (result == -1) {
                Log.e(TAG, "Insert failed - result is -1");
            } else {
                Log.d(TAG, "Insert successful with ID: " + result);
            }
        } catch (Exception e) {
            Log.e(TAG, "Error inserting HoatDong", e);
            result = -1;
        } finally {
            db.close();
        }

        return result;
    }
    public ArrayList<HoatDong> getAllHoatDong() {
        ArrayList<HoatDong> list = new ArrayList<>();
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM HOATDONG ORDER BY THOIGIANBATDAU DESC",
                null
        );

        if (cursor.moveToFirst()) {
            do {
                int nhanVienIndex = cursor.getColumnIndexOrThrow("NHANVIEN");
                int toChucIndex = cursor.getColumnIndexOrThrow("TOCHUC");
                int nguoiLienHeIndex = cursor.getColumnIndexOrThrow("NGUOILIENHE");
                int coHoiIndex = cursor.getColumnIndexOrThrow("COHOI");
                int giaoChoIndex = cursor.getColumnIndexOrThrow("GIAOCHO");
                int typeIndex = cursor.getColumnIndex("TYPE");

                // Xử lý NULL cho các foreign keys
                int nhanVien = cursor.isNull(nhanVienIndex) ? 0 : cursor.getInt(nhanVienIndex);
                int toChuc = cursor.isNull(toChucIndex) ? 0 : cursor.getInt(toChucIndex);
                int nguoiLienHe = cursor.isNull(nguoiLienHeIndex) ? 0 : cursor.getInt(nguoiLienHeIndex);
                int coHoi = cursor.isNull(coHoiIndex) ? 0 : cursor.getInt(coHoiIndex);
                int giaoCho = cursor.isNull(giaoChoIndex) ? 0 : cursor.getInt(giaoChoIndex);
                String type = cursor.isNull(typeIndex) ? null : cursor.getString(typeIndex);

                HoatDong hd = new HoatDong(
                        cursor.getInt(cursor.getColumnIndexOrThrow("ID")),
                        cursor.getString(cursor.getColumnIndexOrThrow("TENHOATDONG")),
                        cursor.getString(cursor.getColumnIndexOrThrow("THOIGIANBATDAU")),
                        cursor.getString(cursor.getColumnIndexOrThrow("THOIGIANKETTHUC")),
                        cursor.getString(cursor.getColumnIndexOrThrow("NGAYBATDAU")),
                        cursor.getString(cursor.getColumnIndexOrThrow("TINHTRANG")),
                        nhanVien,
                        toChuc,
                        nguoiLienHe,
                        coHoi,
                        cursor.getString(cursor.getColumnIndexOrThrow("MOTA")),
                        giaoCho,
                        type
                );

                list.add(hd);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<HoatDong> getHoatDongByNguoiLienHe(int nguoiLienHeId) {
        ArrayList<HoatDong> list = new ArrayList<>();
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM HOATDONG WHERE NGUOILIENHE = ? ORDER BY THOIGIANBATDAU DESC",
                new String[]{String.valueOf(nguoiLienHeId)}
        );

        if (cursor.moveToFirst()) {
            do {
                int nhanVienIndex = cursor.getColumnIndexOrThrow("NHANVIEN");
                int toChucIndex = cursor.getColumnIndexOrThrow("TOCHUC");
                int nguoiLienHeIndex = cursor.getColumnIndexOrThrow("NGUOILIENHE");
                int coHoiIndex = cursor.getColumnIndexOrThrow("COHOI");
                int giaoChoIndex = cursor.getColumnIndexOrThrow("GIAOCHO");
                int typeIndex = cursor.getColumnIndex("TYPE");

                // Xử lý NULL cho các foreign keys
                int nhanVien = cursor.isNull(nhanVienIndex) ? 0 : cursor.getInt(nhanVienIndex);
                int toChuc = cursor.isNull(toChucIndex) ? 0 : cursor.getInt(toChucIndex);
                int nguoiLienHe = cursor.isNull(nguoiLienHeIndex) ? 0 : cursor.getInt(nguoiLienHeIndex);
                int coHoi = cursor.isNull(coHoiIndex) ? 0 : cursor.getInt(coHoiIndex);
                int giaoCho = cursor.isNull(giaoChoIndex) ? 0 : cursor.getInt(giaoChoIndex);
                String type = cursor.isNull(typeIndex) ? null : cursor.getString(typeIndex);

                HoatDong hd = new HoatDong(
                        cursor.getInt(cursor.getColumnIndexOrThrow("ID")),
                        cursor.getString(cursor.getColumnIndexOrThrow("TENHOATDONG")),
                        cursor.getString(cursor.getColumnIndexOrThrow("THOIGIANBATDAU")),
                        cursor.getString(cursor.getColumnIndexOrThrow("THOIGIANKETTHUC")),
                        cursor.getString(cursor.getColumnIndexOrThrow("NGAYBATDAU")),
                        cursor.getString(cursor.getColumnIndexOrThrow("TINHTRANG")),
                        nhanVien,
                        toChuc,
                        nguoiLienHe,
                        coHoi,
                        cursor.getString(cursor.getColumnIndexOrThrow("MOTA")),
                        giaoCho,
                        type
                );

                list.add(hd);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<HoatDong> getHoatDongByDay(String day) {
        ArrayList<HoatDong> list = new ArrayList<>();
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM HOATDONG WHERE NGAYBATDAU = ?",
                new String[]{String.valueOf(day)}
        );

        if (cursor.moveToFirst()) {
            Log.e(TAG, "Ngày đang có trong DB: '" + cursor.getString(0) + "'");
            Log.e(TAG, "Ngày bắt đâu có trong DB: '" + cursor.getString(4) + "'");
            do {
                int nhanVienIndex = cursor.getColumnIndexOrThrow("NHANVIEN");
                int toChucIndex = cursor.getColumnIndexOrThrow("TOCHUC");
                int nguoiLienHeIndex = cursor.getColumnIndexOrThrow("NGUOILIENHE");
                int coHoiIndex = cursor.getColumnIndexOrThrow("COHOI");
                int giaoChoIndex = cursor.getColumnIndexOrThrow("GIAOCHO");
                int typeIndex = cursor.getColumnIndex("TYPE");
                // Xử lý NULL cho các foreign keys
                int nhanVien = cursor.isNull(nhanVienIndex) ? 0 : cursor.getInt(nhanVienIndex);
                int toChuc = cursor.isNull(toChucIndex) ? 0 : cursor.getInt(toChucIndex);
                int nguoiLienHe = cursor.isNull(nguoiLienHeIndex) ? 0 : cursor.getInt(nguoiLienHeIndex);
                int coHoi = cursor.isNull(coHoiIndex) ? 0 : cursor.getInt(coHoiIndex);
                int giaoCho = cursor.isNull(giaoChoIndex) ? 0 : cursor.getInt(giaoChoIndex);
                String type = cursor.isNull(typeIndex) ? null : cursor.getString(typeIndex);

                HoatDong hd = new HoatDong(
                        cursor.getInt(cursor.getColumnIndexOrThrow("ID")),
                        cursor.getString(cursor.getColumnIndexOrThrow("TENHOATDONG")),
                        cursor.getString(cursor.getColumnIndexOrThrow("THOIGIANBATDAU")),
                        cursor.getString(cursor.getColumnIndexOrThrow("THOIGIANKETTHUC")),
                        cursor.getString(cursor.getColumnIndexOrThrow("NGAYBATDAU")),
                        cursor.getString(cursor.getColumnIndexOrThrow("TINHTRANG")),
                        nhanVien,
                        toChuc,
                        nguoiLienHe,
                        coHoi,
                        cursor.getString(cursor.getColumnIndexOrThrow("MOTA")),
                        giaoCho,
                        type
                );

                list.add(hd);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    public ArrayList<HoatDong> getHoatDongByCoHoi(int coHoiId) {
        ArrayList<HoatDong> list = new ArrayList<>();
        SQLiteDatabase db = dbHandler.getReadableDatabase();

        Cursor cursor = db.rawQuery(
                "SELECT * FROM HOATDONG WHERE COHOI = ? ORDER BY THOIGIANBATDAU DESC",
                new String[]{String.valueOf(coHoiId)}
        );

        if (cursor.moveToFirst()) {
            do {
                int nhanVienIndex = cursor.getColumnIndexOrThrow("NHANVIEN");
                int toChucIndex = cursor.getColumnIndexOrThrow("TOCHUC");
                int nguoiLienHeIndex = cursor.getColumnIndexOrThrow("NGUOILIENHE");
                int coHoiIndex = cursor.getColumnIndexOrThrow("COHOI");
                int giaoChoIndex = cursor.getColumnIndexOrThrow("GIAOCHO");
                int typeIndex = cursor.getColumnIndex("TYPE");

                // Xử lý NULL cho các foreign keys
                int nhanVien = cursor.isNull(nhanVienIndex) ? 0 : cursor.getInt(nhanVienIndex);
                int toChuc = cursor.isNull(toChucIndex) ? 0 : cursor.getInt(toChucIndex);
                int nguoiLienHe = cursor.isNull(nguoiLienHeIndex) ? 0 : cursor.getInt(nguoiLienHeIndex);
                int coHoi = cursor.isNull(coHoiIndex) ? 0 : cursor.getInt(coHoiIndex);
                int giaoCho = cursor.isNull(giaoChoIndex) ? 0 : cursor.getInt(giaoChoIndex);
                String type = cursor.isNull(typeIndex) ? null : cursor.getString(typeIndex);

                HoatDong hd = new HoatDong(
                        cursor.getInt(cursor.getColumnIndexOrThrow("ID")),
                        cursor.getString(cursor.getColumnIndexOrThrow("TENHOATDONG")),
                        cursor.getString(cursor.getColumnIndexOrThrow("THOIGIANBATDAU")),
                        cursor.getString(cursor.getColumnIndexOrThrow("THOIGIANKETTHUC")),
                        cursor.getString(cursor.getColumnIndexOrThrow("NGAYBATDAU")),
                        cursor.getString(cursor.getColumnIndexOrThrow("TINHTRANG")),
                        nhanVien,
                        toChuc,
                        nguoiLienHe,
                        coHoi,
                        cursor.getString(cursor.getColumnIndexOrThrow("MOTA")),
                        giaoCho,
                        type
                );

                list.add(hd);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }
}