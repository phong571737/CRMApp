package com.example.crmmobile.QuoteDirectory;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProvider;

import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.example.crmmobile.DataBase.CaNhanRepository;
import com.example.crmmobile.DataBase.NhanVienRepository;
import com.example.crmmobile.IndividualDirectory.CaNhan;
import com.example.crmmobile.LeadDirectory.Nhanvien;
import com.example.crmmobile.MainDirectory.InitClass;
import com.example.crmmobile.R;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

public class DetailQuote extends Fragment {
    private LinearLayout ll_root, ll_quote_information, ll_chance, ll_address;
    private View section;
    private TextView tv_title, tv_company, tv_opportunity, tv_state,
            tv_ship_address, tv_district_ship, tv_province_ship, tv_ship_nation;
    private ImageView iv_addinfor, iv_relative_add, iv_address_add;
    private CreateQuoteViewModel viewModel;
    private CaNhanRepository caNhanRepository;
    private CaNhan caNhan;
    private Quote quote;

    public DetailQuote() {
        // Required empty public constructor
    }

    public static DetailQuote newInstance(String param1, String param2) {
        DetailQuote fragment = new DetailQuote();
        Bundle args = new Bundle();

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
        View view = inflater.inflate(R.layout.fragment_detail_quote, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initViews(view);
        viewModel = new ViewModelProvider(requireActivity()).get(CreateQuoteViewModel.class);
        caNhanRepository = new CaNhanRepository(requireContext());

        bindLiveDataToTextView(viewModel.CompanyName, tv_company);
        bindLiveDataToTextView(viewModel.QuoteName, tv_title);
        bindLiveDataToTextView(viewModel.OpportunityName, tv_opportunity);
        bindLiveDataToTextView(viewModel.State, tv_state);
        bindLiveDataToTextView(viewModel.address_Ship, tv_ship_address);
        bindLiveDataToTextView(viewModel.district_ship, tv_district_ship);
        bindLiveDataToTextView(viewModel.province_ship, tv_province_ship);
        bindLiveDataToTextView(viewModel.nation_ship, tv_ship_nation);

        setupaddInfor(iv_addinfor, ll_quote_information);
        setupaddInfor(iv_relative_add, ll_chance);
        setupaddInfor(iv_address_add, ll_address);
    }

    private void initViews(View view) {
        ll_root = view.findViewById(R.id.ll_root);
        iv_addinfor = view.findViewById(R.id.iv_addinfor);
        iv_relative_add = view.findViewById(R.id.iv_relative_add);
        tv_title = view.findViewById(R.id.tv_title);
        tv_company = view.findViewById(R.id.tv_company);
        tv_opportunity = view.findViewById(R.id.tv_opportunity);
        ll_quote_information = view.findViewById(R.id.ll_quote_information);
        ll_chance = view.findViewById(R.id.ll_chance);
        tv_state = view.findViewById(R.id.tv_state);
        iv_address_add = view.findViewById(R.id.iv_address_add);
        ll_address = view.findViewById(R.id.ll_address);
        tv_ship_address = view.findViewById(R.id.tv_ship_address);
        tv_district_ship = view.findViewById(R.id.tv_district_ship);
        tv_province_ship = view.findViewById(R.id.tv_province_ship);
        tv_ship_nation = view.findViewById(R.id.tv_ship_nation);
    }

    private void bindLiveDataToTextView(MutableLiveData<String> liveData, TextView tv) {
        liveData.observe(getViewLifecycleOwner(), v -> {
            tv.setText(v);
        });
    }

    private void setupaddInfor(ImageView iv, LinearLayout cl){
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