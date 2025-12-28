package com.example.crmmobile.QuoteDirectory;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.example.crmmobile.DataBase.CaNhanRepository;
import com.example.crmmobile.DataBase.CompanyRepository;
import com.example.crmmobile.DataBase.NhanVienRepository;
import com.example.crmmobile.IndividualDirectory.CaNhan;
import com.example.crmmobile.LeadDirectory.Nhanvien;
import com.example.crmmobile.LeadDirectory.ViewModelLead;
import com.example.crmmobile.OpportunityDirectory.Opportunity;
import com.example.crmmobile.OpportunityDirectory.OpportunityDAO;
import com.example.crmmobile.OrganizationDirectory.ToChuc;
import com.example.crmmobile.R;
import com.google.android.material.textfield.MaterialAutoCompleteTextView;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class TaoBaoGiaThongTinChungFragment extends Fragment {

    // Khai báo các View và Button cho 5 sections
    private View layoutBaoGiaContent, layoutLienQuanContent, layoutMoTaContent, layoutQuanLyContent,layoutDiachiContent;
    private ImageView btnToggleBaoGia, btnToggleLienQuan, btnToggleMoTa, btnToggleQuanLy, btnToggleDiachi;
    private CreateQuoteViewModel viewModelQuote;
    private TextInputEditText edtTieuDe, edtMoTa, edtDiachi, edtdistrict, edtprovince, edtnation;
    private MaterialAutoCompleteTextView actCongTy, actNguoiLienHe, actTinhTrang, actCoHoi, actGiaoCho;
    private CompanyRepository companyRepository;
    private OpportunityDAO opportunityDAO;
    private CaNhanRepository caNhanRepository;
    private TextInputLayout til_title, til_company, til_state, til_sendto, til_address;
    private TextView tv_title_error, tv_company_error, tv_state_error, tv_address_error;
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
        caNhanRepository = new CaNhanRepository(requireContext());
        // Ánh xạ các View
        initViews(view);

        //save data to viewmodel
        bindEditTexttoViewModel(edtTieuDe, s -> viewModelQuote.QuoteName.setValue(s));
        bindEditTexttoViewModel(actCongTy, s -> viewModelQuote.CompanyName.setValue(s));
        bindEditTexttoViewModel(actTinhTrang, s -> viewModelQuote.State.setValue(s));
        bindEditTexttoViewModel(actCoHoi, s -> viewModelQuote.OpportunityName.setValue(s));
        bindEditTexttoViewModel(actNguoiLienHe, s -> viewModelQuote.ContactPersonName.setValue(s));
        bindEditTexttoViewModel(actGiaoCho, s -> viewModelQuote.SendtoName.setValue(s));
        bindEditTexttoViewModel(edtDiachi, s -> viewModelQuote.address_Ship.setValue(s));
        bindEditTexttoViewModel(edtdistrict, s -> viewModelQuote.district_ship.setValue(s));
        bindEditTexttoViewModel(edtprovince, s -> viewModelQuote.province_ship.setValue(s));
        bindEditTexttoViewModel(edtnation, s -> viewModelQuote.nation_ship.setValue(s));
        bindEditTexttoViewModel(edtMoTa, s -> viewModelQuote.description.setValue(s));

        // Setup logic ẩn/hiện cho cả 5
        setupToggle(btnToggleBaoGia, layoutBaoGiaContent);
        setupToggle(btnToggleLienQuan, layoutLienQuanContent);
        setupToggle(btnToggleMoTa, layoutMoTaContent);
        setupToggle(btnToggleQuanLy, layoutQuanLyContent);
        setupToggle(btnToggleDiachi, layoutDiachiContent);

        return view;
    }

    private void initViews(View view) {
        layoutBaoGiaContent = view.findViewById(R.id.layoutBaoGiaContent);
        layoutLienQuanContent = view.findViewById(R.id.layoutLienQuanContent);
        layoutMoTaContent = view.findViewById(R.id.layoutMoTaContent);
        layoutQuanLyContent = view.findViewById(R.id.layoutQuanLyContent);
        layoutDiachiContent = view.findViewById(R.id.layoutDiachiContent);

        btnToggleBaoGia = view.findViewById(R.id.btnToggleBaoGia);
        btnToggleLienQuan = view.findViewById(R.id.btnToggleLienQuan);
        btnToggleMoTa = view.findViewById(R.id.btnToggleMoTa);
        btnToggleQuanLy = view.findViewById(R.id.btnToggleQuanLy);
        btnToggleDiachi = view.findViewById(R.id.btnToggleDiachi);

        til_title = view.findViewById(R.id.til_title);
        til_company = view.findViewById(R.id.til_company);
        til_state = view.findViewById(R.id.til_state);
        til_sendto = view.findViewById(R.id.til_sendto);
        til_address = view.findViewById(R.id.til_address);

        //error
        tv_title_error = view.findViewById(R.id.tv_title_error);
        tv_company_error = view.findViewById(R.id.tv_company_error);
        tv_state_error = view.findViewById(R.id.tv_state_error);
        tv_address_error = view.findViewById(R.id.tv_address_error);
        DisplayError();

        //bind view
        edtTieuDe = view.findViewById(R.id.edtTieuDe);
        edtMoTa = view.findViewById(R.id.edtMoTa);
        actCongTy = view.findViewById(R.id.actCongTy);
        getCompany();
        actNguoiLienHe = view.findViewById(R.id.actNguoiLienHe);
        getContactPerson();
        actTinhTrang = view.findViewById(R.id.actTinhTrang);
        getTinhTrang();
        actCoHoi = view.findViewById(R.id.actCoHoi);
        getOpportunity();
        actGiaoCho = view.findViewById(R.id.actGiaoCho);
        getGiaoCho();
        edtDiachi = view.findViewById(R.id.edtDiachi);
        edtdistrict = view.findViewById(R.id.edtdistrict);
        edtprovince = view.findViewById(R.id.edtprovince);
        edtnation = view.findViewById(R.id.edtnation);
    }

    private void DisplayError() {
        viewModelQuote.quoteNameError.observe(requireActivity(), error->{
            if (TextUtils.isEmpty(error)){
                tv_title_error.setVisibility(View.GONE);
            }else {
                tv_title_error.setVisibility(View.VISIBLE);
                tv_title_error.setText(error);
            }
        });
        viewModelQuote.companyError.observe(requireActivity(), error->{
            if (TextUtils.isEmpty(error)){
                tv_company_error.setVisibility(View.GONE);
            }else {
                tv_company_error.setVisibility(View.VISIBLE);
                tv_company_error.setText(error);
            }
        });
        viewModelQuote.stateError.observe(requireActivity(), error->{
            if (TextUtils.isEmpty(error)){
                tv_state_error.setVisibility(View.GONE);
            }else {
                tv_state_error.setVisibility(View.VISIBLE);
                tv_state_error.setText(error);
            }
        });
        viewModelQuote.addressError.observe(requireActivity(), error->{
            if (TextUtils.isEmpty(error)){
                tv_address_error.setVisibility(View.GONE);
            }else {
                tv_address_error.setVisibility(View.VISIBLE);
                tv_address_error.setText(error);
            }
        });
    }

    private void getTinhTrang() {
        String[] state_list = {"Đã gửi cho khách hàng", "Khách hàng đã xem", "Đang thương lượng", "Chấp nhận", "Từ chối"};
        ArrayAdapter<String> AdapterRevenue = new ArrayAdapter<>(requireContext(), android.R.layout.simple_list_item_1, state_list);
        actTinhTrang.setAdapter(AdapterRevenue);
    }

    private void getContactPerson() {
        List<CaNhan> canhanList = caNhanRepository.getAllCaNhan();

        ArrayAdapter<CaNhan> adapter = new ArrayAdapter<>(requireActivity(),
                android.R.layout.simple_dropdown_item_1line, canhanList){
            @NonNull
            @Override
            public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
                TextView tv =  (TextView) super.getView(position, convertView, parent);
                String full_name = canhanList.get(position).getHoVaTen() + " " + canhanList.get(position).getTen();
                tv.setText(full_name);
                return tv;
            }
        };
        actNguoiLienHe.setAdapter(adapter);
        actNguoiLienHe.setOnItemClickListener(((parent, view, position, id) -> {
            CaNhan selected = canhanList.get(position);
            viewModelQuote.ContactPersonID.setValue(selected.getId());
            viewModelQuote.ContactPersonName.setValue(selected.getHoVaTen() + " " + selected.getTen());
        }));
    }

    private void getGiaoCho() {
        Executor executor = Executors.newSingleThreadExecutor();
        Handler mainHandler = new Handler(Looper.getMainLooper());
        executor.execute(()->{
            NhanVienRepository nhanVienRepository = new NhanVienRepository(requireContext());
            nhanVienRepository.AddNhanVien();
            List<Nhanvien> Employees_list = nhanVienRepository.getAllNhanVien();
            mainHandler.post(()->{
                ArrayAdapter<Nhanvien> AdapterEmployer =
                        new ArrayAdapter<>(requireContext(),
                                android.R.layout.simple_list_item_1,
                                Employees_list);
                actGiaoCho.setAdapter(AdapterEmployer);
                actGiaoCho.setOnItemClickListener(((parent, view1, position, id) -> {
                    Nhanvien nv = (Nhanvien) parent.getItemAtPosition(position);
                    viewModelQuote.SendtoID.setValue(nv.getId());
                    viewModelQuote.SendtoName.setValue(nv.getHoten());
                }));
            });
        });
    }

    private void getOpportunity() {
        Executor executor = Executors.newSingleThreadExecutor();
        Handler mainHandler = new Handler(Looper.getMainLooper());
        executor.execute(()->{
            opportunityDAO= new OpportunityDAO(requireContext());
            List<Opportunity> opportunityList = opportunityDAO.getAll();
            mainHandler.post(()->{
                ArrayAdapter<Opportunity> AdapterEmployer =
                        new ArrayAdapter<>(requireContext(),
                                android.R.layout.simple_list_item_1,
                                opportunityList);
                actCoHoi.setAdapter(AdapterEmployer);
                actCoHoi.setOnItemClickListener(((parent, view1, position, id) -> {
                    Opportunity opportunity = (Opportunity) parent.getItemAtPosition(position);
                    viewModelQuote.OpportunityName.setValue(opportunity.getTitle());
                    viewModelQuote.OpportunityID.setValue(opportunity.getId());
                }));
            });
        });
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
