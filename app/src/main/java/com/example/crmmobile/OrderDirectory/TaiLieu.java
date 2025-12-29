package com.example.crmmobile.OrderDirectory;

import com.example.crmmobile.R;

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

        this.iconRes = getIconForFile(tenFile);
    }

    private int getIconForFile(String fileName) {
        String lower = fileName.toLowerCase();

        if (lower.endsWith(".pdf")) {
            return R.drawable.ic_pdf;     // icon PDF
        } else if (lower.endsWith(".xlsx") || lower.endsWith(".xls")) {
            return R.drawable.ic_excel;   // icon Excel
        } else if (lower.endsWith(".docx") || lower.endsWith(".doc")) {
            return R.drawable.ic_word;    // icon Word
        } else {
            return R.drawable.ic_files;    // icon mặc định
        }
    }

    public String getTenFile() { return tenFile; }
    public String getNgay() { return ngay; }
    public String getGio() { return gio; }
    public String getNguoiTao() { return nguoiTao; }
    public int getIconRes() { return iconRes; }
}
