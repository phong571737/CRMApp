package com.example.crmmobile;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class leaddetail extends Fragment {
    private String name, company, daycontact;

    public leaddetail() {}

    public static leaddetail newInstance(String name, String company, String daycontact) {
        leaddetail fragment = new leaddetail();
        Bundle args = new Bundle();
        args.putString("name", name);
        args.putString("company", company);
        args.putString("daycontact", daycontact);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_detail_lead, container, false);

    }

    @Override
    public void onViewCreated(@NonNull View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);

        //lấy data từ lead fragment
        if(getArguments() != null){
            name = getArguments().getString("name");
            company = getArguments().getString("company");
            daycontact = getArguments().getString("daycontact");
        }

        TextView tvUser = view.findViewById(R.id.tv_user);
        TextView tvcompany = view.findViewById(R.id.tv_company);
        ImageView im_back = view.findViewById(R.id.iv_back);

        //quay trở lại trang lead chính
        im_back.setOnClickListener(v->{
            requireActivity().finish();
        });
        tvUser.setText(name);
        tvcompany.setText(company);

        TabLayout tabLayout = view.findViewById(R.id.tabLayout);
        ViewPager2 viewPager2 = view.findViewById(R.id.vp_tab);

        //adapter
        AdapterTab adapterTab = new AdapterTab(this);
        viewPager2.setAdapter(adapterTab);

        new TabLayoutMediator(tabLayout, viewPager2, (tab, position)->{
            switch (position){
                case 0:
                    tab.setText("Tổng quan");
                    break;
                case 1:
                    tab.setText("Chi tiết");
                    break;
                default:
                    break;
            }
        }).attach();
    }
}