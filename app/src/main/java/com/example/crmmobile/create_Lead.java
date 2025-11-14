package com.example.crmmobile;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;

public class create_Lead extends Fragment {

    ImageButton back_btn;
    private TabLayout tabLayout;
    private ViewPager2 viewPager2;

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
        back_btn = view.findViewById(R.id.btn_lead_back);
        tabLayout = view.findViewById(R.id.tablayout);
        viewPager2 = view.findViewById(R.id.vp_tab);
        AdapterCreateLead adapterCreate = new AdapterCreateLead(this);
        viewPager2.setAdapter(adapterCreate);

        new TabLayoutMediator(tabLayout, viewPager2,((tab, i) -> {
            if(i == 0) tab.setText("Thông tin lead");
            else tab.setText("Thông tin khác");
        })).attach();

        back_btn.setOnClickListener(v -> {
            ViewPager2 viewPager = requireActivity().findViewById(R.id.viewPager);
            BottomNavigationView navFooter = requireActivity().findViewById(R.id.nav_footer);
            FrameLayout contain = requireActivity().findViewById(R.id.main_container);

            viewPager.setVisibility(View.VISIBLE);
            navFooter.setVisibility(View.VISIBLE);
            contain.setVisibility(View.GONE);

            navFooter.setSelectedItemId(R.id.nav_lead);
            viewPager.setCurrentItem(1, false);

            requireActivity().getSupportFragmentManager().popBackStack();
        });
        return view;
    }
}