package com.example.crmmobile.SanPhamDirectory;

public class SanPham {
    private Integer ID;
    private String name;
    private String Mota;
    private Integer Dongia;
    private String Trangthai;
    private String Ngaytao;
    private Integer Nguoitao;
    private String MotaThem;

    public SanPham() {
    }

    public SanPham(String name, Integer dongia, String ngaytao, Integer nguoitao, String trangthai) {
        this.name = name;
        Dongia = dongia;
        Ngaytao = ngaytao;
        Nguoitao = nguoitao;
        Trangthai = trangthai;
    }

    public Integer getID() {
        return ID;
    }

    public void setID(Integer ID) {
        this.ID = ID;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getMota() {
        return Mota;
    }

    public void setMota(String mota) {
        Mota = mota;
    }

    public String getTrangthai() {
        return Trangthai;
    }

    public void setTrangthai(String trangthai) {
        Trangthai = trangthai;
    }

    public String getNgaytao() {
        return Ngaytao;
    }

    public void setNgaytao(String ngaytao) {
        Ngaytao = ngaytao;
    }

    public Integer getNguoitao() {
        return Nguoitao;
    }

    public void setNguoitao(Integer nguoitao) {
        Nguoitao = nguoitao;
    }

    public String getMotaThem() {
        return MotaThem;
    }

    public void setMotaThem(String motaThem) {
        MotaThem = motaThem;
    }

    public Integer getDongia() {
        return Dongia;
    }

    public void setDongia(Integer dongia) {
        Dongia = dongia;
    }
}
