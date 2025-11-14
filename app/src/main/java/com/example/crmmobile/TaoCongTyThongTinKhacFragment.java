package com.example.crmmobile;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import java.util.Calendar;

public class TaoCongTyThongTinKhacFragment extends Fragment {

    // Đã loại bỏ layoutHoaDonContent và btnToggleHoaDon
    private View layoutDiaChiContent, layoutMuaHangContent, layoutQuanLyContent;
    private ImageView btnToggleDiaChi, btnToggleMuaHang, btnToggleQuanLy;

    private TextInputEditText edtNgayMuaHangDauTien, edtNgayMuaHangGanNhat;
    private TextInputLayout layoutNgayDauTien, layoutNgayGanNhat;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_taocongty_thongtinkhac, container, false);

        // Ánh xạ layout toggle (còn 3)
        layoutDiaChiContent = view.findViewById(R.id.layoutDiaChiContent);
        layoutMuaHangContent = view.findViewById(R.id.layoutMuaHangContent);
        layoutQuanLyContent = view.findViewById(R.id.layoutQuanLyContent);

        btnToggleDiaChi = view.findViewById(R.id.btnToggleDiaChi);
        btnToggleMuaHang = view.findViewById(R.id.btnToggleMuaHang);
        btnToggleQuanLy = view.findViewById(R.id.btnToggleQuanLy);

        // Setup toggle (còn 3)
        setupToggle(btnToggleDiaChi, layoutDiaChiContent);
        setupToggle(btnToggleMuaHang, layoutMuaHangContent);
        setupToggle(btnToggleQuanLy, layoutQuanLyContent);

        // Ánh xạ các trường ngày (không đổi)
        edtNgayMuaHangDauTien = view.findViewById(R.id.edtNgayMuaHangDauTien);
        edtNgayMuaHangGanNhat = view.findViewById(R.id.edtNgayMuaHangGanNhat);
        layoutNgayDauTien = view.findViewById(R.id.textInputLayoutNgayDauTien);
        layoutNgayGanNhat = view.findViewById(R.id.textInputLayoutNgayGanNhat);

        // Xử lý sự kiện chọn ngày bằng endIcon (không đổi)
        if (layoutNgayDauTien != null) {
            layoutNgayDauTien.setEndIconOnClickListener(v -> showDatePicker(edtNgayMuaHangDauTien));
        }
        if (layoutNgayGanNhat != null) {
            layoutNgayGanNhat.setEndIconOnClickListener(v -> showDatePicker(edtNgayMuaHangGanNhat));
        }

        return view;
    }

    // Hàm setupToggle (không đổi)
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

    // Hàm showDatePicker (không đổi)
    private void showDatePicker(TextInputEditText target) {
        final Calendar calendar = Calendar.getInstance();

        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        DatePickerDialog dialog = new DatePickerDialog(
                requireContext(),
                (dateView, y, m, d) -> {
                    String formatted = String.format("%02d/%02d/%04d", d, m + 1, y);
                    target.setText(formatted);
                },
                year, month, day
        );
        dialog.show();
    }
}