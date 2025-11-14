package com.example.crmmobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TaoBaoGiaThongTinChungFragment extends Fragment {

    // Khai báo các View và Button cho 5 sections
    private View layoutBaoGiaContent, layoutLienQuanContent, layoutDieuKhoanContent, layoutMoTaContent, layoutQuanLyContent;
    private ImageView btnToggleBaoGia, btnToggleLienQuan, btnToggleDieuKhoan, btnToggleMoTa, btnToggleQuanLy;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_taobaogia_thongtinchung, container, false);

        // Ánh xạ các View
        layoutBaoGiaContent = view.findViewById(R.id.layoutBaoGiaContent);
        layoutLienQuanContent = view.findViewById(R.id.layoutLienQuanContent);
        layoutDieuKhoanContent = view.findViewById(R.id.layoutDieuKhoanDieuKienContent);
        layoutMoTaContent = view.findViewById(R.id.layoutMoTaContent);
        layoutQuanLyContent = view.findViewById(R.id.layoutQuanLyContent);

        btnToggleBaoGia = view.findViewById(R.id.btnToggleBaoGia);
        btnToggleLienQuan = view.findViewById(R.id.btnToggleLienQuan);
        btnToggleDieuKhoan = view.findViewById(R.id.btnToggleDieuKhoanDieuKien);
        btnToggleMoTa = view.findViewById(R.id.btnToggleMoTa);
        btnToggleQuanLy = view.findViewById(R.id.btnToggleQuanLy);

        // Setup logic ẩn/hiện cho cả 5
        setupToggle(btnToggleBaoGia, layoutBaoGiaContent);
        setupToggle(btnToggleLienQuan, layoutLienQuanContent);
        setupToggle(btnToggleDieuKhoan, layoutDieuKhoanContent);
        setupToggle(btnToggleMoTa, layoutMoTaContent);
        setupToggle(btnToggleQuanLy, layoutQuanLyContent);

        return view;
    }

    // Hàm chung để xử lý ẩn/hiện
    private void setupToggle(ImageView button, View contentLayout) {
        button.setOnClickListener(v -> {
            if (contentLayout.getVisibility() == View.VISIBLE) {
                contentLayout.setVisibility(View.GONE);
                button.setImageResource(R.drawable.ic_arrow_down);
            } else {
                contentLayout.setVisibility(View.VISIBLE);
                button.setImageResource(R.drawable.ic_arrow_up);
            }
        });
    }
}
