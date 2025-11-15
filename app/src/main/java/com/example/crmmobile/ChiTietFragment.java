package com.example.myapplication;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ChiTietFragment extends Fragment {

    public ChiTietFragment() {
        // Bắt buộc constructor rỗng
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Gắn layout XML của tab Chi tiết
        return inflater.inflate(R.layout.fragment_tab_chitiet, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // === THÔNG TIN CHUNG ===
        setupToggle(
                view.findViewById(R.id.thongtinchung),
                view.findViewById(R.id.layoutThongTinChung)
        );

        // === THÔNG TIN ĐỊA CHỈ ===
        setupToggle(
                view.findViewById(R.id.thongtindiachi),
                view.findViewById(R.id.layoutThongTinDiaChi)
        );

        // === THÔNG TIN MÔ TẢ ===
        setupToggle(
                view.findViewById(R.id.thongtinmota),
                view.findViewById(R.id.layoutThongTinMoTa)
        );

//        // === THÔNG TIN MUA HÀNG ===
//        setupToggle(
//                view.findViewById(R.id.thongtinmuahang),
//                view.findViewById(R.id.layoutThongTinMuaHang)
//        );
//
//        // === THÔNG TIN QUẢN LÝ ===
//        setupToggle(
//                view.findViewById(R.id.thongtinquanly),
//                view.findViewById(R.id.layoutThongTinQuanLy)
//        );
//
//        // === THÔNG TIN HỆ THỐNG ===
//        setupToggle(
//                view.findViewById(R.id.thongtinhethong),
//                view.findViewById(R.id.layoutThongTinHeThong)
//        );
    }

    /**
     * Gắn sự kiện click để mở rộng / thu gọn layout con.
     */
    private void setupToggle(TextView titleView, LinearLayout detailLayout) {
        if (titleView == null || detailLayout == null) return;

        // Ban đầu hiển thị chi tiết và mũi tên xuống
        detailLayout.setVisibility(View.VISIBLE);
        titleView.setCompoundDrawablesRelativeWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down, 0);

        // Khi click vào tiêu đề
        titleView.setOnClickListener(v -> {
            boolean isVisible = detailLayout.getVisibility() == View.VISIBLE;

            // Ẩn/hiện layout con
            detailLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);

            // Đổi mũi tên lên/xuống
            titleView.setCompoundDrawablesRelativeWithIntrinsicBounds(
                    0, 0,
                    isVisible ? R.drawable.ic_arrow_up : R.drawable.ic_arrow_drop_down,
                    0
            );
        });
    }
}
