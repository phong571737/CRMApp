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

public class ThongTinNguoiLienHeFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        // Tạo View từ XML
        return inflater.inflate(R.layout.fragment_thong_tin_nguoi_lien_he, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView thongTinNguoiLienHe = view.findViewById(R.id.thongtinnguoilienhe);
        LinearLayout layoutChiTiet = view.findViewById(R.id.layoutThongTinNguoiLienHeChiTiet);

        // 👇 Đặt icon mặc định khi mới vào (hiện chi tiết → mũi tên lên)
        thongTinNguoiLienHe.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down, 0);

        thongTinNguoiLienHe.setOnClickListener(v -> {
            if (layoutChiTiet.getVisibility() == View.VISIBLE) {
                // Nếu đang mở → ẩn đi + đổi icon xuống
                layoutChiTiet.setVisibility(View.GONE);
                thongTinNguoiLienHe.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up, 0);
            } else {
                // Nếu đang ẩn → mở ra + đổi icon lên
                layoutChiTiet.setVisibility(View.VISIBLE);
                thongTinNguoiLienHe.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down, 0);
            }
        });
    }


}
