package com.example.crmmobile.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crmmobile.LeadDirectory.Lead;

import java.util.ArrayList;
import java.util.List;

public class LeadRepository {
    DBCRMHandler dbHelper;

    public LeadRepository(Context context){
        dbHelper = new DBCRMHandler(context);
    }

    //Add Lead
    public long addLead(Lead lead){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("TITLE", lead.getTitle());
        values.put("TEN", lead.getTen());
        values.put("HOVATENDEM", lead.getHovaTendem());
        values.put("DIENTHOAI", lead.getDienThoai());
        values.put("EMAIL", lead.getEmail());
        values.put("NGAYSINH", lead.getNgaysinh());
        values.put("NGANHNGHE", lead.getNganhnghe());
        values.put("GIOITINH", lead.getGioitinh());
        values.put("DIACHI", lead.getDiachi());
        values.put("QUANHUYEN", lead.getQuanHuyen());
        values.put("TINH", lead.getTinh());
        values.put("THANHPHO", lead.getThanhpho());
        values.put("QUOCGIA", lead.getQuocGia());
        values.put("CHUCVU", lead.getChucvu());
        values.put("CONGTY", lead.getCongty());
        values.put("DOANHTHU", lead.getDoanhThu());
        values.put("SONV", lead.getSoNV());
        values.put("MASOTHUE", lead.getMaThue());
        values.put("TINHTRANG", lead.getTinhTrang());
        values.put("MOTA", lead.getMota());
        values.put("GHICHU", lead.getGhichu());
        values.put("TIEMNANG", lead.getTiemnang());
        values.put("DANHGIA", lead.getDanhgia());
        values.put("GIAOCHO", lead.getGiaochoID());
        values.put("NGUOITAO", lead.getNguoitaoID());
        values.put("NGAYLIENHE", lead.getNgayLienHe());
        values.put("CUOCGOI", lead.getCuocgoi());
        values.put("CUOCHOP", lead.getCuochop());

        long newId = db.insert("LEAD", null, values);
        db.close();
        return  newId;
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
                lead.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("TITLE")));
                lead.setHovaTendem(cursor.getString(cursor.getColumnIndexOrThrow("HOVATENDEM")));
                lead.setTen(cursor.getString(cursor.getColumnIndexOrThrow("TEN")));
                lead.setDienThoai(cursor.getString(cursor.getColumnIndexOrThrow("DIENTHOAI")));
                lead.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("EMAIL")));
                lead.setNgaysinh(cursor.getString(cursor.getColumnIndexOrThrow("NGAYSINH")));
                lead.setNganhnghe(cursor.getString(cursor.getColumnIndexOrThrow("NGANHNGHE")));
                lead.setGioitinh(cursor.getString(cursor.getColumnIndexOrThrow("GIOITINH")));
                lead.setDiachi(cursor.getString(cursor.getColumnIndexOrThrow("DIACHI")));
                lead.setQuanHuyen(cursor.getString(cursor.getColumnIndexOrThrow("QUANHUYEN")));
                lead.setTinh(cursor.getString(cursor.getColumnIndexOrThrow("TINH")));
                lead.setThanhpho(cursor.getString(cursor.getColumnIndexOrThrow("THANHPHO")));
                lead.setQuocGia(cursor.getString(cursor.getColumnIndexOrThrow("QUOCGIA")));
                lead.setChucvu(cursor.getString(cursor.getColumnIndexOrThrow("CHUCVU")));
                lead.setCongty(cursor.getString(cursor.getColumnIndexOrThrow("CONGTY")));
                lead.setDoanhThu(cursor.getString(cursor.getColumnIndexOrThrow("DOANHTHU")));
                lead.setSoNV(cursor.getString(cursor.getColumnIndexOrThrow("SONV")));
                lead.setMaThue(cursor.getString(cursor.getColumnIndexOrThrow("MASOTHUE")));
                lead.setTinhTrang(cursor.getString(cursor.getColumnIndexOrThrow("TINHTRANG")));
                lead.setMota(cursor.getString(cursor.getColumnIndexOrThrow("MOTA")));
                lead.setGhichu(cursor.getString(cursor.getColumnIndexOrThrow("GHICHU")));
                lead.setTiemnang(cursor.getString(cursor.getColumnIndexOrThrow("TIEMNANG")));
                lead.setDanhgia(cursor.getString(cursor.getColumnIndexOrThrow("DANHGIA")));
                lead.setGiaochoID(cursor.getInt(cursor.getColumnIndexOrThrow("GIAOCHO")));
                lead.setNguoitaoID(cursor.getInt(cursor.getColumnIndexOrThrow("NGUOITAO")));
                lead.setNgayLienHe(cursor.getString(cursor.getColumnIndexOrThrow("NGAYLIENHE")));
                lead.setCuocgoi(cursor.getInt(cursor.getColumnIndexOrThrow("CUOCGOI")));
                lead.setCuochop(cursor.getInt(cursor.getColumnIndexOrThrow("CUOCHOP")));

                list.add(lead);
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

        values.put("TITLE", lead.getTitle());
        values.put("HOVATENDEM", lead.getHovaTendem());
        values.put("TEN", lead.getTen());
        values.put("DIENTHOAI", lead.getDienThoai());
        values.put("EMAIL", lead.getEmail());
        values.put("NGAYSINH", lead.getNgaysinh());
        values.put("NGANHNGHE", lead.getNganhnghe());
        values.put("GIOITINH", lead.getGioitinh());
        values.put("DIACHI", lead.getDiachi());
        values.put("QUANHUYEN", lead.getQuanHuyen());
        values.put("TINH", lead.getTinh());
        values.put("THANHPHO", lead.getThanhpho());
        values.put("QUOCGIA", lead.getQuocGia());
        values.put("CHUCVU", lead.getChucvu());
        values.put("CONGTY", lead.getCongty());
        values.put("DOANHTHU", lead.getDoanhThu());
        values.put("SONV", lead.getSoNV());
        values.put("MASOTHUE", lead.getMaThue());
        values.put("TINHTRANG", lead.getTinhTrang());
        values.put("MOTA", lead.getMota());
        values.put("GHICHU", lead.getGhichu());
        values.put("TIEMNANG", lead.getTiemnang());
        values.put("DANHGIA", lead.getDanhgia());
        values.put("GIAOCHO", lead.getGiaochoID());
        values.put("NGUOITAO", lead.getNguoitaoID());
        values.put("NGAYLIENHE", lead.getNgayLienHe());
        values.put("CUOCGOI", lead.getCuocgoi());
        values.put("CUOCHOP", lead.getCuochop());

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

    public void updateStatus(int leadID, String status){
        SQLiteDatabase db = this.dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("TINHTRANG", status);
        db.update("LEAD", values, "ID=?", new String[]{String.valueOf(leadID)});
    }

    public Lead getLeadByID(int id){
        SQLiteDatabase db = this.dbHelper.getReadableDatabase();
        Lead lead = null;

        Cursor cursor = db.rawQuery("SELECT * FROM LEAD WHERE ID=?", new String[]{String.valueOf(id)});

        if(cursor.moveToFirst()){
            lead = new Lead();
            lead.setID(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
            lead.setTitle(cursor.getString(cursor.getColumnIndexOrThrow("TITLE")));
            lead.setHovaTendem(cursor.getString(cursor.getColumnIndexOrThrow("HOVATENDEM")));
            lead.setTen(cursor.getString(cursor.getColumnIndexOrThrow("TEN")));
            lead.setDienThoai(cursor.getString(cursor.getColumnIndexOrThrow("DIENTHOAI")));
            lead.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("EMAIL")));
            lead.setNgaysinh(cursor.getString(cursor.getColumnIndexOrThrow("NGAYSINH")));
            lead.setNganhnghe(cursor.getString(cursor.getColumnIndexOrThrow("NGANHNGHE")));
            lead.setGioitinh(cursor.getString(cursor.getColumnIndexOrThrow("GIOITINH")));
            lead.setDiachi(cursor.getString(cursor.getColumnIndexOrThrow("DIACHI")));
            lead.setQuanHuyen(cursor.getString(cursor.getColumnIndexOrThrow("QUANHUYEN")));
            lead.setTinh(cursor.getString(cursor.getColumnIndexOrThrow("TINH")));
            lead.setThanhpho(cursor.getString(cursor.getColumnIndexOrThrow("THANHPHO")));
            lead.setQuocGia(cursor.getString(cursor.getColumnIndexOrThrow("QUOCGIA")));
            lead.setChucvu(cursor.getString(cursor.getColumnIndexOrThrow("CHUCVU")));
            lead.setCongty(cursor.getString(cursor.getColumnIndexOrThrow("CONGTY")));
            lead.setDoanhThu(cursor.getString(cursor.getColumnIndexOrThrow("DOANHTHU")));
            lead.setSoNV(cursor.getString(cursor.getColumnIndexOrThrow("SONV")));
            lead.setMaThue(cursor.getString(cursor.getColumnIndexOrThrow("MASOTHUE")));
            lead.setTinhTrang(cursor.getString(cursor.getColumnIndexOrThrow("TINHTRANG")));
            lead.setMota(cursor.getString(cursor.getColumnIndexOrThrow("MOTA")));
            lead.setGhichu(cursor.getString(cursor.getColumnIndexOrThrow("GHICHU")));
            lead.setTiemnang(cursor.getString(cursor.getColumnIndexOrThrow("TIEMNANG")));
            lead.setDanhgia(cursor.getString(cursor.getColumnIndexOrThrow("DANHGIA")));
            lead.setGiaochoID(cursor.getInt(cursor.getColumnIndexOrThrow("GIAOCHO")));
            lead.setNguoitaoID(cursor.getInt(cursor.getColumnIndexOrThrow("NGUOITAO")));
            lead.setNgayLienHe(cursor.getString(cursor.getColumnIndexOrThrow("NGAYLIENHE")));
            lead.setCuocgoi(cursor.getInt(cursor.getColumnIndexOrThrow("CUOCGOI")));
            lead.setCuochop(cursor.getInt(cursor.getColumnIndexOrThrow("CUOCHOP")));
        }
        cursor.close();
        db.close();

        return lead;
    }
}
