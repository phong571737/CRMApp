package com.example.crmmobile;


import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class ThongTinNguoiLienHeFragment extends Fragment {

    private AutoCompleteTextView actDanhXung, actGioiTinh, actCongTy;
    private EditText edtHoVaTenDem, edtTen, edtDiDong, edtEmail, edtNgaySinh;

    // Lưu model tạm để populate khi view đã sẵn sàng
    private CaNhan caNhan;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_thong_tin_nguoi_lien_he, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        TextView tvHeader = view.findViewById(R.id.thongtinnguoilienhe);
        LinearLayout layoutDetail = view.findViewById(R.id.layoutThongTinNguoiLienHeChiTiet);

        actDanhXung = view.findViewById(R.id.actdanhxung);
        edtHoVaTenDem = view.findViewById(R.id.edthovatendem);
        edtTen = view.findViewById(R.id.edtten);

        actCongTy = view.findViewById(R.id.actCongTy);
        actGioiTinh = view.findViewById(R.id.actgioitinh);

        edtDiDong = view.findViewById(R.id.edtdidong);
        edtEmail = view.findViewById(R.id.edtemail);
        edtNgaySinh = view.findViewById(R.id.edtngaysinh);

        ArrayAdapter<String> adapterCongTy = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                new String[]{"Cty TNHH ABC", "Cty TNHH Hỷ Lâm Môn"}
        );
        actCongTy.setAdapter(adapterCongTy);


        ArrayAdapter<String> adapterDanhXung = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                new String[]{"Anh", "Chị"}
        );
        actDanhXung.setAdapter(adapterDanhXung);

        ArrayAdapter<String> adapterGioiTinh = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                new String[]{"Nam", "Nữ"}
        );
        actGioiTinh.setAdapter(adapterGioiTinh);


        tvHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0);

        tvHeader.setOnClickListener(v -> {
            if (layoutDetail.getVisibility() == View.VISIBLE) {
                layoutDetail.setVisibility(View.GONE);
                tvHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_up, 0);
            } else {
                layoutDetail.setVisibility(View.VISIBLE);
                tvHeader.setCompoundDrawablesWithIntrinsicBounds(0, 0, R.drawable.ic_arrow_down, 0);
            }
        });

        // Nếu đã có CaNhan được set trước đó -> populate
        if (caNhan != null) {
            populateFromCaNhan();
        }
    }

    private void populateFromCaNhan() {
        if (caNhan == null) return;
        actDanhXung.setText(nullToEmpty(caNhan.getDanhXung()));
        edtHoVaTenDem.setText(nullToEmpty(caNhan.getHoVaTen()));
        edtTen.setText(nullToEmpty(caNhan.getTen()));
        actCongTy.setText(nullToEmpty(caNhan.getCongTy()));
        actGioiTinh.setText(nullToEmpty(caNhan.getGioiTinh()));
        edtDiDong.setText(nullToEmpty(caNhan.getDiDong()));
        edtEmail.setText(nullToEmpty(caNhan.getEmail()));
        edtNgaySinh.setText(nullToEmpty(caNhan.getNgaySinh()));
    }

    private String nullToEmpty(String s) {
        return s == null ? "" : s;
    }

    // Cho Activity truyền CaNhan vào để chỉnh sửa
    public void setCaNhan(CaNhan cn) {
        this.caNhan = cn;
        // Nếu view đã sẵn sàng, populate luôn
        if (getView() != null) {
            populateFromCaNhan();
        }
    }

    // GETTERS hiện tại (không đổi)
    public String getDanhXung() { return actDanhXung.getText().toString(); }
    public String getHoVaTenDem() { return edtHoVaTenDem.getText().toString(); }
    public String getTen() { return edtTen.getText().toString(); }
    public String getCongTy() { return actCongTy.getText().toString(); }
    public String getGioiTinh() { return actGioiTinh.getText().toString(); }
    public String getDiDong() { return edtDiDong.getText().toString(); }
    public String getEmail() { return edtEmail.getText().toString(); }
    public String getNgaySinh() { return edtNgaySinh.getText().toString(); }
}
