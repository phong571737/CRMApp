package com.example.crmmobile.IndividualDirectory;

import java.io.Serializable;

public class CaNhan implements Serializable {
    private int id;
    private String hoVaTen;
    private String danhXung;
    private String ten;
    private String congTy;
    private String gioiTinh;
    private String diDong;
    private String email;
    private String ngaySinh;
    private String ngayTao;
    private String ngaySua;
    private String diaChi;
    private String quanHuyen;
    private String tinhTP;
    private String quocGia;
    private String moTa;
    private String ghiChu;
    private String giaoCho;
    private int soCuocGoi;
    private int soCuocHop;

    // Constructor mặc định
    public CaNhan() {}

    // Constructor đơn giản để khởi tạo mặc định
    public CaNhan(String hoVaTen, String congTy, String ngayTao, int soCuocGoi, int soCuocHop) {
        this.hoVaTen = hoVaTen;
        this.congTy = congTy;
        this.ngayTao = ngayTao;
        this.soCuocGoi = soCuocGoi;
        this.soCuocHop = soCuocHop;
    }

    public CaNhan(String danhXung, String hoVaTen, String ten, String congTy, String ngayTao, int soCuocGoi, int soCuocHop) {
        this.danhXung = danhXung;
        this.hoVaTen = hoVaTen;
        this.ten = ten;
        this.congTy = congTy;
        this.ngayTao = ngayTao;
        this.soCuocGoi = soCuocGoi;
        this.soCuocHop = soCuocHop;
    }

    // Getter / Setter
    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getHoVaTen() { return hoVaTen; }
    public void setHoVaTen(String hoVaTen) { this.hoVaTen = hoVaTen; }

    public String getDanhXung() { return danhXung; }
    public void setDanhXung(String danhXung) { this.danhXung = danhXung; }

    public String getTen() { return ten; }
    public void setTen(String ten) { this.ten = ten; }

    public String getCongTy() { return congTy; }
    public void setCongTy(String congTy) { this.congTy = congTy; }

    public String getGioiTinh() { return gioiTinh; }
    public void setGioiTinh(String gioiTinh) { this.gioiTinh = gioiTinh; }

    public String getDiDong() { return diDong; }
    public void setDiDong(String diDong) { this.diDong = diDong; }

    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }

    public String getNgaySinh() { return ngaySinh; }
    public void setNgaySinh(String ngaySinh) { this.ngaySinh = ngaySinh; }

    public String getNgayTao() { return ngayTao; }
    public void setNgayTao(String ngayTao) { this.ngayTao = ngayTao; }

    public String getNgaySua() { return ngaySua; }
    public void setNgaySua(String ngaySua) { this.ngaySua = ngaySua; }

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getQuanHuyen() { return quanHuyen; }
    public void setQuanHuyen(String quanHuyen) { this.quanHuyen = quanHuyen; }

    public String getTinhTP() { return tinhTP; }
    public void setTinhTP(String tinhTP) { this.tinhTP = tinhTP; }

    public String getQuocGia() { return quocGia; }
    public void setQuocGia(String quocGia) { this.quocGia = quocGia; }

    public String getMoTa() { return moTa; }
    public void setMoTa(String moTa) { this.moTa = moTa; }

    public String getGhiChu() { return ghiChu; }
    public void setGhiChu(String ghiChu) { this.ghiChu = ghiChu; }

    public String getGiaoCho() { return giaoCho; }
    public void setGiaoCho(String giaoCho) { this.giaoCho = giaoCho; }

    public int getSoCuocGoi() { return soCuocGoi; }
    public void setSoCuocGoi(int soCuocGoi) { this.soCuocGoi = soCuocGoi; }

    public int getSoCuocHop() { return soCuocHop; }
    public void setSoCuocHop(int soCuocHop) { this.soCuocHop = soCuocHop; }
}