package com.example.crmmobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class OpportunityDetailTabOverviewFragment extends Fragment {

    private Opportunity opportunity;
    private TextView tvTitle, tvPrice, tvDate, tvStatus, tvCallCount, tvMessageCount, tvExchange;

    public static OpportunityDetailTabOverviewFragment newInstance(Opportunity opportunity) {
        OpportunityDetailTabOverviewFragment fragment = new OpportunityDetailTabOverviewFragment();
        Bundle args = new Bundle();
        args.putSerializable("opportunity", opportunity);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            opportunity = (Opportunity) getArguments().getSerializable("opportunity");
        }
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_opportunity_detail_tab_overview, container, false);

        // Ánh xạ các view - QUAN TRỌNG: BỎ COMMENT
        tvTitle = view.findViewById(R.id.tv_opportunity_title);
        tvPrice = view.findViewById(R.id.tv_opportunity_price);
        tvDate = view.findViewById(R.id.tv_opportunity_date);
        tvStatus = view.findViewById(R.id.tv_opportunity_status);
        tvCallCount = view.findViewById(R.id.tv_call_count);
        tvMessageCount = view.findViewById(R.id.tv_message_count);
        tvExchange = view.findViewById(R.id.tv_opportunity_exchange);

        // Hiển thị dữ liệu
        if (opportunity != null) {
            displayOpportunityData();
        }

        return view;
    }

    private void displayOpportunityData() {
        if (tvTitle != null) tvTitle.setText(opportunity.getTitle());
        if (tvPrice != null) tvPrice.setText(opportunity.getPrice());
        if (tvDate != null) tvDate.setText(opportunity.getDate());
        if (tvStatus != null) tvStatus.setText(opportunity.getStatus());
        if (tvCallCount != null) tvCallCount.setText(opportunity.getCallCount() + " cuộc gọi");
        if (tvMessageCount != null) tvMessageCount.setText(opportunity.getMessageCount() + " tin nhắn");
        if (tvExchange != null) tvExchange.setText(opportunity.getExchangeText());
    }
}
