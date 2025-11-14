package com.example.crmmobile.feature.salesorder.ui.create;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crmmobile.R;

public class ThongTinChungFragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_tin_chung, container, false);

        setRow(view, R.id.row_ma_don_hang, "Mã đơn hàng", "SO00109");
        setRow(view, R.id.row_tieu_de, "Tiêu đề", "Đơn hàng công ty TNHH Công nghệ CloudGO");
        setRow(view, R.id.row_cong_ty, "Công ty", "Công ty TNHH Công nghệ CloudGO");
        setRow(view, R.id.row_nguoi_lien_he, "Người liên hệ", "Trần Văn Khải");
        setRow(view, R.id.row_di_dong, "Di động", "0350336148");
        setRow(view, R.id.row_ngay_dat_hang, "Ngày đặt hàng", "30/07/2024");

        View rowTinhTrang = view.findViewById(R.id.row_tinh_trang);
        ((TextView) rowTinhTrang.findViewById(R.id.tvLabel)).setText("Tình trạng");
        ((TextView) rowTinhTrang.findViewById(R.id.tvValue)).setText("Đã giao hàng");

        return view;
    }

    private void setRow(View parent, int rowId, String label, String value) {
        View row = parent.findViewById(rowId);
        ((TextView) row.findViewById(R.id.tvLabel)).setText(label);
        ((TextView) row.findViewById(R.id.tvValue)).setText(value);
    }
}

