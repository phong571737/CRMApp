package com.example.crmmobile.IndividualDirectory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.AdapterView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crmmobile.R;

import com.example.crmmobile.OpportunityDirectory.Opportunity;
import com.example.crmmobile.OpportunityDirectory.OpportunityRepository;
import com.example.crmmobile.DataBase.NhanVienRepository;
import com.example.crmmobile.LeadDirectory.Nhanvien;
import com.example.crmmobile.DataBase.CompanyRepository;
import com.example.crmmobile.OrganizationDirectory.ToChuc;

import java.util.ArrayList;
import java.util.List;

public class HoatDongFragment extends Fragment {

    private CaNhan caNhan;
    private int nguoiLienHeId = 0; //

    private EditText edtTieuDe, edtMoTa;
    private AutoCompleteTextView actNguoiDung, actCongTy, actCaNhan, actCoHoi;

    // Danh sách cơ hội lấy từ DB và ID cơ hội được chọn
    private List<Opportunity> opportunityList = new ArrayList<>();
    private int selectedCoHoiId = 0;

    // Danh sách công ty lấy từ DB và ID công ty được chọn
    private List<ToChuc> companyList = new ArrayList<>();
    private int selectedCongTyId = 0;

    public HoatDongFragment() {}

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

        // Load danh sách công ty từ database
        CompanyRepository companyRepository = new CompanyRepository(requireContext());
        companyList = companyRepository.getAllCompany();
        ArrayAdapter<ToChuc> companyAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                companyList
        );
        actCongTy.setAdapter(companyAdapter);

        // Khi chọn 1 công ty, lưu lại ID tương ứng để dùng khi lưu hoạt động
        actCongTy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (position >= 0 && position < companyList.size()) {
                    selectedCongTyId = companyList.get(position).getId();
                } else {
                    selectedCongTyId = 0;
                }
            }
        });

        // Load danh sách nhân viên từ database
        NhanVienRepository nhanVienRepository = new NhanVienRepository(requireContext());
        nhanVienRepository.AddNhanVien(); // Đảm bảo có dữ liệu
        List<Nhanvien> nhanVienList = nhanVienRepository.getAllNhanVien();
        ArrayAdapter<Nhanvien> nhanVienAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                nhanVienList
        );
        actNguoiDung.setAdapter(nhanVienAdapter);

        // Lấy danh sách cơ hội từ database và hiển thị vào AutoCompleteTextView
        OpportunityRepository opportunityRepository = OpportunityRepository.getInstance(requireContext());
        opportunityList = opportunityRepository.getAll();

        List<String> coHoiTitles = new ArrayList<>();
        for (Opportunity opportunity : opportunityList) {
            coHoiTitles.add(opportunity.getTitle());
        }

        ArrayAdapter<String> coHoiAdapter = new ArrayAdapter<>(
                requireContext(),
                android.R.layout.simple_list_item_1,
                coHoiTitles
        );
        actCoHoi.setAdapter(coHoiAdapter);

        // Khi chọn 1 cơ hội, lưu lại ID tương ứng để dùng khi lưu hoạt động
        actCoHoi.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View v, int position, long id) {
                if (position >= 0 && position < opportunityList.size()) {
                    selectedCoHoiId = opportunityList.get(position).getId();
                } else {
                    selectedCoHoiId = 0;
                }
            }
        });

        if (caNhan != null) {
            actCaNhan.setText(
                    caNhan.getHoVaTen() + " " + caNhan.getTen(),
                    false
            );
        }
    }

    // SET CaNhan + ID
    public void setCaNhan(CaNhan cn) {
        this.caNhan = cn;
        if (cn != null) {
            this.nguoiLienHeId = cn.getId();
        }
    }

    // Getter dữ liệu
    public String getTieuDe() { return edtTieuDe.getText().toString(); }
    public String getMoTa() { return edtMoTa.getText().toString(); }
    public String getNguoiDung() { return actNguoiDung.getText().toString(); }
    public String getCongTy() { return actCongTy.getText().toString(); }
    public String getCoHoi() { return actCoHoi.getText().toString(); }

    // Trả về ID cơ hội được chọn (0 nếu không chọn)
    public int getCoHoiId() { return selectedCoHoiId; }
    // Trả về ID công ty được chọn (0 nếu không chọn)
    public int getCongTyId() { return selectedCongTyId; }

    public int getNguoiLienHeId() {
        return nguoiLienHeId;
    }
}
