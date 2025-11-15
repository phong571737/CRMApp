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

public class ThongTinKhacFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thong_tin_khac, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // --- Khối 1: Thông tin địa chỉ ---
        TextView thongTinDiaChi = view.findViewById(R.id.thongtindiachi);
        LinearLayout layoutDiaChi = view.findViewById(R.id.layoutThongTinDiaChiChiTiet);
        setupToggle(thongTinDiaChi, layoutDiaChi);

        // --- Khối 2: Thông tin mô tả ---
        TextView thongTinMoTa = view.findViewById(R.id.thongtinmota);
        LinearLayout layoutMoTa = view.findViewById(R.id.layoutThongTinMoTaChiTiet);
        setupToggle(thongTinMoTa, layoutMoTa);

        // --- Khối 3: Thông tin quản lý ---
        TextView thongTinQuanLy = view.findViewById(R.id.thongtinquanly);
        LinearLayout layoutQuanLy = view.findViewById(R.id.layoutThongTinQuanLyChiTiet);
        setupToggle(thongTinQuanLy, layoutQuanLy);
    }

    /**
     * Hàm gắn sự kiện mở rộng / thu gọn cho 1 tiêu đề và layout chi tiết.
     */
    private void setupToggle(TextView titleView, LinearLayout detailLayout) {
        // Mặc định hiển thị chi tiết + icon mũi tên lên
        detailLayout.setVisibility(View.VISIBLE);
        titleView.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_drop_down, 0);

        titleView.setOnClickListener(v -> {
            boolean isVisible = detailLayout.getVisibility() == View.VISIBLE;

            // Ẩn hoặc hiện phần chi tiết
            detailLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);

            // Cập nhật icon mũi tên
            titleView.setCompoundDrawablesWithIntrinsicBounds(
                    0, 0,
                    isVisible ? R.drawable.ic_arrow_up : R.drawable.ic_arrow_drop_down,
                    0
            );
        });
    }
}
