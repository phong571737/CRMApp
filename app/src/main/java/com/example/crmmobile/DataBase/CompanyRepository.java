package com.example.crmmobile.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.crmmobile.IndividualDirectory.CaNhan;
import com.example.crmmobile.OrganizationDirectory.ToChuc;
import com.example.crmmobile.DataBase.Table.CompanyTable;

import java.util.ArrayList;
import java.util.List;

public class CompanyRepository {
    DBCRMHandler dbHelper;

    public CompanyRepository(Context context){
        dbHelper = new DBCRMHandler(context);
    }

    // Thêm Company
    public long addCompany(ToChuc toChuc){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("TEN_CONG_TY", toChuc.getCompanyName());
        values.put("TRANG_THAI", toChuc.getTrangThai() != null ? toChuc.getTrangThai().name() : "NONE");
        values.put("WEBSITE", toChuc.getWebsite());
        values.put("DIEN_THOAI", toChuc.getPhone());
        values.put("EMAIL", toChuc.getEmail());
        values.put("NGANH_NGHE", toChuc.getIndustry());
        values.put("DIA_CHI", toChuc.getAddress());
        values.put("QUAN_HUYEN", toChuc.getDistrict());
        values.put("TINH_TP", toChuc.getCity());
        values.put("QUOC_GIA", toChuc.getCountry());
        values.put("TINH_TRANG_MUA_HANG", toChuc.getBuyingStatus());
        values.put("SO_DON_HANG", toChuc.getOrderCount());
        values.put("NGAY_MUA_DAU", toChuc.getFirstPurchaseDate());
        values.put("NGAY_MUA_CUOI", toChuc.getLastPurchaseDate());
        values.put("TONG_DOANH_THU", toChuc.getTotalRevenue());
        values.put("GIAO_CHO", toChuc.getAssignedTo());
        values.put("IS_STARRED", toChuc.isStarred() ? 1 : 0);
        values.put("SHOW_TRAO_DOI", toChuc.isShowTraoDoi() ? 1 : 0);

        long newId = db.insert(CompanyTable.TABLE_NAME, null, values);
        db.close();
        return newId;
    }

