package com.example.crmmobile.LeadDirectory;

import java.io.Serializable;

public class Lead{
    private Integer ID;
    private String HovaTendem;
    private String Title;
    private String Ten;
    private String DienThoai;
    private String Email;
    private String Ngaysinh;
    private String Gioitinh;
    private String Diachi;
    private String Chucvu;
    private String Congty;
    private String TinhTrang;
    private String Mota;
    private String Ghichu;
    private String Tiemnang;
    private String Danhgia;
    private String Nguoitao;
    private Integer NguoitaoID;
    private String Giaocho;
    private Integer GiaochoID;
    private String NgayLienHe;
    private String Nganhnghe;
    private String SoNV;
    private String DoanhThu;
    private String MaThue;
    private String QuanHuyen;
    private String Tinh;
    private String Thanhpho;
    private String QuocGia;
    private Integer Cuocgoi;
    private Integer Cuochop;

    public Lead(){}

    public Lead(String Titile, String HovaTendem,String Ten, String ngayLienHe, String congty, String Email, String TinhTrang) {
        this.Title = Titile;
        this.HovaTendem = HovaTendem;
        this.Ten = Ten;
        this.NgayLienHe = ngayLienHe;
        this.Congty = congty;
        this.Email = Email;
        this.TinhTrang = TinhTrang;
    }

    public String getTiemnang() {
        return Tiemnang;
    }

    public void setTiemnang(String tiemnang) {
        Tiemnang = tiemnang;
    }

    public String getDanhgia() {
        return Danhgia;
    }

    public void setDanhgia(String danhgia) {
        Danhgia = danhgia;
    }

    public String getNguoitao() {
        return Nguoitao;
    }

    public void setNguoitao(String nguoitao) {
        Nguoitao = nguoitao;
    }

    public Integer getNguoitaoID() {
        return NguoitaoID;
    }

    public void setNguoitaoID(Integer nguoitaoID) {
        NguoitaoID = nguoitaoID;
    }

    public String getGhichu() {
        return Ghichu;
    }

    public void setGhichu(String ghichu) {
        Ghichu = ghichu;
    }

    public String getThanhpho() {
        return Thanhpho;
    }

    public void setThanhpho(String thanhpho) {
        Thanhpho = thanhpho;
    }

    public String getQuocGia() {
        return QuocGia;
    }

    public void setQuocGia(String quocGia) {
        QuocGia = quocGia;
    }

    public String getTinh() {
        return Tinh;
    }

    public void setTinh(String tinh) {
        Tinh = tinh;
    }

    public String getQuanHuyen() {
        return QuanHuyen;
    }

    public void setQuanHuyen(String quanHuyen) {
        QuanHuyen = quanHuyen;
    }

    public String getMaThue() {
        return MaThue;
    }

    public void setMaThue(String maThue) {
        MaThue = maThue;
    }

    public String getDoanhThu() {
        return DoanhThu;
    }

    public void setDoanhThu(String doanhThu) {
        DoanhThu = doanhThu;
    }

    public String getSoNV() {
        return SoNV;
    }

    public void setSoNV(String soNV) {
        SoNV = soNV;
    }

    public String getNganhnghe() {
        return Nganhnghe;
    }

    public void setNganhnghe(String nganhnghe) {
        Nganhnghe = nganhnghe;
    }

    public String getHovaTendem() {
        return HovaTendem;
    }

    public void setHovaTendem(String hovaTendem) {
        HovaTendem = hovaTendem;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getTen() {
        return Ten;
    }

    public void setTen(String ten) {
        Ten = ten;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
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

    public Integer getGiaochoID() {
        return GiaochoID;
    }

    public void setGiaochoID(Integer giaochoID) {
        GiaochoID = giaochoID;
    }

    public String getNgayLienHe() {
        return NgayLienHe;
    }

    public void setNgayLienHe(String ngayLienHe) {
        NgayLienHe = ngayLienHe;
    }

    public Integer getCuocgoi() {
        return Cuocgoi;
    }

    public void setCuocgoi(Integer cuocgoi) {
        Cuocgoi = cuocgoi;
    }

    public Integer getCuochop() {
        return Cuochop;
    }

    public void setCuochop(Integer cuochop) {
        Cuochop = cuochop;
    }
}
