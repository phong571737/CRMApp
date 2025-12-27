package com.example.crmmobile.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.crmmobile.DataBase.Table.BaogiaTable;
import com.example.crmmobile.DataBase.Table.CohoiTable;
import com.example.crmmobile.DataBase.Table.CompanyTable;
import com.example.crmmobile.DataBase.Table.ContactTable;
import com.example.crmmobile.DataBase.Table.DonhangTable;
import com.example.crmmobile.DataBase.Table.GopyTable;
import com.example.crmmobile.DataBase.Table.HoatdongTable;
import com.example.crmmobile.DataBase.Table.LeadTable;
import com.example.crmmobile.DataBase.Table.NhanVienTable;
import com.example.crmmobile.DataBase.Table.RecentAccessTable;
import com.example.crmmobile.DataBase.Table.SanPhamTable;
import com.example.crmmobile.IndividualDirectory.CaNhan;

import java.nio.charset.CoderMalfunctionError;
import java.util.ArrayList;
import java.util.List;

public class DBCRMHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "crm.db";
    private static final int DATABASE_VERSION = 6;

    public DBCRMHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 1. Bảng NHANVIEN
        db.execSQL(NhanVienTable.CREATE_TABLE);
        // 2. Bảng COMPANY
        db.execSQL(CompanyTable.CREATE_TABLE);
        // 3. Bảng LEAD
        db.execSQL(LeadTable.CREATE_TABLE);
        // 4. Bảng CONTACT (ĐÃ ĐIỀU CHỈNH)
        db.execSQL(ContactTable.CREATE_TABLE);
        // 5. Bảng COHOI
        db.execSQL(CohoiTable.CREATE_TABLE);
        // 6. Bảng BAOGIA
        db.execSQL(BaogiaTable.CREATE_TABLE);
        // 7. Bảng DONHANG
        db.execSQL(DonhangTable.CREATE_TABLE);
        // 8. Bảng HOATDONG
        db.execSQL(HoatdongTable.CREATE_TABLE);
        // 9. Bảng GOPY
        db.execSQL(GopyTable.CREATE_TABLE);
        // 10. Bảng SANPHAM
        db.execSQL(SanPhamTable.CREATE_TABLE);
        // 11. Bảng RECENT
        db.execSQL(RecentAccessTable.CREATE_TABLE);
    }


    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        try {
            // v6: thêm cột EXTRA_JSON cho DONHANG
            if (oldVersion < 6) {
                try {
                    db.execSQL("ALTER TABLE DONHANG ADD COLUMN EXTRA_JSON TEXT");
                } catch (Exception ignored) {}
            }

            // ... các upgrade khác về sau

        } catch (Exception ex) {
            // ⚠️ Chỉ dùng khi bạn chấp nhận mất dữ liệu (fallback)
            String[] tables = {"NHANVIEN", "COMPANY", "LEAD", "CONTACT", "COHOI", "BAOGIA", "DONHANG", "HOATDONG", "GOPY", "SANPHAM", "RECENT"};
            for (String table : tables) {
                db.execSQL("DROP TABLE IF EXISTS " + table);
            }
            onCreate(db);
        }
    }

    public void add(CaNhan cn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Thay KEY_HOTEN bằng "HOTEN", KEY_DANHXUNG bằng "DANHXUNG"...
        values.put("HOTEN", cn.getHoVaTen());
        values.put("DANHXUNG", cn.getDanhXung());
        values.put("TEN", cn.getTen());
        values.put("CONGTY", cn.getCongTy());
        values.put("GIOITINH", cn.getGioiTinh());
        values.put("DIENTHOAI", cn.getDiDong());
        values.put("EMAIL", cn.getEmail());
        values.put("NGAYSINH", cn.getNgaySinh());
        values.put("NGAYTAO", cn.getNgayTao());
        // THÊM CÁI NÀY VÀO
        values.put("NGAYSUA", cn.getNgaySua());

        values.put("DIACHI", cn.getDiaChi());
        values.put("QUANHUYEN", cn.getQuanHuyen());
        values.put("TINHTP", cn.getTinhTP());
        values.put("QUOCGIA", cn.getQuocGia());
        values.put("MOTA", cn.getMoTa());
        values.put("GHICHU", cn.getGhiChu());
        values.put("GIAOCHO", cn.getGiaoCho());
        values.put("CUOCGOI", cn.getSoCuocGoi());
        values.put("CUOCHOP", cn.getSoCuocHop());

        db.insert("CONTACT", null, values); // Dùng trực tiếp tên bảng "CONTACT"
        db.close();
    }


    public List<CaNhan> getAllCaNhan() {
        List<CaNhan> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CONTACT", null);

        if (cursor.moveToFirst()) {
            do {
                CaNhan cn = new CaNhan();
                // Dùng getColumnIndexOrThrow với chuỗi cứng
                cn.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
                cn.setHoVaTen(cursor.getString(cursor.getColumnIndexOrThrow("HOTEN")));
                cn.setDanhXung(cursor.getString(cursor.getColumnIndexOrThrow("DANHXUNG")));
                cn.setTen(cursor.getString(cursor.getColumnIndexOrThrow("TEN")));
                cn.setCongTy(cursor.getString(cursor.getColumnIndexOrThrow("CONGTY")));
                cn.setGioiTinh(cursor.getString(cursor.getColumnIndexOrThrow("GIOITINH")));
                cn.setDiDong(cursor.getString(cursor.getColumnIndexOrThrow("DIENTHOAI")));
                cn.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("EMAIL")));
                cn.setNgaySinh(cursor.getString(cursor.getColumnIndexOrThrow("NGAYSINH")));
                cn.setNgayTao(cursor.getString(cursor.getColumnIndexOrThrow("NGAYTAO")));
                // THÊM CÁI NÀY VÀO
                cn.setNgaySua(cursor.getString(cursor.getColumnIndexOrThrow("NGAYSUA")));

                cn.setDiaChi(cursor.getString(cursor.getColumnIndexOrThrow("DIACHI")));
                cn.setQuanHuyen(cursor.getString(cursor.getColumnIndexOrThrow("QUANHUYEN")));
                cn.setTinhTP(cursor.getString(cursor.getColumnIndexOrThrow("TINHTP")));
                cn.setQuocGia(cursor.getString(cursor.getColumnIndexOrThrow("QUOCGIA")));
                cn.setMoTa(cursor.getString(cursor.getColumnIndexOrThrow("MOTA")));
                cn.setGhiChu(cursor.getString(cursor.getColumnIndexOrThrow("GHICHU")));
                cn.setGiaoCho(cursor.getString(cursor.getColumnIndexOrThrow("GIAOCHO")));
                cn.setSoCuocGoi(cursor.getInt(cursor.getColumnIndexOrThrow("CUOCGOI")));
                cn.setSoCuocHop(cursor.getInt(cursor.getColumnIndexOrThrow("CUOCHOP")));
                list.add(cn);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    public void deleteCaNhan(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        // Dùng "CONTACT" và "ID"
        db.delete("CONTACT", "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public int updateCaNhan(CaNhan cn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put("HOTEN", cn.getHoVaTen());
        values.put("DANHXUNG", cn.getDanhXung());
        values.put("TEN", cn.getTen());
        values.put("CONGTY", cn.getCongTy());
        values.put("GIOITINH", cn.getGioiTinh());
        values.put("DIENTHOAI", cn.getDiDong());
        values.put("EMAIL", cn.getEmail());
        values.put("NGAYSINH", cn.getNgaySinh());
        values.put("NGAYTAO", cn.getNgayTao());
        // THÊM CÁI NÀY VÀO
        values.put("NGAYSUA", cn.getNgaySua());

        values.put("DIACHI", cn.getDiaChi());
        values.put("QUANHUYEN", cn.getQuanHuyen());
        values.put("TINHTP", cn.getTinhTP());
        values.put("QUOCGIA", cn.getQuocGia());
        values.put("MOTA", cn.getMoTa());
        values.put("GHICHU", cn.getGhiChu());
        values.put("GIAOCHO", cn.getGiaoCho());
        values.put("CUOCGOI", cn.getSoCuocGoi());
        values.put("CUOCHOP", cn.getSoCuocHop());

        int result = db.update("CONTACT", values, "ID=?", new String[]{String.valueOf(cn.getId())});
        db.close();
        return result;
    }
}