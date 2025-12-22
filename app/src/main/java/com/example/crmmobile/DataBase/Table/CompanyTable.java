package com.example.crmmobile.DataBase.Table;

public class CompanyTable {
    public static final String TABLE_NAME = "COMPANY";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME + " (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "TEN_CONG_TY TEXT," +
                    "TRANG_THAI TEXT," +
                    "WEBSITE TEXT," +
                    "DIEN_THOAI TEXT," +
                    "EMAIL TEXT," +
                    "NGANH_NGHE TEXT," +
                    "DIA_CHI TEXT," +
                    "QUAN_HUYEN TEXT," +
                    "TINH_TP TEXT," +
                    "QUOC_GIA TEXT," +
                    "TINH_TRANG_MUA_HANG TEXT," +
                    "SO_DON_HANG INTEGER," +
                    "NGAY_MUA_DAU TEXT," +
                    "NGAY_MUA_CUOI TEXT," +
                    "TONG_DOANH_THU REAL," +
                    "GIAO_CHO INTEGER," +
                    "IS_STARRED INTEGER," + // 0: false, 1: true
                    "SHOW_TRAO_DOI INTEGER," + // 0: false, 1: true

                    // Có thể thêm các khóa ngoại nếu cần
                    "FOREIGN KEY(GIAO_CHO) REFERENCES NHANVIEN(ID) ON DELETE SET NULL" +
                    ");";
}
