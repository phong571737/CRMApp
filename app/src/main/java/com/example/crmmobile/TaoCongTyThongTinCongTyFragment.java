package com.example.crmmobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class TaoCongTyThongTinCongTyFragment extends Fragment {

    private View groupThongTinCongTy;
    private ImageView btnToggleThongTinCongTy;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_taocongty_thongtincongty, container, false);

        // Ánh xạ view
        groupThongTinCongTy = view.findViewById(R.id.groupThongTinCongTy);
        btnToggleThongTinCongTy = view.findViewById(R.id.btnToggleThongTinCongTy);

        // Xử lý ẩn/hiện khi bấm icon
        btnToggleThongTinCongTy.setOnClickListener(v -> {
            if (groupThongTinCongTy.getVisibility() == View.VISIBLE) {
                groupThongTinCongTy.setVisibility(View.GONE);
                btnToggleThongTinCongTy.setImageResource(R.drawable.ic_arrow_down);
            } else {
                groupThongTinCongTy.setVisibility(View.VISIBLE);
                btnToggleThongTinCongTy.setImageResource(R.drawable.ic_arrow_up);
            }
        });

        return view;
    }
}