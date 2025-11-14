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

public class ChiTietToChucTongQuanFragment extends Fragment {

    // === KHAI BÁO CÁC BIẾN VIEW ===
    private RelativeLayout sectionHoatDong, sectionBinhLuan;
    private LinearLayout contentHoatDong, contentBinhLuan;
    private ImageView toggleHoatDong, toggleBinhLuan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chitiettochuc_tongquan, container, false);

        // === ÁNH XẠ CÁC VIEW ===
        // Mục 1: Hoạt động
        sectionHoatDong = view.findViewById(R.id.section_hoat_dong);
        contentHoatDong = view.findViewById(R.id.content_hoat_dong);
        toggleHoatDong = view.findViewById(R.id.toggle_hoat_dong);

        // Mục 2: Bình luận
        sectionBinhLuan = view.findViewById(R.id.section_binh_luan);
        contentBinhLuan = view.findViewById(R.id.content_binh_luan);
        toggleBinhLuan = view.findViewById(R.id.toggle_binh_luan);

        // === GÁN SỰ KIỆN CLICK ===
        setupToggle(sectionHoatDong, contentHoatDong, toggleHoatDong);
        setupToggle(sectionBinhLuan, contentBinhLuan, toggleBinhLuan);

        return view;
    }

    // === THÊM HÀM HELPER ĐỂ XỬ LÝ ẨN/HIỆN ===
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
