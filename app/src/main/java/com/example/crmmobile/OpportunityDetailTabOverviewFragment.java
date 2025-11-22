package com.example.crmmobile;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
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
        return inflater.inflate(R.layout.fragment_opportunity_detail_tab_overview, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        // 🔹 Hoạt động đã lên lịch
        ImageView ivOpportunity = view.findViewById(R.id.iv_scheduled_activities_toggle);
        LinearLayout layoutOpportunity = view.findViewById(R.id.layout_empty_activities);
        setupToggle(ivOpportunity, layoutOpportunity);

        // 🔹 Comment
        ImageView ivOpportunity2 = view.findViewById(R.id.iv_comment_toggle);
        LinearLayout layoutOpportunity2 = view.findViewById(R.id.layout_comment_content);
        setupToggle(ivOpportunity2, layoutOpportunity2);


    }

    private void setupToggle(ImageView toggleIcon, LinearLayout contentLayout) {
        if (toggleIcon == null || contentLayout == null) return;

        // Ban đầu hiển thị
        contentLayout.setVisibility(View.VISIBLE);
        toggleIcon.setImageResource(R.drawable.ic_arrow_up);

        toggleIcon.setOnClickListener(v -> {
            boolean isVisible = contentLayout.getVisibility() == View.VISIBLE;
            contentLayout.setVisibility(isVisible ? View.GONE : View.VISIBLE);
            toggleIcon.setImageResource(isVisible ? R.drawable.ic_arrow_down : R.drawable.ic_arrow_up);
        });
    }

}
