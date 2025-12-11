package com.example.crmmobile.LeadDirectory;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.viewpager2.widget.ViewPager2;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.Toast;

import com.example.crmmobile.Adapter.AdapterCreateLead;
import com.example.crmmobile.DataBase.LeadReposity;
import com.example.crmmobile.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.button.MaterialButton;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayoutMediator;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class create_Lead extends Fragment {

    ImageButton back_btn;
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
        back_btn = view.findViewById(R.id.btn_lead_back);
        tabLayout = view.findViewById(R.id.tablayout);
        viewPager2 = view.findViewById(R.id.vp_tab);
        btnSave = view.findViewById(R.id.save_button);
        btnAbort = view.findViewById(R.id.abort_button);
        AdapterCreateLead adapterCreate = new AdapterCreateLead(this);
        viewPager2.setAdapter(adapterCreate);

        viewModelLead = new ViewModelProvider(requireActivity()).get(ViewModelLead.class);

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

            navFooter.setSelectedItemId(R.id.nav_lead);
            viewPager.setCurrentItem(1, false);

            requireActivity().getSupportFragmentManager().popBackStack();
        });
        return view;
    }

    private void saveCreateLead() {
        String name = viewModelLead.name;
        String company = viewModelLead.company;
        String title = viewModelLead.title;

        LeadReposity db = new LeadReposity(requireContext());

        Lead lead = new Lead();
        lead.setHoten(viewModelLead.name);
        lead.setCongty(viewModelLead.company);

        db.addLead(lead);
        //Thông báo cho fragment refresh dữ liệu
        Bundle result = new Bundle();
        result.putBoolean("refresh", true);
        getParentFragmentManager().setFragmentResult("createleadkey", result);

        Toast.makeText(getContext(), "Lưu Lead thành công", Toast.LENGTH_SHORT).show();
        requireActivity().getSupportFragmentManager().popBackStack();
    }
}