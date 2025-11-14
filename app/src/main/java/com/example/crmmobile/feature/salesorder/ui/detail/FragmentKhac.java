package com.example.crmmobile.feature.salesorder.ui.detail;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crmmobile.R;

public class FragmentKhac extends Fragment {

    private View root;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        root = inflater.inflate(R.layout.fragment_khac, container, false);

        // Điều khoản & Điều kiện: tvDieuKhoan + tvXemThemDieuKhoan
        bindExpandableText(
                findTextView(R.id.tvDieuKhoan),
                findTextView(R.id.tvXemThemDieuKhoan),
                3
        );

        // Thông tin mô tả: tvThongTinMoTa + tvXemThemMoTa
        bindExpandableText(
                findTextView(R.id.tvThongTinMoTa),
                findTextView(R.id.tvXemThemMoTa),
                3
        );

        // Nếu muốn set dữ liệu động cho các TextView còn lại, làm tại đây:
        // setTextIfNotNull(R.id.tvSoHopDong, "C016429-5");
        // setTextIfNotNull(R.id.tvNgayChotDuKy, "30/07/2024");
        // setTextIfNotNull(R.id.tvNgayKhachHangKy, "30/07/2024");
        // setTextIfNotNull(R.id.tvTrangThaiHopDong, "Đã xong");
        // setTextIfNotNull(R.id.tvDaNhanLaiHD, "Không");
        // setTextIfNotNull(R.id.tvCoHoi, "Công ty CloudSO - Khẩn");
        // setTextIfNotNull(R.id.tvBaoGia, "Công ty CloudSO - 4th item CloudSale");
        // setTextIfNotNull(R.id.tvTinhTrangHoaDon, "Tạo nháp");
        // setTextIfNotNull(R.id.tvGiaoCho, "Nguyễn Minh Hà");
        // setTextIfNotNull(R.id.tvNgayHen, "30/07/2024 09:56");
        // setTextIfNotNull(R.id.tvNgayTao, "30/07/2024 09:56");
        // setTextIfNotNull(R.id.tvNgaySua, "30/07/2024 09:56");

        return root;
    }

    /** Gắn logic Xem thêm/Thu gọn cho 1 cặp TextView (nội dung dài + nút) */
    private void bindExpandableText(@Nullable TextView contentTv,
                                    @Nullable TextView actionTv,
                                    int collapsedLines) {
        if (contentTv == null || actionTv == null) return;

        // Trạng thái mặc định: thu gọn
        contentTv.setMaxLines(collapsedLines);
        contentTv.setEllipsize(TextUtils.TruncateAt.END);
        actionTv.setText("Xem thêm");

        actionTv.setOnClickListener(v -> {
            boolean collapsed = contentTv.getMaxLines() == collapsedLines;
            if (collapsed) {
                contentTv.setMaxLines(Integer.MAX_VALUE);
                contentTv.setEllipsize(null);
                actionTv.setText("Thu gọn");
            } else {
                contentTv.setMaxLines(collapsedLines);
                contentTv.setEllipsize(TextUtils.TruncateAt.END);
                actionTv.setText("Xem thêm");
            }
        });
    }

    @Nullable
    private TextView findTextView(int id) {
        if (root == null) return null;
        View v = root.findViewById(id);
        return (v instanceof TextView) ? (TextView) v : null;
    }

    private void setTextIfNotNull(int id, @NonNull String text) {
        TextView tv = findTextView(id);
        if (tv != null) tv.setText(text);
    }
}
