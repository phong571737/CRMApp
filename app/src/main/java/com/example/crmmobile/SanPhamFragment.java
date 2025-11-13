package com.example.crmmobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class SanPhamFragment extends Fragment {

    private View layoutSanPhamContent;
    private ImageView btnToggleSanPham;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_taobaogia_sanpham, container, false);

        // Ánh xạ view
        layoutSanPhamContent = view.findViewById(R.id.layoutSanPhamContent);
        btnToggleSanPham = view.findViewById(R.id.btnToggleSanPham);

        // Xử lý ẩn/hiện khi bấm icon
        setupToggle(btnToggleSanPham, layoutSanPhamContent);

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