package com.example.crmmobile.DataBase.Table;

public class BaogiaTable {
    public static final String TABLE_NAME = "BAOGIA";
    public static final String CREATE_TABLE =
            "CREATE TABLE " + TABLE_NAME  + " (" +
                    "ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                    "TENBAOGIA TEXT," +
                    "CONGTY INTEGER," +
                    "NGUOILIENHE INTEGER," +
                    "COHOI INTEGER," +
                    "TINHTRANG TEXT," +
                    "SANPHAM TEXT," +
                    "NGAYTAO TEXT," +
                    "SOLUONG INTEGER," +
                    "DONGIA INTEGER," +
                    "TONGTIEN INTEGER," +
                    "GIAOCHO INTEGER," +
                    "FOREIGN KEY(CONGTY) REFERENCES COMPANY(ID) ON DELETE SET NULL," +
                    "FOREIGN KEY(NGUOILIENHE) REFERENCES CONTACT(ID) ON DELETE SET NULL," +
                    "FOREIGN KEY(COHOI) REFERENCES COHOI(ID) ON DELETE SET NULL" +
                    ");";
}
