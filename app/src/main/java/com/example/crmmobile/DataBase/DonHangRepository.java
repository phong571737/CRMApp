package com.example.crmmobile.DataBase;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.crmmobile.OrderDirectory.DonHang;
import com.example.crmmobile.OrderDirectory.Order;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class DonHangRepository {

    private final DBCRMHandler dbHelper;

    // Tên bảng + cột đúng như trong DBCRMHandler
    private static final String TABLE_NAME       = "DONHANG";
    private static final String COL_ID           = "ID";
    private static final String COL_TENDONHANG   = "TENDONHANG";
    private static final String COL_CONGTY       = "CONGTY";
    private static final String COL_NGUOILIENHE  = "NGUOILIENHE";
    private static final String COL_COHOI        = "COHOI";
    private static final String COL_BAOGIA       = "BAOGIA";
    private static final String COL_TINHTRANG    = "TINHTRANG";
    private static final String COL_NGAYDATHANG  = "NGAYDATHANG";
    private static final String COL_NGAYNHANHANG = "NGAYNHANHANG";
    private static final String COL_SANPHAM      = "SANPHAM";
    private static final String COL_SOLUONG      = "SOLUONG";
    private static final String COL_DONGIA       = "DONGIA";
    private static final String COL_TONGTIEN     = "TONGTIEN";
    private static final String COL_MOTA         = "MOTA";
    private static final String COL_GIAOCHO      = "GIAOCHO";
    private static final String COL_NGUOITAO    = "NGUOITAO";
    private static final String COL_EXTRA_JSON  = "EXTRA_JSON";

    public DonHangRepository(Context context) {
        dbHelper = new DBCRMHandler(context.getApplicationContext());
    }
    private String getStringSafe(Cursor c, String col) {
        int idx = c.getColumnIndex(col);
        return (idx >= 0 && !c.isNull(idx)) ? c.getString(idx) : null;
    }

    private int getIntSafe(Cursor c, String col, int def) {
        int idx = c.getColumnIndex(col);
        if (idx < 0 || c.isNull(idx)) return def;
        try {
            return c.getInt(idx);
        } catch (Exception e) {
            try { return Integer.parseInt(c.getString(idx)); } catch (Exception ex) { return def; }
        }
    }

    private long getLongSafe(Cursor c, String col, long def) {
        int idx = c.getColumnIndex(col);
        if (idx < 0 || c.isNull(idx)) return def;
        try {
            return c.getLong(idx);
        } catch (Exception e) {
            try { return Long.parseLong(c.getString(idx)); } catch (Exception ex) { return def; }
        }
    }



    // ===== CREATE =====
    public long insert(DonHang dh) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        long newId = -1;

        ContentValues v = new ContentValues();

        // ✅ đúng schema
        v.put("TENDONHANG", dh.getTenDonHang());

        // DB của bạn đang lưu TEXT cho các cột này, không phải ID
        // Nếu bạn không có getter riêng thì lấy từ MoTa "Công ty - Liên hệ"
        String mota = dh.getMoTa() == null ? "" : dh.getMoTa();
        String congTy = "";
        String lienHe = "";
        if (mota.contains(" - ")) {
            String[] parts = mota.split(" - ");
            if (parts.length >= 1) congTy = parts[0].trim();
            if (parts.length >= 2) lienHe = parts[1].trim();
        }

        v.put("CONGTY", congTy);
        v.put("NGUOILIENHE", lienHe);

        // nếu chưa có thì để rỗng
        v.put("COHOI", "");
        v.put("BAOGIA", "");

        v.put("TINHTRANG", dh.getTinhTrang());
        v.put("NGAYDATHANG", dh.getNgayDatHang());
        v.put("NGAYNHANHANG", dh.getNgayNhanHang());

        v.put("SANPHAM", dh.getSanPham());
        v.put("SOLUONG", dh.getSoLuong());
        v.put("DONGIA", dh.getDonGia());
        v.put("TONGTIEN", dh.getTongTien());

        v.put("MOTA", dh.getMoTa());

        // GIAOCHO của bạn cũng là TEXT theo schema
        // nếu chưa có UI thì để rỗng
        v.put("GIAOCHO", "");

        try {
            newId = db.insertOrThrow("DONHANG", null, v);
        } catch (Exception e) {
            android.util.Log.e("DONHANG", "insert failed", e);
        } finally {
            db.close();
        }
        return newId;
    }


    // ===== READ: tất cả đơn hàng =====
    public List<DonHang> getAll() {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        List<DonHang> result = new ArrayList<>();

        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                null, null,
                null, null,
                COL_ID + " DESC"
        );

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    do {
                        result.add(fromCursor(cursor));
                    } while (cursor.moveToNext());
                }
            } finally {
                cursor.close();
            }
        }
        return result;
    }

    // ===== READ: 1 đơn theo ID =====
    public DonHang getById(int id) {
        SQLiteDatabase db = dbHelper.getReadableDatabase();
        DonHang donHang = null;

        Cursor cursor = db.query(
                TABLE_NAME,
                null,
                COL_ID + " = ?",
                new String[]{ String.valueOf(id) },
                null, null, null
        );

        if (cursor != null) {
            try {
                if (cursor.moveToFirst()) {
                    donHang = fromCursor(cursor);
                }
            } finally {
                cursor.close();
            }
        }
        return donHang;
    }

    // ===== UPDATE =====
    public int update(DonHang donHang) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(COL_NGUOITAO,   donHang.getNguoiTaoId());
        values.put(COL_EXTRA_JSON, donHang.getExtraJson());
        values.put(COL_TENDONHANG,   donHang.getTenDonHang());
        values.put(COL_CONGTY,       donHang.getCongTyId());
        values.put(COL_NGUOILIENHE,  donHang.getNguoiLienHeId());
        values.put(COL_COHOI,        donHang.getCoHoiId());
        values.put(COL_BAOGIA,       donHang.getBaoGiaId());
        values.put(COL_TINHTRANG,    donHang.getTinhTrang());
        values.put(COL_NGAYDATHANG,  donHang.getNgayDatHang());
        values.put(COL_NGAYNHANHANG, donHang.getNgayNhanHang());
        values.put(COL_SANPHAM,      donHang.getSanPham());
        values.put(COL_SOLUONG,      donHang.getSoLuong());
        values.put(COL_DONGIA,       donHang.getDonGia());
        values.put(COL_TONGTIEN,     donHang.getTongTien());
        values.put(COL_MOTA,         donHang.getMoTa());
        values.put(COL_GIAOCHO,      donHang.getGiaoChoId());

        return db.update(
                TABLE_NAME,
                values,
                COL_ID + " = ?",
                new String[]{ String.valueOf(donHang.getId()) }
        );
    }

    // ===== DELETE =====
    public int delete(int id) {
        SQLiteDatabase db = dbHelper.getWritableDatabase();
        return db.delete(
                TABLE_NAME,
                COL_ID + " = ?",
                new String[]{ String.valueOf(id) }
        );
    }

    // ===== Map Cursor -> DonHang =====
    private DonHang fromCursor(Cursor c) {
        DonHang dh = new DonHang();

        // ===== Cột thật sự có trong bảng DONHANG =====
        dh.setId(getIntSafe(c, "ID", 0));
        dh.setTenDonHang(getStringSafe(c, "TENDONHANG"));

        dh.setTinhTrang(getStringSafe(c, "TINHTRANG"));
        dh.setNgayDatHang(getStringSafe(c, "NGAYDATHANG"));
        dh.setNgayNhanHang(getStringSafe(c, "NGAYNHANHANG"));

        dh.setSanPham(getStringSafe(c, "SANPHAM"));
        dh.setSoLuong(getIntSafe(c, "SOLUONG", 0));
        dh.setDonGia(getLongSafe(c, "DONGIA", 0L));
        dh.setTongTien(getLongSafe(c, "TONGTIEN", 0L));

        // ===== Công ty / Người liên hệ đang là TEXT trong DB =====
        String congTyName  = getStringSafe(c, "CONGTY");
        String lienHeName  = getStringSafe(c, "NGUOILIENHE");
        String coHoiName   = getStringSafe(c, "COHOI");
        String baoGiaName  = getStringSafe(c, "BAOGIA");
        String giaoChoName = getStringSafe(c, "GIAOCHO");

        // Bạn đang dùng MoTa kiểu "Công ty - Liên hệ" để hiển thị
        String mota = getStringSafe(c, "MOTA");
        if ((mota == null || mota.trim().isEmpty())) {
            String ct = (congTyName == null) ? "" : congTyName.trim();
            String lh = (lienHeName == null) ? "" : lienHeName.trim();
            if (!ct.isEmpty() || !lh.isEmpty()) mota = ct + " - " + lh;
        }
        dh.setMoTa(mota);

        // ===== Các field ...Id trong DonHang object: DB không có -> set 0 =====
        dh.setCongTyId(0);
        dh.setNguoiLienHeId(0);
        dh.setCoHoiId(0);
        dh.setBaoGiaId(0);
        dh.setGiaoChoId(0);
        dh.setNguoiTaoId(0);

        // EXTRA_JSON không có cột -> để "{}" hoặc nhét thông tin text vào JSON (tùy bạn)
        // Nếu DonHang bạn có setExtraJson() thì dùng:
        try {
            JSONObject extra = new JSONObject();
            if (congTyName != null)  extra.put("company", congTyName);
            if (lienHeName != null)  extra.put("contact", lienHeName);
            if (coHoiName != null)   extra.put("coHoi", coHoiName);
            if (baoGiaName != null)  extra.put("baoGia", baoGiaName);
            if (giaoChoName != null) extra.put("giaoCho", giaoChoName);
            dh.setExtraJson(extra.toString());
        } catch (Exception e) {
            dh.setExtraJson("{}");
        }

        return dh;
    }


    // ===== Helper: convert sang List<Order> cho màn hình list =====
    public List<Order> getOrdersForList() {
        List<DonHang> donHangs = getAll();
        List<Order> orders = new ArrayList<>();

        for (DonHang dh : donHangs) {
            String orderCode = dh.getTenDonHang();
            //String company   = "Công ty #" + dh.getCongTyId(); // sau này map từ CompanyRepository
            String company   = dh.getMoTa();  // tạm dùng mô tả để show tên công ty
            String price     = String.valueOf(dh.getTongTien()) + " đ";
            String date      = dh.getNgayDatHang();
            String status    = dh.getTinhTrang();
            String orderType = ""; // có thể để trống hoặc suy ra từ TINHTRANG

            // Order sẽ được chỉnh thêm field id ở bước dưới
            Order o = new Order(dh.getId(), orderCode, company, price, date, status, orderType);
            orders.add(o);
        }
        return orders;
    }
}
