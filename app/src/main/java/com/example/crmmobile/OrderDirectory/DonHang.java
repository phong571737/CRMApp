package com.example.crmmobile.OrderDirectory;

/**
 * Entity map 1-1 với bảng DONHANG trong DB.
 * Cột trong DB:
 *  ID, TENDONHANG, CONGTY, NGUOILIENHE, COHOI, BAOGIA,
 *  TINHTRANG, NGAYDATHANG, NGAYNHANHANG,
 *  SANPHAM, SOLUONG, DONGIA, TONGTIEN,
 *  MOTA, GIAOCHO
 */
public class DonHang {

    private int id;
    private String tenDonHang;
    private int congTyId;
    private int nguoiLienHeId;
    private int coHoiId;
    private int baoGiaId;
    private String tinhTrang;
    private String ngayDatHang;
    private String ngayNhanHang;
    private String sanPham;   // tạm: chuỗi mô tả danh sách SP
    private int soLuong;      // tổng số lượng
    private long donGia;      // có thể dùng đơn giá trung bình hoặc 0
    private long tongTien;    // tổng tiền
    private String moTa;
    private int giaoChoId;
    private int nguoiTaoId;     // ✅ NEW
    private String extraJson;   // ✅ NEW


    public DonHang() {
    }

    public DonHang(int id,
                   String tenDonHang,
                   int congTyId,
                   int nguoiLienHeId,
                   int coHoiId,
                   int baoGiaId,
                   String tinhTrang,
                   String ngayDatHang,
                   String ngayNhanHang,
                   String sanPham,
                   int soLuong,
                   long donGia,
                   long tongTien,
                   String moTa,
                   int giaoChoId) {
        this.id = id;
        this.tenDonHang = tenDonHang;
        this.congTyId = congTyId;
        this.nguoiLienHeId = nguoiLienHeId;
        this.coHoiId = coHoiId;
        this.baoGiaId = baoGiaId;
        this.tinhTrang = tinhTrang;
        this.ngayDatHang = ngayDatHang;
        this.ngayNhanHang = ngayNhanHang;
        this.sanPham = sanPham;
        this.soLuong = soLuong;
        this.donGia = donGia;
        this.tongTien = tongTien;
        this.moTa = moTa;
        this.giaoChoId = giaoChoId;
    }

    // === Getter / Setter ===
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getTenDonHang() { return tenDonHang; }
    public void setTenDonHang(String tenDonHang) { this.tenDonHang = tenDonHang; }

    public int getCongTyId() { return congTyId; }
    public void setCongTyId(int congTyId) { this.congTyId = congTyId; }

    public int getNguoiLienHeId() { return nguoiLienHeId; }
    public void setNguoiLienHeId(int nguoiLienHeId) { this.nguoiLienHeId = nguoiLienHeId; }

    public int getCoHoiId() { return coHoiId; }
    public void setCoHoiId(int coHoiId) { this.coHoiId = coHoiId; }

    public int getBaoGiaId() { return baoGiaId; }
    public void setBaoGiaId(int baoGiaId) { this.baoGiaId = baoGiaId; }

    public String getTinhTrang() { return tinhTrang; }
    public void setTinhTrang(String tinhTrang) { this.tinhTrang = tinhTrang; }

    public String getNgayDatHang() { return ngayDatHang; }
    public void setNgayDatHang(String ngayDatHang) { this.ngayDatHang = ngayDatHang; }

    public String getNgayNhanHang() { return ngayNhanHang; }
    public void setNgayNhanHang(String ngayNhanHang) { this.ngayNhanHang = ngayNhanHang; }

    public String getSanPham() { return sanPham; }
    public void setSanPham(String sanPham) { this.sanPham = sanPham; }

    public int getSoLuong() { return soLuong; }
    public void setSoLuong(int soLuong) { this.soLuong = soLuong; }

    public long getDonGia() { return donGia; }
    public void setDonGia(long donGia) { this.donGia = donGia; }

    public long getTongTien() { return tongTien; }
    public void setTongTien(long tongTien) { this.tongTien = tongTien; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public int getGiaoChoId() { return giaoChoId; }
    public void setGiaoChoId(int giaoChoId) { this.giaoChoId = giaoChoId; }
    public int getNguoiTaoId() { return nguoiTaoId; }
    public void setNguoiTaoId(int nguoiTaoId) { this.nguoiTaoId = nguoiTaoId; }

    public String getExtraJson() { return extraJson; }
    public void setExtraJson(String extraJson) { this.extraJson = extraJson; }
}
