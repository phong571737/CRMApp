package com.example.crmmobile;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.viewpager2.widget.ViewPager2;

import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class leadFragment extends Fragment {
    RecyclerView recyclerLead;
    AdapterLead adapter;
    List<Lead> leadList;

    FloatingActionButton lead_create_button;

    BottomNavigationView bottomNavigationView;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        View view = inflater.inflate(R.layout.fragment_lead, container, false);

        recyclerLead = view.findViewById(R.id.LeadRecycler);
        lead_create_button = view.findViewById(R.id.btn_add_lead);

        recyclerLead.setLayoutManager(new LinearLayoutManager(getContext()));
        leadList = new ArrayList<>();
        leadList.add(new Lead("Nguyễn Văn A", "Công ty X", "21/07/2024"));
        leadList.add(new Lead("Trần Thị B", "Công ty Y", "22/07/2024"));
        leadList.add(new Lead("Lê Văn C", "Công ty Z", "23/07/2024"));
        leadList.add(new Lead("Nguyễn Văn D", "Công ty X", "21/07/2024"));
        leadList.add(new Lead("Trần Thị E", "Công ty Y", "22/07/2024"));
        leadList.add(new Lead("Lê Văn F", "Công ty Z", "23/07/2024"));

        lead_create_button.setOnClickListener(v -> {
            Fragment createFragment = new create_Lead();
            BottomNavigationView navFooter = requireActivity().findViewById(R.id.nav_footer);
            FrameLayout contain = requireActivity().findViewById(R.id.main_container);
            ViewPager2 viewPager = requireActivity().findViewById(R.id.viewPager);

            viewPager.setVisibility(View.GONE);
            navFooter.setVisibility(View.GONE);
            contain.setVisibility(View.VISIBLE);

            requireActivity().getSupportFragmentManager()
                    .beginTransaction()
                    .replace(R.id.main_container, createFragment)
                    .addToBackStack(null)
                    .commit();
        });

        adapter = new AdapterLead(leadList, (item, position) -> {
            BottomSheetActionLead.ShowBottomSheetLead(requireContext(), item, position);
        }, lead -> {
            Intent intent = new Intent(getContext(), DetailLeadActivity.class);
            intent.putExtra("name", lead.getName());
            intent.putExtra("company", lead.getCompany());
            intent.putExtra("daycontact", lead.getDaycontact());
            startActivity(intent);
        });
        recyclerLead.setAdapter(adapter);
        return view;
    }
}
