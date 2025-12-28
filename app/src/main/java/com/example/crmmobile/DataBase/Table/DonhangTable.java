package com.example.crmmobile.DataBase.Table;

public class DonhangTable {
    public static final String TABLE_NAME = "DONHANG";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
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

                    "NGUOITAO INTEGER," +
                    "EXTRA_JSON TEXT," +
                    "PHUONG_THUC_THANH_TOAN TEXT"+
                    "TINH_TRANG_THANH_TOAN TEXTTINH_TRANG_THANH_TOAN TEXT"+

                    "FOREIGN KEY(CONGTY) REFERENCES COMPANY(ID) ON DELETE SET NULL," +
                    "FOREIGN KEY(NGUOILIENHE) REFERENCES CONTACT(ID) ON DELETE SET NULL," +
                    "FOREIGN KEY(COHOI) REFERENCES COHOI(ID) ON DELETE SET NULL," +
                    "FOREIGN KEY(BAOGIA) REFERENCES BAOGIA(ID) ON DELETE SET NULL," +
                    "FOREIGN KEY(GIAOCHO) REFERENCES NHANVIEN(ID) ON DELETE SET NULL" +
                    ");";
}
