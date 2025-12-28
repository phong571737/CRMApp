package com.example.crmmobile.LeadDirectory;

import android.nfc.Tag;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.crmmobile.Adapter.AdapterCreateLead;
import com.example.crmmobile.AppConstant;
import com.example.crmmobile.DataBase.LeadRepository;
import com.example.crmmobile.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class create_Lead extends Fragment {
    private static final String TAG = "CREATE_LEAD";
    private ImageButton back_btn;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;
    private MaterialButton btnSave, btnAbort;
    private ViewModelLead viewModelLead;
    
    public create_Lead() {
        // Required empty public constructor
    }

    public static create_Lead newInstance(String param1, String param2) {
        create_Lead fragment = new create_Lead();

        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_create_lead, container, false);
        viewModelLead = new ViewModelProvider(requireActivity()).get(ViewModelLead.class);
        initViews(view);
        AdapterCreateLead adapterCreate = new AdapterCreateLead(this);
        viewPager2.setAdapter(adapterCreate);

        new TabLayoutMediator(tabLayout, viewPager2,((tab, i) -> {
            if(i == 0) tab.setText("Thông tin lead");
            else tab.setText("Thông tin khác");
        })).attach();

        btnSave.setOnClickListener(v -> saveCreateLead());

        back_btn.setOnClickListener(v -> {
            ViewPager2 viewPager = requireActivity().findViewById(R.id.viewPager);
            BottomNavigationView navFooter = requireActivity().findViewById(R.id.nav_footer);
            FrameLayout contain = requireActivity().findViewById(R.id.main_container);

            viewPager.setVisibility(View.VISIBLE);
            navFooter.setVisibility(View.VISIBLE);
            contain.setVisibility(View.GONE);

            requireActivity().getSupportFragmentManager().popBackStack();
        });
        return view;
    }

    private void initViews(View view) {
        back_btn = view.findViewById(R.id.btn_lead_back);
        tabLayout = view.findViewById(R.id.tablayout);
        viewPager2 = view.findViewById(R.id.vp_tab);
        btnSave = view.findViewById(R.id.save_button);
        btnAbort = view.findViewById(R.id.abort_button);
    }

    private void saveCreateLead() {
        String hovatendem = viewModelLead.hovatendem.getValue();
        String first_name = viewModelLead.first_name.getValue();
        String company = viewModelLead.company.getValue();
        String title = viewModelLead.title.getValue();
        String phone_number = viewModelLead.phonenumber.getValue();
        String job = viewModelLead.Job.getValue();
        String SendtoName = viewModelLead.SendtoName.getValue();
        Integer SentoID = viewModelLead.SendtoID.getValue();
        String Email = viewModelLead.Email.getValue();
        String Sex = viewModelLead.Sex.getValue();
        String contact_day = viewModelLead.contact_day.getValue();
        String birthday = viewModelLead.Birthday.getValue();
        String Address = viewModelLead.Address.getValue();
        String Province = viewModelLead.Province.getValue();
        String Nation = viewModelLead.Nation.getValue();
        String District = viewModelLead.District.getValue();
        String position_company = viewModelLead.position_company.getValue();
        String state = viewModelLead.state.getValue();
        String Tax = viewModelLead.Tax.getValue();
        String Number_of_Employees = viewModelLead.number_of_employees.getValue();
        String Revenue = viewModelLead.Revenue.getValue();
        String description = viewModelLead.description.getValue();
        Integer Created_by_ID = viewModelLead.CreatedByID.getValue();
        String evaluation = viewModelLead.Evaluate.getValue();
        String note = viewModelLead.Note.getValue();

        LeadRepository db = new LeadRepository(requireContext());
        if(TextUtils.isEmpty(first_name)){
            Toast.makeText(getContext(), "Vui lòng nhập tên.", Toast.LENGTH_SHORT).show();
        }
        if (TextUtils.isEmpty(phone_number)){
            Toast.makeText(getContext(), "Vui lòng nhập số điện thoại.", Toast.LENGTH_SHORT).show();
        }
        if (SentoID == null){
            Toast.makeText(getContext(), "Vui lòng nhập giao cho.", Toast.LENGTH_SHORT).show();
            return;
        }
        if (Created_by_ID == null){
            Toast.makeText(getContext(), "Vui lòng chọn người tạo.", Toast.LENGTH_SHORT).show();
            return;
        }
        Lead lead = new Lead();
        lead.setHovaTendem(hovatendem);
        lead.setCongty(company);
        lead.setNganhnghe(job);
        lead.setTen(first_name);
        lead.setTitle(title);
        lead.setEmail(Email);
        lead.setDienThoai(phone_number);
        lead.setGioitinh(Sex);
        lead.setGiaocho(SendtoName);
        lead.setGiaochoID(SentoID);
        lead.setNgayLienHe(contact_day);
        lead.setNgaysinh(birthday);
        lead.setDiachi(Address);
        lead.setTinhTrang(state);
        lead.setTinh(Province);
        lead.setQuocGia(Nation);
        lead.setQuanHuyen(District);
        lead.setMota(description);
        lead.setChucvu(position_company);
        lead.setMaThue(Tax);
        lead.setSoNV(Number_of_Employees);
        lead.setDoanhThu(Revenue);
        lead.setNguoitaoID(Created_by_ID);
        lead.setGhichu(note);
        lead.setDanhgia(evaluation);

        db.addLead(lead);
        viewModelLead.clearCreateData();
        Log.e(TAG, "SendtoID = " + viewModelLead.SendtoID.getValue());
        Log.e(TAG, "Send to Name = " + viewModelLead.SendtoName.getValue());
        Log.e(TAG, "CreatedByID = " + viewModelLead.CreatedByID.getValue());

        //Thông báo cho fragment refresh dữ liệu
        Bundle result = new Bundle();
        result.putBoolean(AppConstant.REFRESH, true);
        getParentFragmentManager().setFragmentResult(AppConstant.KEY_CREATE_LEAD, result);

        Toast.makeText(getContext(), "Tạo Lead thành công", Toast.LENGTH_SHORT).show();

        ViewPager2 viewPager = requireActivity().findViewById(R.id.viewPager);
        BottomNavigationView navFooter = requireActivity().findViewById(R.id.nav_footer);
        FrameLayout contain = requireActivity().findViewById(R.id.main_container);

        viewPager.setVisibility(View.VISIBLE);
        navFooter.setVisibility(View.VISIBLE);
        contain.setVisibility(View.GONE);

        requireActivity().getSupportFragmentManager().popBackStack();
    }
}