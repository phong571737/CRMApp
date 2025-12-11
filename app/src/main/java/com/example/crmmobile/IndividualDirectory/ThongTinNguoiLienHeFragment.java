package com.example.crmmobile.IndividualDirectory;
import com.example.crmmobile.R;
import android.app.DatePickerDialog; // Import thêm
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker; // Import thêm

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat; // Import format ngày
import java.util.Calendar;
import java.util.Locale;

public class ThongTinNguoiLienHeFragment extends Fragment {

    private AutoCompleteTextView actDanhXung, actCongTy, actGioiTinh;
    private TextInputEditText edtHoVaTenDem, edtTen, edtDiDong, edtEmail, edtNgaySinh;
    private CaNhan pendingData;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_thong_tin_nguoi_lien_he, container, false);

        // 1. Ánh xạ View
        actDanhXung = view.findViewById(R.id.actdanhxung);
        edtHoVaTenDem = view.findViewById(R.id.edthovatendem);
        edtTen = view.findViewById(R.id.edtten);
        actCongTy = view.findViewById(R.id.actCongTy);
        actGioiTinh = view.findViewById(R.id.actgioitinh);
        edtDiDong = view.findViewById(R.id.edtdidong);
        edtEmail = view.findViewById(R.id.edtemail);
        edtNgaySinh = view.findViewById(R.id.edtngaysinh);

        // 2. Setup Dropdown (Giới tính, Công ty...)
        setupDropdowns();

        // 3. Setup Chọn ngày sinh (MỚI THÊM)
        setupDatePicker();

        // 4. Fill dữ liệu nếu có (Edit mode)
        if (pendingData != null) {
            fillData(pendingData);
        }

        return view;
    }

    // --- HÀM XỬ LÝ LỊCH (DATE PICKER) ---
    private void setupDatePicker() {
        // Vô hiệu hóa nhập text từ bàn phím, biến EditText thành như một cái nút bấm
        edtNgaySinh.setFocusable(false);
        edtNgaySinh.setClickable(true);
        edtNgaySinh.setCursorVisible(false); // Ẩn con trỏ nhấp nháy

        // Sự kiện khi click vào ô ngày sinh
        edtNgaySinh.setOnClickListener(v -> showDatePickerDialog());
    }

    private void showDatePickerDialog() {
        if (getContext() == null) return;

        final Calendar calendar = Calendar.getInstance();

        // Logic: Nếu ô đang có dữ liệu (ví dụ 15/05/1990), set lịch hiển thị đúng ngày đó
        // Nếu trống, hiển thị ngày hiện tại
        String currentText = edtNgaySinh.getText().toString();
        if (!currentText.isEmpty()) {
            try {
                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                calendar.setTime(sdf.parse(currentText));
            } catch (Exception e) {
                // Nếu lỗi format thì mặc định lấy ngày hiện tại, không làm gì cả
            }
        }

        int day = calendar.get(Calendar.DAY_OF_MONTH);
        int month = calendar.get(Calendar.MONTH);
        int year = calendar.get(Calendar.YEAR);

        DatePickerDialog datePickerDialog = new DatePickerDialog(
                getContext(),
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    // Xử lý khi người dùng chọn xong
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(selectedYear, selectedMonth, selectedDay);

                    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                    edtNgaySinh.setText(sdf.format(selectedDate.getTime()));
                },
                year, month, day
        );

        datePickerDialog.show();
    }

    // --- CÁC HÀM CŨ ---

    private void setupDropdowns() {
        if (getContext() == null) return;

        String[] danhXungArr = {"Anh", "Chị", "Ông", "Bà", "Em"};
        ArrayAdapter<String> adapterDanhXung = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, danhXungArr);
        actDanhXung.setAdapter(adapterDanhXung);

        String[] gioiTinhArr = {"Nam", "Nữ", "Khác"};
        ArrayAdapter<String> adapterGioiTinh = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, gioiTinhArr);
        actGioiTinh.setAdapter(adapterGioiTinh);

        String[] congTyArr = {"Công ty A", "Công ty B", "Công ty C", "Freelancer"};
        ArrayAdapter<String> adapterCongTy = new ArrayAdapter<>(getContext(), android.R.layout.simple_dropdown_item_1line, congTyArr);
        actCongTy.setAdapter(adapterCongTy);
    }

    public void setCaNhan(CaNhan cn) {
        this.pendingData = cn;
        if (edtHoVaTenDem != null) {
            fillData(cn);
        }
    }

    private void fillData(CaNhan cn) {
        if(actDanhXung != null) actDanhXung.setText(cn.getDanhXung(), false);
        if(actCongTy != null) actCongTy.setText(cn.getCongTy(), false);
        if(actGioiTinh != null) actGioiTinh.setText(cn.getGioiTinh(), false);

        if(edtHoVaTenDem != null) edtHoVaTenDem.setText(cn.getHoVaTen());
        if(edtTen != null) edtTen.setText(cn.getTen());
        if(edtDiDong != null) edtDiDong.setText(cn.getDiDong());
        if(edtEmail != null) edtEmail.setText(cn.getEmail());
        if(edtNgaySinh != null) edtNgaySinh.setText(cn.getNgaySinh());
    }

    // Safe Getters
    private String getTextSafe(TextInputEditText edt) {
        return edt != null && edt.getText() != null ? edt.getText().toString().trim() : "";
    }
    private String getTextSafe(AutoCompleteTextView act) {
        return act != null && act.getText() != null ? act.getText().toString().trim() : "";
    }

    public String getDanhXung() { return getTextSafe(actDanhXung); }
    public String getHoVaTenDem() { return getTextSafe(edtHoVaTenDem); }
    public String getTen() { return getTextSafe(edtTen); }
    public String getCongTy() { return getTextSafe(actCongTy); }
    public String getGioiTinh() { return getTextSafe(actGioiTinh); }
    public String getDiDong() { return getTextSafe(edtDiDong); }
    public String getEmail() { return getTextSafe(edtEmail); }
    public String getNgaySinh() { return getTextSafe(edtNgaySinh); }
}
