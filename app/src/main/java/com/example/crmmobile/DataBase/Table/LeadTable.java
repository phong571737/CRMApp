package com.example.crmmobile.DataBase.Table;

public class LeadTable {
    public static final String TABLE_NAME = "LEAD";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "TITLE TEXT," +
                    "HOVATENDEM TEXT," +
                    "TEN TEXT," +
                    "DIENTHOAI TEXT," +
                    "EMAIL TEXT," +
                    "NGAYSINH TEXT," +
                    "NGANHNGHE TEXT," +
                    "GIOITINH TEXT," +
                    "DIACHI TEXT," +
                    "QUANHUYEN TEXT," +
                    "TINH TEXT," +
                    "THANHPHO TEXT," +
                    "QUOCGIA TEXT," +
                    "CHUCVU TEXT," +
                    "CONGTY TEXT," +
                    "DOANHTHU TEXT," +
                    "SONV TEXT," +
                    "MASOTHUE TEXT," +
                    "TINHTRANG TEXT," +
                    "MOTA TEXT," +
                    "GHICHU TEXT," +
                    "TIEMNANG TEXT," +
                    "DANHGIA TEXT," +
                    "NGUOITAO INTEGER," +
                    "CUOCGOI INTEGER," +
                    "CUOCHOP INTEGER," +
                    "GIAOCHO INTEGER," +
                    "NGAYLIENHE TEXT," +
                    "FOREIGN KEY(NGUOITAO) REFERENCES NHANVIEN(ID) ON DELETE SET NULL," +
                    "FOREIGN KEY(GIAOCHO) REFERENCES NHANVIEN(ID) ON DELETE SET NULL" +
                    ");";
}
