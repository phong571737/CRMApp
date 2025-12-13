package com.example.crmmobile.IndividualDirectory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crmmobile.R;

public class HoatDongFragment extends Fragment {
    private CaNhan caNhan;

    private EditText edtTieuDe, edtMoTa;
    private AutoCompleteTextView actNguoiDung,actCongTy, actCaNhan, actCoHoi;

    public HoatDongFragment() {
        // Constructor rỗng là bắt buộc
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_cuocgoi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        edtTieuDe = view.findViewById(R.id.edttieude);
        edtMoTa = view.findViewById(R.id.edtmota);
        actNguoiDung = view.findViewById(R.id.actnguoidung);

        actCongTy = view.findViewById(R.id.actcongty);
        actCaNhan = view.findViewById(R.id.actcanhan);
        actCoHoi = view.findViewById(R.id.actcohoi);

        ArrayAdapter<String> adapterCongTy = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                new String[]{"Cty TNHH ABC", "Cty TNHH Hỷ Lâm Môn"}
        );
        actCongTy.setAdapter(adapterCongTy);

        ArrayAdapter<String> adapterNguoiDung = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                new String[]{"Phan Vi", "Tường Vi", "Phan Thị Tường Vi"}
        );
        actNguoiDung.setAdapter(adapterNguoiDung);

        ArrayAdapter<String> adapterGioiTinh = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                new String[]{"Cơ hội 100 tỷ", "Cơ hội 50 triệu"}
        );
        actCoHoi.setAdapter(adapterGioiTinh);

        if (caNhan != null) {
            actCaNhan.setText(caNhan.getHoVaTen() + caNhan.getTen() , false); // <-- Tự động điền tên
        }

    }

    public void setCaNhan(CaNhan cn) {
        this.caNhan = cn;
    }

    public String getTieuDe() { return edtTieuDe.getText().toString(); }
    public String getMoTa() { return edtMoTa.getText().toString(); }
    public String getNguoiDung() { return actNguoiDung.getText().toString(); }
    public String getCongTy() { return actCongTy.getText().toString(); }
    public String getCaNhan() { return actCaNhan.getText().toString(); }
    public String getCoHoi() { return actCoHoi.getText().toString(); }

}