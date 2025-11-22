package com.example.crmmobile;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.content.ContentValues;

import java.util.ArrayList;
import java.util.List;

public class DBHelper extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "crm_mobile.db";
    private static final int DATABASE_VERSION = 1;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onConfigure(SQLiteDatabase db) {
        super.onConfigure(db);
        db.setForeignKeyConstraintsEnabled(true); // Bật kiểm tra khóa ngoại
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // Bảng NHANVIEN
        db.execSQL("CREATE TABLE NHANVIEN (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "HOTEN TEXT," +
                "EMAIL TEXT," +
                "DIENTHOAI TEXT," +
                "CHUCVU TEXT," +
                "BOPHAN TEXT," +
                "TAIKHOAN TEXT," +
                "MATKHAU TEXT," +
                "ROLE TEXT," +
                "TRANGTHAI TEXT," +
                "NGAYVAOLAM TEXT," +
                "NGAYNGHIVIEC TEXT," +
                "MOTA TEXT" +
                ");");

        // Bảng COMPANY
        db.execSQL("CREATE TABLE COMPANY (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TENCONGTY TEXT," +
                "NGANHNGHE TEXT," +
                "DIENTHOAI TEXT," +
                "EMAIL TEXT," +
                "DIACHI TEXT," +
                "TRANGTHAI TEXT," +
                "NGAYTHANHLAP TEXT," +
                "GIAOCHO INTEGER," +
                "FOREIGN KEY(GIAOCHO) REFERENCES NHANVIEN(ID) ON DELETE SET NULL" +
                ");");

        // Bảng LEAD
        db.execSQL("CREATE TABLE LEAD (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "HOTEN TEXT," +
                "DIENTHOAI TEXT," +
                "EMAIL TEXT," +
                "NGAYSINH TEXT," +
                "GIOITINH TEXT," +
                "DIACHI TEXT," +
                "CHUCVU TEXT," +
                "CONGTY TEXT," +
                "TINHTRANG TEXT," +
                "MOTA TEXT," +
                "GIAOCHO INTEGER," +
                "FOREIGN KEY(GIAOCHO) REFERENCES NHANVIEN(ID) ON DELETE SET NULL" +
                ");");

        // Bảng CONTACT
        db.execSQL("CREATE TABLE CONTACT (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "HOTEN TEXT," +
                "DIENTHOAI TEXT," +
                "EMAIL TEXT," +
                "NGAYSINH TEXT," +
                "GIOITINH TEXT," +
                "DIACHI TEXT," +
                "CHUCVU TEXT," +
                "CONGTY INTEGER," +
                "MOTA TEXT," +
                "GIAOCHO INTEGER," +
                "FOREIGN KEY(CONGTY) REFERENCES COMPANY(ID) ON DELETE SET NULL," +
                "FOREIGN KEY(GIAOCHO) REFERENCES NHANVIEN(ID) ON DELETE SET NULL" +
                ");");

        // Bảng COHOI (Opportunity)
        db.execSQL("CREATE TABLE COHOI (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TENCOHOI TEXT," +
                "CONGTY INTEGER," +
                "NGUOILIENHE INTEGER," +
                "GIATRI REAL," +
                "BUOCBANHANG TEXT," +
                "MOTA TEXT," +
                "GIAOCHO INTEGER," +
                "NGAYTAO TEXT," +
                "NGAYCHOT TEXT," +
                "FOREIGN KEY(CONGTY) REFERENCES COMPANY(ID) ON DELETE SET NULL," +
                "FOREIGN KEY(NGUOILIENHE) REFERENCES CONTACT(ID) ON DELETE SET NULL," +
                "FOREIGN KEY(GIAOCHO) REFERENCES NHANVIEN(ID) ON DELETE SET NULL" +
                ");");

        // Bảng BAOGIA (Quote)
        db.execSQL("CREATE TABLE BAOGIA (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TENBAOGIA TEXT," +
                "CONGTY INTEGER," +
                "NGUOILIENHE INTEGER," +
                "COHOI INTEGER," +
                "TINHTRANG TEXT," +
                "SANPHAM TEXT," +
                "SOLUONG INTEGER," +
                "DONGIA REAL," +
                "TONGTIEN REAL," +
                "FOREIGN KEY(CONGTY) REFERENCES COMPANY(ID) ON DELETE SET NULL," +
                "FOREIGN KEY(NGUOILIENHE) REFERENCES CONTACT(ID) ON DELETE SET NULL," +
                "FOREIGN KEY(COHOI) REFERENCES COHOI(ID) ON DELETE SET NULL" +
                ");");

        // Bảng DONHANG (Order)
        db.execSQL("CREATE TABLE DONHANG (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TENDONHANG TEXT," +
                "CONGTY INTEGER," +
                "NGUOILIENHE INTEGER," +
                "COHOI INTEGER," +
                "BAOGIA INTEGER," +
                "TINHTRANG TEXT," +
                "NGAYDATHANG TEXT," +
                "NGAYNHANHANG TEXT," +
                "SANPHAM TEXT," +
                "SOLUONG INTEGER," +
                "DONGIA REAL," +
                "TONGTIEN REAL," +
                "MOTA TEXT," +
                "GIAOCHO INTEGER," +
                "FOREIGN KEY(CONGTY) REFERENCES COMPANY(ID) ON DELETE SET NULL," +
                "FOREIGN KEY(NGUOILIENHE) REFERENCES CONTACT(ID) ON DELETE SET NULL," +
                "FOREIGN KEY(COHOI) REFERENCES COHOI(ID) ON DELETE SET NULL," +
                "FOREIGN KEY(BAOGIA) REFERENCES BAOGIA(ID) ON DELETE SET NULL," +
                "FOREIGN KEY(GIAOCHO) REFERENCES NHANVIEN(ID) ON DELETE SET NULL" +
                ");");

        // Bảng HOATDONG (Activity)
        db.execSQL("CREATE TABLE HOATDONG (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TENHOATDONG TEXT," +
                "THOIGIANBATDAU TEXT," +
                "THOIGIANKETTHUC TEXT," +
                "TINHTRANG TEXT," +
                "KHACHHANG TEXT," +
                "NHANVIEN INTEGER," +
                "TOCHUC TEXT," +
                "NGUOILIENHE INTEGER," +
                "LEAD INTEGER," +
                "LIENQUANTOI TEXT," +
                "MOTA TEXT," +
                "GIAOCHO INTEGER," +
                "FOREIGN KEY(NHANVIEN) REFERENCES NHANVIEN(ID) ON DELETE SET NULL," +
                "FOREIGN KEY(NGUOILIENHE) REFERENCES CONTACT(ID) ON DELETE SET NULL," +
                "FOREIGN KEY(LEAD) REFERENCES LEAD(ID) ON DELETE SET NULL," +
                "FOREIGN KEY(GIAOCHO) REFERENCES NHANVIEN(ID) ON DELETE SET NULL" +
                ");");

        // Bảng GOPY (Feedback)
        db.execSQL("CREATE TABLE GOPY (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "NGUOILIENHE INTEGER," +
                "DONHANG INTEGER," +
                "RATING INTEGER," +
                "COMMENT TEXT," +
                "GIAOCHO INTEGER," +
                "FOREIGN KEY(NGUOILIENHE) REFERENCES CONTACT(ID) ON DELETE SET NULL," +
                "FOREIGN KEY(DONHANG) REFERENCES DONHANG(ID) ON DELETE SET NULL," +
                "FOREIGN KEY(GIAOCHO) REFERENCES NHANVIEN(ID) ON DELETE SET NULL" +
                ");");

        // Bảng SANPHAM
        db.execSQL("CREATE TABLE SANPHAM (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TEN TEXT," +
                "MOTA TEXT," +
                "DONGIA REAL," +
                "TRANGTHAI TEXT," +
                "NGAYTAO TEXT," +
                "NGUOITAO INTEGER," +
                "MOTA_THEM TEXT," +
                "FOREIGN KEY(NGUOITAO) REFERENCES NHANVIEN(ID) ON DELETE SET NULL" +
                ");");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        // Xóa các bảng cũ nếu nâng cấp version
        db.execSQL("DROP TABLE IF EXISTS SANPHAM");
        db.execSQL("DROP TABLE IF EXISTS GOPY");
        db.execSQL("DROP TABLE IF EXISTS HOATDONG");
        db.execSQL("DROP TABLE IF EXISTS DONHANG");
        db.execSQL("DROP TABLE IF EXISTS BAOGIA");
        db.execSQL("DROP TABLE IF EXISTS COHOI");
        db.execSQL("DROP TABLE IF EXISTS CONTACT");
        db.execSQL("DROP TABLE IF EXISTS LEAD");
        db.execSQL("DROP TABLE IF EXISTS COMPANY");
        db.execSQL("DROP TABLE IF EXISTS NHANVIEN");

        onCreate(db);
    }

    public List<CaNhan> getAllCaNhan() {
        List<CaNhan> list = new ArrayList<>();
        SQLiteDatabase db = this.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CONTACT", null);
        if (cursor.moveToFirst()) {
            do {
                CaNhan cn = new CaNhan();
                cn.setHoTen(cursor.getString(cursor.getColumnIndexOrThrow("HOTEN")));
                cn.setCongTy(cursor.getString(cursor.getColumnIndexOrThrow("CONGTY")));
                cn.setNgaySinh(cursor.getString(cursor.getColumnIndexOrThrow("NGAYSINH")));
                cn.setDiaChi(cursor.getString(cursor.getColumnIndexOrThrow("DIACHI")));
                cn.setMoTa(cursor.getString(cursor.getColumnIndexOrThrow("MOTA")));
                cn.setGiaoCho(cursor.getString(cursor.getColumnIndexOrThrow("GIAOCHO")));
                cn.setSoCuocGoi(cursor.getInt(cursor.getColumnIndexOrThrow("SOCUOCGOI")));
                cn.setSoMeeting(cursor.getInt(cursor.getColumnIndexOrThrow("SOMEETING")));
                list.add(cn);
            } while (cursor.moveToNext());
        }
        cursor.close();
        return list;
    }
}
