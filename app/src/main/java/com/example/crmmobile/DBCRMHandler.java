package com.example.crmmobile;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;
import java.util.List;

public class DBCRMHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "crm.db";
    private static final int DATABASE_VERSION = 1;
    private static final String TABLE_CONTACT = "Contact";

    private static final String KEY_ID = "id";
    private static final String KEY_HOTEN = "hoten";
    private static final String KEY_DANHXUNG = "danhxung";
    private static final String KEY_TEN = "ten";
    private static final String KEY_CONGTY = "congty";
    private static final String KEY_GIOITINH = "gioitinh";
    private static final String KEY_DIENTHOAI = "dienthoai";
    private static final String KEY_EMAIL = "email";
    private static final String KEY_NGAYSINH = "ngaysinh";
    private static final String KEY_NGAYTAO = "ngaytao";
    private static final String KEY_DIACHI = "diachi";
    private static final String KEY_QUANHUYEN = "quanhuyen";
    private static final String KEY_TINHTP = "tinhtp";
    private static final String KEY_QUOCGIA = "quocgia";
    private static final String KEY_MOTA = "mota";
    private static final String KEY_GHICHU = "ghichu";
    private static final String KEY_GIAOCHO = "giaocho";
    private static final String KEY_CUOCGOI = "cuocgoi";
    private static final String KEY_CUOCHOP = "cuochop";

    public DBCRMHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_TABLE = "CREATE TABLE " + TABLE_CONTACT + "("
                + KEY_ID + " INTEGER PRIMARY KEY AUTOINCREMENT,"
                + KEY_HOTEN + " TEXT,"
                + KEY_DANHXUNG + " TEXT,"
                + KEY_TEN + " TEXT,"
                + KEY_CONGTY + " TEXT,"
                + KEY_GIOITINH + " TEXT,"
                + KEY_DIENTHOAI + " TEXT,"
                + KEY_EMAIL + " TEXT,"
                + KEY_NGAYSINH + " TEXT,"
                + KEY_NGAYTAO + " TEXT,"
                + KEY_DIACHI + " TEXT,"
                + KEY_QUANHUYEN + " TEXT,"
                + KEY_TINHTP + " TEXT,"
                + KEY_QUOCGIA + " TEXT,"
                + KEY_MOTA + " TEXT,"
                + KEY_GHICHU + " TEXT,"
                + KEY_GIAOCHO + " TEXT,"
                + KEY_CUOCGOI + " INTEGER,"
                + KEY_CUOCHOP + " INTEGER)";
        db.execSQL(CREATE_TABLE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONTACT);
        onCreate(db);
    }

    // --- Thêm CaNhan ---
    public void add(CaNhan cn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        // Không cần set id, AUTOINCREMENT
        values.put(KEY_HOTEN, cn.getHoVaTen());
        values.put(KEY_DANHXUNG, cn.getDanhXung());
        values.put(KEY_TEN, cn.getTen());
        values.put(KEY_CONGTY, cn.getCongTy());
        values.put(KEY_GIOITINH, cn.getGioiTinh());
        values.put(KEY_DIENTHOAI, cn.getDiDong());
        values.put(KEY_EMAIL, cn.getEmail());
        values.put(KEY_NGAYSINH, cn.getNgaySinh());
        values.put(KEY_NGAYTAO, cn.getNgayTao());
        values.put(KEY_DIACHI, cn.getDiaChi());
        values.put(KEY_QUANHUYEN, cn.getQuanHuyen());
        values.put(KEY_TINHTP, cn.getTinhTP());
        values.put(KEY_QUOCGIA, cn.getQuocGia());
        values.put(KEY_MOTA, cn.getMoTa());
        values.put(KEY_GHICHU, cn.getGhiChu());
        values.put(KEY_GIAOCHO, cn.getGiaoCho());
        values.put(KEY_CUOCGOI, cn.getSoCuocGoi());
        values.put(KEY_CUOCHOP, cn.getSoCuocHop());

        db.insert(TABLE_CONTACT, null, values);
        db.close();
    }

    // --- Lấy danh sách CaNhan ---
    public List<CaNhan> getAllCaNhan() {
        List<CaNhan> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM " + TABLE_CONTACT, null);

        if (cursor.moveToFirst()) {
            do {
                CaNhan cn = new CaNhan();
                cn.setId(cursor.getInt(cursor.getColumnIndex(KEY_ID)));
                cn.setHoVaTen(cursor.getString(cursor.getColumnIndex(KEY_HOTEN)));
                cn.setDanhXung(cursor.getString(cursor.getColumnIndex(KEY_DANHXUNG)));
                cn.setTen(cursor.getString(cursor.getColumnIndex(KEY_TEN)));
                cn.setCongTy(cursor.getString(cursor.getColumnIndex(KEY_CONGTY)));
                cn.setGioiTinh(cursor.getString(cursor.getColumnIndex(KEY_GIOITINH)));
                cn.setDiDong(cursor.getString(cursor.getColumnIndex(KEY_DIENTHOAI)));
                cn.setEmail(cursor.getString(cursor.getColumnIndex(KEY_EMAIL)));
                cn.setNgaySinh(cursor.getString(cursor.getColumnIndex(KEY_NGAYSINH)));
                cn.setNgayTao(cursor.getString(cursor.getColumnIndex(KEY_NGAYTAO)));
                cn.setDiaChi(cursor.getString(cursor.getColumnIndex(KEY_DIACHI)));
                cn.setQuanHuyen(cursor.getString(cursor.getColumnIndex(KEY_QUANHUYEN)));
                cn.setTinhTP(cursor.getString(cursor.getColumnIndex(KEY_TINHTP)));
                cn.setQuocGia(cursor.getString(cursor.getColumnIndex(KEY_QUOCGIA)));
                cn.setMoTa(cursor.getString(cursor.getColumnIndex(KEY_MOTA)));
                cn.setGhiChu(cursor.getString(cursor.getColumnIndex(KEY_GHICHU)));
                cn.setGiaoCho(cursor.getString(cursor.getColumnIndex(KEY_GIAOCHO)));
                cn.setSoCuocGoi(cursor.getInt(cursor.getColumnIndex(KEY_CUOCGOI)));
                cn.setSoCuocHop(cursor.getInt(cursor.getColumnIndex(KEY_CUOCHOP)));
                list.add(cn);
            } while (cursor.moveToNext());
        }
        cursor.close();
        db.close();
        return list;
    }

    // --- Xóa CaNhan theo id ---
    public void deleteCaNhan(int id) {
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(TABLE_CONTACT, KEY_ID + "=?", new String[]{String.valueOf(id)});
        db.close();
    }

    // --- Cập nhật CaNhan ---
    public int updateCaNhan(CaNhan cn) {
        SQLiteDatabase db = this.getWritableDatabase();
        ContentValues values = new ContentValues();

        values.put(KEY_HOTEN, cn.getHoVaTen());
        values.put(KEY_DANHXUNG, cn.getDanhXung());
        values.put(KEY_TEN, cn.getTen());
        values.put(KEY_CONGTY, cn.getCongTy());
        values.put(KEY_GIOITINH, cn.getGioiTinh());
        values.put(KEY_DIENTHOAI, cn.getDiDong());
        values.put(KEY_EMAIL, cn.getEmail());
        values.put(KEY_NGAYSINH, cn.getNgaySinh());
        values.put(KEY_NGAYTAO, cn.getNgayTao());
        values.put(KEY_DIACHI, cn.getDiaChi());
        values.put(KEY_QUANHUYEN, cn.getQuanHuyen());
        values.put(KEY_TINHTP, cn.getTinhTP());
        values.put(KEY_QUOCGIA, cn.getQuocGia());
        values.put(KEY_MOTA, cn.getMoTa());
        values.put(KEY_GHICHU, cn.getGhiChu());
        values.put(KEY_GIAOCHO, cn.getGiaoCho());
        values.put(KEY_CUOCGOI, cn.getSoCuocGoi());
        values.put(KEY_CUOCHOP, cn.getSoCuocHop());

        int result = db.update(TABLE_CONTACT, values, KEY_ID + "=?", new String[]{String.valueOf(cn.getId())});
        db.close();
        return result;
    }
}


