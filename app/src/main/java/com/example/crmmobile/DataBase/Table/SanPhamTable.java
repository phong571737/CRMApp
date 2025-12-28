package com.example.crmmobile.DataBase.Table;

public class SanPhamTable {
    public static final String TABLE_NAME = "SANPHAM";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "TEN TEXT," +
                    "MOTA TEXT," +
                    "DONGIA INTEGER," +
                    "TRANGTHAI TEXT," +
                    "NGAYTAO TEXT," +
                    "NGUOITAO INTEGER," +
                    "MOTA_THEM TEXT," +
                    "FOREIGN KEY(NGUOITAO) REFERENCES NHANVIEN(ID) ON DELETE SET NULL" +
                    ");";
}
