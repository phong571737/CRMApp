package com.example.crmmobile.HoatDongDirectory;

import java.io.Serializable;

public class HoatDong implements Serializable {

    private int id;
    private String tenHoatDong;
    private String thoiGianBatDau;
    private String thoiGianKetThuc;
    private String tinhTrang;
    private String khachHang;
    private Integer nhanVien;      // FK → NHANVIEN.ID
    private String toChuc;
    private Integer nguoiLienHe;   // FK → CONTACT.ID
    private Integer lead;          // FK → LEAD.ID
    private String lienQuanToi;
    private String moTa;
    private Integer giaoCho;       // FK → NHANVIEN.ID

    public HoatDong() {
    }

    public HoatDong(int id, String tenHoatDong, String thoiGianBatDau, String thoiGianKetThuc,
                    String tinhTrang, String khachHang, Integer nhanVien, String toChuc,
                    Integer nguoiLienHe, Integer lead, String lienQuanToi, String moTa,
                    Integer giaoCho) {

        this.id = id;
        this.tenHoatDong = tenHoatDong;
        this.thoiGianBatDau = thoiGianBatDau;
        this.thoiGianKetThuc = thoiGianKetThuc;
        this.tinhTrang = tinhTrang;
        this.khachHang = khachHang;
        this.nhanVien = nhanVien;
        this.toChuc = toChuc;
        this.nguoiLienHe = nguoiLienHe;
        this.lead = lead;
        this.lienQuanToi = lienQuanToi;
        this.moTa = moTa;
        this.giaoCho = giaoCho;
    }

    // GETTERS & SETTERS
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTenHoatDong() {
        return tenHoatDong;
    }

    public void setTenHoatDong(String tenHoatDong) {
        this.tenHoatDong = tenHoatDong;
    }

    public String getThoiGianBatDau() {
        return thoiGianBatDau;
    }

    public void setThoiGianBatDau(String thoiGianBatDau) {
        this.thoiGianBatDau = thoiGianBatDau;
    }

    public String getThoiGianKetThuc() {
        return thoiGianKetThuc;
    }

    public void setThoiGianKetThuc(String thoiGianKetThuc) {
        this.thoiGianKetThuc = thoiGianKetThuc;
    }

    public String getTinhTrang() {
        return tinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        this.tinhTrang = tinhTrang;
    }

    public String getKhachHang() {
        return khachHang;
    }

    public void setKhachHang(String khachHang) {
        this.khachHang = khachHang;
    }

    public Integer getNhanVien() {
        return nhanVien;
    }

    public void setNhanVien(Integer nhanVien) {
        this.nhanVien = nhanVien;
    }

    public String getToChuc() {
        return toChuc;
    }

    public void setToChuc(String toChuc) {
        this.toChuc = toChuc;
    }

    public Integer getNguoiLienHe() {
        return nguoiLienHe;
    }

    public void setNguoiLienHe(Integer nguoiLienHe) {
        this.nguoiLienHe = nguoiLienHe;
    }

    public Integer getLead() {
        return lead;
    }

    public void setLead(Integer lead) {
        this.lead = lead;
    }

    public String getLienQuanToi() {
        return lienQuanToi;
    }

    public void setLienQuanToi(String lienQuanToi) {
        this.lienQuanToi = lienQuanToi;
    }

    public String getMoTa() {
        return moTa;
    }

    public void setMoTa(String moTa) {
        this.moTa = moTa;
    }

    public Integer getGiaoCho() {
        return giaoCho;
    }

    public void setGiaoCho(Integer giaoCho) {
        this.giaoCho = giaoCho;
    }

    @Override
    public String toString() {
        return tenHoatDong;
    }
}
