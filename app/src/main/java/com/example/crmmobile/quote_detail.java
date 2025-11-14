package com.example.crmmobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TableLayout;

import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class quote_detail extends Fragment {
    ImageView iv_back;
    TabLayout tabLayout;
    ViewPager2 viewPager2;

    public quote_detail() {}

    public static quote_detail newInstance() {
        quote_detail fragment = new quote_detail();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState){
        super.onViewCreated(view, savedInstanceState);
        iv_back = view.findViewById(R.id.iv_back);
        tabLayout = view.findViewById(R.id.tabLayout);
        viewPager2 = view.findViewById(R.id.vp_tab);

        AdapterQuoteTab adapterTab = new AdapterQuoteTab(this);
        viewPager2.setAdapter(adapterTab);

        new TabLayoutMediator(tabLayout, viewPager2,((tab, position) ->{
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
        } )).attach();

        //back to quote layout
        iv_back.setOnClickListener(v -> {
            requireActivity().finish();
        });
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_quote_detail, container, false);
        return view;
    }
}