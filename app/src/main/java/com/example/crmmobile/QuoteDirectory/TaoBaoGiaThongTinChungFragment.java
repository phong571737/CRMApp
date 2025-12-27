package com.example.crmmobile.QuoteDirectory;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.crmmobile.DataBase.CompanyRepository;
import com.example.crmmobile.OrganizationDirectory.ToChuc;
import com.example.crmmobile.R;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.List;

public class TaoBaoGiaThongTinChungFragment extends Fragment {

    // Khai báo các View và Button cho 5 sections
    private View layoutBaoGiaContent, layoutLienQuanContent, layoutDieuKhoanContent, layoutMoTaContent, layoutQuanLyContent;
    private ImageView btnToggleBaoGia, btnToggleLienQuan, btnToggleDieuKhoan, btnToggleMoTa, btnToggleQuanLy;
    private CreateQuoteViewModel viewModelQuote;
    private TextInputEditText edtTieuDe, edtDieuKhoanDieuKien, edtMoTa;
    private MaterialAutoCompleteTextView actCongTy, actNguoiLienHe, actTinhTrang, actCoHoi, actGiaoCho;
    private CompanyRepository companyRepository;
    public interface StringUpdater{
        void update(String s);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_taobaogia_thongtinchung, container, false);
        viewModelQuote = new ViewModelProvider(requireActivity()).get(CreateQuoteViewModel.class);
        companyRepository = new CompanyRepository(requireContext());
        // Ánh xạ các View
        initViews(view);

        //save data to viewmodel
        bindEditTexttoViewModel(edtTieuDe, s -> viewModelQuote.QuoteName.setValue(s));
        bindEditTexttoViewModel(actCongTy, s -> viewModelQuote.CompanyName.setValue(s));
        bindEditTexttoViewModel(actTinhTrang, s -> viewModelQuote.State.setValue(s));
        bindEditTexttoViewModel(actCoHoi, s -> viewModelQuote.OpportunityName.setValue(s));
        bindEditTexttoViewModel(actNguoiLienHe, s -> viewModelQuote.ContactPerson.setValue(s));

        // Setup logic ẩn/hiện cho cả 5
        setupToggle(btnToggleBaoGia, layoutBaoGiaContent);
        setupToggle(btnToggleLienQuan, layoutLienQuanContent);
        setupToggle(btnToggleDieuKhoan, layoutDieuKhoanContent);
        setupToggle(btnToggleMoTa, layoutMoTaContent);
        setupToggle(btnToggleQuanLy, layoutQuanLyContent);

        return view;
    }

    private void initViews(View view) {
        layoutBaoGiaContent = view.findViewById(R.id.layoutBaoGiaContent);
        layoutLienQuanContent = view.findViewById(R.id.layoutLienQuanContent);
        layoutDieuKhoanContent = view.findViewById(R.id.layoutDieuKhoanDieuKienContent);
        layoutMoTaContent = view.findViewById(R.id.layoutMoTaContent);
        layoutQuanLyContent = view.findViewById(R.id.layoutQuanLyContent);

        btnToggleBaoGia = view.findViewById(R.id.btnToggleBaoGia);
        btnToggleLienQuan = view.findViewById(R.id.btnToggleLienQuan);
        btnToggleDieuKhoan = view.findViewById(R.id.btnToggleDieuKhoanDieuKien);
        btnToggleMoTa = view.findViewById(R.id.btnToggleMoTa);
        btnToggleQuanLy = view.findViewById(R.id.btnToggleQuanLy);

        //bind view
        edtTieuDe = view.findViewById(R.id.edtTieuDe);
        edtDieuKhoanDieuKien = view.findViewById(R.id.edtDieuKhoanDieuKien);
        edtMoTa = view.findViewById(R.id.edtMoTa);
        actCongTy = view.findViewById(R.id.actCongTy);
        getCompany();
        actNguoiLienHe = view.findViewById(R.id.actNguoiLienHe);
        actTinhTrang = view.findViewById(R.id.actTinhTrang);
        actCoHoi = view.findViewById(R.id.actCoHoi);
        actGiaoCho = view.findViewById(R.id.actGiaoCho);
    }
    private void getCompany() {
        List<ToChuc> companyList = companyRepository.getAllCompany();
        List<String> companyName = new ArrayList<>();
        for (ToChuc tc : companyList){
            companyName.add(tc.getCompanyName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_dropdown_item_1line, companyName);
        actCongTy.setAdapter(adapter);
    }

    // Hàm chung để xử lý ẩn/hiện
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

    private void bindEditTexttoViewModel(EditText editText, StringUpdater updater) {
        editText.addTextChangedListener(new TextWatcher() {
                @Override
                public void afterTextChanged(Editable s) {

                }

                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    updater.update(s.toString());
                }
            }
        );
    }
}
