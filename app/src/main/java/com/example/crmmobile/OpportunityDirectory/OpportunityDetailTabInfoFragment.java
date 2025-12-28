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

public class OpportunityDetailTabInfoFragment extends Fragment {
    private int opportunityId;
    private OpportunityDetailViewModel detailVM;

    public static OpportunityDetailTabInfoFragment newInstance(int opportunityId) {
        OpportunityDetailTabInfoFragment fragment = new OpportunityDetailTabInfoFragment();
        Bundle args = new Bundle();
        args.putInt("opportunity_id", opportunityId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //LẤY ViewModel CHUNG VỚI ACTIVITY
        detailVM = new ViewModelProvider(requireActivity())
                .get(OpportunityDetailViewModel.class);
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

        detailVM.getUI().observe(getViewLifecycleOwner(), ui -> {
            if (ui != null) {
                bindData(view, ui);
            }
        });


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

    private String formatCurrency(double amount) {
        return String.format("%,.0f đ", amount);
    }

    private void bindData(View view, OpportunityDetailUI ui) {

        if (ui == null) return;

        // ===== Header =====
        TextView tvTitle = view.findViewById(R.id.tv_company_description_value);
        tvTitle.setText(ui.title != null ? ui.title : "-");

        // ===== Thông tin cơ hội =====
        TextView tvCompanyValue = view.findViewById(R.id.tv_company_value);
        TextView tvContactValue = view.findViewById(R.id.tv_contact_value);
        TextView tvOpportunityValue = view.findViewById(R.id.tv_opportunity_value);
        TextView tvOpportunityStatus = view.findViewById(R.id.tv_opportunity_status);
        TextView tvCloseDateValue = view.findViewById(R.id.tv_close_date_value);

        TextView tvCreatedAt = view.findViewById(R.id.tv_created_date_value);
        TextView tvUpdatedAt = view.findViewById(R.id.tv_modified_date_value);

        tvCompanyValue.setText(
                ui.companyName != null ? ui.companyName : "-"
        );

        tvContactValue.setText(
                ui.contactName != null ? ui.contactName : "-"
        );

        tvOpportunityValue.setText(formatCurrency(ui.price));

        tvOpportunityStatus.setText(
                ui.status != null ? ui.status : "-"
        );

        tvCloseDateValue.setText(
                ui.date != null && !ui.date.isEmpty() ? ui.date : "-"
        );

        // ===== Mô tả =====
        TextView tvDescriptionContent = view.findViewById(R.id.tv_description_content);
        tvDescriptionContent.setText(
                ui.description != null && !ui.description.isEmpty()
                        ? ui.description
                        : "Không có mô tả"
        );

        // ===== Quản lý =====
        TextView tvAssignedToValue = view.findViewById(R.id.tv_assigned_to_value);
        tvAssignedToValue.setText(
                ui.managementName != null ? ui.managementName : "-"
        );

        // ===== Hệ thống (nếu chưa có backend) =====
//        ((TextView) view.findViewById(R.id.tv_created_date_value)).setText("-");
//        ((TextView) view.findViewById(R.id.tv_modified_date_value)).setText("-");
        tvCreatedAt.setText(ui.createdAt);
        tvUpdatedAt.setText(ui.updatedAt);

    }
}