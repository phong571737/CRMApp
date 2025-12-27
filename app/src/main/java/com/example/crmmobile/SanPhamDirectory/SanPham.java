package com.example.crmmobile.SanPhamDirectory;

public class SanPham {
    private String name;
    private String Mota;
    private long Donggia;
    private String Trangthai;
    private String Ngaytao;
    private String Nguoitao;
    private String MotaThem;
    public SanPham() { }

    public SanPham(String name, String mota, long donggia,
                   String trangthai, String ngaytao,
                   String nguoitao, String motaThem) {
        this.name = name;
        this.Mota = mota;
        this.Donggia = donggia;
        this.Trangthai = trangthai;
        this.Ngaytao = ngaytao;
        this.Nguoitao = nguoitao;
        this.MotaThem = motaThem;
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

    public long getDonggia() {
        return Donggia;
    }

    public void setDonggia(long donggia) {
        Donggia = donggia;
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

    public String getNguoitao() {
        return Nguoitao;
    }

    public void setNguoitao(String nguoitao) {
        Nguoitao = nguoitao;
    }

    public String getMotaThem() {
        return MotaThem;
    }

    public void setMotaThem(String motaThem) {
        MotaThem = motaThem;
    }

    public void setID(int id) {
    }
}
