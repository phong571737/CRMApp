package com.example.crmmobile.DataBase;
import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.example.crmmobile.IndividualDirectory.CaNhan;

import java.util.ArrayList;
import java.util.List;

public class CaNhanRepository {

    private DBCRMHandler dbHandler;

    public CaNhanRepository(Context context) {
        dbHandler = new DBCRMHandler(context);
    }

//    public void add(CaNhan cn) {
//        SQLiteDatabase db = dbHandler.getWritableDatabase();
//        ContentValues values = new ContentValues();
//
//        values.put("HOTEN", cn.getHoVaTen());
//        values.put("DANHXUNG", cn.getDanhXung());
//        values.put("TEN", cn.getTen());
//        values.put("CONGTY", cn.getCongTy());
//        values.put("GIOITINH", cn.getGioiTinh());
//        values.put("DIENTHOAI", cn.getDiDong());
//        values.put("EMAIL", cn.getEmail());
//        values.put("NGAYSINH", cn.getNgaySinh());
//        values.put("NGAYTAO", cn.getNgayTao());
//        values.put("DIACHI", cn.getDiaChi());
//        values.put("QUANHUYEN", cn.getQuanHuyen());
//        values.put("TINHTP", cn.getTinhTP());
//        values.put("QUOCGIA", cn.getQuocGia());
//        values.put("MOTA", cn.getMoTa());
//        values.put("GHICHU", cn.getGhiChu());
//        values.put("GIAOCHO", cn.getGiaoCho());
//        values.put("CUOCGOI", cn.getSoCuocGoi());
//        values.put("CUOCHOP", cn.getSoCuocHop());
//
//        db.insert("CONTACT", null, values);
//        db.close();
//    }

    public long add(CaNhan cn) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
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
        values.put("DIACHI", cn.getDiaChi());
        values.put("QUANHUYEN", cn.getQuanHuyen());
        values.put("TINHTP", cn.getTinhTP());
        values.put("QUOCGIA", cn.getQuocGia());
        values.put("MOTA", cn.getMoTa());
        values.put("GHICHU", cn.getGhiChu());
        // Lưu giaoChoID nếu có, nếu không thì lưu null
        if (cn.getGiaoChoID() != null && cn.getGiaoChoID() > 0) {
            values.put("GIAOCHO", cn.getGiaoChoID());
        } else {
            values.putNull("GIAOCHO");
        }
        values.put("CUOCGOI", cn.getSoCuocGoi());
        values.put("CUOCHOP", cn.getSoCuocHop());

