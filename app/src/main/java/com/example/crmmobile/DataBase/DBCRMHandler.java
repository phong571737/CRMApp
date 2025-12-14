package com.example.crmmobile.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import com.example.crmmobile.IndividualDirectory.CaNhan;

import java.util.ArrayList;
import java.util.List;

public class DBCRMHandler extends SQLiteOpenHelper {

    private static final String DATABASE_NAME = "crm.db";
    private static final int DATABASE_VERSION = 5;

    public DBCRMHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        // 1. Bảng NHANVIEN
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

        // 2. Bảng COMPANY
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

        // 3. Bảng LEAD
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
                "NGAYLIENHE TEXT," +
                "FOREIGN KEY(GIAOCHO) REFERENCES NHANVIEN(ID) ON DELETE SET NULL" +
                ");");

        // 4. Bảng CONTACT (ĐÃ ĐIỀU CHỈNH)
        db.execSQL("CREATE TABLE CONTACT (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "HOTEN TEXT," +
                "DANHXUNG TEXT," +
                "TEN TEXT," +
                "DIENTHOAI TEXT," +
                "EMAIL TEXT," +
                "NGAYSINH TEXT," +
                "GIOITINH TEXT," +
                "DIACHI TEXT," +
                "QUANHUYEN TEXT," +
                "TINHTP TEXT," +
                "QUOCGIA TEXT," +
                "CHUCVU TEXT," +
                "CONGTY TEXT," +
                "MOTA TEXT," +
                "GHICHU TEXT," +
                "GIAOCHO TEXT," +
                "NGAYTAO TEXT," +
                "NGAYSUA TEXT," + // THÊM CÁI NÀY VÀO
                "CUOCGOI INTEGER," +
                "CUOCHOP INTEGER," +
                "FOREIGN KEY(GIAOCHO) REFERENCES NHANVIEN(ID) ON DELETE SET NULL" +
                ");");

        // 5. Bảng COHOI
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

        // 6. Bảng BAOGIA
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

        // 7. Bảng DONHANG
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

        // 8. Bảng HOATDONG
        db.execSQL("CREATE TABLE HOATDONG (" +
                "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "TENHOATDONG TEXT," +
                "THOIGIANBATDAU TEXT," +
                "THOIGIANKETTHUC TEXT," +
                "NGAYBATDAU TEXT," +
                "TINHTRANG TEXT," +
                "NHANVIEN INTEGER," +
                "TOCHUC INTEGER," +
                "NGUOILIENHE INTEGER," +
                "COHOI INT," +
                "MOTA TEXT," +
                "GIAOCHO INTEGER," +
                "FOREIGN KEY(NHANVIEN) REFERENCES NHANVIEN(ID) ON DELETE SET NULL," +
                "FOREIGN KEY(TOCHUC) REFERENCES CONGTY(ID) ON DELETE SET NULL," +
                "FOREIGN KEY(NGUOILIENHE) REFERENCES CONTACT(ID) ON DELETE SET NULL," +
                "FOREIGN KEY(COHOI) REFERENCES LEADCOHOI(ID) ON DELETE SET NULL," +
                "FOREIGN KEY(GIAOCHO) REFERENCES NHANVIEN(ID) ON DELETE SET NULL" +
                ");");

        // 9. Bảng GOPY
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

        // 10. Bảng SANPHAM
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
        // Xóa bảng cũ
        String[] tables = {"NHANVIEN", "COMPANY", "LEAD", "CONTACT", "COHOI", "BAOGIA", "DONHANG", "HOATDONG", "GOPY", "SANPHAM"};
        for (String table : tables) {
            db.execSQL("DROP TABLE IF EXISTS " + table);
        }
        onCreate(db);
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