package com.example.crmmobile.OpportunityDirectory;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.example.crmmobile.R;

public class OpportunityDetailTabInfoFragment extends Fragment {
    private Opportunity opportunity;

    public static OpportunityDetailTabInfoFragment newInstance(Opportunity opportunity) {
        OpportunityDetailTabInfoFragment fragment = new OpportunityDetailTabInfoFragment();
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
        return inflater.inflate(R.layout.fragment_opportunity_detail_tab_info, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        // 🔹 Thông tin Cơ hội
        ImageView ivOpportunity = view.findViewById(R.id.iv_opportunity_info_toggle);
        LinearLayout layoutOpportunity = view.findViewById(R.id.layout_opportunity_info_content);
        setupToggle(ivOpportunity, layoutOpportunity);

        // 🔹 Thông tin Mô tả
        ImageView ivDescription = view.findViewById(R.id.iv_description_toggle);
        LinearLayout layoutDescription = view.findViewById(R.id.layout_description_content);
        setupToggle(ivDescription, layoutDescription);

        // 🔹 Thông tin Quản lý
        ImageView ivManagement = view.findViewById(R.id.iv_management_toggle);
        LinearLayout layoutManagement = view.findViewById(R.id.layout_management_content);
        setupToggle(ivManagement, layoutManagement);

        // 🔹 Thông tin Hệ thống
        ImageView ivSystem = view.findViewById(R.id.iv_system_info_toggle);
        LinearLayout layoutSystem = view.findViewById(R.id.layout_system_info_content);
        setupToggle(ivSystem, layoutSystem);
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