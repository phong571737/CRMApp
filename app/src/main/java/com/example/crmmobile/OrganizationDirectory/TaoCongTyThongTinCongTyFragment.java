package com.example.crmmobile.OrganizationDirectory;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crmmobile.R;
import com.google.android.material.textfield.TextInputEditText;

public class TaoCongTyThongTinCongTyFragment extends Fragment {
    private View groupThongTinCongTy;
    private ImageView btnToggleThongTinCongTy;
    private TextInputEditText edtTenCongTy, edtWebsite, edtDienThoai, edtEmail;
    private AutoCompleteTextView actTrangThai, actNganhNghe;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_taocongty_thongtincongty, container, false);

        // 1. Ánh xạ View
        groupThongTinCongTy = view.findViewById(R.id.groupThongTinCongTy);
        btnToggleThongTinCongTy = view.findViewById(R.id.btnToggleThongTinCongTy);
        edtTenCongTy = view.findViewById(R.id.edtTenCongTy);
        edtWebsite = view.findViewById(R.id.edtWebsite);
        edtDienThoai = view.findViewById(R.id.edtDienThoai);
        edtEmail = view.findViewById(R.id.edtEmail);
        actTrangThai = view.findViewById(R.id.actTrangThai);
        actNganhNghe = view.findViewById(R.id.actNganhNghe);

        // 2. Cấu hình Dropdown (Trạng thái, Ngành nghề)
        String[] listTrangThai = {"Không quan tâm", "Có cơ hội", "Cần quan tâm"};
        ArrayAdapter<String> adapterTrangThai = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, listTrangThai);
        actTrangThai.setAdapter(adapterTrangThai);

        String[] listNganhNghe = {"Công nghệ", "Mỹ phẩm", "Giáo dục", "Mua sắm", "Bất động sản", "Khác"};
        ArrayAdapter<String> adapterNganhNghe = new ArrayAdapter<>(requireContext(), android.R.layout.simple_dropdown_item_1line, listNganhNghe);
        actNganhNghe.setAdapter(adapterNganhNghe);

        // 3. Logic Toggle
        btnToggleThongTinCongTy.setOnClickListener(v -> {
            if (groupThongTinCongTy.getVisibility() == View.VISIBLE) {
                groupThongTinCongTy.setVisibility(View.GONE);
                btnToggleThongTinCongTy.setImageResource(R.drawable.ic_arrow_down);
            } else {
                groupThongTinCongTy.setVisibility(View.VISIBLE);
                btnToggleThongTinCongTy.setImageResource(R.drawable.ic_arrow_up);
            }
        });

        // === 4. QUAN TRỌNG: NHẬN DỮ LIỆU TỪ BUNDLE VÀ HIỂN THỊ ===
        if (getArguments() != null && getArguments().containsKey("COMPANY_DATA")) {
            ToChuc data = (ToChuc) getArguments().getSerializable("COMPANY_DATA");
            Log.d("DEBUG_APP", "Fragment đã nhận được tên: " + data.getCompanyName());
            setFormData(data);
        } else {
            Log.d("DEBUG_APP", "Fragment KHÔNG nhận được dữ liệu!");
        }

        return view;
    }

    // Hàm lấy dữ liệu (Khi bấm Lưu)
    public void getDataFromForm(ToChuc toChuc) {
        if (getView() == null) return;
        if(edtTenCongTy != null) toChuc.setCompanyName(edtTenCongTy.getText().toString());
        if(edtWebsite != null) toChuc.setWebsite(edtWebsite.getText().toString());
        if(edtDienThoai != null) toChuc.setPhone(edtDienThoai.getText().toString());
        if(edtEmail != null) toChuc.setEmail(edtEmail.getText().toString());
        if(actNganhNghe != null) toChuc.setIndustry(actNganhNghe.getText().toString());

        String statusText = actTrangThai.getText().toString();
        if (statusText.contains("Không quan tâm")) toChuc.setTrangThai(ToChuc.TrangThai.KHONG_QUAN_TAM);
        else if (statusText.contains("Có cơ hội")) toChuc.setTrangThai(ToChuc.TrangThai.CO_CO_HOI);
        else if (statusText.contains("Cần quan tâm")) toChuc.setTrangThai(ToChuc.TrangThai.CAN_QUAN_TAM);
        else toChuc.setTrangThai(ToChuc.TrangThai.NONE);
    }

    // === HÀM ĐIỀN DỮ LIỆU VÀO VIEW  ===
    public void setFormData(ToChuc toChuc) {
        if (toChuc == null) return;

        // Chỉ cần kiểm tra các biến view đã được ánh xạ chưa
        if (edtTenCongTy != null) {
            edtTenCongTy.setText(toChuc.getCompanyName());
        }

        if (edtWebsite != null) edtWebsite.setText(toChuc.getWebsite());
        if (edtDienThoai != null) edtDienThoai.setText(toChuc.getPhone());
        if (edtEmail != null) edtEmail.setText(toChuc.getEmail());
        if (actNganhNghe != null) actNganhNghe.setText(toChuc.getIndustry(), false);

        if (toChuc.getTrangThai() != null && actTrangThai != null) {
            String label = "";
            switch (toChuc.getTrangThai()) {
                case KHONG_QUAN_TAM: label = "Không quan tâm"; break;
                case CO_CO_HOI: label = "Có cơ hội"; break;
                case CAN_QUAN_TAM: label = "Cần quan tâm"; break;
            }
            if (!label.isEmpty()) actTrangThai.setText(label, false);
        }
    }
}