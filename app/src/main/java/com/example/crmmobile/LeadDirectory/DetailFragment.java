package com.example.crmmobile.LeadDirectory;

import static android.content.Intent.getIntent;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.crmmobile.AppConstant;
import com.example.crmmobile.R;

public class DetailFragment extends Fragment {
    private ViewModelLead viewModelLead;
    private ImageView iv_addinforlead;
    private ImageView iv_addinforcompany;
    private ImageView iv_addinforaddress;
    private ImageView iv_addinfordescript;
    private ConstraintLayout cl_inforlead;
    private ConstraintLayout cl_addinforcompany;
    private ConstraintLayout cl_addinforaddress;
    private ConstraintLayout cl_addinfordescript;
    private TextView tv_full_name, tv_first_name, tv_sex, tv_birthday, tv_phone,
            tv_email, tv_state, tv_revenue, tv_tax, tv_job_name,tv_address,
            tv_district, tv_province, tv_nation, tv_description, tv_company,
            tv_number_of_employees, tv_special_notes;

    public DetailFragment() {
        // Required empty public constructor
    }

    public static DetailFragment newInstance(Lead lead) {
        DetailFragment fragment = new DetailFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
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
        View view =  inflater.inflate(R.layout.fragment_detail_lead, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initVariables(view);
        viewModelLead = new ViewModelProvider(requireActivity()).get(ViewModelLead.class);

        bindLiveDataToTextView(viewModelLead.hovatendem, tv_full_name);
        bindLiveDataToTextView(viewModelLead.first_name, tv_first_name);
        bindLiveDataToTextView(viewModelLead.Sex, tv_sex);
        bindLiveDataToTextView(viewModelLead.Birthday, tv_birthday);
        bindLiveDataToTextView(viewModelLead.Email, tv_email);
        bindLiveDataToTextView(viewModelLead.phonenumber, tv_phone);
        bindLiveDataToTextView(viewModelLead.state, tv_state);
        bindLiveDataToTextView(viewModelLead.Tax, tv_tax);
        bindLiveDataToTextView(viewModelLead.Job, tv_job_name);
        bindLiveDataToTextView(viewModelLead.Address, tv_address);
        bindLiveDataToTextView(viewModelLead.District, tv_district);
        bindLiveDataToTextView(viewModelLead.Province, tv_province);
        bindLiveDataToTextView(viewModelLead.Nation, tv_nation);
        bindLiveDataToTextView(viewModelLead.Revenue, tv_revenue);
        bindLiveDataToTextView(viewModelLead.description, tv_description);
        bindLiveDataToTextView(viewModelLead.company, tv_company);
        bindLiveDataToTextView(viewModelLead.number_of_employees, tv_number_of_employees);
        bindLiveDataToTextView(viewModelLead.Note, tv_special_notes);

        setupaddInfor(iv_addinforaddress, cl_addinforaddress);
        setupaddInfor(iv_addinforlead, cl_inforlead);
        setupaddInfor(iv_addinforcompany, cl_addinforcompany);
        setupaddInfor(iv_addinfordescript, cl_addinfordescript);
    }

    private void bindLiveDataToTextView(MutableLiveData<String> liveData, TextView tv) {
        liveData.observe(getViewLifecycleOwner(), v -> {
            tv.setText(v);
        });
    }

    private void initVariables(View view) {
        tv_full_name = view.findViewById(R.id.tv_full_name);
        tv_first_name = view.findViewById(R.id.tv_first_name);
        tv_sex = view.findViewById(R.id.tv_sex);
        tv_birthday = view.findViewById(R.id.tv_birthday);
        tv_phone = view.findViewById(R.id.tv_phone);
        tv_email = view.findViewById(R.id.tv_email);
        tv_state = view.findViewById(R.id.tv_state);
        tv_revenue = view.findViewById(R.id.tv_revenue);
        tv_tax = view.findViewById(R.id.tv_tax);
        tv_job_name = view.findViewById(R.id.tv_job_name);
        tv_address = view.findViewById(R.id.tv_address);
        tv_district = view.findViewById(R.id.tv_district);
        tv_province = view.findViewById(R.id.tv_province);
        tv_nation = view.findViewById(R.id.tv_nation);
        tv_description = view.findViewById(R.id.tv_description);
        tv_company = view.findViewById(R.id.tv_company);
        tv_number_of_employees = view.findViewById(R.id.tv_number_of_employees);
        tv_special_notes = view.findViewById(R.id.tv_special_notes);

        iv_addinforlead = view.findViewById(R.id.iv_addinfor);
        cl_inforlead = view.findViewById(R.id.cl_infor_lead);
        iv_addinforcompany = view.findViewById(R.id.iv_add_company_infor);
        cl_addinforcompany = view.findViewById(R.id.cl_company_infor);
        iv_addinforaddress = view.findViewById(R.id.iv_add_address_infor);
        cl_addinforaddress = view.findViewById(R.id.cl_address_infor);
        iv_addinfordescript = view.findViewById(R.id.iv_descriptive_infor);
        cl_addinfordescript = view.findViewById(R.id.cl_descript_infor);
    }

    private void setupaddInfor(ImageView iv, ConstraintLayout cl){
        iv.setOnClickListener(v -> {
            if(cl.getVisibility() == View.GONE){
                cl.setVisibility(View.VISIBLE);
                iv.setImageResource(R.drawable.ic_arrow_up);

            }
            else{
                cl.setVisibility(View.GONE);
                iv.setImageResource(R.drawable.ic_arrow_down);
            }
        });
    }
}