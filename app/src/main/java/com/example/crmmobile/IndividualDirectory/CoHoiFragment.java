package com.example.crmmobile.IndividualDirectory;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.crmmobile.Adapter.AdapterOpportunity;
import com.example.crmmobile.IndividualDirectory.CaNhan;
import com.example.crmmobile.OpportunityDirectory.Opportunity;
import com.example.crmmobile.OpportunityDirectory.OpportunityDetailActivity;
import com.example.crmmobile.OpportunityDirectory.OpportunityRepository;
import com.example.crmmobile.R;

import java.util.ArrayList;
import java.util.List;

public class CoHoiFragment extends Fragment {

    private RecyclerView rvCoHoi;
    private TextView tvEmpty;
    private AdapterOpportunity adapter;
    private List<Opportunity> opportunityList;
    private OpportunityRepository opportunityRepository;
    private CaNhan caNhan;

    public CoHoiFragment() {
        // Constructor rỗng là bắt buộc
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_co_hoi, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initViews(view);

        // Lấy CaNhan từ Bundle
        if (getArguments() != null && getArguments().containsKey("CANHAN_DATA")) {
            caNhan = (CaNhan) getArguments().getSerializable("CANHAN_DATA");
        }

        opportunityRepository = OpportunityRepository.getInstance(requireContext());
        opportunityList = new ArrayList<>();

        // Setup RecyclerView
        adapter = new AdapterOpportunity(
                opportunityList,
                (item, id, anchor) -> {
                    // Menu click - có thể xử lý nếu cần
                },
                (item, id) -> {
                    // Item click - mở chi tiết cơ hội
                    Intent intent = new Intent(requireContext(), OpportunityDetailActivity.class);
                    intent.putExtra("OPPORTUNITY_ID", id);
                    startActivity(intent);
                }
        );

        rvCoHoi.setLayoutManager(new LinearLayoutManager(requireContext()));
        rvCoHoi.setAdapter(adapter);

        loadCoHoi();
    }

    private void initViews(View view) {
        rvCoHoi = view.findViewById(R.id.rvCoHoi);
        tvEmpty = view.findViewById(R.id.tvEmpty);
    }

    private void loadCoHoi() {
        if (caNhan == null || caNhan.getId() <= 0) {
            // Nếu không có CaNhan hoặc ID không hợp lệ, hiển thị empty state
            opportunityList.clear();
            adapter.setData(opportunityList);
            updateEmptyState();
            return;
        }

        // Lấy cơ hội theo contact ID
        List<Opportunity> listFromDB = opportunityRepository.getByContactId(caNhan.getId());
        opportunityList.clear();
        opportunityList.addAll(listFromDB);
        adapter.setData(opportunityList);
        updateEmptyState();
    }

    private void updateEmptyState() {
        if (opportunityList.isEmpty()) {
            tvEmpty.setVisibility(View.VISIBLE);
            rvCoHoi.setVisibility(View.GONE);
        } else {
            tvEmpty.setVisibility(View.GONE);
            rvCoHoi.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        loadCoHoi();
    }
}

