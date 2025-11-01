package com.example.crmmobile;

public class TaiLieu {
    private String tenFile;
    private String ngay;
    private String gio;
    private String nguoiTao;
    private int iconRes;

    //Constructor
    public TaiLieu(String tenFile, String ngay, String gio, String nguoiTao) {
        this.tenFile = tenFile;
        this.ngay = ngay;
        this.gio = gio;
        this.nguoiTao = nguoiTao;

        //Tự động chọn icon theo phần mở rộng
        this.iconRes = getIconForFile(tenFile);
    }

    //Lấy icon theo loại file
    private int getIconForFile(String fileName) {
        String lower = fileName.toLowerCase();

        if (lower.endsWith(".pdf")) {
            return R.drawable.ic_pdf;     // icon PDF
        } else if (lower.endsWith(".xlsx") || lower.endsWith(".xls")) {
            return R.drawable.ic_excel;   // icon Excel
        } else if (lower.endsWith(".docx") || lower.endsWith(".doc")) {
            return R.drawable.addfile;    // icon Word
        } else {
            return R.drawable.ic_files;    // icon mặc định
        }
    }

    //Getter
    public String getTenFile() { return tenFile; }
    public String getNgay() { return ngay; }
    public String getGio() { return gio; }
    public String getNguoiTao() { return nguoiTao; }
    public int getIconRes() { return iconRes; }
}
