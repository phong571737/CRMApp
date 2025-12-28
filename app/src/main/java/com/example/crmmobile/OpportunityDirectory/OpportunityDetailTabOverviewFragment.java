package com.example.crmmobile.OpportunityDirectory;

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
import androidx.lifecycle.ViewModelProvider;

import com.example.crmmobile.OpportunityDirectory.Opportunity;
import com.example.crmmobile.R;

public class OpportunityDetailTabOverviewFragment extends Fragment {

    private int opportunityId;
    private OpportunityDetailViewModel detailVM;

    private TextView tvTitle, tvPrice, tvDate, tvStatus, tvCallCount, tvMessageCount, tvExchange;

    public static OpportunityDetailTabOverviewFragment newInstance(int opportunityId) {
        OpportunityDetailTabOverviewFragment fragment = new OpportunityDetailTabOverviewFragment();
        Bundle args = new Bundle();
        args.putInt("opportunity_id", opportunityId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            opportunityId = getArguments().getInt("opportunity_id");
        }

        //LẤY ViewModel CHUNG VỚI ACTIVITY
        detailVM = new ViewModelProvider(requireActivity())
                .get(OpportunityDetailViewModel.class);
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
//        ImageView ivOpportunity2 = view.findViewById(R.id.iv_comment_toggle);
//        LinearLayout layoutOpportunity2 = view.findViewById(R.id.layout_comment_content);
//        setupToggle(ivOpportunity2, layoutOpportunity2);

        detailVM.getOpportunity().observe(
                getViewLifecycleOwner(),
                opportunity -> {
                    if (opportunity != null) {
                        bindData(view, opportunity);
                    }
                }
        );

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



    private void bindData(View view, Opportunity o) {
//        TextView tvTitle = view.findViewById(R.id.tv_title);
//        TextView tvPrice = view.findViewById(R.id.tv_price);
//        TextView tvStatus = view.findViewById(R.id.tv_status);
//
//        tvTitle.setText(o.getName());
//        tvPrice.setText(String.valueOf(o.getAmount()));
//        tvStatus.setText(o.getStatus());
    }


}
