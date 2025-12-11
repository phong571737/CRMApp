package com.example.crmmobile.LeadDirectory;

public class Lead {
    private Integer ID;
    private String Hoten;
    private String DienThoai;
    private String Email;
    private String Ngaysinh;
    private String Gioitinh;
    private String Diachi;
    private String Chucvu;
    private String Congty;
    private String TinhTrang;
    private String Mota;
    private String Giaocho;
    private String NgayLienHe;

    public Lead(){}

    public Lead(String hoten, String ngayLienHe, String congty) {
        this.Hoten = hoten;
        this.NgayLienHe = ngayLienHe;
        this.Congty = congty;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getHoten() {
        return Hoten;
    }

    public void setHoten(String hoten) {
        Hoten = hoten;
    }

    public String getDienThoai() {
        return DienThoai;
    }

    public void setDienThoai(String dienThoai) {
        DienThoai = dienThoai;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getNgaysinh() {
        return Ngaysinh;
    }

    public void setNgaysinh(String ngaysinh) {
        Ngaysinh = ngaysinh;
    }

    public String getGioitinh() {
        return Gioitinh;
    }

    public void setGioitinh(String gioitinh) {
        Gioitinh = gioitinh;
    }

    public String getDiachi() {
        return Diachi;
    }

    public void setDiachi(String diachi) {
        Diachi = diachi;
    }

    public String getChucvu() {
        return Chucvu;
    }

    public void setChucvu(String chucvu) {
        Chucvu = chucvu;
    }

    public String getCongty() {
        return Congty;
    }

    public void setCongty(String congty) {
        Congty = congty;
    }

    public String getTinhTrang() {
        return TinhTrang;
    }

    public void setTinhTrang(String tinhTrang) {
        TinhTrang = tinhTrang;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }

    public String getGiaocho() {
        return Giaocho;
    }

    public void setGiaocho(String giaocho) {
        Giaocho = giaocho;
    }

    public String getNgayLienHe() {
        return NgayLienHe;
    }

    public void setNgayLienHe(String ngayLienHe) {
        NgayLienHe = ngayLienHe;
    }
}
