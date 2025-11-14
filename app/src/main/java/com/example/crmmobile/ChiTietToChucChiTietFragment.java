package com.example.crmmobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ChiTietToChucChiTietFragment extends Fragment {

    // Khai báo các View cho các mục
    private RelativeLayout sectionThongTinCongTy, sectionDiaChi, sectionMoTa, sectionMuaHang, sectionQuanLy, sectionHeThong;
    private LinearLayout contentThongTinCongTy, contentDiaChi, contentMoTa, contentMuaHang, contentQuanLy, contentHeThong;
    private ImageView toggleThongTinCongTy, toggleDiaChi, toggleMoTa, toggleMuaHang, toggleQuanLy, toggleHeThong;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chitiettochuc_chitiet, container, false);

        // Ánh xạ các mục
        sectionThongTinCongTy = view.findViewById(R.id.section_thong_tin_cong_ty);
        contentThongTinCongTy = view.findViewById(R.id.content_thong_tin_cong_ty);
        toggleThongTinCongTy = view.findViewById(R.id.toggle_thong_tin_cong_ty);

        sectionDiaChi = view.findViewById(R.id.section_dia_chi);
        contentDiaChi = view.findViewById(R.id.content_dia_chi);
        toggleDiaChi = view.findViewById(R.id.toggle_dia_chi);

        sectionMoTa = view.findViewById(R.id.section_mo_ta);
        contentMoTa = view.findViewById(R.id.content_mo_ta);
        toggleMoTa = view.findViewById(R.id.toggle_mo_ta);

        sectionMuaHang = view.findViewById(R.id.section_mua_hang);
        contentMuaHang = view.findViewById(R.id.content_mua_hang);
        toggleMuaHang = view.findViewById(R.id.toggle_mua_hang);

        sectionQuanLy = view.findViewById(R.id.section_quan_ly);
        contentQuanLy = view.findViewById(R.id.content_quan_ly);
        toggleQuanLy = view.findViewById(R.id.toggle_quan_ly);

        sectionHeThong = view.findViewById(R.id.section_he_thong);
        contentHeThong = view.findViewById(R.id.content_he_thong);
        toggleHeThong = view.findViewById(R.id.toggle_he_thong);

        // Gán sự kiện click cho các mục
        setupToggle(sectionThongTinCongTy, contentThongTinCongTy, toggleThongTinCongTy);
        setupToggle(sectionDiaChi, contentDiaChi, toggleDiaChi);
        setupToggle(sectionMoTa, contentMoTa, toggleMoTa);
        setupToggle(sectionMuaHang, contentMuaHang, toggleMuaHang);
        setupToggle(sectionQuanLy, contentQuanLy, toggleQuanLy);
        setupToggle(sectionHeThong, contentHeThong, toggleHeThong);

        return view;
    }

    // Hàm chung để xử lý ẩn/hiện
    private void setupToggle(View header, View content, ImageView arrow) {
        // Mặc định, tất cả đều mở
        content.setVisibility(View.VISIBLE);
        arrow.setImageResource(R.drawable.ic_arrow_up);

        header.setOnClickListener(v -> {
            if (content.getVisibility() == View.VISIBLE) {
                // Đóng
                content.setVisibility(View.GONE);
                arrow.setImageResource(R.drawable.ic_arrow_down);
            } else {
                // Mở
                content.setVisibility(View.VISIBLE);
                arrow.setImageResource(R.drawable.ic_arrow_up);
            }
        });
    }
}
