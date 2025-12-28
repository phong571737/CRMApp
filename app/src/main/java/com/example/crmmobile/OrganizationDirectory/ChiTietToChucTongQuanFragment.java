package com.example.crmmobile.OrganizationDirectory;

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

import com.example.crmmobile.R;

public class ChiTietToChucTongQuanFragment extends Fragment {

    // === KHAI BÁO CÁC BIẾN VIEW ===
    private RelativeLayout sectionHoatDong;
    private LinearLayout contentHoatDong;
    private ImageView toggleHoatDong;

    private static final String ARG_COMPANY_ID = "company_id";
    private int companyId;

    public static ChiTietToChucTongQuanFragment newInstance(int companyId) {
        ChiTietToChucTongQuanFragment fragment = new ChiTietToChucTongQuanFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_COMPANY_ID, companyId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            companyId = getArguments().getInt(ARG_COMPANY_ID);
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_chitiettochuc_tongquan, container, false);

        // === ÁNH XẠ CÁC VIEW  ===
        // Mục Hoạt động
        sectionHoatDong = view.findViewById(R.id.section_hoat_dong);
        contentHoatDong = view.findViewById(R.id.content_hoat_dong);
        toggleHoatDong = view.findViewById(R.id.toggle_hoat_dong);
        setupToggle(sectionHoatDong, contentHoatDong, toggleHoatDong);

        return view;
    }

    // === HÀM HELPER ĐỂ XỬ LÝ ẨN/HIỆN ===
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