    // Lấy danh sách Company
    public List<ToChuc> getAllCompany(){
        List<ToChuc> list = new ArrayList<>();
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();

        Cursor cursor = db.rawQuery("SELECT * FROM " + CompanyTable.TABLE_NAME + " ORDER BY ID DESC", null);

        if(cursor.moveToFirst()){
            do {
                ToChuc toChuc = mapCursorToToChuc(cursor);
                list.add(toChuc);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();

        return list;
    }

    // Cập nhật Company
    public int updateCompany(ToChuc toChuc){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("TEN_CONG_TY", toChuc.getCompanyName());
        values.put("TRANG_THAI", toChuc.getTrangThai() != null ? toChuc.getTrangThai().name() : "NONE");
        values.put("WEBSITE", toChuc.getWebsite());
        values.put("DIEN_THOAI", toChuc.getPhone());
        values.put("EMAIL", toChuc.getEmail());
        values.put("NGANH_NGHE", toChuc.getIndustry());
        values.put("DIA_CHI", toChuc.getAddress());
        values.put("QUAN_HUYEN", toChuc.getDistrict());
        values.put("TINH_TP", toChuc.getCity());
        values.put("QUOC_GIA", toChuc.getCountry());
        values.put("TINH_TRANG_MUA_HANG", toChuc.getBuyingStatus());
        values.put("SO_DON_HANG", toChuc.getOrderCount());
        values.put("NGAY_MUA_DAU", toChuc.getFirstPurchaseDate());
        values.put("NGAY_MUA_CUOI", toChuc.getLastPurchaseDate());
        values.put("TONG_DOANH_THU", toChuc.getTotalRevenue());
        values.put("GIAO_CHO", toChuc.getAssignedTo());
        values.put("IS_STARRED", toChuc.isStarred() ? 1 : 0);
        values.put("SHOW_TRAO_DOI", toChuc.isShowTraoDoi() ? 1 : 0);

        int result = db.update(CompanyTable.TABLE_NAME, values, "ID=?", new String[]{String.valueOf(toChuc.getId())});
        db.close();
        return result;
    }

    // Xóa Company
    public void deleteCompany(int id){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        db.delete(CompanyTable.TABLE_NAME, "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // Hàm lấy chi tiết Company theo ID
    public ToChuc getCompanyByID(int id){
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        ToChuc toChuc = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + CompanyTable.TABLE_NAME + " WHERE ID=?", new String[]{String.valueOf(id)});

        if(cursor.moveToFirst()){
            toChuc = mapCursorToToChuc(cursor);
        }
        cursor.close();
        db.close();

        return toChuc;
    }

    // Hàm phụ trợ để map dữ liệu (tránh viết lặp lại code)
    private ToChuc mapCursorToToChuc(Cursor cursor) {
        ToChuc toChuc = new ToChuc();
        toChuc.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
        toChuc.setCompanyName(cursor.getString(cursor.getColumnIndexOrThrow("TEN_CONG_TY")));

        try {
            String tt = cursor.getString(cursor.getColumnIndexOrThrow("TRANG_THAI"));
            toChuc.setTrangThai(ToChuc.TrangThai.valueOf(tt));
        } catch (Exception e) {
            toChuc.setTrangThai(ToChuc.TrangThai.NONE);
        }

        toChuc.setWebsite(cursor.getString(cursor.getColumnIndexOrThrow("WEBSITE")));
        toChuc.setPhone(cursor.getString(cursor.getColumnIndexOrThrow("DIEN_THOAI")));
        toChuc.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("EMAIL")));
        toChuc.setIndustry(cursor.getString(cursor.getColumnIndexOrThrow("NGANH_NGHE")));
        toChuc.setAddress(cursor.getString(cursor.getColumnIndexOrThrow("DIA_CHI")));
        toChuc.setDistrict(cursor.getString(cursor.getColumnIndexOrThrow("QUAN_HUYEN")));
        toChuc.setCity(cursor.getString(cursor.getColumnIndexOrThrow("TINH_TP")));
        toChuc.setCountry(cursor.getString(cursor.getColumnIndexOrThrow("QUOC_GIA")));
        toChuc.setBuyingStatus(cursor.getString(cursor.getColumnIndexOrThrow("TINH_TRANG_MUA_HANG")));
        toChuc.setOrderCount(cursor.getInt(cursor.getColumnIndexOrThrow("SO_DON_HANG")));
        toChuc.setFirstPurchaseDate(cursor.getString(cursor.getColumnIndexOrThrow("NGAY_MUA_DAU")));
        toChuc.setLastPurchaseDate(cursor.getString(cursor.getColumnIndexOrThrow("NGAY_MUA_CUOI")));
        toChuc.setTotalRevenue(cursor.getDouble(cursor.getColumnIndexOrThrow("TONG_DOANH_THU")));
        toChuc.setAssignedTo(cursor.getString(cursor.getColumnIndexOrThrow("GIAO_CHO")));
        toChuc.setStarred(cursor.getInt(cursor.getColumnIndexOrThrow("IS_STARRED")) == 1);
        toChuc.setShowTraoDoi(cursor.getInt(cursor.getColumnIndexOrThrow("SHOW_TRAO_DOI")) == 1);

        return toChuc;
    }

    // Hàm kiểm tra công ty có tồn tại hay không
    public ToChuc getCompanyByName(String name){
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        ToChuc toChuc = null;

        Cursor cursor = db.rawQuery("SELECT * FROM " + CompanyTable.TABLE_NAME + " WHERE TEN_CONG_TY=?", new String[]{name.trim()});

        if(cursor.moveToFirst()){
            toChuc = new ToChuc();
            toChuc.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
            toChuc.setCompanyName(
                    cursor.getString(cursor.getColumnIndexOrThrow("TEN_CONG_TY"))
            );
            toChuc.setAddress(
                    cursor.getString(cursor.getColumnIndexOrThrow("DIA_CHI"))
            );
        }
        cursor.close();
        db.close();

        return toChuc;
    }

    // Xuan them vao de lay du lieu id + name cua company cho dropdown trong form
    public List<ToChuc> getAllIdName() {
        List<ToChuc> list = new ArrayList<>();

        SQLiteDatabase db = dbHelper.getReadableDatabase();
        Cursor cursor = db.rawQuery(
                "SELECT ID, TEN_CONG_TY FROM COMPANY",
                null
        );

        Log.d("DEBUG_CompanyRepo", "cursor count = " + cursor.getCount());

        while (cursor.moveToNext()) {
            ToChuc cn = new ToChuc();
            cn.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
            cn.setCompanyName(cursor.getString(cursor.getColumnIndexOrThrow("TEN_CONG_TY")));

            list.add(cn);

            Log.d("DEBUG_CompanyRepo",
                    "Loaded: id=" + cn.getId() + ", name=" + cn.getCompanyName());
        }

        cursor.close();
        db.close();

        return list;
    }

    // Tìm kiếm Company theo tên
    public List<ToChuc> searchCompany(String keyword) {
        List<ToChuc> list = new ArrayList<>();
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        // Tìm kiếm theo tên công ty
        Cursor cursor = db.rawQuery("SELECT * FROM " + CompanyTable.TABLE_NAME + " WHERE TEN_CONG_TY LIKE ? ORDER BY ID DESC",
                new String[]{"%" + keyword.trim() + "%"});
        if (cursor.moveToFirst()) {
            do {
                list.add(mapCursorToToChuc(cursor));
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }
}