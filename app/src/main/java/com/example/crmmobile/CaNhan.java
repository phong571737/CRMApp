package com.example.crmmobile;

public class CaNhan {
    private String danhxung;
    private String hoTen;
    private String ten;
    private String congTy;
    private String gioiTinh;
    private String diDong;
    private String email;
    private String ngaySinh;
    private String diaChi;
    private String quan;
    private String tinh;
    private String quocGia;
    private String moTa;
    private String ghiChu;
    private String giaoCho;

    private int soCuocGoi = 0;
    private int soMeeting = 0;

    // Constructor rỗng
    public CaNhan() {}

    // Constructor bắt buộc
    public CaNhan(String hoTen, String congTy, String ngaySinh, int soCuocGoi, int soMeeting) {
        this.hoTen = hoTen;
        this.congTy = congTy;
        this.ngaySinh = ngaySinh;
        this.soCuocGoi = soCuocGoi;
        this.soMeeting = soMeeting;
    }

    // Constructor đầy đủ tất cả trường
    public CaNhan(String danhxung, String hoTen, String ten, String congTy, String gioiTinh,
                  String diDong, String email, String ngaySinh, String diaChi, String quan,
                  String tinh, String quocGia, String moTa, String ghiChu, String giaoCho,
                  int soCuocGoi, int soMeeting) {
        this.danhxung = danhxung;
        this.hoTen = hoTen;
        this.ten = ten;
        this.congTy = congTy;
        this.gioiTinh = gioiTinh;
        this.diDong = diDong;
        this.email = email;
        this.ngaySinh = ngaySinh;
        this.diaChi = diaChi;
        this.quan = quan;
        this.tinh = tinh;
        this.quocGia = quocGia;
        this.moTa = moTa;
        this.ghiChu = ghiChu;
        this.giaoCho = giaoCho;
        this.soCuocGoi = soCuocGoi;
        this.soMeeting = soMeeting;
    }

    // Getters & Setters
    public String getDanhxung() { return danhxung; }
    public void setDanhxung(String danhxung) { this.danhxung = danhxung; }

    public String getHoTen() { return hoTen; }
    public void setHoTen(String hoTen) { this.hoTen = hoTen; }

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

    public String getDiaChi() { return diaChi; }
    public void setDiaChi(String diaChi) { this.diaChi = diaChi; }

    public String getQuan() { return quan; }
    public void setQuan(String quan) { this.quan = quan; }

    public String getTinh() { return tinh; }
    public void setTinh(String tinh) { this.tinh = tinh; }

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

    public int getSoMeeting() { return soMeeting; }
    public void setSoMeeting(int soMeeting) { this.soMeeting = soMeeting; }
}
