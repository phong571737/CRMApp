package com.example.crmmobile.HoatDongDirectory;


import java.io.Serializable;

public class HoatDong implements Serializable {

    private int id;
    private String tenHoatDong;
    private String thoiGianBatDau;
    private String thoiGianKetThuc;
    private String ngayBatDau;
    private String tinhTrang;
    private int nhanVien;
    private String toChuc;
    private int nguoiLienHe;
    private int coHoi;
    private String moTa;
    private int giaoCho;

    // Constructor dùng khi INSERT
    public HoatDong(int id, String tenHoatDong,
                    String thoiGianBatDau,
                    String thoiGianKetThuc,
                    String ngayBatDau,
                    String tinhTrang,
                    int nhanVien,
                    String toChuc,
                    int nguoiLienHe,
                    int coHoi,
                    String moTa,
                    int giaoCho) {
        this.id = id;
        this.tenHoatDong = tenHoatDong;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.ngayBatDau = ngayBatDau;
        this.tinhTrang = tinhTrang;
        this.nhanVien = nhanVien;
        this.toChuc = toChuc;
        this.nguoiLienHe = nguoiLienHe;
        this.coHoi = coHoi;
        this.moTa = moTa;
        this.giaoCho = giaoCho;
    }

    public HoatDong(String tenHoatDong, String thoiGianBatDau,String thoiGianKetThuc, String ngayBatDau, String tinhTrang){
        this.tenHoatDong = tenHoatDong;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.ngayBatDau = ngayBatDau;
        this.tinhTrang = tinhTrang;
    }

    // Constructor dùng khi INSERT
    public HoatDong(String tenHoatDong,
                    String thoiGianBatDau,
                    String thoiGianKetThuc,
                    String ngayBatDau,
                    String tinhTrang,
                    int nhanVien,
                    String toChuc,
                    int nguoiLienHe,
                    int coHoi,
                    String moTa,
                    int giaoCho) {

        this.tenHoatDong = tenHoatDong;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.ngayBatDau = ngayBatDau;
        this.tinhTrang = tinhTrang;
        this.nhanVien = nhanVien;
        this.toChuc = toChuc;
        this.nguoiLienHe = nguoiLienHe;
        this.coHoi = coHoi;
        this.moTa = moTa;
        this.giaoCho = giaoCho;
    }


    // Getter
    public String getTenHoatDong() { return tenHoatDong; }
    public String getThoiGianBatDau() { return thoiGianBatDau; }
    public String getThoiGianKetThuc() { return thoiGianKetThuc; }
    public String getNgayBatDau() { return ngayBatDau; }
    public String getTinhTrang() { return tinhTrang; }
    public int getNhanVien() { return nhanVien; }
    public String getToChuc() { return toChuc; }
    public int getNguoiLienHe() { return nguoiLienHe; }
    public int getCoHoi() { return coHoi; }

    public String getMoTa() { return moTa; }

    public int getGiaoCho() { return giaoCho; }

    // Setter
    public void setId(int id) {
        this.id = id;
    }

    public void setTenHoatDong(String tenHoatDong) {
        this.tenHoatDong = tenHoatDong;
    }

    public void setThoiGianBatDau(String thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public void setThoiGianKetThuc(String thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public void setNgayBatDau(String ngayBatDau) {
        this.ngayBatDau = ngayBatDau;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public void setNhanVien(int nhanVien) {
        this.nhanVien = nhanVien;
    }

    public void setToChuc(String toChuc) {
        this.toChuc = toChuc;
    }

    public void setNguoiLienHe(int nguoiLienHe) {
        this.nguoiLienHe = nguoiLienHe;
    }

    public void setCoHoi(int coHoi) {
        this.coHoi = coHoi;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public void setGiaoCho(int giaoCho) {
        this.giaoCho = giaoCho;
    }

}