        long newId = db.insert("CONTACT", null, values);
        db.close();
        return newId;
    }

    public List<CaNhan> getAllCaNhan() {
        List<CaNhan> list = new ArrayList<>();
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        Cursor cursor = db.rawQuery("SELECT * FROM CONTACT", null);

        if (cursor.moveToFirst()) {
            do {
                CaNhan cn = new CaNhan();
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
                cn.setDiaChi(cursor.getString(cursor.getColumnIndexOrThrow("DIACHI")));
                cn.setQuanHuyen(cursor.getString(cursor.getColumnIndexOrThrow("QUANHUYEN")));
                cn.setTinhTP(cursor.getString(cursor.getColumnIndexOrThrow("TINHTP")));
                cn.setQuocGia(cursor.getString(cursor.getColumnIndexOrThrow("QUOCGIA")));
                cn.setMoTa(cursor.getString(cursor.getColumnIndexOrThrow("MOTA")));
                cn.setGhiChu(cursor.getString(cursor.getColumnIndexOrThrow("GHICHU")));
                // Đọc GIAOCHO từ database (INTEGER) và set vào giaoChoID
                int giaoChoIdIndex = cursor.getColumnIndexOrThrow("GIAOCHO");
                if (!cursor.isNull(giaoChoIdIndex)) {
                    int giaoChoId = cursor.getInt(giaoChoIdIndex);
                    cn.setGiaoChoID(giaoChoId);
                } else {
                    cn.setGiaoChoID(null);
                }
                cn.setSoCuocGoi(cursor.getInt(cursor.getColumnIndexOrThrow("CUOCGOI")));
                cn.setSoCuocHop(cursor.getInt(cursor.getColumnIndexOrThrow("CUOCHOP")));
                list.add(cn);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();
        return list;
    }

    public void delete(int id) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
        db.delete("CONTACT", "ID=?", new String[]{String.valueOf(id)});
        db.close();
    }

    public int update(CaNhan cn) {
        SQLiteDatabase db = dbHandler.getWritableDatabase();
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
        values.put("DIACHI", cn.getDiaChi());
        values.put("QUANHUYEN", cn.getQuanHuyen());
        values.put("TINHTP", cn.getTinhTP());
        values.put("QUOCGIA", cn.getQuocGia());
        values.put("MOTA", cn.getMoTa());
        values.put("GHICHU", cn.getGhiChu());
        // Lưu giaoChoID nếu có, nếu không thì lưu null
        if (cn.getGiaoChoID() != null && cn.getGiaoChoID() > 0) {
            values.put("GIAOCHO", cn.getGiaoChoID());
        } else {
            values.putNull("GIAOCHO");
        }
        values.put("CUOCGOI", cn.getSoCuocGoi());
        values.put("CUOCHOP", cn.getSoCuocHop());

        int result = db.update("CONTACT", values, "ID=?", new String[]{String.valueOf(cn.getId())});
        db.close();
        return result;
    }

// them vao de lay du lieu id + name cua nguoi lien he cho dropdown trong form
public List<CaNhan> getAllIdName() {
    List<CaNhan> list = new ArrayList<>();

    SQLiteDatabase db = dbHandler.getReadableDatabase();
    Cursor cursor = db.rawQuery(
            "SELECT ID, HOTEN, TEN FROM CONTACT",
            null
    );

    Log.d("DEBUG_CaNhanRepo", "cursor count = " + cursor.getCount());

    while (cursor.moveToNext()) {
        CaNhan cn = new CaNhan();

        int id = cursor.getInt(cursor.getColumnIndexOrThrow("ID"));
        String hoTen = cursor.getString(cursor.getColumnIndexOrThrow("HOTEN"));
        String ten = cursor.getString(cursor.getColumnIndexOrThrow("TEN"));

        cn.setId(id);

        // GHÉP 2 CỘT
        cn.setHoVaTen(
                (hoTen != null ? hoTen : "") +
                        (ten != null ? " " + ten : "")
        );

        list.add(cn);

        Log.d("DEBUG_CaNhanRepo",
                "Loaded: id=" + cn.getId() + ", name=" + cn.getHoVaTen());
    }

        cursor.close();
        db.close();

        return list;
    }

    public CaNhan getById(int id) {
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        CaNhan cn = null;

        Cursor cursor = db.rawQuery("SELECT * FROM CONTACT WHERE ID=?",
                new String[]{String.valueOf(id)});

        if (cursor.moveToFirst()) {
            cn = new CaNhan();
            cn.setId(cursor.getInt(cursor.getColumnIndexOrThrow("ID")));
            cn.setHoVaTen(cursor.getString(cursor.getColumnIndexOrThrow("HOTEN")));
            cn.setDanhXung(cursor.getString(cursor.getColumnIndexOrThrow("DANHXUNG")));
            cn.setTen(cursor.getString(cursor.getColumnIndexOrThrow("TEN")));
            cn.setDiDong(cursor.getString(cursor.getColumnIndexOrThrow("DIENTHOAI")));
            cn.setEmail(cursor.getString(cursor.getColumnIndexOrThrow("EMAIL")));
            cn.setCongTy(cursor.getString(cursor.getColumnIndexOrThrow("CONGTY")));
            cn.setGioiTinh(cursor.getString(cursor.getColumnIndexOrThrow("GIOITINH")));
            cn.setNgaySinh(cursor.getString(cursor.getColumnIndexOrThrow("NGAYSINH")));
            cn.setNgayTao(cursor.getString(cursor.getColumnIndexOrThrow("NGAYTAO")));
            cn.setDiaChi(cursor.getString(cursor.getColumnIndexOrThrow("DIACHI")));
            cn.setQuanHuyen(cursor.getString(cursor.getColumnIndexOrThrow("QUANHUYEN")));
            cn.setTinhTP(cursor.getString(cursor.getColumnIndexOrThrow("TINHTP")));
            cn.setQuocGia(cursor.getString(cursor.getColumnIndexOrThrow("QUOCGIA")));
            cn.setMoTa(cursor.getString(cursor.getColumnIndexOrThrow("MOTA")));
            cn.setGhiChu(cursor.getString(cursor.getColumnIndexOrThrow("GHICHU")));
            // Đọc GIAOCHO từ database (INTEGER) và set vào giaoChoID
            int giaoChoIdIndex = cursor.getColumnIndexOrThrow("GIAOCHO");
            if (!cursor.isNull(giaoChoIdIndex)) {
                int giaoChoId = cursor.getInt(giaoChoIdIndex);
                cn.setGiaoChoID(giaoChoId);
            } else {
                cn.setGiaoChoID(null);
            }
            cn.setSoCuocGoi(cursor.getInt(cursor.getColumnIndexOrThrow("CUOCGOI")));
            cn.setSoCuocHop(cursor.getInt(cursor.getColumnIndexOrThrow("CUOCHOP")));
        }

        cursor.close();
        db.close();
        return cn;
    }

    // Lấy số lượng CaNhan theo GIAOCHO (trả về Map với key là ID nhân viên, value là số lượng)
    public java.util.Map<Integer, Integer> getCountByGiaoCho() {
        java.util.Map<Integer, Integer> result = new java.util.HashMap<>();
        SQLiteDatabase db = dbHandler.getReadableDatabase();
        
        // Query để đếm số lượng CaNhan theo GIAOCHO
        // Xử lý cả trường hợp GIAOCHO là INTEGER hoặc TEXT trong database
        Cursor cursor = db.rawQuery(
            "SELECT CAST(GIAOCHO AS INTEGER) as GIAOCHO_ID, COUNT(*) as COUNT " +
            "FROM CONTACT " +
            "WHERE GIAOCHO IS NOT NULL AND GIAOCHO != '' AND CAST(GIAOCHO AS INTEGER) > 0 " +
            "GROUP BY CAST(GIAOCHO AS INTEGER) " +
            "ORDER BY CAST(GIAOCHO AS INTEGER)",
            null
        );
        
        if (cursor.moveToFirst()) {
            do {
                try {
                    int giaoChoId = cursor.getInt(cursor.getColumnIndexOrThrow("GIAOCHO_ID"));
                    int count = cursor.getInt(cursor.getColumnIndexOrThrow("COUNT"));
                    result.put(giaoChoId, count);
                    Log.d("CaNhanRepository", "GIAOCHO_ID: " + giaoChoId + ", COUNT: " + count);
                } catch (Exception e) {
                    Log.e("CaNhanRepository", "Error reading GIAOCHO: " + e.getMessage());
                }
            } while (cursor.moveToNext());
        }
        
        Log.d("CaNhanRepository", "Total distinct GIAOCHO: " + result.size());
    cursor.close();
    db.close();
        return result;
}

}
